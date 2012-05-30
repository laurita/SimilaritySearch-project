package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.MatrixTools;

public class StableMarriage_v3 {
	
	
	// variables
	protected ArrayList<Integer> freeMan;
	protected ArrayList<Integer> freeWomen;
	protected ArrayList<ArrayList<Integer>> possProp;
	protected double[][] distances;
	
	
	// returns -1 if there is no such man
	private int findMan() {
		// loop over all the man
		for (int i = 0; i < distances.length; i++) {
			// check if there are still women available
			if (possProp.get(i).size() > 0) {
				return i;
			}
		}
		return -1;
	}
	
	// if this is true we need to transpose the result (!)
	private boolean transposed = false;
	
	// make row as preference
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
		new StableMarriage_v3(new double[][] { 
				{ 0.5, 0.7, 0.9 },
				{ 0.7, 0.5, 0.9 }, 
				{ 0.5, 0.7, 0.9 } }
		);
	}
	
	public StableMarriage_v3(double[][] distances) {
		
		// store distances
		this.distances = distances;
		
		// transpose the matrix if necessary
		if (distances.length > distances[0].length) {
			distances = MatrixTools.transpose(distances);
			transposed = true;
		}
		
		// init free lists
		freeMan = new ArrayList<Integer>();
		for (int i = 0; i < distances.length; i++) {
			freeMan.add(i);
		}
		freeWomen = new ArrayList<Integer>();
		for (int i = 0; i < distances[0].length; i++) {
			freeWomen.add(i);
		}
		
		// init the "can propose list", ranked by preference
		possProp = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < distances.length; i++) {
			possProp.add(asPref(distances[i]));
//			for (int k = 0; k < asPref(distances[i]).size(); k++) {
//				System.out.print( " " + asPref(distances[i]).get(k));
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
		
//		int cman = findMan();
//		while (cman != -1) {
//			
//			
//			
//			cman = findMan();
//		}
		
	}
}
