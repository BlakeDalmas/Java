import java.util.*;
import java.io.*;

public class Logic {
		
	public void Menu() throws FileNotFoundException{
		Main main = new Main();
		boolean valid_input = false;
		int choice = 0;
		
		if (main.matrix_loaded){
			
			while (!valid_input){
				System.out.println("\nWhat would you like to do?");
				System.out.println("1- Insert a key into the loaded matrix.");
				System.out.println("2- Save the current matrix to your computer.");
				System.out.println("3- Display the matrix.");
				System.out.println("4- Go back.\n");
				
				Scanner _1 = new Scanner(System.in);
				choice = _1.nextInt();
				
				if(choice == 1 || choice == 2 || choice == 3 || choice == 4){
					valid_input = true;
				} else{
					choice = 0;
				}
			}
			
			if (choice == 1){
				Decrypt();
			} else if (choice == 2){
				Save();
			} else if (choice == 3){
				Display();
			} else if (choice == 4){
				main.matrix_loaded = false;
				Menu();
			}
			
		} else if (!main.matrix_loaded){
			
			while (!valid_input){
				System.out.println("What would you like to do?");
				System.out.println("1- Encrypt a new word (creates a new matrix).");
				System.out.println("2- Load a matrix from your computer.");
				
				Scanner _1 = new Scanner(System.in);
				choice = _1.nextInt();
				
				if(choice == 1 || choice == 2){
					valid_input = true;
					main.matrix_loaded = true;
				} else{
					choice = 0;
				}
			}

			if (choice == 1){
				System.out.println("Type word to encrypt: ");
				
				Scanner _1 = new Scanner(System.in);
				Encrypt(_1.next());	
			} else if (choice == 2){
				Load();
			}
			
		}
	}
	
	public void Encrypt(String word) throws FileNotFoundException{
		Main main = new Main();
		String[][] tracker = new String[word.length()][5];
		int[] randomseq = new int[5];
		String[] chars = new String[word.length()];
		
		for(int c = 0; c < chars.length; c++){
			chars[c] = word.substring(c,c+1);
		}
		
		// Builds the key
 		for(int i = 0; i < word.length(); i++){
			for (int k = 0; k < randomseq.length; k++){
				randomseq[k] = (int)(Math.random() * 10);
			}
			main.matrix[randomseq[0]][randomseq[1]][randomseq[2]][randomseq[3]][randomseq[4]] = chars[i];
			
			for (int z = 0; z < randomseq.length; z++){
				tracker[i][z] = "" + randomseq[z];
			}
		}
 		
 		System.out.println("Your key is : ");
 		for(int f = 0; f < tracker.length; f++){
 			for(int s = 0; s < tracker[f].length; s++){
 				System.out.print(tracker[f][s]);
 			}
 		}
 		
 		// Fills the rest of the matrix with random characters
 		int length = main.matrix.length;
 		for(int a = 0; a < length; a++){
 			for(int b = 0; b < length; b++){
 				for(int c = 0; c < length; c++){
 					for(int d = 0; d < length; d++){
 						for(int e = 0; e < length; e++){
 							if(main.matrix[a][b][c][d][e] == null){
 								int r = (int)(48 + (Math.random() * 75));
 								char temp = (char)r;
 								String randomchar = "" + temp;
 								
 								main.matrix[a][b][c][d][e] = randomchar;
 							}
 						}
 					}
 				}
 			}
 		}
 		
 		Menu();
	}
	
	public void Decrypt() throws FileNotFoundException{
		Main main = new Main();
		
		System.out.println("\nInsert Key : ");
		Scanner _1 = new Scanner(System.in);
		String input = _1.next();
		
		int[] logic = new int[5];
		String word = "";
		int tracker = 0;
		
		// Gets the data that the key references
		for(int i = 0; i < input.length(); i++){
			logic[tracker] = Integer.parseInt("" + input.charAt(i));
			
			if(tracker > 3){
				tracker = -1;
				word = word + main.matrix[logic[0]][logic[1]][logic[2]][logic[3]][logic[4]];
			}
			tracker++;
		}
		
		System.out.println("\nThe data found with that key is : " + word);
		
		Menu();	
	}
	
	public void Display() throws FileNotFoundException{
		Main main = new Main();
 		int length = main.matrix.length;
 		
 		// Prints out the array, I formatted it to be more readable.
 		for(int a = 0; a < length; a++){
 			for(int b = 0; b < length; b++){
 				for(int c = 0; c < length; c++){
 					for(int d = 0; d < length; d++){
 						for(int e = 0; e < length; e++){
 							System.out.print(main.matrix[a][b][c][d][e]);
 							
 							if(e == (length - 1)){
 								System.out.print("|");
 							}
 						}
 						if (d == (length - 1)){
 							System.out.println();
 						}
 					}
 					if(c == (length - 1)){
 						System.out.println("--------------------------------------------------------------------------------------------------------------");
 					}
 				}
 				if(b == (length - 1)){
 					System.out.println("\nA"+a+"\n--------------------------------------------------------------------------------------------------------------");
 				}
 			}
 		}
 		Menu();
	}
	
	public void Save() throws FileNotFoundException{
		Main main = new Main();
		
		System.out.println("Type in the file path (or just type d for default)");
		System.out.println("Default Path: " + main.default_path);
		
		Scanner _1 = new Scanner(System.in);
		String input = _1.next();
		
		// Checks whether or not you want the default path, appends .txt to the end to set it to a text file.
		String path = "";
		if (input.equalsIgnoreCase("d")){
			path = main.default_path + ".txt";
		} else{
			path = input + ".txt";
		}
		
		File file = new File(path);
		System.out.println("Saving...");
		
 		int length = main.matrix.length;
 		String temp = "";
 		
 		// Puts all the data in the matrix into a single string of characters (makes no sense when you look at it but easier to program).
 		for(int a = 0; a < length; a++){
 			for(int b = 0; b < length; b++){
 				for(int c = 0; c < length; c++){
 					for(int d = 0; d < length; d++){
 						for(int e = 0; e < length; e++){
 							temp = temp + main.matrix[a][b][c][d][e];
 						}
 					}
 				}
 			}
 		}
 		
 		// Writes it to the file.
		try {	  	      		 
		    FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		  	BufferedWriter bw = new BufferedWriter(fw);
		  	bw.write("");
	 		bw.write(temp);
	 		bw.close();
	    	} catch (IOException e) {
	  	      e.printStackTrace();
	  	}
		
		System.out.println("- file saved");
		Menu();
	}
	
	public void Load() throws FileNotFoundException{
		Main main = new Main();
		
		System.out.println("Type in the location of the matrix (type d for default)");
		System.out.println("Default path: " + main.default_path);
		
		Scanner _1 = new Scanner(System.in);
		String input = _1.next();
		
		// Checks whether or not you selected the default path, appends .txt to the end.
		String path = "";
		if (input.equalsIgnoreCase("d")){
			path = main.default_path + ".txt";
		} else{
			path = input + ".txt";
		}
				
		_1 = new Scanner(new File(path));
		String data = _1.next();
		
		int length = main.matrix.length;
		int sub_tracker = 0;
		
		// Builds the matrix based off the data in the text file.
 		for(int a = 0; a < length; a++){
 			for(int b = 0; b < length; b++){
 				for(int c = 0; c < length; c++){
 					for(int d = 0; d < length; d++){
 						for(int e = 0; e < length; e++){
 							main.matrix[a][b][c][d][e] = data.substring(sub_tracker, sub_tracker + 1);
 							sub_tracker++;
 						}
 					}
 				}
 			}
 		}
		
		Menu();
	}
	
}
