package tests;

import java.util.ArrayList;

import util.TestTools;

/* 
 * This class performs some testing on our implementation of the 
 * global greedy algorithm
 */

public class GlobalGreedyTest {
	public static boolean test(double[][] matrix) {
		boolean result = true;
		
		// create result for our implementation
		ArrayList<int[]> assignment1 = algorithms.GlobalGreedy.match(matrix);		
		
		// do stability check
		if (!TestTools.isStable(assignment1, matrix)) {
			System.out.println("Stability check failed for Global Greedy");
			result = false;
		}
		
		// do sanity check on result
		if (!TestTools.doCheck(assignment1)) {
			System.out.println("Sanity check failed for Global Greedy");
			result = false;
		}
		
		return result;
		
	}
}
