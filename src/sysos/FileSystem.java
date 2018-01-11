package sysos;

import java.util.ArrayList;

class File {
	public String name;
	public int blockIndex;
	
	public File(String name_, int blockIndex_){
		name = name_;
		blockIndex = blockIndex_;
	}
}


class Catalog {
	ArrayList<File> catalog;
	
	public Catalog(){
		catalog = new ArrayList<>();
	}
}


public class FileSystem {
	private final int discSize = 1024, bitVectorSize = 32, nrOfBlocks = 32; // bo 32*32 = 1024
	final char emptySign = '^';
	char[] disc;
	boolean[] bitVector;
	Catalog root;

	public FileSystem() {
		disc = new char[discSize];
		for (int i = 0; i < discSize; i++) {
			disc[i] = emptySign;
		}
		bitVector = new boolean[bitVectorSize];
		root = new Catalog();
	}

	public void createFile(String name) {
		for (File f : root.catalog) {
			if (f.name.equals(name)) {
				System.out.println("Plik o podanej nazwie juz istnieje!");
				return;
			}
		}
		int tempIndex = assignIndex();
		if (tempIndex != -1) {
			File f = new File(name, tempIndex);
			root.catalog.add(f);
		} else {
			System.out.println("Brak wolnego bloku!");
			return;
		}
	}

	public void writeFile(String name, String content) {
		for (File f : root.catalog) {
			if (f.name.equals(name)) {
				int neededBlocks = content.length() / nrOfBlocks + 1;
				int index = 0;
				for (int i = 0; i < neededBlocks; i++) {
					int tempIndex = assignIndex();
					if (tempIndex != -1) {
						for (int j = f.blockIndex; j < f.blockIndex + nrOfBlocks; j++) {
							if (disc[j] == emptySign) {
								disc[j] = (char) tempIndex;
								for (int k = tempIndex; k < tempIndex + nrOfBlocks; k++) {
									if (index < content.length()) {
										disc[k] = content.charAt(index++);
									}
								}
								break;
							}
						}
					} else {
						System.out.println("Brak wolnego bloku!");
						return;
					}
				}
				return;
			}
		}
		System.out.println("Plik o podanej nazwie nie istnieje!");
	}

	public void readFile(String name) {
		for (File f : root.catalog) {
			if (f.name.equals(name)) {
				String data = "";
				for (int i = f.blockIndex; i < f.blockIndex + nrOfBlocks; i++) {
					if (disc[i] != emptySign) {
						int currentBlockNr = (int) disc[i];
						for (int j = currentBlockNr; j < currentBlockNr + nrOfBlocks; j++) {
							if (disc[j] != emptySign) {
								data += disc[j];
							}
						}
					}
				}
				System.out.println(data);
				return;
			}
		}
		System.out.println("Plik o podanej nazwie nie istnieje");
	}

	public void deleteFile(String name) {
		for (File f : root.catalog) {
			if (f.name.equals(name)) {
				for (int i = f.blockIndex; i < f.blockIndex + nrOfBlocks; i++) {
					if (disc[i] != emptySign) {
						int currentBlockNr = (int) disc[i];
						for (int j = currentBlockNr; j < currentBlockNr + 32; j++) {
							disc[j] = emptySign;
						}
						bitVector[currentBlockNr / nrOfBlocks] = false;
						disc[i] = emptySign;
					}
				}
				bitVector[f.blockIndex / nrOfBlocks] = false;
				root.catalog.remove(f);
				return;
			}
			System.out.println("Plik o podanej nazwie nie istnieje");
			return;
		}
	}

	public void listAllFiles() {
		for(File f : root.catalog) {
			System.out.println(f.name + " " + f.blockIndex);
		}
	}
	
	public void printFileData(String name) {
		for (File f : root.catalog) {
			if (f.name.equals(name)) {
				System.out.println(f.name + " " + f.blockIndex);
				return;
			}
		}
		System.out.println("Plik o podanej nazwie nie istnieje");
	}

	private int assignIndex() {
		for (int i = 0; i < bitVectorSize; i++) {
			if (!bitVector[i]) {
				bitVector[i] = true;
				return i * nrOfBlocks;
			}
		}
		return -1;
	}

	public void printDisc() {
		for (int i = 0; i < disc.length; i++) {
			if ((i + 1) % 128 == 0) {
				System.out.println(disc[i]);
			} else {
				System.out.print(disc[i]);
			}
		}
	}
}


