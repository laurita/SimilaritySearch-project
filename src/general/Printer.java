package general;

import java.util.ArrayList;

// class that can print basic stuff

public class Printer {
	// prints the results
	public static void printResults(DataObj matrix, ArrayList<int[]> result) {
		for (int j = 0, len = result.size(); j < len; j++) {
			System.out.println(matrix.rowNames[result.get(j)[0]] + " - "
					+ matrix.colNames[result.get(j)[1]] + " @ "
					+ matrix.values[result.get(j)[0]][result.get(j)[1]]);
		}
	}

	// prints the matrix (to see if it's correctly loaded)
	public static void printMatrix(DataObj matrix) {
		System.out.print("  ");
		for (int x = 0; x < matrix.colCount; x++) {
			System.out.print(matrix.colNames[x] + " ");
		}
		System.out.println();
		for (int x = 0; x < matrix.rowCount; x++) {
			System.out.print(matrix.rowNames[x] + " ");
			for (int y = 0; y < matrix.colCount; y++) {
				System.out.print(matrix.values[x][y] + " ");
			}
			System.out.println();
		}
	}

}
