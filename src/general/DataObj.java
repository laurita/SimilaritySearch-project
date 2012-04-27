package general;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import util.ArrayTools;

public class DataObj {
	
	public final double[][] values;
	public final String[] rowNames;
	public final String[] colNames;
	public final int colCount;
	public final int rowCount;

	// generate object form existing data
	public DataObj(double[][] matrix, String[] rowNames, String[] colNames) {
		this.values = matrix;
		this.rowNames = rowNames;
		this.colNames = colNames;
		this.rowCount = rowNames.length;
		this.colCount = colNames.length;
	}
	
	// generate object from file
	public DataObj(String filename) {
		double[][] matrix = null;
		String[] rowNames = null;
		String[] colNames = null;
		int colCount = 0;
		int rowCount = 0;
		try {
			// the file
			BufferedReader in = new BufferedReader(new FileReader(filename));
			// buffer
			String line;
			
			// all the row objects (in correct order)
			ArrayList<String> rowobj = new ArrayList<String>();
			
			// all the col objects (in correct order)
			ArrayList<String> colobj = new ArrayList<String>();
			
			// get names for the data
			while ((line = in.readLine()) != null) {
				if (line.charAt(0) != '#') {
					String[] filerow = line.split("\\|");
					if (!rowobj.contains(filerow[0])) {
						rowobj.add(filerow[0]);
					}
					if (!colobj.contains(filerow[1])) {
						colobj.add(filerow[1]);
					}
				}
			}
			
			// assign the row and column sizes and names
			rowNames = new String[rowobj.size()];
			rowobj.toArray(rowNames);
			colNames = new String[colobj.size()];
			colobj.toArray(colNames);
			colCount = colobj.size();
			rowCount = rowobj.size();
			
			// create the matrix
			matrix = new double[rowobj.size()][colobj.size()];
			
			// read the matrix entries
			in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				if (line.charAt(0) != '#') {
					int pos[] = new int[2];
					String[] filerow = line.split("\\|");
					pos[0] = rowobj.indexOf(filerow[0]);
					pos[1] = colobj.indexOf(filerow[1]);
					matrix[pos[0]][pos[1]] = Double.valueOf(filerow[2]);
				}
			}
		} catch (Exception e) {
			System.err.println("Error while reading the matrix file.");
			System.err.println(e);
		}
		
		// assign (final)
		this.values = matrix;
		this.rowNames = rowNames;
		this.colNames = colNames;
		this.colCount = colCount;
		this.rowCount = rowCount;
	}
}
