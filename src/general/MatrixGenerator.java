package general;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

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
	
	// generate a zipf distribution data abject
	public static DataObj generateZipf(int sizex, int sizey) {
		double[][] matrix = new double[sizex][sizey];
		String[] rowNames = new String[sizey];
		String[] colNames = new String[sizex];
		Random rnd = new Random();
		for (int i = 0; i < sizex; i++) {
			for (int j = 0; j < sizey; j++) {
				matrix[i][j] = zipf(rnd.nextInt(10));
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
	
	// return zipf cdf(x) with parameter value = 2
	public static double zipf(int x) {
		switch (x) {
			case 0: return 0.0; 
			case 1: return 0.8319416331806163; 
			case 2: return 0.9359343373281933; 
			case 3: return 0.9667469904089568; 
			case 4: return 0.9797460784274039; 
			case 5: return 0.9864016114928489; 
			case 6: return 1.0; 
			case 7: return 1.0; 
			case 8: return 1.0; 
			case 9: return 1.0; 
		}
		return 1.0;
		/*
		double RO = 2;
		double harmonicNumber = 0;
		double riemansZeta = 0;
		for (int k = 1; k <= x; k++) {
			harmonicNumber += 1.0 / Math.pow(k, (RO + 1));
		}
		for (int k = 1; k <= 100; k++) {
			riemansZeta += 1.0 / Math.pow(k, (RO + 1));
		}
		return ((harmonicNumber / riemansZeta) > 0.99) ? 1 : (harmonicNumber / riemansZeta);
		*/
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
