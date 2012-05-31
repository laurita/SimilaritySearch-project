package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.MatrixTools;

// adapted pseudocode from 
// http://en.wikipedia.org/wiki/Stable_marriage_problem

public class StableMarriage {

	// possible proposals for man
	// for every man this lists the woman that this man has 
	// not proposed to, ordered by preference (high ~ first)
	protected ArrayList<ArrayList<Integer>> possPropM;
	// for each woman this holds all the preferences for
	// all the man
	protected ArrayList<int[]> prefW;
	// the distance matrix as given to this class
	protected double[][] distances;
	// all the man, if they are engaged the id
	// of the woman, otherwise -1
	protected int[] matchesM;
	// all the woman, if they are engaged the id
	// of the man, otherwise -1
	protected int[] matchesW;
	
	// finds a free man that can still propose to a woman
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
	
	// transform row into array (generate prefW row)
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
	// (generate possPropM entry)
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
			result.add((int) (tmp.get(i)[0]));
		}
		return result;
	}
	
	public StableMarriage(double[][] distances) {
		
		// store distances
		this.distances = distances;
		
		// transpose the matrix if necessary
		if (distances.length > distances[0].length) {
			distances = MatrixTools.transpose(distances);
			transposed = true;
		}
		
		// init the matches with -1
		matchesM = new int[distances.length];
		for (int i = 0; i < matchesM.length; i++) {
			matchesM[i] = -1;
		}
		matchesW = new int[distances[0].length];
		for (int i = 0; i < matchesW.length; i++) {
			matchesW[i] = -1;
		}
		
		// init the "has not proposed list", ranked by preference
		possPropM = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < distances.length; i++) {
			possPropM.add(asPref(distances[i]));
		}
		
		// init the woman's ranking of the man (man id -> rank)
		prefW = new ArrayList<int[]>();
		double[][] distancesTransp = MatrixTools.transpose(distances);
		for (int i = 0; i < distancesTransp.length; i++) {
			prefW.add(asPrefW(distancesTransp[i]));
		}
		
		// find free man m who still has a woman w to propose to
		int cman = findMan();
		// while \exists free man m who still has a woman w to propose to
		while (cman != -1) {
			// w = m's highest ranked such woman to whom he has not yet proposed
			int hiWoman = possPropM.get(cman).get(0);
			// if w is free
			if (matchesW[hiWoman] == -1) {
				// (m, w) become engaged
				matchesM[cman] = hiWoman;
				matchesW[hiWoman] = cman;
			// else some pair (m', w) already exists
			} else {
				// if w prefers m to m'
				if (prefW.get(hiWoman)[cman] < prefW.get(hiWoman)[matchesW[hiWoman]]) {
					// (m, w) become engaged
					matchesM[cman] = hiWoman;
					// m' becomes free
					matchesM[matchesW[hiWoman]] = -1;
					matchesW[hiWoman] = cman;
				} else {
					// (m', w) remain engaged
				}
			}
			
			// remove this proposal
			possPropM.get(cman).remove(0);
			
			// find new free man m who still has a woman w to propose to
			cman = findMan();
		}

	}
	
	public ArrayList<int[]> getMatches() {
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
