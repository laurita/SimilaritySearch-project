package util;

import java.util.ArrayList;

/* contains basic checks */

public class TestTools {
	
	// check that the result is stable w.r. to the matrix
	public static boolean isStable(ArrayList<int[]> assignment1, double[][] matrix) {
		boolean error = false;
		boolean result = true;
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
				result = false;
			}
		}
		return result;
	}
	
	// basic check that the result makes sense:
	// each row is matched to maximum one column and vice versa
	public static boolean doCheck(ArrayList<int[]> matches) {
		boolean result = true;
		
		ArrayList<Integer> rows = new ArrayList<Integer>();
		ArrayList<Integer> cols = new ArrayList<Integer>();
		
		for (int i = 0; i < matches.size(); i++) {
			if (!rows.contains(matches.get(i)[0]) && !cols.contains(matches.get(i)[1])) {
				rows.add(matches.get(i)[0]);
				cols.add(matches.get(i)[1]);
			} else {
				result = false;
				break;
			}
		}
		
		return result;
	}
}
