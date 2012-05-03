package test;

import java.util.ArrayList;

public class RNNTest {
	public static boolean test(double[][] matrix) {
		boolean result = true;
		
		// create result for our implementation
		ArrayList<int[]> assignment1 = algorithms.RNN.match(matrix);		
		
		// do sanity check
		if (!SanityTest.doCheck(assignment1)) {
			System.out.println("Sanity check failed for RNN");
			result = false;
		}
		
		return result;
		
	}
}
