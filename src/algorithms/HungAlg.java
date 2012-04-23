package algorithms;

import general.DataObj;

import java.util.ArrayList;

// the main issue described ~minute 37
// http://www.youtube.com/watch?v=BUGIhEecipE

// implementation taken form here
// http://csclab.murraystate.edu/bob.pilgrim/445/munkres.html

public class HungAlg {

	// the cost matrix
	protected final double[][] matrix;
	// special attributes of the values in the cost matrix
	// 0 ~ unmarked, 1 ~ starred, 2 ~ primed
	protected final int[][] blank;
	// covered colums
	protected final int[] colCov;
	protected final int[] rowCov;
	// results
	protected final ArrayList<int[]> results = new ArrayList<int[]>();

	// #############################################
	// ################## Helper ###################
	// #############################################
	// transpose a matrix
	private double[][] transposeMatrix(double[][] matrix) {
		double[][] transpose = new double[matrix[0].length][matrix.length];

		for (int x = 0; x < matrix[0].length; x++) {
			for (int y = 0; y < matrix.length; y++)
				transpose[x][y] = matrix[y][x];
		}
		return transpose;
	}

	// find smallest value in array and subtract it from all values in this
	// array
	private double[] subtractSV(double[] row) {
		// find value
		double value = row[0];
		for (int i = 1; i < row.length; i++) {
			if (value > row[i]) {
				value = row[i];
			}
		}
		// subtract value
		for (int i = 0; i < row.length; i++) {
			row[i] -= value;
		}
		return row;
	}

