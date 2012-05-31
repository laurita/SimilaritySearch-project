package tests;

import java.util.ArrayList;

import util.TestTools;

/* 
 * This class performs some testing on our implementation of the 
 * stable marriage algorithm
 */

public class StabMarrTest {
	public static boolean test(double[][] matrix) {
		boolean result = true;

		// create result for our implementation
		ArrayList<int[]> assignment1 = new algorithms.StableMarriage(matrix)
				.getMatches();

		// do stability check
		if (!TestTools.isStable(assignment1, matrix)) {
			System.out.println("Stability check failed for Stable Marriage");
			result = false;
		}

		// do result sanity check
		if (!TestTools.doCheck(assignment1)) {
			System.out.println("Sanity check failed for Stable Marriage");
			result = false;
		}

		return result;
	}
}
