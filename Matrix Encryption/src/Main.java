import java.io.*;
import java.util.*;

public class Main {
	
	public static String[][][][][] matrix = new String[10][10][10][10][10];
	public static boolean matrix_loaded = false;
	// Set this to your desired path
	public static String default_path = "C:\\Users\\Blake\\Desktop\\matrix";
	
	public static void main(String args[]) throws FileNotFoundException{
		Logic logic = new Logic();
		System.out.println("-= Matrix Encryption =-");
		logic.Menu();			
	}
}
