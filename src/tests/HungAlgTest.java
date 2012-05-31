package tests;

import java.util.ArrayList;

import util.TestTools;

import external.HungarianAlgorithm;

/* 
 * This class is for testing our implementation of the 
 * hungarian and testing it against
 * an implementation that we found online. 
 */

public class HungAlgTest {

	// get the score of the solution
	private static double getScore(double[][] matrix, ArrayList<int[]> solution) {
		double sum = 0;
		for (int i = 0, len = solution.size(); i < len; i++) {
			sum += matrix[solution.get(i)[0]][solution.get(i)[1]];
		}
		return sum;
	}

	// do a test for a matrix
	public static boolean test(double[][] matrix) {
		
		boolean result = true;

		// create result for our implementation
		ArrayList<int[]> assignment1 = new algorithms.HungAlg(matrix)
				.getMatches();
		
		// do solution sanity check
		if (!TestTools.doCheck(assignment1)) {
			System.out.println("Sanity check failed for Hungarian Algorithm");
			result = false;
		}
		
		// get result for external implementation
		ArrayList<int[]> assignment2 = new ArrayList<int[]>();
		{
			int[][] tmp = new HungarianAlgorithm().computeAssignments(matrix);
			// convert
			for (int i = 0; i < tmp.length; i++) {
				assignment2.add(new int[] { tmp[i][0], tmp[i][1] });
			}
		}
		
		// the result might not be the same, but the score should
		// be the same (except for rounding errors)
		// also the size should be the same
		if (assignment1.size() != assignment2.size()
				|| Math.abs(getScore(matrix, assignment1) - getScore(matrix,
						assignment2)) > 0.00000000001) {
			System.out.println("Solution score/size of external implementation " +
					"differs for Hungarian Algorithm");
			result = false;
		}
		
		return result;
	}
}
