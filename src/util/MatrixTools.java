package util;

public class MatrixTools {
	public static double[][] transpose(double[][] matrix) {
		double[][] transposed = new double[matrix[0].length][matrix.length];

		for (int x = 0; x < matrix[0].length; x++) {
			for (int y = 0; y < matrix.length; y++)
				transposed[x][y] = matrix[y][x];
		}
		return transposed;
	}
	
	public static int[][] transpose(int[][] matrix) {
		int[][] transposed = new int[matrix[0].length][matrix.length];

		for (int x = 0; x < matrix[0].length; x++) {
			for (int y = 0; y < matrix.length; y++)
				transposed[x][y] = matrix[y][x];
		}
		return transposed;
	}
}
