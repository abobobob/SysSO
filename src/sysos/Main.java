package sysos;

import java.util.Scanner;

public class Main {
	
	public static Memory M = new Memory();
	public static process_manager P = new process_manager();
	public static FileSystem F = new FileSystem();
	public static int OBECNY_PROCES;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String string;
		do {
			System.out.println("command: ");
			
			Scanner in = new Scanner(System.in);
			string = in.nextLine();
			String[] tab = string.split(" ");
			
			
			if(tab[0].equals("DEL"))   ///////////////////////////////////////////////////////////////////usuwanie pliku
			{
				if(tab.length==2)
					F.deleteFile(tab[1]);
				else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			if(tab[0].equals("COPY")&&tab[1].equals("NULL"))  ////////////////////////////////////////////tworzenie pustego pliku
			{
				if(tab.length==3)
					F.createFile(tab[2]);
				else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			if(tab[0].equals("OVR"))  ///////////////////////////////////////////////////////////////////dopisywanie danych do pliku
			{
				if(tab.length>2)
				{
					String temp = "";
					for (int i = 2; i < tab.length; i++) {
						temp += tab[i];
						temp += " ";
					}
					F.writeFile(tab[1], temp);
					//System.out.println("dopisywanie danych do pliku");
				}
				else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			if(tab[0].equals("OPF"))   ///////////////////////////////////////////////////////////////////usuwanie pliku
			{
				if(tab.length==2)
					F.readFile(tab[1]);
				else
					System.out.println("nieprawidlowe wywolanie komendy");
			
			}
			
			if(tab[0].equals("HELP"))   ////////////////////////////////////////////////////////////////////pomoc
			{
				if(tab.length==1) {
					System.out.println("DEL nazwa_pliku: usuwanie wskazanego pliku; ");
					System.out.println("COPY NULL nazwa_pliku: tworzenie pustego pliku o wskazanej nazwie; ");
					System.out.println("OVR nazwa_pliku: dopisanie danych do danego pliku; ");
					System.out.println("OPF nazwa_pliku: czytanie wskazanego pliku; ");
					System.out.println("MEMORY: sprawdzanie stanu pamieci; ");
					System.out.println("CHKDSK: sprawdzanie zawarto�ci dysku; ");
					System.out.println("LS: wyswietlenie plikow; ");
					System.out.println("TASKLIST: sprawdzanie listy procesow; ");
					System.out.println("TASKKILL: nazwa_procesu: zabijanie procesu; ");
					System.out.println("GO: kolejny krok wykonywanego procesu; ");
					System.out.println("START nazwa_procesu grupa_procesu nazwa_pliku: stworzenie procesu; ");
					
				}else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			if(tab[0].equals("MEMORY"))   //////////////////////////////////////////////////////////////////pami�� 
			{
				if(tab.length==1) {
					
					M.printMemory();
					System.out.println("stan pamieci");
				
					
				}else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			if(tab[0].equals("CHKDSK"))   //////////////////////////////////////////////////////////////////dysk
			{
				if(tab.length==1) {
					
					//funkcja pokazuj�ca zawarto�� dysku 
					System.out.println("zawartosc dysku");
					F.printDisc();
					
				}else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			if(tab[0].equals("LS"))   //////////////////////////////////////////////////////////////////dysk
			{
				if(tab.length==1) {
					
					//funkcja pokazuj�ca zawarto�� dysku 
					System.out.println("Pliki");
					F.listAllFiles();					
				}else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			if(tab[0].equals("TASKLIST"))   ///////////////////////////////////////////////////////////////lista proces�w
			{
				if(tab.length==1) {
					
					//funkcja pokazuj�ca liste proces�w 
					System.out.println("lista procesow");
					
				}else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			if(tab[0].equals("TASKKILL"))   /////////////////////////////////////////////////////////////////zabijanie procesu
			{
				if(tab.length==2)
					//tab[1] to nazwa procesu
					
					System.out.println("zabicie procesu");
				else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			if(tab[0].equals("GO"))   /////////////////////////////////////////////////////////////////////kolejny krok w procesie
			{
				if(tab.length==1) {
					
					//stan procesu po wykonaniu jednego kroku 
					System.out.println("kolejny krok w procesie");
				
					
				}
				else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
			
			
			if(tab[0].equals("START"))   //////////////////////////////////////////////////////////////////tworzenie procesu
			{
				if(tab.length==4) {
					/*
					 tab[1] nazwa procesu
					 tab[2] grupa procesu
					 tab[3] nazwa pliku
					 */
					
					//tworzenie procesu 
					System.out.println("tworzenie procesu");
				
				}else
					System.out.println("nieprawidlowe wywolanie komendy");
			}
			
		}while(!string.equals("exit"));	
			
		
	}

}
