package main;

import general.Analyzer;
import general.DataObj;
import general.MatrixGenerator;
import general.Printer;
import general.Timer;

import java.util.ArrayList;

import algorithms.GlobalGreedy;
import algorithms.RNN;

public class SimSearch {

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
			GlobalGreedy.match(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			//*/
			
			// running algorithm 3
			// ...
			
			// running algorithm 4
			// ...
			
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
			
			// running the algorithms
			
			// calculating algorithm 1
			// (reverse nearest neighbor)
			timer.startfresh();
			ArrayList<int[]> result = RNN.matchAll(matrix);
			result = RNN.matchAll(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			
			// analyze the result
			analyzer.analyze(result);
			System.out.println("Recall: " + Math.round( analyzer.getRecall() * 10000 ) / 100.0 + "%");
			System.out.println("Precision: " + Math.round( analyzer.getPrecision() * 10000) / 100.0 + "%");
			
			// print the result
			//Printer.printResults(matrix, result);
			
			
			// calculating algorithm 2
			// ...
			
			
			// calculating algorithm 3
			// ...
			
			
			// calculating algorithm 4
			// ...
			
			System.out.println("----------------");
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		runtimetest();
		qualitytest();
	}

}
