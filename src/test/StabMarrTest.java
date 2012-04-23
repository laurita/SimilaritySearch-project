package test;

import java.util.ArrayList;

/* 
 * This class is for testing our implementation of the 
 * stable marriage against
 * an implementation that we found online. 
 */

public class StabMarrTest {
	public static boolean test(double[][] matrix) {
		boolean result = true;
		
		// create result for our implementation
		ArrayList<int[]> assignment1 = algorithms.StableMarriage.match(matrix);
		// result for imported implementation
		//ArrayList<int[]> assignment2 = external.StableMarriage.
		
		// ...
		
		return result;
	}
}
