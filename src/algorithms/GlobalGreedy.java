package algorithms;

import general.DataObj;
import general.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.ArrayTools;

public class GlobalGreedy {

	// reference:
	// Nikolaus Augsten, Approximate Matching of Hierarchical Data, Ph.D. Dissertation
	
	public static ArrayList<int[]> match(DataObj matrix) {
		ArrayList<int[]> M = new ArrayList<int[]>();
		double[][] D = ArrayTools.cloneMatrix(matrix.values);
		String[] Alfa = matrix.rowNames.clone();
		String[] Beta = matrix.colNames.clone();
		ArrayList<Tuple> S = new ArrayList<Tuple>();
		for (int i = 0; i < Alfa.length; i++) {
			for (int j = 0; j < Beta.length; j++) {
				S.add(new Tuple(Alfa[i], i, Beta[j], j, D[i][j]));
			} 
		}
		Collections.sort(S, new Comparator<Object>(){ 
	        public int compare(Object o1, Object o2) {
	        	Tuple p1 = (Tuple) o1;
	            Tuple p2 = (Tuple) o2;
	            return (int) Math.signum(p1.distance - p2.distance);
	        }
	    });
		boolean[] seen_row = new boolean[Alfa.length]; 
		boolean[] seen_col = new boolean[Beta.length]; 
		int s = 0;
		while (M.size() < Math.min(Alfa.length, Beta.length)) {
			Tuple tuple = S.get(s);
			int[] match = new int[2];
			match[0] = tuple.row;
			match[1] = tuple.col;
			if (!seen_row[tuple.row] && !seen_col[tuple.col]) {
				M.add(match);
				seen_row[tuple.row] = true;
				seen_col[tuple.col] = true;
			}
			s++;
		}
		return M;
	}
}
