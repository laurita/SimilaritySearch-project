package main;

import general.Analyzer;
import general.DataObj;
import general.MatrixGenerator;
import general.Timer;

import java.util.ArrayList;
import java.util.Random;

import test.GlobalGreedyTest;
import test.HungAlgTest;
import test.RNNTest;
import test.StabMarrTest;
import algorithms.GlobalGreedy;
import algorithms.HungAlg;
import algorithms.RNN;
import algorithms.StableMarriage;

public class SimSearch {

	public static void runtimetest() {

		System.out.println("Testing runtime.");
		//int[] testsizes = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000 };
		int[] testsizes = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
				1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900};
				//1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000,
				//2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000};
		//int[] testsizes = new int[] { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
		Timer timer = new Timer();
		
		// holds the values
		int[][] RNN = new int[testsizes.length][];
		int[][] GG = new int[testsizes.length][];
		int[][] SM = new int[testsizes.length][];
		int[][] HA = new int[testsizes.length][];

		for (int i = 0; i < testsizes.length; i++) {
			System.out.println("Testing for size "
					+ String.valueOf(testsizes[i]) + ".");
			System.out.print("Generating matrix...");
			DataObj matrix = MatrixGenerator.generate(testsizes[i],
					testsizes[i]);
			System.out.println("ok");
			System.out.println("Running algorithms:");

			// running algorithm 1 (Reverse neareast neighbour)
			// runtime should be linear but is n^2
			// but how can that be if the matrix is n x n ?!?!
			/*
			timer.startfresh();
			algorithms.RNN.match(matrix.values);
			timer.stop();
			RNN[i] = new int[] {testsizes[i], (int) timer.getTime()};
			System.out.println("RNN took " + timer.getTime() + " ms.");
			// */

			// running algorithm 2 (global greedy)
			// note: runtime seems ok
			//*
			timer.startfresh();
			GlobalGreedy.match(matrix.values);
			timer.stop();
			GG[i] = new int[] {testsizes[i], (int) timer.getTime()};
			System.out.println("GG took " + timer.getTime() + " ms.");
			// */

			// running algorithm 3 (stable marriage)
			// runtime should be n^2 (see example) but is n^3
			/*
			timer.startfresh();
			//new StableMarriage2(testsizes[i]);
			StableMarriage.match(matrix.values);
			timer.stop();
			SM[i] = new int[] {testsizes[i], (int) timer.getTime()};
			System.out.println("Stable Marriage took " + timer.getTime()
					+ " ms.");
			// */

			// running algorithm 4 (hungarian)
			// runtime is n^4, but all other implementation also have the same runtime
			// not sure how this can be reduced to n^3
			/*
			timer.startfresh();
			//HungarianAlgorithm2.hgAlgorithm(matrix.values, "min");
			new HungAlg(matrix.values).getMatches();
			timer.stop();
			HA[i] = new int[] {testsizes[i], (int) timer.getTime()};
			System.out.println("Hungarian took " + timer.getTime() + " ms.");
			// */
		}
		System.out.println();
		// print the maple output
		System.out.println("############Maple###########");
		System.out.print("with(plots):");
		/*
		System.out.print("RNN := [");
		for (int i = 0; i < RNN.length; i++) {
			System.out.print("[" + RNN[i][0] + ", " + RNN[i][1] + "]" + 
					(i + 1 < RNN.length ? ", " : ""));
			
		}
		System.out.print("]:");
		*/
		
		System.out.print("GG := [");
		for (int i = 0; i < GG.length; i++) {
			System.out.print("[" + GG[i][0] + ", " + GG[i][1] + "]" + 
					(i + 1 < GG.length ? ", " : ""));
		}
		System.out.print("]:");
		
		/*
		System.out.print("SM := [");
		for (int i = 0; i < SM.length; i++) {
			System.out.print("[" + SM[i][0] + ", " + SM[i][1] + "]" + 
					(i + 1 < GG.length ? ", " : ""));
		}
		System.out.print("]:");
		
		System.out.print("HA := [");
		for (int i = 0; i < HA.length; i++) {
			System.out.print("[" + HA[i][0] + ", " + HA[i][1] + "]" + 
					(i + 1 < GG.length ? ", " : ""));
		}
		System.out.print("]:");
		//*/
		
		System.out.println("display({logplot(RNN, axis[2] = [gridlines], color = blue), logplot(GG, color = green), logplot(SM, color = black), logplot(HA)}, title = \"blue = RNN, green = GG, black = SM, red = HA\");");
		
		System.out.println("############################");
	}

	public static void qualitytest() {
		// the files to test on
		// String[] data = new String[] {"testmatrix2.txt"};
		String[] data = new String[] { "Np3q2.dm", "Nw3p2q.dm", "Nw5p1q.dm", "Nw8p2q.dm" };

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
			
			//System.out.println(matrix.values[72][299]);
			
			// test that the matrix is correctly loaded
			// Printer.printMatrix(matrix);

			// load the analyzer
			Analyzer analyzer = new Analyzer(matrix);
			ArrayList<int[]> result;

			// running the algorithms

			// calculating algorithm 1 (reverse nearest neighbor)
			/*
			should be:
			Distance Correct Recall (%) Precision (%) F-measure Runtime (s)
			Windowed pq-grams (w=8, p=2, q=2) 248 82.9 98.4 0.900 24.9
			Windowed pq-grams (w=5, p=1, q=2) 245 81.9 98.8 0.896 12.7
			Windowed pq-grams (w=3, p=2, q=2) 240 80.3 98.8 0.886 7.4
			*/
			
			timer.startfresh();
			result = RNN.match(matrix.values);
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
			result = GlobalGreedy.match(matrix.values);
			timer.stop();
			System.out
					.println("Global Greedy took " + timer.getTime() + " ms.");
			// analyze the result
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
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
					.println("Precision: "
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
		
		/*
		matrix = new double[][] {
				{10,19,8,15},
				{10,18,7,17},
				{13,16,9,14},
				{12,19,8,18},
				{14,17,10,19}
		};
		//*/

		// test on hungarian algorithm
		//*
		if (!HungAlgTest.test(matrix)) {
			System.out.println("Hungarian Algorithm failed... (" + number + ")");
		}
		//*/
		
		// test for stable marriage
		//*
		if (!StabMarrTest.test(matrix)) {
			System.out.println("Stable Marriage Algorithm failed... (" + number + ")");
		}
		//*/
		
		// test the RNN
		//*
		if (!RNNTest.test(matrix)) {
			System.out.println("Stable Marriage Algorithm failed... (" + number + ")");
		}
		//*/
		
		// test the GlobalGreedy
		//*
		if (!GlobalGreedyTest.test(matrix)) {
			System.out.println("Global Greedy failed... (" + number + ")");
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
