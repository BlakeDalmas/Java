/**
	Similar to Counting Sort, it runs in O(n + k) as long as the range of the elements
	is low enough.

	So for example, a sequence of 1 10000 300 50000 would be inefficient, but a sequence of
	1 4 2 3 7 4 9 would perform really well.
	
	The first thing the algorithm needs to do is determine the largest element in the array.
 	Then, it creates an two dimensional array [a][b].
	[a] has a length of the maximum value found earlier.
	[b] always has a length of 2.
	[a] stores the values of the unsorted array almost like a hash table where the value is
	also the index.
	[b] stores the number of times that value is repeated.
	After doing this, it simply converts this two dimensional array into the sorted one
	dimensional array. 
	
	In this version, the sorting algorithm only works with non-negative integers. 
	However, it could easily be adjusted to sort other things.
	
	@author BlakeDalmas
	@version 10.18.2018
*/

public class Testing {
	
	public static void main(String args[]) {		
		TestCases();
	}
	
	public static int[] ArraySort(int[] unsorted) {		
		// Find maximum value.
		int max_val = 0;
				
		for (int i = 0; i < unsorted.length; i++) {
			if (unsorted[i] > max_val) max_val = unsorted[i];
		}
				
		// Create two dimensional array.	
		int[][] sorted = new int[max_val + 1][2];
		// [i][0] = Value.
		// [i][1] = Number of repeats.
		
		// (Optional) Fill two dimensional array with default values.
		for (int i = 0; i < sorted.length; i++) {
			sorted[i][0] = -1;
			sorted[i][1] = 0;
		}
		
		// Put unsorted values into two dimensional array.
		for (int i = 0; i < unsorted.length; i++) {
			int current = unsorted[i];
			
			if (sorted[current][1] == 0) sorted[current][0] = current;
			sorted[current][1]++;
		}		
		
		// Convert two dimensional array into a single sorted array.
		int[] solution = new int[unsorted.length];
		int index = 0;
		int i = 0;
		
		while (i < sorted.length) {
			int current = sorted[i][0];
			
			if (current >= 0) {
				int reps = sorted[i][1];
				for (int k = 0; k < reps; k++) {
					solution[index] = current;
					index++;
				}
			}
			
			i++;
		}
				
		return solution;
	}
	
	public static void TestCases() {
		int[][] tests = {
			{1, 1, 1, 1,},
			{},
			{5, 3, 9, 10, 1, 5, 2, 3, 4, 7, 8, 5, 4, 4, 4, 1},
			{1, 2, 3, 2, 1},
			{1, 1000, 5, 10000, 50, 90000},
			{0},
			{5},
			{0, 5, 1, 0, 999}
		};
		
		System.out.println("Expected Results");
		System.out.println("1, 1, 1, 1");
		System.out.println("");
		System.out.println("1, 1, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 7, 8, 9, 10");
		System.out.println("1, 1, 2, 2, 3");
		System.out.println("1, 5, 50, 1000, 10000, 90000");
		System.out.println("0");
		System.out.println("5");
		System.out.println("0, 0, 1, 5, 999");
		
		System.out.println("\nResults:");
		
		for (int i = 0; i < tests.length; i++) {
			int[] test = tests[i];
			PrintArray(ArraySort(test));
		}
	}
	
	public static void PrintArray(int[] toprint) {
		System.out.println();
		
		for (int i = 0; i < toprint.length; i++) {
			System.out.print(toprint[i]);
			if (i < toprint.length - 1) System.out.print(", ");
		}
	}
	
}
