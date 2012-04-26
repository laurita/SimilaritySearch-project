package test;

import java.util.ArrayList;

import external.HungarianAlgorithm;

/* 
 * This class is for testing our implementation of the 
 * hungarian algorithm against
 * an implementation that we found online. 
 */

public class HungAlgTest {

	// get the score of the solution
	private static double getScore(double[][] matrix, ArrayList<int[]> solution) {
		double sum = 0;
		for (int i = 0, len = solution.size(); i < len; i++) {
			sum += matrix[solution.get(i)[0]][solution.get(i)[1]];
			//System.out.println("[" + solution.get(i)[0] + ", " + solution.get(i)[1] + "]");
		}
		return sum;
	}

	// do a test for a matrix
	public static boolean test(double[][] matrix) {
		
		boolean result = true;

		// create result for our implementation
		ArrayList<int[]> assignment1 = new algorithms.HungAlg(matrix)
				.getMatches();
		
		// do sanity check
		if (!SanityTest.doCheck(assignment1)) {
			System.out.println("Sanity check failed for Hungarian Algorithm");
			result = false;
		}
		
		// result for imported implementation
		ArrayList<int[]> assignment2 = new ArrayList<int[]>();
		{
			int[][] tmp = new HungarianAlgorithm().computeAssignments(matrix);
			// convert
			for (int i = 0; i < tmp.length; i++) {
				assignment2.add(new int[] { tmp[i][0], tmp[i][1] });
			}
		}
		
		// check for correctness
		if (assignment1.size() != assignment2.size()
				|| Math.abs(getScore(matrix, assignment1) - getScore(matrix,
						assignment2)) > 0.00000000001) {
			result = false;
		}
		//System.out.println(getScore(matrix, assignment1) + " vs " + getScore(matrix, assignment2));
		//System.out.println(assignment1.size() + " vs " + assignment2.size());
		
		return result;
	}
}
