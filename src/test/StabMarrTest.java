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
		
		// check that the result is stable
		// ...
		
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
