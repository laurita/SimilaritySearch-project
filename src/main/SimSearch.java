package main;

import java.util.ArrayList;

import algorithms.RNN;
import general.DataObj;
import general.Timer;

public class SimSearch {

	public static void main(String[] args) {
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
			timer.start();
			DataObj matrix = new DataObj(filename);
			timer.stop();
			System.out.println("ok");
			System.out.println("Loading took " + timer.getTime() + " ms.");
			
			// test that the matrix is correctly loaded
			/*System.out.print("  ");
			for (int x = 0; x < matrix.colCount; x++) {
				System.out.print(matrix.colNames[x] + " ");
			}			
			System.out.println();
			for (int y = 0; y < matrix.colCount; y++) {
				System.out.print(matrix.rowNames[y] + " ");
				for (int x = 0; x < matrix.rowCount; x++) {
					System.out.print(matrix.values[x][y] + " ");
				}
				System.out.println();
			}*/
			
			// running the algorithms
			// calculate the reverse nearest neighbor
			timer.reset();
			timer.start();
			ArrayList<int[]> result = RNN.matchAll(matrix);
			timer.stop();
			System.out.println("RNN took " + timer.getTime() + " ms.");
			
			// print the result
			/*for (int j = 0, len = result.size(); j < len; j++) {
				System.out.println(
					matrix.rowNames[result.get(j)[0]] + " - " + 
					matrix.rowNames[result.get(j)[1]] + " @ " + 
					matrix.values[result.get(j)[0]][result.get(j)[1]]
				);
			}*/
			
			
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

}
