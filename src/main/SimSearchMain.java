package main;

import java.io.File;
import java.io.IOException;

import util.ReaderTool;

/*
 * The main class that deals with the user input and 
 * inits the desired functionality
 */

public class SimSearchMain {

	public static void main(String[] args) {
		
		// decide which test to do
		System.out.println("Select mode (q = quality, r = runtime, v = verify):");
		String input = ReaderTool.getString();
		if (input.equals("q")) {
			// quality test
			System.out.println("Selected quality test.");		
			System.out.println("Select matrix input files name (same folder). Separate files with \", \".");
			System.out.println("Sample: \"Np3q2.dm, Nw3p2q.dm, Nw5p1q.dm, Nw8p2q.dm\"");
			// get the desired files
			String[] matrixfiles = ReaderTool.getString().split(", ");
			boolean error = false;
			for (int i = 0; i < matrixfiles.length; i++) {
				if (!new File(matrixfiles[i]).exists()) {
					System.out.println("Error. Matrix file \"" + matrixfiles[i] + "\" not found.");
					error = true;
				}
			}
			if (!error) {
				// start quality test with these files
				QualityTest.qualitytest(matrixfiles);
			}
		} else if (input.equals("r")) {
			// runtime test
			System.out.println("Selected runtime test.");
			// start n
			System.out.println("Enter n to start with:");
			int start = ReaderTool.getNumber();
			// step size
			System.out.println("Enter stepsize:");
			int stepsize = ReaderTool.getNumber();
			// steps
			System.out.println("Enter number of steps (minimum one):");
			int steps = ReaderTool.getNumber();
			// select algorithms
			System.out.println("Enter algorithms to use, pick from {RNN, GG, SM, HA}.");
			System.out.println("Separate by \", \".");
			String[] algorithms = ReaderTool.getString().split(", ");
			// start runtime test
			RuntimeTest.runtimetest(start, stepsize, steps, algorithms);
		} else {
			// selected verification test of implementation
			System.out.println("Starting verification of algorithms...");
			for (int i = 0; i < 1000; i++) {
				ImplTest.impltest(i);
			}
			System.out.println("Verification finished.");
		}
		
		// wait for user input to close (the window)
		try {
			System.out.println("Press Enter to exit.");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
