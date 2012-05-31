package main;

import general.Analyzer;
import general.DataObj;
import general.Timer;

import java.util.ArrayList;

import algorithms.GlobalGreedy;
import algorithms.HungAlg;
import algorithms.RNN;
import algorithms.StableMarriage;

public class QualityTest {
	public static void qualitytest(String[] data) {

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

			// load the analyzer and init var
			Analyzer analyzer = new Analyzer(matrix);
			ArrayList<int[]> result;

			// calculating algorithm 1 (RNN)	
			timer.startfresh();
			result = RNN.match(matrix.values);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");
			System.out
			.println("F1 Measure: "
					+ Math.round(analyzer.getFMeasure() * 10000)
					/ 100.0 + "%");

			// calculating algorithm 2 (global greedy)
			timer.startfresh();
			result = GlobalGreedy.match(matrix.values);
			timer.stop();
			System.out
					.println("Global Greedy took " + timer.getTime() + " ms.");
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");
			System.out
			.println("F1 Measure: "
					+ Math.round(analyzer.getFMeasure() * 10000)
					/ 100.0 + "%");

			// calculating algorithm 3 (stable marriage)
			timer.startfresh();
			result = new StableMarriage(matrix.values).getMatches();
			timer.stop();
			System.out.println("Stable Marriage took " + timer.getTime()
					+ " ms.");
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");
			System.out
			.println("F1 Measure: "
					+ Math.round(analyzer.getFMeasure() * 10000)
					/ 100.0 + "%");

			// calculating algorithm 4 (hungarian)
			timer.startfresh();
			result = new HungAlg(matrix.values).getMatches();
			timer.stop();
			System.out.println("HungAlg took " + timer.getTime() + " ms.");
			analyzer.analyze(result);
			System.out.println("Recall: "
					+ Math.round(analyzer.getRecall() * 10000) / 100.0 + "%");
			System.out
					.println("Precision: "
							+ Math.round(analyzer.getPrecision() * 10000)
							/ 100.0 + "%");
			System.out
			.println("F1 Measure: "
					+ Math.round(analyzer.getFMeasure() * 10000)
					/ 100.0 + "%");

			System.out.println("----------------");
			System.out.println();
		}
	}
}
