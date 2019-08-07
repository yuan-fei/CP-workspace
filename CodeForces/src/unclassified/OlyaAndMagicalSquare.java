package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class OlyaAndMagicalSquare {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int k = in.nextInt();
			int r = solve(n, k);
			if (r >= 0) {
				System.out.println("YES " + r);
			} else {
				System.out.println("NO");
			}

		}
		in.close();
	}

	private static int solve(int n, int k) {
		long count = 0;
		long last = 0;
		while (n > 0) {
			long tmp = count += 2 * last + 1;
			if (tmp < k) {
				count = tmp;
				last = last * 2 + 1;
				n--;
			} else {
				if (k < tmp) {

				}
				return n;
			}

		}
		return -1;
	}

	private long count(int n) {
		return (2 << (2 * n) - 1) / 3;
	}
}
