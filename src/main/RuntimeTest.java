package main;

import general.DataObj;
import general.MatrixGenerator;
import general.Printer;
import general.Timer;
import algorithms.GlobalGreedy;
import algorithms.HungAlg;
import algorithms.StableMarriage;

public class RuntimeTest {
	
	/* performs runtime tests */
	
	public static void runtimetest(int start, int stepsize, int steps, String[] usedAlgorithms) {

		// find out the algorithms to use
		boolean useRNN = false;
		boolean useGG = false;
		boolean useSM = false;
		boolean useHA = false;
		for (int i = 0; i < usedAlgorithms.length; i++) {
			if (usedAlgorithms[i].equals("RNN")) {
				useRNN = true;
			}
			if (usedAlgorithms[i].equals("GG")) {
				useGG = true;
			}
			if (usedAlgorithms[i].equals("SM")) {
				useSM = true;
			}
			if (usedAlgorithms[i].equals("HA")) {
				useHA = true;
			}
		}
		
		// prepare timer and variables for testing
		System.out.println("Testing runtime.");
		int[] testsizes = new int[steps];
		for (int i = 0; i < steps; i++) {
			testsizes[i] = start + i * stepsize;
		}
		Timer timer = new Timer();
		int[][] RNN = new int[testsizes.length][];
		int[][] GG = new int[testsizes.length][];
		int[][] SM = new int[testsizes.length][];
		int[][] HA = new int[testsizes.length][];
		
		// run tests for the different matrix sizes
		for (int i = 0; i < testsizes.length; i++) {
			System.out.println("Testing for size "
					+ String.valueOf(testsizes[i]) + ".");
			System.out.print("Generating matrix...");
			DataObj matrix = MatrixGenerator.generate(testsizes[i], testsizes[i]);
			//DataObj matrix = MatrixGenerator.generateZipf(testsizes[i], testsizes[i]);
			//DataObj matrix = MatrixGenerator.generateLog(testsizes[i], testsizes[i]);
			//Printer.printMatrix(matrix);
			System.out.println("ok");
			System.out.println("Running algorithms:");

			// running algorithm 1 (Reverse neareast neighbour)
			if (useRNN) {
				timer.startfresh();
				algorithms.RNN.match(matrix.values);
				timer.stop();
				RNN[i] = new int[] {testsizes[i], (int) timer.getTime()};
				System.out.println("RNN took " + timer.getTime() + " ms.");
			}

			// running algorithm 2 (global greedy)
			if (useGG) {
				timer.startfresh();
				GlobalGreedy.match(matrix.values);
				timer.stop();
				GG[i] = new int[] {testsizes[i], (int) timer.getTime()};
				System.out.println("GG took " + timer.getTime() + " ms.");
			}

			// running algorithm 3 (stable marriage)
			if (useSM) {
				timer.startfresh();
				new StableMarriage(matrix.values).getMatches();
				timer.stop();
				SM[i] = new int[] {testsizes[i], (int) timer.getTime()};
				System.out.println("Stable Marriage took " + timer.getTime()
						+ " ms.");
			}

			// running algorithm 4 (hungarian)
			if (useHA) {
				timer.startfresh();
				new HungAlg(matrix.values).getMatches();
				timer.stop();
				HA[i] = new int[] {testsizes[i], (int) timer.getTime()};
				System.out.println("Hungarian took " + timer.getTime() + " ms.");
			}
		}
		System.out.println();
		
		
		// print the maple output
		System.out.println("############Maple Code###########");
		System.out.print("with(plots):");
		if (useRNN) {
			System.out.print("RNN := [");
			for (int i = 0; i < RNN.length; i++) {
				System.out.print("[" + RNN[i][0] + ", " + RNN[i][1] + "]" + 
						(i + 1 < RNN.length ? ", " : ""));
				
			}
			System.out.print("]:");
		}
		
		if (useGG) {
			System.out.print("GG := [");
			for (int i = 0; i < GG.length; i++) {
				System.out.print("[" + GG[i][0] + ", " + GG[i][1] + "]" + 
						(i + 1 < GG.length ? ", " : ""));
			}
			System.out.print("]:");
		}
		
		if (useSM) {
			System.out.print("SM := [");
			for (int i = 0; i < SM.length; i++) {
				System.out.print("[" + SM[i][0] + ", " + SM[i][1] + "]" + 
						(i + 1 < GG.length ? ", " : ""));
			}
			System.out.print("]:");
		}
		
		if (useHA) {
			System.out.print("HA := [");
			for (int i = 0; i < HA.length; i++) {
				System.out.print("[" + HA[i][0] + ", " + HA[i][1] + "]" + 
						(i + 1 < GG.length ? ", " : ""));
			}
			System.out.print("]:");
		}
		
		System.out.println("display({" +
				"logplot([], axis[2] = [gridlines])" +
					(useRNN?", logplot(RNN, axis[2] = [gridlines], color = blue)":"") +
					(useGG?", logplot(GG, color = green)":"") +
					(useSM?", logplot(SM, color = black)":"") +
					(useHA?", logplot(HA)":"") +
				"}, " +
				"title = \"" +
					(useRNN?"blue = RNN":"") +
					(useGG?", green = GG":"") +
					(useSM?", black = SM":"") +
					(useHA?", red = HA\");":"") + 
				"\");");
		
		System.out.println("############################");
	}

}