	// check if there is no starred zero in row or column of this pos
	private boolean checkForStarred(int x, int y) {
		for (int i = 0; i < matrix[0].length; i++) {
			if (blank[x][i] == 1 && matrix[x][i] == 0) {
				return false;
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			if (blank[i][y] == 1 && matrix[i][y] == 0) {
				return false;
			}
		}
		return true;
	}

	// print a matrix
	private void printMatrix() {
		for (int i = 0; i < colCov.length; i++) {
			System.out.print("   " + colCov[i]);
		}
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(rowCov[i]);
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(" " + (int) matrix[i][j] + "[" + blank[i][j]
						+ "]");
			}
			System.out.println();
		}
		System.out.println();
	}

	// find a starred element in this row
	private int findStarInRow(int row) {
		for (int i = 0; i < matrix[row].length; i++) {
			if (blank[row][i] == 1) {
				return i;
			}
		}
		return -1;
	}

	// find a zero that is not covered
	private int[] findNonCovZero() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; rowCov[i] == 0 && j < matrix[0].length; j++) {
				if (colCov[j] == 0) {
					if (matrix[i][j] == 0) {
						return new int[] { i, j };
					}
				}
			}
		}
		return new int[] { -1, -1 };
	}

	// find the smallest uncovered item
	private double findSmallestUncovItem() {
		double value = -1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; rowCov[i] == 0 && j < matrix[0].length; j++) {
				if (colCov[j] == 0) {
					if (value == -1 || value > matrix[i][j]) {
						value = matrix[i][j];
					}
				}
			}
		}
		return value;
	}
	
	// find starred zero in column
	private int findStarredZeroInCol(int col) {
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][col] == 0 && blank[i][col] == 1) {
				return i;
			}
		}
		return -1;
	}
	
	// find primed zero in row
	private int findPrimedZeroInRow(int row) {
		for (int i = 0; i < matrix[0].length; i++) {
			if (matrix[row][i] == 0 && blank[row][i] == 2) {
				return i;
			}
		}
		return -1;
	}

	// #############################################

	public HungAlg(double[][] matrixInp) {
		/*
		 * Step 0: Create an nxm matrix called the cost matrix in which each
		 * element represents the cost of assigning one of n workers to one of m
		 * jobs. Rotate the matrix so that there are at least as many columns as
		 * rows and let k=min(n,m).
		 */
		boolean needToInvert = false;
		if (matrixInp[0].length < matrixInp.length) {
			matrix = transposeMatrix(matrixInp);
			needToInvert = true;
		} else {
			matrix = matrixInp;
		}

		/* prepare: init all as unmarked */
		blank = new int[matrix.length][matrix[0].length];
		colCov = new int[matrix[0].length];
		rowCov = new int[matrix.length];

		printStep(0);

		/*
		 * Step 1: For each row of the matrix, find the smallest element and
		 * subtract it from every element in its row. Go to Step 2.
		 */
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = subtractSV(matrix[i]);
		}

		printStep(1);

		/*
		 * Step 2: Find a zero (Z) in the resulting matrix. If there is no
		 * starred zero in its row or column, star Z. Repeat for each element in
		 * the matrix. Go to Step 3.
		 */
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0 && checkForStarred(i, j)) {
					// 1 ~ starred
					blank[i][j] = 1;
				}
			}
		}
		int step = 3;
		printStep(2);
		while (step != 0) {
			switch (step) {
			case 3:
				/*
				 * Step 3: Cover each column containing a starred zero. If K
				 * columns are covered, the starred zeros describe a complete
				 * set of unique assignments. In this case, Go to DONE,
				 * otherwise, Go to Step 4.
				 */
				for (int j = 0; j < colCov.length; j++) {
					for (int i = 0; colCov[j] == 0 && i < matrix.length; i++) {
						if (blank[i][j] == 1) {
							colCov[j] = 1;
						}
					}
				}
				int sum = 0;
				for (int i = 0; i < colCov.length; i++) {
					sum += colCov[i];
				}
				if (sum == rowCov.length) {
					step = 0;
				} else {
					step = 4;
				}
				printStep(3);
				break;
			case 4:
				/*
				 * Step 4: Find a noncovered zero and prime it. If there is no
				 * starred zero in the row containing this primed zero, Go to
				 * Step 5. Otherwise, cover this row and uncover the column
				 * containing the starred zero. Continue in this manner until
				 * there are no uncovered zeros left. Save the smallest
				 * uncovered value and Go to Step 6.
				 */
				boolean done = false;
				while (!done) {
					int[] zero = findNonCovZero();
					// System.out.println(zero[0] + ", " + zero[1]);
					if (zero[0] != -1) {
						blank[zero[0]][zero[1]] = 2;
						int starred = findStarInRow(zero[0]);
						if (starred != -1) {
							// possible error ...
							rowCov[zero[0]] = 1;
							colCov[starred] = 0;
						} else {
							done = true;
							step = 5;
						}
					} else {
						done = true;
						step = 6;
					}
				}
				printStep(4);
				break;
			case 5:
				/*
				 * Step 5: Construct a series of alternating primed and starred
				 * zeros as follows. Let Z0 represent the uncovered primed zero
				 * found in Step 4. Let Z1 denote the starred zero in the column
				 * of Z0 (if any). Let Z2 denote the primed zero in the row of
				 * Z1 (there will always be one). Continue until the series
				 * terminates at a primed zero that has no starred zero in its
				 * column. Unstar each starred zero of the series, star each
				 * primed zero of the series, erase all primes and uncover every
				 * line in the matrix. Return to Step 3.
				 */
				boolean loop = true;
				ArrayList<int[]> series = new ArrayList<int[]>();
				int[] Z0 = findNonCovZero();
				series.add(Z0);
				while (loop) {
					int Z1 = findStarredZeroInCol(Z0[1]);
					if (Z1 != -1) {
						series.add(new int[] {Z1, Z0[1]});
						int Z2 = findPrimedZeroInRow(Z1);
						Z0 = new int[] {Z1, Z2};
						series.add(Z0);
					} else {
						loop = false;
					}
				}
				
				for (int i = 0; i < series.size(); i++) {
					if (blank[series.get(i)[0]][series.get(i)[1]] == 1) {
						blank[series.get(i)[0]][series.get(i)[1]] = 0;
					}
					if (blank[series.get(i)[0]][series.get(i)[1]] == 2) {
						blank[series.get(i)[0]][series.get(i)[1]] = 1;
					}
					//System.out.println(series.get(i)[0] + ", " + series.get(i)[1]);
				}
				
				// erase all primes
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[0].length; j++) {
						if (blank[i][j] == 2) {
							blank[i][j] = 0;
						}
					}
				}
				
				// uncover
				for (int i = 0; i < colCov.length; i++) {
					colCov[i] = 0;
				}
				for (int i = 0; i < rowCov.length; i++) {
					rowCov[i] = 0;
				}
				
				printStep(5);
				step = 3;
				break;
			case 6:
				// unclear - what if there was no value found?
				/*
				 * Step 6: Add the value found in Step 4 to every element of
				 * each covered row, and subtract it from every element of each
				 * uncovered column. Return to Step 4 without altering any
				 * stars, primes, or covered lines.
				 */
				// find the smallest value
				double minval = findSmallestUncovItem();
				// System.out.println(minval);
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[0].length; j++) {
						if (rowCov[i] == 1) {
							matrix[i][j] += minval;
						}
						if (colCov[j] == 0) {
							matrix[i][j] -= minval;
						}
					}
				}
				printStep(6);
				step = 4;
				break;
			}
		}
		
		/*
		 * DONE: Assignment pairs are indicated by the positions of the starred
		 * zeros in the cost matrix. If C(i,j) is a starred zero, then the element
		 * associated with row i is assigned to the element associated with column
		 * j.
		 */

		// we are done
		for (int i = 0; i < blank.length; i++) {
			for (int j = 0; j < blank[i].length; j++) {
				if (blank[i][j] == 1) {
					// check if the matrix was transposed before
					if (needToInvert) {
						results.add(new int[] {j,i});
					} else {
						results.add(new int[] {i,j});
					}
					break;
				}
			}
		}

	}

	private void printStep(int i) {
		/*
		System.out.println("step " + i);
		printMatrix();
		*/
	}

	public ArrayList<int[]> getMatches() {
		return results;
	}

}
