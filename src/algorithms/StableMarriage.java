package algorithms;

import java.util.ArrayList;

import util.MatrixTools;
import general.DataObj;

public class StableMarriage {

	// reference:
	// College Admissions and the Stability of Marriage, D. Gale; L. S. Shapley
	// The American Mathematical Monthly, Vol. 69, No. 1. (Jan., 1962), pp. 9-15.
	
	public static ArrayList<int[]> match(double[][] distances) {
		ArrayList<int[]> M = new ArrayList<int[]>();
		boolean transposed = false;
		if (distances.length < distances[0].length) {
			distances = MatrixTools.transpose(distances);
			transposed = true;
		}
		int nr_cols = distances[0].length;
		int nr_rows = distances.length;
		int[][] rowwise_ranks = StableMarriage.rankRowwise(distances);
		int[][] columnwise_ranks = StableMarriage.rankColumnwise(distances);
		boolean allColumnsReceivedProposal = false;
		boolean[][] proposals = new boolean[nr_rows][nr_cols];
		boolean[][] rejections = new boolean[nr_rows][nr_cols];
		// initial proposal
		int proposal_index;
		for (int i = 0; i < nr_rows; i++) {
			proposal_index = findNextRowProposal(rowwise_ranks[i], rejections[i]);
			proposals[i][proposal_index] = true;
		}
		int best_proposal_index;
		while (!allColumnsReceivedProposal) {
			boolean[] rejected = new boolean[nr_rows];
			// reject all, but first choice proposals, unmark proposals and mark rejections
			for (int j = 0; j < nr_cols; j++) {
				if (StableMarriage.hasManyProposals(proposals, j)) {
					best_proposal_index = findBestProposal(columnwise_ranks, proposals, j);
					for (int i = 0; i < nr_rows; i++) {
						if (proposals[i][j] && i != best_proposal_index) {
							proposals[i][j] = false;
							rejections[i][j] = true;
							rejected[i] = true;
						}
					}
				}
			}
			// all rejected propose once more
			for (int i =  0; i < nr_rows; i++) {
				if (rejected[i]) {
					proposal_index = findNextRowProposal(rowwise_ranks[i], rejections[i]);
					proposals[i][proposal_index] = true;
				}
			}
			// check, if all columns received proposal
			allColumnsReceivedProposal = true;
			for (int j = 0; j < nr_cols; j++) {
				if (!hasProposal(proposals, j)) {
					allColumnsReceivedProposal = false;
				}
			}
		}
		// put all matches to ArrayList
		if (transposed) {
			for (int j = 0; j < nr_cols; j++) {
				int[] match = new int[2];
				match[1] = findBestProposal(columnwise_ranks, proposals, j);
				match[0] = j;
				M.add(match);
			}
		}
		else {
			for (int j = 0; j < nr_cols; j++) {
				int[] match = new int[2];
				match[0] = findBestProposal(columnwise_ranks, proposals, j);
				match[1] = j;
				M.add(match);
			}
		}
		
		return M;
	}
	
	
	private static int findMin(double[] array, boolean[] marked) {
		double min = 2; // starting min value, because distance are <= 1
		int min_i = 0;
		for (int i = 0; i < array.length; i++) {
			if (!marked[i] && array[i] < min) {
				min_i = i;
				min = array[i];
			}
		}
		return min_i;
	}
	
	private static int[][] rankRowwise(double[][] distances) {
		int nr_cols = distances[0].length;
		int nr_rows = distances.length;
		int[][] rowwise_ranks = new int[nr_rows][nr_cols];
		for (int i = 0; i < nr_rows; i++) {
			boolean[] marked = new boolean[nr_cols]; ;
			double[] row = distances[i];
			int min_index;
			for (int j = 1; j <= nr_cols; j++) {
				min_index = findMin(row, marked);
				marked[min_index] = true;
				rowwise_ranks[i][min_index] = j;
			}
		}
		return rowwise_ranks;
	}
	
	private static int[][] rankColumnwise(double[][] distances) {
		int nr_cols = distances[0].length;
		int nr_rows = distances.length;
		int[][] columnwise_ranks = new int[nr_rows][nr_cols];
		for (int j = 0; j < nr_cols; j++) {
			boolean[] marked = new boolean[nr_rows]; ;
			double[] column = new double[nr_rows];
			for (int i = 0; i < nr_rows; i++) {
				column[i] = distances[i][j];
			}
			int min_index;
			for (int i = 1; i <= nr_rows; i++) {
				min_index = findMin(column, marked);
				marked[min_index] = true;
				columnwise_ranks[min_index][j] = i;
			}
		}
		return columnwise_ranks;
	}
	
	private static boolean hasManyProposals(boolean[][] proposals, int col_index) {
		int count = 0; 
		int row_index = 0;
		boolean result;
		while (count < 2 &&  row_index < proposals.length) {
			if (proposals[row_index][col_index] == true) {
				count += 1;
			}
			row_index += 1;
		}
		if (count == 2) {
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	private static boolean hasProposal(boolean[][] proposals, int col_index) {
		int row_index = 0;
		boolean has = false;
		while (row_index < proposals.length) {
			if (proposals[row_index][col_index] == true) {
				has = true;
				break;
			}
			row_index += 1;
		}
		return has;
	}
	
	private static int findNextRowProposal(int[] row_ranks, boolean[] row_rejections) {
		int nr_cols = row_ranks.length;
		int min = nr_cols + 1; // starting min value, because ranks are <+ nr_cols
		int min_i = 0;
		for (int i = 0; i < nr_cols; i++) {
			if (!row_rejections[i] && row_ranks[i] < min) {
				min_i = i;
				min = row_ranks[i];
			}
		}
		return min_i;
	}
	
	private static int findBestProposal(int[][] columnwise_ranks, boolean[][] proposals, int col_index) {
		int i = 0;
		while (!proposals[i][col_index]) {
			i++;
		}
		int best = columnwise_ranks[i][col_index];
		int best_index = i;
		while (i < columnwise_ranks.length) {
			if (proposals[i][col_index] && columnwise_ranks[i][col_index] < best) {
				best = columnwise_ranks[i][col_index];
				best_index = i;
			}
			i++;
		}
		return best_index;
	}
}
