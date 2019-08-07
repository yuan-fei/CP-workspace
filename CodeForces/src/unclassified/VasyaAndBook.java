package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class VasyaAndBook {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			int d = in.nextInt();
			long r = solve(n, x, y, d);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int n, int x, int y, int d) {
		if (Math.abs(x - y) % d == 0) {
			return Math.abs(x - y) / d;
		} else {
			int l = -1;
			int r = -1;
			if ((y - 1) % d == 0) {
				l = (y - 1) / d;
				l += Math.ceil(1.0 * (x - 1) / d);
			}
			if ((n - y) % d == 0) {
				r = (n - y) / d;
				r += Math.ceil(1.0 * (n - x) / d);
			}
			if (l == -1) {
				return r;
			} else if (r == -1) {
				return l;
			} else {
				return Math.min(l, r);
			}
		}

	}
}
