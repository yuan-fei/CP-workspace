package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EhabAndAnotherCnstructionProblem {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int x = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		int[] r = solve(x);
		if (r.length == 0) {
			System.out.println(-1);
		} else {
			System.out.println(r[0] + " " + r[1]);
		}

		in.close();
	}

	private static int[] solve(int x) {
		if (x == 1) {
			return new int[0];
		} else {
			return new int[] { x, x };
		}
	}
}
