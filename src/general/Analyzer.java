package general;

import java.util.ArrayList;

import util.ArrayTools;

// class that calculates recall and precision

public class Analyzer {
	
	// the initial data
	protected final int relevantcount;
	protected final DataObj matrix;
	
	// the data for this result
	private int resultcount = 0;
	private int relresultcount = 0;
	
	// get the count of all the real matches in our matrix
	protected int getRelevant(DataObj matrix) {
		// find the real match count
		int count = 0;
		String[] a = matrix.colNames.clone();
		String[] b = matrix.rowNames.clone();
		java.util.Arrays.sort(a);
		java.util.Arrays.sort(b);
		int aindex = 0;  
		int bindex = 0;  
		while (aindex < a.length && bindex < b.length) {  
		  if (a[aindex].equals(b[bindex])) {  
			count++; 
		    aindex++;  
		    bindex++;  
		  }  
		  else if (a[aindex].compareTo(b[bindex]) < 0) {  
		    aindex++;
		  }  
		  else {  
		    bindex++;
		  }  
		} 
		return count;
	}
	
	// load the analyzer with the initial matrix
	public Analyzer(DataObj matrix) {
		relevantcount = getRelevant(matrix);
		this.matrix = matrix;
	}
	
	// analyze a result
	public void analyze(ArrayList<int[]> result) {
		// how many matches
		resultcount = result.size();
		
		// find all the correct matches
		relresultcount = 0;
		for (int i = 0, len = result.size(); i < len; i++) {
			if (matrix.rowNames[result.get(i)[0]].equals(matrix.colNames[result.get(i)[1]])) {
				relresultcount++;
			}
		}
		
		// for verification / testing
		//System.out.println("resultcount " + resultcount);
		//System.out.println("relresultcount " + relresultcount);
		//System.out.println("relevantcount " + relevantcount);
		
	}
	
	public double getPrecision() {
		return (double)relresultcount / resultcount;
	}
	
	public double getRecall() {
		return (double)relresultcount / relevantcount;
	}
}
