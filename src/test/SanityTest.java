package test;

import java.util.ArrayList;

// check the result for sanity
// (each row is matched to maximum one column and vice versa)

public class SanityTest {
	public static boolean doCheck(ArrayList<int[]> matches) {
		boolean result = true;
		
		ArrayList<Integer> rows = new ArrayList<Integer>();
		ArrayList<Integer> cols = new ArrayList<Integer>();
		
		for (int i = 0; i < matches.size(); i++) {
			if (!rows.contains(matches.get(i)[0]) && !cols.contains(matches.get(i)[1])) {
				rows.add(matches.get(i)[0]);
				cols.add(matches.get(i)[1]);
			} else {
				result = false;
				break;
			}
		}
		
		return result;
	}
}
