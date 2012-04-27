package algorithms;

import general.DataObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RNN {
	
	// reference:
	// http://cgi.cse.unsw.edu.au/~macheema/thesis/node22.html
	
	/*
	@SuppressWarnings("unchecked")
	private static Comparator compare = new Comparator<double[]>(){ 
        public int compare(double[] o1, double[] o2) {
            return (int) Math.signum(o1[1] - o1[1]);
        }
    };
	*/
	
	/*
	private static int findColMin(int col, double matrix[][], int[] colMin) {
		if (colMin[col] == -2) {
			// check for minimum
			double[] min = new double[] {-1, -1};
			double[] min2 = new double[] {-1, -1};
			for (int i = 0, len = matrix.length; i < len; i++) {
				if (matrix[i][col] < min[0] || min[0] == -1) {
					min2 = min;
					min = new double[] {matrix[i][col], i};
				}
			}
			if (min[0] != min2[0]) {
				colMin[col] = (int) min[1];
			} else {
				colMin[col] = -1;
			}
		}
		
		return colMin[col];
	}
	
	public static ArrayList<int[]> match(double[][] matrix) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		
		int[] colMin = new int[matrix[0].length];
		for (int i = 0; i < colMin.length; i++) {
			colMin[i] = -2;
		}
		
		// loop over rows
		for (int i = 0, len = matrix.length; i < len; i++) {
			// check for minimum
			double[] min = new double[] {-1, -1};
			double[] min2 = new double[] {-1, -1};
			for (int j = 0, len2 = matrix[0].length; j < len2; j++) {
				if (matrix[i][j] < min[0] || min[0] == -1) {
					min2 = min;
					min = new double[] {matrix[i][j], j};
				}
			}
			// check that minimum is unique
			// check that this is the minimum in the col
			if (min[0] != min2[0] && findColMin((int) min[1], matrix, colMin) == i) {
				result.add(new int [] {i, (int) min[1]});			
			}
		}
	
		
		return result;
	}
	*/
	
	//*
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
	//*/
}
