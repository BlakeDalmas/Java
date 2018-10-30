/**
  	This algorithm is very similar to Counting Sort. In fact, you can almost think of it as
  	a, "Simplified Counting Sort". I believe it runs in O(n + max) where max is the value of the 
  	largest element in the array. As long as the range of elements is low enough this algorithm is very fast.
  	I devised this algorithm independently based on an idea, I didn't know counting sort existed until after
  	I made this.
 
	So for example, a sequence of 1 10000 300 50000 would be inefficient, but a sequence of
	1 4 2 3 7 4 9 would perform really well.
	
	The first thing the algorithm does is determines the largest element in the array. Then
	it makes this the max and creates an array called count with a size of max. 
	The algorithm then loops through the original array once O(n), in each iteration, it
	takes the value of the current index, uses that as an index of the count array, and increments
	that index in the count array. Then it can find the solution with the O(n + max) loop that
	essentially iterates through the count array and fills the solution array with its non-zero
	values. (If you want 0s to be included, you can just set a default value and have it check
	for that instead.)
	
	In this version, the sorting algorithm only works with non-negative integers greater than 0. 
	However, it could easily be adjusted to sort other things.
	
	@author BlakeDalmas
	@version 2.10.18.2018
*/

public class Testing {
	
	public static void main(String args[]) {		
		TestCases();
	}
		
	public static int[] ArraySort(int[] unsorted) {
		if (unsorted.length <= 1) return unsorted;
		
		// Find max value
		int max = unsorted[0];
		
		for (int i = 1; i < unsorted.length; i++) {
			if (unsorted[i] > max) max = unsorted[i];
		}
		
		// Count occurrences of each element in unsorted array in count array.
		int[] count = new int[max + 1];
		
		for (int i = 0; i < unsorted.length; i++) {
			count[unsorted[i]]++;
		}
		
		// Create solution array. O(n + max)
		int[] solution = new int[unsorted.length];
		
		int count_index = 0;
		int solution_index = 0;
		
		while (solution_index < solution.length) {
			int local_count = count[count_index];
			
			if (local_count != 0) {
				for (int j = 0; j < local_count; j++) {
					solution[solution_index] = count_index;
					solution_index++;
				}
			}
			
			count_index++;
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

// The old array sort:
/*
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
*/