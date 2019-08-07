package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MargariteAndTheBestPresent {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int q = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= q; ++i) {
			long l = in.nextLong();
			long r = in.nextLong();
			long res = solve(l, r);
			System.out.println(res);
		}
		in.close();
	}

	private static long solve(long l, long r) {
		if (l == r) {
			return (l % 2 == 0) ? l : -l;
		}
		if ((r - l) % 2 == 1) {
			return (l % 2 == 1) ? (r - l + 1) / 2 : -(r - l + 1) / 2;
		} else {
			long res = (l % 2 == 0) ? l : -l;
			return res + solve(l + 1, r);
		}
	}
}
