package tests;

import java.util.ArrayList;

import util.TestTools;

/*
 * This class is for testing our implementation of the 
 * RNN algorithm
 */

public class RNNTest {
	public static boolean test(double[][] matrix) {
		boolean result = true;
		
		// create result for our implementation
		ArrayList<int[]> assignment1 = algorithms.RNN.match(matrix);		
		
		// do sanity check
		if (!TestTools.doCheck(assignment1)) {
			System.out.println("Sanity check failed for RNN");
			result = false;
		}
		
		return result;
		
	}
}
