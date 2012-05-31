package main;

import java.util.Random;

import tests.GlobalGreedyTest;
import tests.HungAlgTest;
import tests.RNNTest;
import tests.StabMarrTest;

public class ImplTest {
	// test the implementation of our algorithms (for correctness)
	public static void impltest(int number) {
		// generate a square matrix of random size
		// (for the external HA the solution is not optimal for non-square matrixes!)
		// all the other tests also succeed for non-sq matrixes
		Random rand = new Random(number);
		int tmp = rand.nextInt(100) + 1;
		double[][] matrix = new double[tmp][tmp];
		// this would be for a non square matrix
		//double[][] matrix = new double[rand.nextInt(100) + 1][rand.nextInt(100) + 1];

		// fill the matrix with random values
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = rand.nextDouble();
			}
		}

		// test on hungarian algorithm
		if (!HungAlgTest.test(matrix)) {
			System.out.println("Hungarian Algorithm failed... (" + number + ")");
		}
		
		// test for stable marriage
		if (!StabMarrTest.test(matrix)) {
			System.out.println("Stable Marriage Algorithm failed... (" + number + ")");
		}
		
		// test the RNN
		if (!RNNTest.test(matrix)) {
			System.out.println("RNN Algorithm failed... (" + number + ")");
		}
		
		// test the GlobalGreedy
		if (!GlobalGreedyTest.test(matrix)) {
			System.out.println("Global Greedy failed... (" + number + ")");
		}
		
	}
}
