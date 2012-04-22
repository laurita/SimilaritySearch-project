package main;

import general.Analyzer;
import general.DataObj;
import general.MatrixGenerator;
import general.Printer;
import general.Timer;

import java.util.ArrayList;
import java.util.Random;

import algorithms.GlobalGreedy;
import algorithms.HungAlg;
import algorithms.HungarianAlgorithm;
import algorithms.RNN;

public class SimSearch {
	
	public static void printSum(double[][] matrix, ArrayList<int[]> result) {
		double sum = 0;
		for (int i = 0, len = result.size(); i < len; i++) {
			sum += matrix[result.get(i)[0]][result.get(i)[0]];
		}
		System.out.println("Sum is: " + sum);
	}

	public static void runtimetest() {
		
		System.out.println("Testing runtime.");
		int[] testsizes = new int[]{10,100,1000};
		Timer timer = new Timer();
		
		
		for (int i = 0; i < testsizes.length; i++) {
			System.out.println("Testing for size " + String.valueOf(testsizes[i]) + ".");
			System.out.print("Generating matrix...");
			DataObj matrix = MatrixGenerator.generate(testsizes[i], testsizes[i]);
			System.out.println("ok");
			System.out.println("Running algorithms:");
			
			// running algorithm 1
			timer.startfresh();
			RNN.matchAll(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			
			// running algorithm 2
			//*
			timer.startfresh();
			GlobalGreedy.match(matrix);
			timer.stop();
			System.out.println("GG took " + timer.getTime() + " ms.");
			//*/
			
			// running algorithm 3
			// ...
			
			// running algorithm 4
			timer.startfresh();
			new HungAlg(matrix.values).getMatches();
			timer.stop();
			System.out.println("Hung took " + timer.getTime() + " ms.");
			
		}
		System.out.println();
	}
	
	public static void qualitytest() {
		// the files to test on
		//String[] data = new String[] {"testmatrix.txt"};
		String[] data = new String[] {"Np3q2.dm", "Nw3p2q.dm", "Nw5p1q.dm", "Nw8p2q.dm"};
		
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
			//Printer.printMatrix(matrix);
			
			// load the analyzer
			Analyzer analyzer = new Analyzer(matrix);
			ArrayList<int[]> result;
			
			// running the algorithms
			
			// calculating algorithm 1 (reverse nearest neighbor)
			/*
			timer.startfresh();
			result = RNN.matchAll(matrix);
			result = RNN.matchAll(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			
			// analyze the result
			analyzer.analyze(result);
			System.out.println("Recall: " + Math.round( analyzer.getRecall() * 10000 ) / 100.0 + "%");
			System.out.println("Precision: " + Math.round( analyzer.getPrecision() * 10000) / 100.0 + "%");
			
			// print the result
			//Printer.printResults(matrix, result);
			//*/
			
			// calculating algorithm 2
			// ...
			
			
			// calculating algorithm 3
			// ...
			
			// calculating algorithm 4
			//*
			timer.startfresh();
			result = new HungAlg(matrix.values).getMatches();
			timer.stop();
			System.out.println("HungAlg took " + timer.getTime() + " ms.");
			
			printSum(matrix.values, result);
			
			analyzer.analyze(result);
			System.out.println("Recall: " + Math.round( analyzer.getRecall() * 10000 ) / 100.0 + "%");
			System.out.println("Precision: " + Math.round( analyzer.getPrecision() * 10000) / 100.0 + "%");
			//*/
			
			// testing alternative implementation
			// http://konstantinosnedas.com/dev/soft/munkres.htm
			//*
			timer.startfresh();
			int[][] tmp = HungarianAlgorithm.hgAlgorithm(matrix.values, "min");
			timer.stop();
			result = new ArrayList<int[]>();
			for (int x = 0; x < tmp.length; x++) {
				result.add(tmp[x]);
			}
			System.out.println("Hung (test) took " + timer.getTime() + " ms.");
			
			printSum(matrix.values, result);
			
			analyzer.analyze(result);
			System.out.println("Recall: " + Math.round( analyzer.getRecall() * 10000 ) / 100.0 + "%");
			System.out.println("Precision: " + Math.round( analyzer.getPrecision() * 10000) / 100.0 + "%");
			//*/
			
			System.out.println("----------------");
			System.out.println();
		}
	}
	
	public static void HungTest() {
		/*double[][] matrix = new double[][] {
		{1,2,3},
		{2,4,6},
		{3,6,9}};*/

		/*double[][] matrix = new double[][] {
		    {0, 8, 9, 0, 89, 12},
		    {5, 0, 6, 6, 6, 12},
		    {4, 5, 0, 8, 8, 43},
		    {1, 2, 3, 0, 6, 7},
		    {19, 67, 54, 2, 0, 6},
		    {1, 2, 6, 8, 8, 0}
		};*/
		
		double[][] matrix = new double[4][3];
		
		// random
		Random rand = new Random();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = rand.nextDouble();
			}
		}
		
		
		// test of hungarian
		
		ArrayList<int[]> result = new HungAlg(matrix).getMatches();
		/*
		for (int i=0; i<result.size(); i++)
		{
			System.out.printf("array(%d,%d)\n", (result.get(i)[0]+1), (result.get(i)[1]+1));
		}
		//*/
		
		
		// the real solution
		int[][] assignment = HungarianAlgorithm.hgAlgorithm(matrix, "min");
		/*
		for (int i=0; i<assignment.length; i++)
		{
			System.out.printf("array(%d,%d)\n", (assignment[i][0]+1), (assignment[i][1]+1));
		}
		//*/
		
		//*
		if (assignment.length != result.size()) {
			System.out.println("Error (1)");
		} else {
			for (int i=0; i < assignment.length; i++)
			{
				if (assignment[i][0] != result.get(i)[0] || assignment[i][1] != result.get(i)[1]) {
					System.out.println("Error (2)");
				}
			}
		}
		//*/
	}
	
	public static void main(String[] args) {
		//runtimetest();
		qualitytest();
		/*for (int i = 0; i < 1; i++) {
			HungTest();
		}*/
	}

}
