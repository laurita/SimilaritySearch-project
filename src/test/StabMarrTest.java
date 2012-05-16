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
		
		// to test if this throws errors
		// ArrayList<int[]> assignment1 = new algorithms.HungAlg(matrix).getMatches();
		// doesn't throw errors (obviously!)
		// ArrayList<int[]> assignment1 = algorithms.GlobalGreedy.match(matrix);	
		// not throwing errors (of course)
		// ArrayList<int[]> assignment1 = algorithms.RNN.match(matrix);
		
		// check that the result is stable
		boolean error = false;
		for (int i = 0, len = assignment1.size(); i < len; i++) {
			double value = matrix[assignment1.get(i)[0]][assignment1.get(i)[1]];
			error = false;
			// loop over entries
			for (int x = 0; x < matrix.length; x++) {
				// check all solutions that are better
				if (matrix[x][assignment1.get(i)[1]] < value) {
					// find the result in this row
					double hook = -1;
					for (int j = 0; j < assignment1.size(); j++) {
						// check if there is a result with this
						if (assignment1.get(j)[0] == x) {
							hook = matrix[assignment1.get(j)[0]][assignment1.get(j)[1]];
						}
					}
					// check if the result is better
					if (hook != -1) {
						if (matrix[x][assignment1.get(i)[1]] < hook) {
							error = true;
						}
					}
				}
			}
			
			// loop over entries
			for (int y = 0; y < matrix[0].length; y++) {
				// check all solutions that are better
				if (matrix[assignment1.get(i)[0]][y] < value) {
					// find the result in this col
					double hook = -1;
					for (int j = 0; j < assignment1.size(); j++) {
						// check if there is a result with this
						if (assignment1.get(j)[1] == y) {
							hook = matrix[assignment1.get(j)[0]][assignment1.get(j)[1]];
						}
					}
					// check if the result is better
					if (hook != -1) {
						if (matrix[assignment1.get(i)[0]][y] < hook) {
							error = true;
						}
					}
				}
			}
			
			if (error) {
				System.out.println("Unstable at " + assignment1.get(i)[0] + ", " + assignment1.get(i)[1]);
			}
		}
		
		// do sanity check
		if (!SanityTest.doCheck(assignment1)) {
			System.out.println("Sanity check failed for Stable Marriage");
			result = false;
		}
		
		/*
		// result for imported implementation
		ArrayList<int[]> assignment2 = new ArrayList<int[]>();
		{
			int[] tmp = new external.StableMarriage(matrix).stable();
			// convert
			for (int i = 0; i < tmp.length; i++) {
				assignment2.add(new int[] { i, tmp[i] });
			}
		}
		
		// compare
		if (assignment1.size() != assignment2.size()) {
			System.out.println(assignment2.size());
			result = false;
		}
		for (int i = 0; i < Math.min(assignment1.size(), assignment2.size()); i++) {
			if (assignment1.get(i)[0] != assignment2.get(i)[0] || assignment1.get(i)[1] != assignment2.get(i)[1]) {
				result = false;
			}
		}
		*/
		
		return result;
	}
}
