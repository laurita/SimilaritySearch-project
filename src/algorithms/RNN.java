package algorithms;

import general.DataObj;

import java.util.ArrayList;

public class RNN {
	
	// reference:
	// http://cgi.cse.unsw.edu.au/~macheema/thesis/node22.html
	
	public static ArrayList<int[]> match(double[][] matrix) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < matrix.length; i++) {
			
			// find the minimum value in the current row 
			// and the corresponding column
			double min = 1;
			// the second smallest entry
			double min2 = 1;
			int pos = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				if (min >= matrix[i][j]) {
					min2 = min;
					min = matrix[i][j];
					pos = j;
				}
			}
			
			// check that the minimum value is unique
			if (min < min2) {			
				// loop through the column and find the minimum value
				boolean isRNN = true;
				for (int x = 0; x < matrix.length; x++) {
					// if value is smaller or equal, the minimum is not 
					// the unique minimum value in this column
					if ((min >= matrix[x][pos]) && (x != i)) {
						isRNN = false;
						break;
					}
				}
				
				// check if we need to add
				if (isRNN) {
					result.add(new int[]{i,pos});
				}
			}
		}
		
		return result;
	}
}
