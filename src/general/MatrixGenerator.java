package general;

import java.io.BufferedWriter;
import java.io.FileWriter;

/* this class generates randomly distance matrix files and objects */

public class MatrixGenerator {
	// generate a random data object
	public static DataObj generate(int sizex, int sizey) {
		double[][] matrix = new double[sizex][sizey];
		String[] rowNames = new String[sizey];
		String[] colNames = new String[sizex];
		for (int i = 0; i < sizex; i++) {
			for (int j = 0; j < sizey; j++) {
				matrix[i][j] = Math.random();
			}
		}
		for (int i = 0; i < sizex; i++) {
			colNames[i] = String.valueOf(i);
		}
		for (int i = 0; i < sizex; i++) {
			rowNames[i] = String.valueOf(i);
		}

		return new DataObj(matrix, rowNames, colNames);

	}

	// write a random matrix file
	public static void generateFile(String filename, int sizex, int sizey) {
		try {
			// Create file
			FileWriter fstream = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(fstream);

			for (int i = 0; i < sizex; i++) {
				for (int j = 0; j < sizey; j++) {
					out.write(i + "|" + j + "|" + Math.random() + "\n");
				}
			}

			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
