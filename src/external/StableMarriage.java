package external;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import util.MatrixTools;

/**
 * An implementation of the stable marriage algorithm from Chapter 1-2 in
 * "Algorithm Design" by Kleinberg and Tardos.
 * 
 * @author Stefan Nilsson
 * @version 2008.10.23
 */
/**
 * Modified version to make it compatible with our input.
 */
public class StableMarriage {
	// Number of men (=number of women)
	private int n;

	// Preference tables (size nxn)
	private int[][] manPref;
	private int[][] womanPref;

	private static final boolean DEBUGGING = false;
	
	boolean transposed = false;
	
	// make row as preference
	private static int[] asPref(double[] row) {
		int[] result = new int[row.length];
		ArrayList<double[]> tmp = new ArrayList<double[]>();
		for (int i = 0; i < row.length; i++) {
			tmp.add(new double[] { i, row[i] });
		}
		Collections.sort(tmp, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				double[] p1 = (double[]) o1;
				double[] p2 = (double[]) o2;
				return (int) Math.signum(p1[1] - p2[1]);
			}
		});
		for (int i = 0, len = tmp.size(); i < len; i++) {
			//System.out.println((int) (tmp.get(i)[0] + 1) + " <- " + tmp.get(i)[1]);
			result[(int) (tmp.get(i)[0])] = i;
		}
		return result;
	}

	/**
	 * Creates a marriage problem
	 */
	public StableMarriage(double[][] matrix) {
		if (matrix[0].length > matrix.length) {
    		matrix = MatrixTools.transpose(matrix);
    		transposed = true;
    	}
		
		n = matrix.length;
		
		double[][] manPref;
		double[][] womanPref;
		
		manPref = new double[matrix.length][];
		womanPref = new double[matrix[0].length][matrix.length];
		
		this.manPref = new int[manPref.length][];
		this.womanPref = new int[womanPref.length][];
		
		for (int i = 0; i < matrix.length; i++) {
			manPref[i] = matrix[i];
		}
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				womanPref[i][j] = matrix[j][i];
			}
		}
		
		for (int i = 0; i < manPref.length; i++) {
			this.manPref[i] = asPref(manPref[i]); 
		}
		for (int i = 0; i < womanPref.length; i++) {
			this.womanPref[i] = asPref(womanPref[i]); 
		}
	}

	/**
	 * Returns a stable marriage in the form an int array v, where v[i] is the
	 * man married to woman i.
	 */
	public int[] stable() {
		// Indicates that woman i is currently engaged to
		// the man v[i].
		int[] current = new int[n];
		final int NOT_ENGAGED = -1;
		for (int i = 0; i < current.length; i++)
			current[i] = NOT_ENGAGED;

		// List of men that are not currently engaged.
		LinkedList<Integer> freeMen = new LinkedList<Integer>();
		for (int i = 0; i < current.length; i++)
			freeMen.add(i);

		// next[i] is the next woman to whom i has not yet proposed.
		int[] next = new int[n];

		// computeRanking();
		while (!freeMen.isEmpty()) {
			int m = freeMen.remove();
			int w = manPref[m][next[m]];
			next[m]++;
			printDebug("m=" + m + " w=" + w);
			if (current[w] == NOT_ENGAGED) {
				current[w] = m;
			} else {
				int m1 = current[w];
				if (prefers(w, m, m1)) {
					current[w] = m;
					freeMen.add(m1);
				} else {
					freeMen.add(m);
				}
			}
		}
		return current;
	}

	/**
	 * Returns true iff w prefers x to y.
	 */
	private boolean prefers(int w, int x, int y) {
		for (int i = 0; i < n; i++) {
			int pref = womanPref[w][i];
			if (pref == x)
				return true;
			if (pref == y)
				return false;
		}
		// This should never happen.
		System.out.println("Error in womanPref list " + w);
		return false;
	}

	public void printPrefTables() {
		System.out.println("manPref:");
		printMatrix(manPref);
		System.out.println("womanPref:");
		printMatrix(womanPref);
	}

	@SuppressWarnings("unused")
	private void printMarriage(int[] m) {
		System.out.println("Married couples (woman + man): ");
		for (int i = 0; i < m.length; i++)
			System.out.println(i + " + " + m[i]);
	}

	private void printDebug(String s) {
		if (DEBUGGING) {
			System.out.println(s);
		}
	}

	/**
	 * Prints the matrix v.
	 */
	private void printMatrix(int[][] v) {
		if (v == null) {
			System.out.println("<null>");
			return;
		}
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < v[i].length; j++)
				System.out.print(v[i][j] + " ");
			System.out.println();
		}
	}
}
