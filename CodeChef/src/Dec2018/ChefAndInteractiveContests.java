package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChefAndInteractiveContests {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		int r = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String res = (n >= r) ? "Good boi" : "Bad boi";
			System.out.println(res);
		}
		in.close();
	}
}
