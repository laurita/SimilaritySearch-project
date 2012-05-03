package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.ArrayTools;

public class GlobalGreedy {

	// reference:
	// Nikolaus Augsten, Approximate Matching of Hierarchical Data, Ph.D. Dissertation
	
	public static ArrayList<int[]> match(double[][] matrix) {
		ArrayList<int[]> M = new ArrayList<int[]>();
		double[][] D = ArrayTools.cloneMatrix(matrix);
		ArrayList<double[]> S = new ArrayList<double[]>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				S.add(new double[] {i, j, D[i][j]});
			} 
		}
		Collections.sort(S, new Comparator<Object>(){ 
	        public int compare(Object o1, Object o2) {
	        	double[] p1 = (double[]) o1;
	            double[] p2 = (double[]) o2;
	            return (int) Math.signum(p1[2] - p2[2]);
	        }
	    });
		boolean[] seen_row = new boolean[matrix.length]; 
		boolean[] seen_col = new boolean[matrix[0].length]; 
		int s = 0;
		while (M.size() < Math.min(matrix.length, matrix[0].length)) {
			double[] tuple = S.get(s);
			int[] match = new int[2];
			match[0] = (int) tuple[0];
			match[1] = (int) tuple[1];
			if (!seen_row[match[0]] && !seen_col[match[1]]) {
				M.add(match);
				seen_row[match[0]] = true;
				seen_col[match[1]] = true;
			}
			s++;
		}
		return M;
	}
}
