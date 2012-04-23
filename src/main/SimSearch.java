package main;

import general.Analyzer;
import general.DataObj;
import general.MatrixGenerator;
import general.Timer;

import java.util.ArrayList;
import java.util.Random;

import test.HungAlgTest;
import test.StabMarrTest;
import util.ArrayTools;
import algorithms.GlobalGreedy;
import algorithms.HungAlg;
import algorithms.RNN;
import algorithms.StableMarriage;

public class SimSearch {

	public static void runtimetest() {

		System.out.println("Testing runtime.");
		int[] testsizes = new int[] { 10, 100, 1000 };
		Timer timer = new Timer();

		for (int i = 0; i < testsizes.length; i++) {
			System.out.println("Testing for size "
					+ String.valueOf(testsizes[i]) + ".");
			System.out.print("Generating matrix...");
			DataObj matrix = MatrixGenerator.generate(testsizes[i],
					testsizes[i]);
			System.out.println("ok");
			System.out.println("Running algorithms:");

			// running algorithm 1 (Reverse neareast neighbour)
			// *
			timer.startfresh();
			RNN.matchAll(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			// */

			// running algorithm 2 (global greedy)
			// *
			timer.startfresh();
			GlobalGreedy.match(matrix);
			timer.stop();
			System.out.println("GG took " + timer.getTime() + " ms.");
			// */

			// running algorithm 3 (stable marriage)
			// *
			timer.startfresh();
			StableMarriage.match(matrix.values);
			timer.stop();
			System.out.println("Stable Marriage took " + timer.getTime()
					+ " ms.");
			// */

			// running algorithm 4 (hungarian)
			// *
			timer.startfresh();
			new HungAlg(matrix.values).getMatches();
			timer.stop();
			System.out.println("Hungarian took " + timer.getTime() + " ms.");
			// */
		}
		System.out.println();
	}

	public static void qualitytest() {
		// the files to test on
		// String[] data = new String[] {"testmatrix.txt"};
		String[] data = new String[] { "Np3q2.dm", "Nw3p2q.dm", "Nw5p1q.dm",
				"Nw8p2q.dm" };

		// test for all the files
		for (int i = 0; i < data.length; i++) {
			// init for the run on this file
			Timer timer = new Timer();
			String filename = data[i];
			System.out.println("########################");
			System.out.println("File \"" + data[i] + "\"");
			System.out.println("########################");

			// loading the data
			System.out.print("Loading data... ");
			timer.startfresh();
			DataObj matrix = new DataObj(filename);
			timer.stop();
			System.out.println("ok");
			System.out.println("Loading took " + timer.getTime() + " ms.");

			// test that the matrix is correctly loaded
			// Printer.printMatrix(matrix);

			// load the analyzer
			Analyzer analyzer = new Analyzer(matrix);
			ArrayList<int[]> result;

			// running the algorithms

			// calculating algorithm 1 (reverse nearest neighbor)
			timer.startfresh();
			result = RNN.matchAll(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			// analyze the result analyzer.analyze(result);
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");
			// print the result
			// Printer.printResults(matrix, result);

			// calculating algorithm 2 (global greedy)
			timer.startfresh();
			result = GlobalGreedy.match(matrix);
			timer.stop();
			System.out
					.println("Global Greedy took " + timer.getTime() + " ms.");
			// analyze the result
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Prezision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");

			// calculating algorithm 3 (stable marriage)
			timer.startfresh();
			result = StableMarriage.match(matrix.values);
			timer.stop();
			System.out.println("Stable Marriage took " + timer.getTime()
					+ " ms.");
			// analyze the result
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Prezision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");

			// calculating algorithm 4 (hungarian)
			// *
			timer.startfresh();
			result = new HungAlg(matrix.values).getMatches();
			timer.stop();
			System.out.println("HungAlg took " + timer.getTime() + " ms.");
			// analyze the result
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");
			// */

			System.out.println("----------------");
			System.out.println();
		}
	}

	// test the implementation of our algorithms (for correctness)
	public static void impltest(int number) {
		// generate a matrix of random size
		Random rand = new Random(number);
		// double[][] matrix = new double[10][10];
		int tmp = rand.nextInt(100) + 1;
		double[][] matrix = new double[tmp][tmp];
		// for non-square matrixes the other implementation is not optimized
		// our solution is always better!
		//double[][] matrix = new double[rand.nextInt(100) + 1][rand.nextInt(100) + 1];

		// fill the matrix with random values
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = rand.nextDouble();
			}
		}
		
		/*matrix = new double[][] {
				{10,19,8,15},
				{10,18,7,17},
				{13,16,9,14},
				{12,19,8,18},
				{14,17,10,19}
		};*/

		// test on hungarian algorithm
		//*
		if (!HungAlgTest.test(matrix)) {
			System.out.println("Hungarian Algorithm failed... (" + number + ")");
		}
		//*/
		
		// test for stable marriage
		/*
		if (!StabMarrTest.test(matrix)) {
			System.out.println("Stable Marriage Algorithm failed... (" + number + ")");
		}
		//*/
	}

	public static void main(String[] args) {
		 runtimetest();
		// qualitytest();
		/*
		for (int i = 0; i < 100; i++) {
			impltest(i);
		}
		//*/
	}
}
