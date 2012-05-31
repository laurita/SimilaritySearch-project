package util;

/* Some basic functions to make dealing with matrixes simpler */

public class MatrixTools {
	// clones a matrix of doubles
	public static double[][] cloneMatrix(double[][] input) {
		if (input == null)
			return null;
		double[][] result = new double[input.length][];
		for (int r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}

	// clones a matrix for integers
	public static int[][] cloneMatrix(int[][] input) {
		if (input == null)
			return null;
		int[][] result = new int[input.length][];
		for (int r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}
	
	// transposes a double matrix
	public static double[][] transpose(double[][] matrix) {
		double[][] transposed = new double[matrix[0].length][matrix.length];

		for (int x = 0; x < matrix[0].length; x++) {
			for (int y = 0; y < matrix.length; y++)
				transposed[x][y] = matrix[y][x];
		}
		return transposed;
	}
	
	// transposes an integer matrix
	public static int[][] transpose(int[][] matrix) {
		int[][] transposed = new int[matrix[0].length][matrix.length];

		for (int x = 0; x < matrix[0].length; x++) {
			for (int y = 0; y < matrix.length; y++)
				transposed[x][y] = matrix[y][x];
		}
		return transposed;
	}
}
