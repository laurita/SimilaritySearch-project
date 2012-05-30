package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import test.StabMarrTest;
import util.BiMap;
import util.MatrixTools;

public class StableMarriage_v3 {
	
	
	// variables
	protected ArrayList<ArrayList<Integer>> possPropM;
	protected ArrayList<int[]> prefW;
	protected double[][] distances;
	protected int[] matchesM;
	protected int[] matchesW;
	
	// returns -1 if there is no such man
	private int findMan() {
		// loop over all man
		for (int i = 0; i < matchesM.length; i++) {
			// if free
			if (matchesM[i] == -1) {
				// check if there are still women available
				if (possPropM.get(i).size() > 0) {
					return i;
				}
			}		
		}
		return -1;
	}
	
	// if this is true we need to transpose the result (!)
	private boolean transposed = false;
	
	// transform row into array
	private int[] asPrefW(double[] row) {
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
	
	// transform row into array, ordered by preference
	private ArrayList<Integer> asPref(double[] row) {
		ArrayList<Integer> result = new ArrayList<Integer>();
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
			result.add((int) (tmp.get(i)[0]));
		}
		return result;
	}
	
	public static void main(String[] args) {
		double[][] matrix = new double[][] { 
				{ 0.8, 0.7, 0.9, 1.3 },
				{ 0.7, 0.5, 0.9, 4.3 }, 
				{ 0.5, 0.7, 0.9, 4.2 } };
		StabMarrTest.test(matrix);
		ArrayList<int[]> result = new StableMarriage_v3(matrix).compute();
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i)[0] + " -> " + result.get(i)[1]);
		}
	}
	
	public StableMarriage_v3(double[][] distances) {
		
		// store distances
		this.distances = distances;
		
		// transpose the matrix if necessary
		if (distances.length > distances[0].length) {
			distances = MatrixTools.transpose(distances);
			transposed = true;
		}
		
		// init the amount of matches with -1
		matchesM = new int[distances.length];
		for (int i = 0; i < matchesM.length; i++) {
			matchesM[i] = -1;
		}
		matchesW = new int[distances[0].length];
		for (int i = 0; i < matchesW.length; i++) {
			matchesW[i] = -1;
		}
		
		// init the "can propose list", ranked by preference
		possPropM = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < distances.length; i++) {
			possPropM.add(asPref(distances[i]));
//			for (int k = 0; k < asPref(distances[i]).size(); k++) {
//				System.out.print( " " + asPref(distances[i]).get(k));
//			}
//			System.out.println();
		}
		
		prefW = new ArrayList<int[]>();
		double[][] distancesTransp = MatrixTools.transpose(distances);
		for (int i = 0; i < distancesTransp.length; i++) {
			prefW.add(asPrefW(distancesTransp[i]));
//			for (int k = 0; k < asPrefW(distancesTransp[i]).length; k++) {
//				System.out.print( " " + asPrefW(distancesTransp[i])[k]);
//			}
//			System.out.println();
		}
		
		/*
		function stableMatching {
		    Initialize all m \in M and w \in W to free
		    while \exists free man m who still has a woman w to propose to {
		       w = m's highest ranked such woman to whom he has not yet proposed
		       if w is free
		         (m, w) become engaged
		       else some pair (m', w) already exists
		         if w prefers m to m'
		           (m, w) become engaged
		           m' becomes free
		         else
		           (m', w) remain engaged
		    }
		}
		 */
		
		int cman = findMan();
		while (cman != -1) {
			// get highest ranked woman
			int hiWoman = possPropM.get(cman).get(0);
			// if woman is free
			if (matchesW[hiWoman] == -1) {
				//System.out.println(cman + " eng to free " + hiWoman);
				matchesM[cman] = hiWoman;
				matchesW[hiWoman] = cman;
			} else {
				if (prefW.get(hiWoman)[cman] < prefW.get(hiWoman)[matchesW[hiWoman]]) {
					//System.out.println(matchesW[hiWoman] + " and " + hiWoman + " break up.");
					//System.out.println(cman + " eng to " + hiWoman);
					matchesM[cman] = hiWoman;
					matchesM[matchesW[hiWoman]] = -1;
					matchesW[hiWoman] = cman;
				} else {
					// nothing
				}
			}
			
			// remove this proposal
			possPropM.get(cman).remove(0);
			
			cman = findMan();
		}
		
		/*for (int i = 0; i < matchesM.length; i++) {
			System.out.println(i + " -> " + matchesM[i]);
		}*/
	}
	
	public ArrayList<int[]> compute() {
		ArrayList<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < matchesM.length; i++) {
			if (transposed) {
				//System.out.println(matchesM[i] + " to " + i);
				result.add(new int[]{matchesM[i], i});
			} else {
				result.add(new int[]{i, matchesM[i]});
			}
		}
		return result;
	}
}
