package test;

import java.util.ArrayList;

public class GlobalGreedyTest {
	public static boolean test(double[][] matrix) {
		boolean result = true;
		
		// create result for our implementation
		ArrayList<int[]> assignment1 = algorithms.GlobalGreedy.match(matrix);		
		
		// do sanity check
		if (!SanityTest.doCheck(assignment1)) {
			System.out.println("Sanity check failed for Global Greedy");
			result = false;
		}
		
		return result;
		
	}
}
