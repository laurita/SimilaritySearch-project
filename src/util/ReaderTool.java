package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderTool {
	// get a string from the input
	public static String getString() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String text = "";
		try {
			if ((text = br.readLine()) != null) {
				return text;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// get an integer from the input
	public static Integer getNumber() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String text = "";
		try {
			if ((text = br.readLine()) != null) {
				return Integer.valueOf(text);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
