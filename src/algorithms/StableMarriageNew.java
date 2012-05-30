package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import util.MatrixTools;

public class StableMarriageNew {
	
	private int nr_men;
	private int nr_women;
	
	// Preference tables (size nr_men x nr_women)
	private int[][] manPref;
    private int[][] womanPref;
	
	public StableMarriageNew(double[][] distances) {
		double[][] transposedDistances = MatrixTools.transpose(distances);
		this.nr_men = distances.length;
		this.nr_women = distances[0].length;
		manPref = new int[nr_men][nr_women];
		womanPref = new int[nr_women][nr_men];
		for (int i = 0; i < nr_men; i++) {
		    manPref[i] = asPref(distances[i]);
		}
		for (int i = 0; i < nr_women; i++) {
			womanPref[i] = asPref(transposedDistances[i]);
		}
		
	}
	
	private int[] asPref(double[] row) {
		int[] result = new int[row.length];
		ArrayList<double[]> tmp = new ArrayList<double[]>();
		for (int i = 0; i < row.length; i++) {
			tmp.add(new double[]{i, row[i]});
		}
		Collections.sort(tmp, new Comparator<Object>(){ 
	        public int compare(Object o1, Object o2) {
	        	double[] p1 = (double[]) o1;
	        	double[] p2 = (double[]) o2;
	            return (int) Math.signum(p1[1] - p2[1]);
	        }
	    });
		for (int i = 0, len = tmp.size(); i < len; i++) {
			result[(int) (tmp.get(i)[0])] = i;
		}
		return result;
	}
	
	/**
     * Returns true iff w prefers x to y.
     */
	private boolean prefers(int w, int x, int y) {
		for (int i = 0; i < nr_men; i++) {
		    int pref = womanPref[w][i];
		    if (pref == x) {
		    	return true;
		    }
		    if (pref == y) {
		    	return false;
		    }
		}
		// This should never happen.
		System.out.println("Error in womanPref list " + w);
		return false;
	}
	
	public int[] stable() {
		// Indicates that woman i is currently engaged to
		// the man v[i].
		int[] current = new int[nr_women];
		final int NOT_ENGAGED = -1;
		for (int i = 0; i < current.length; i++) {
		    current[i] = NOT_ENGAGED;
		}
		// List of men that are not currently engaged.
		LinkedList<Integer> freeMen = new LinkedList<Integer>();
		for (int i = 0; i < current.length; i++) {
		    freeMen.add(i);
		}
		// next[i] is the next woman to whom i has not yet proposed.
		int[] next = new int[nr_men];

		//computeRanking();
		while (!freeMen.isEmpty()) {
		    int m = freeMen.remove();
		    int w = manPref[m][next[m]];
		    next[m]++;
		    //printDebug("m=" + m + " w=" + w);
		    if (current[w] == NOT_ENGAGED) {
		    	current[w] = m;
		    } 
		    else {
		    	int m1 = current[w];
		    	if (prefers(w, m, m1)) {
		    		current[w] = m;
		    		freeMen.add(m1);
		    	} 
		    	else {
		    		freeMen.add(m);
		    	}
		    }	    
		}
		return current;	
	}
	
	
	private static void printMatrix(int[][] v) {
		if (v == null) {
		    System.out.println("<null>");
		    return;
		}
		for (int i = 0; i < v.length; i++) {
		    for (int j = 0; j < v[i].length; j++) {
		    	System.out.print(v[i][j] + " ");
		    }
		    System.out.println();
		}
	}
	
	private static void printMarriage(int[] m) {
		System.out.println("Married couples (woman + man): ");
		for (int i = 0; i < m.length; i++) {
		    System.out.println(i + " + " + m[i]);
		}
	}
	public static void main(String[] args) {
		double[][] matrix = new double[][] {
				{0.12,0.19,0.8,0.15},
				{0.10,0.18,0.7,0.17},
				{0.13,0.16,0.9,0.14},
				{0.12,0.19,0.8,0.18},
				{0.14,0.17,0.10,0.19}
		};
		
		StableMarriageNew stableMarriage = new StableMarriageNew(matrix);
		System.out.println("Men Preferrences:");
		printMatrix(stableMarriage.manPref);
		System.out.println("Women Preferrences:");
		printMatrix(stableMarriage.womanPref);
		System.out.println("Matches:");
		printMarriage(stableMarriage.stable());
	}
}
