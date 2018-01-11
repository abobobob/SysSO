package sysos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import sysos.process_manager;
import sysos.process_manager.process;

public class Memory {

	private char[] ram; // tablica zawierajaca pamiec
	private File swap; // plik wymiany
	public FFA ffa; // tablica wolnych ramek
	public Stack<Integer> lruStack; // stos do LRU
	public int SWAP_END = 1;
	private process_manager T;
	//private process OBECNY = Main.S.runningProcess;
	//private Integer OBECNY = T.find(Main.S.runningProcess);
	//////////////////////////////// KOSNSTRUKTOR //////////////////////////

	public Memory() { // konstruktor, inicjalizacja
		T = Main.T; // Shell.T; // tutaj wskazuje na obiekt memory manager
	//	OBECNY = Main.OBECNY_PROCES; // Shell.OBECNY_PROCES; // tutaj wskazuje na inta z obecnym procesem
		
		ram = new char[128];
		for (int i = 0; i < ram.length; i++)
			ram[i] = ' ';
		ffa = new FFA();
		swap = new File("swap.txt");
		if (!swap.exists())
			try {
				swap.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		wipeSwap();
		lruStack = new Stack<>();
	}

	////////////////////////////////// PUBLICZNE///////////////////////////////////////////////////////////////////
	public void freeMemory() {
		for (int i = 0; i < 8; i++) {
			if (!ffa.ffa[i].isFree) {
				deallocateMemory(ffa.ffa[i].processID);
			}
		}
	}
	
	public void writeMemory(int l_addr, char value) {
		process current = Main.S.runningProcess;

		if (l_addr > getProgramSize(current)) {
			System.out.println("ADRES LOGICZNY WIEKSZY NIZ ROZMIAR PROGRAMU");
		}
		int strona = l_addr / 16;
		int ramka = current.pageCheck(strona);
		if (ramka != -1) {
			updateStack(ramka);
			ram[ramka * 16 + l_addr % 16] = value;
			return;
		}

		if (isRamFull()) {
			int victim = getVictim();
			T.find(ffa.checkFrame(victim)).pageDisable(ffa.checkFrame(victim));
			putPageToSwap(victim, T.find(ffa.checkFrame(victim)).swapFileBeginning * 16 + strona);
			ffa.releaseFrame(victim);
		}
		int free = findFreeFrame();
		String toPut = getPageFromSwap(current.swapFileBeginning + strona);
		for (int i = free * 16, j = 0; j < 16; i++, j++) {
			ram[i] = toPut.charAt(j);
		}
		current.pageEnable(strona, free);
		ramka = current.pageCheck(strona);
		ffa.occupyFrame(ramka, Main.S.runningProcess.id);
		updateStack(ramka);

		ram[ramka * 16 + l_addr % 16] = value;
	}

	public void allocateMemory(String program, int size) { // Wpisuje program do pliku wymiany
		process pcb = Main.S.runningProcess;
		pcb.createPageTable(size);
		pcb.swapFileBeginning = SWAP_END;
		pcb.programSize = size;
		SWAP_END += (size + 16) / 16;

		String temp = new String();
		try {
			Scanner scan = new Scanner(swap);

			temp = scan.nextLine();
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(swap));
			temp += program;
			int l = (16 * (((size / 16) + 1) - ((program.length() / 16) + 1))) + 16 - (program.length() % 16);
			for (int i = 0; i < l; i++) {
				temp += ' ';
			}
			out.write(temp);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deallocateMemory(int processID) { // zwalnia zasoby pamieci po zakonczeniu procesu
		ffa.deallocate(processID);
	}

	public String readUntilSpace(int l_addr) { // zwraca string do spacji
		String out = new String();
		for (int i = 0; l_addr + i < getProgramSize(); i++) {
			char temp = readMemory(l_addr + i);
			if (temp == ' ')
				break;
			out += temp;
		}
		return out;
	}

	//////////////////////////////// METODY_MOJE///////////////////////////////////////////////////////
	private void putPageToSwap(int victim, int place) { // umieszcza stronĂŞ w podanym miejscu w pliku wymiany
		String toPut = new String();
		for (int i = victim * 16; i < (victim * 16) + 16; i++) {
			toPut += ram[i];
		}
		String input = new String();
		try {
			Scanner scan = new Scanner(swap);
			input = scan.nextLine();
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String temp = new String();
		temp += input.substring(0, place);
		temp += toPut;
		temp += input.substring((place) + 16);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(swap));
			out.write(temp);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPageFromSwap(int pageNumber) { // zwraca stronĂŞ o danym numerze z pliku wymiany
		String out = new String();
		try {
			FileInputStream fis = new FileInputStream(swap);
			char temp;
			for (int i = 0; fis.available() > 0 && i < pageNumber * 16 + 16; i++) {
				temp = (char) fis.read();
				if (i >= pageNumber * 16)
					out += temp;
			}
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	private int findFreeFrame() { // zwraca numer wolnej ramki
		for (int i = 0; i < 8; i++) {
			if (ffa.checkFrame(i) == -1)
				return i;
		}
		return -1;
	}

	private int getVictim() { // zwraca numer ofiary LRU
		return lruStack.firstElement();
	}

	private int getProgramSize() { // zwraca rozmiar obecnego programu
		return Main.S.runningProcess.programSize;
	}

	private int getProgramSize(process p) { // zwraca rozmiar programu o podanym PCB
		return p.programSize;
	}

	private boolean isRamFull() { // sprawdza czy wszystkie ramki sa zajete
		for (int i = 0; i < 8; i++) {
			if (ffa.checkFrame(i) == -1) {
				return false;
			}
		}
		return true;
	}

	private void updateStack(int nr) { // aktualizuje stos LRU podanym numerem ramki
		int position = lruStack.search(nr);
		if (position != -1) {
			lruStack.remove(lruStack.size() - position);
			lruStack.push(nr);
		} else
			lruStack.push(nr);
	}

	private void wipeSwap() { // zeruje plik wymiany i umieszcza kod bezczynnosci
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(swap));
			out.write("1&&&&&&&3&&&&&&0"); // tutaj umiescic kod bezczynnosci
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public char readMemory(int l_addr) { // zwraca char z pamiĂŞci
		process current = Main.S.runningProcess;

		if (l_addr > getProgramSize(current)) {
			System.out.println("ADRES LOGICZNY WIEKSZY NIZ ROZMIAR PROGRAMU");
			return ' ';
		}
		int strona = l_addr / 16;
		int ramka = current.pageCheck(strona);

		if (ramka != -1) {
			updateStack(ramka);
			return ram[ramka * 16 + l_addr % 16];
		}

		if (isRamFull()) {
			int victim = getVictim();
			int f = ffa.checkFrame(victim);
			process p = T.find(f);
			p.pageDisable(victim);
			putPageToSwap(victim, p.swapFileBeginning * 16 + strona);
			ffa.releaseFrame(victim);
		}

		int free = findFreeFrame();
		String toPut = getPageFromSwap(current.swapFileBeginning + strona);
		for (int i = free * 16, j = 0; j < 16 && j < toPut.length(); i++, j++) {
			ram[i] = toPut.charAt(j);
		}
		current.pageEnable(strona, free);
		ramka = Main.S.runningProcess.pageCheck(strona);
		ffa.occupyFrame(ramka, Main.S.runningProcess.id);
		updateStack(ramka);

		int doZwrotu = ramka * 16 + l_addr % 16;
		return ram[doZwrotu];
	}
	//////////////////////////// TESTOWE ////////////////////////////////////

	public void printStack() { // wyswietla status stosu LRU
		for (int i = 0; i < lruStack.size(); i++)
			System.out.println(lruStack.get(i));
	}

	public void printMemory() { // wyswietla zawartosc pamieci
		for (int i = 0; i < 128; i++) {
			System.out.println(i + ": " + ram[i]);
		}
	}

	public void printMemory(int addr, int size) { // wyswietla konkretny przedzial pamieci
		for (int i = addr; i < 128 && i < addr + size; i++) {
			System.out.println(i + ": " + ram[i]);
		}
	}

	public void printFramesStatus() { // wyswietla status wolnych/zajetych ramek
		for (int i = 0; i < 8; i++) {
			if (ffa.isFree(i))
				System.out.println("P" + i + ": free");
			else
				System.out.println("P" + i + ": occupied");
		}
	}
	////////////////////// TABLICA_WOLNYCH_RAMEK/////////////////////////////////

	private class FFA { // Free Frames Array, Tablica wolnych ramek

		private class Pair {
			public boolean isFree = true;
			public int processID = -1;
		}

		public Pair[] ffa = new Pair[8];

		public FFA() {
			for (int i = 0; i < 8; i++) {
				ffa[i] = new Pair();
			}
		}

		public int checkFrame(int which) {
			if (ffa[which].isFree)
				return -1;
			else
				return ffa[which].processID;
		}

		public boolean isFree(int which) {
			return ffa[which].isFree;
		}

		public void occupyFrame(int which, int processID) {
			if (ffa[which].isFree) {
				ffa[which].isFree = false;
				ffa[which].processID = processID;
			} else
				System.out.println("RAMKA ZAJETA");
		}

		public void releaseFrame(int which) {
			ffa[which].isFree = true;
			ffa[which].processID = -1;
		}

		public void deallocate(int id) {
			for (int i = 0; i < 8; i++) {
				if (ffa[id].processID == id) {
					ffa[id].isFree = true;
				}
			}

		}

	}
}