package r1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class BitParty {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int r = in.nextInt();
			int b = in.nextInt();
			int c = in.nextInt();
			long[][] p = getLongArr(in, c, 3);
			long res = solve(r, b, c, p);
			System.out.println("Case #" + i + ": " + res);
		}
		in.close();
	}

	private static long solve(long r, long b, int c, long[][] p) {
		long low = 1;
		long high = 2L << 61;
		while (high - low > 1) {
			long mid = low + (high - low) / 2;
			if (feasible(r, b, c, p, mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		if (feasible(r, b, c, p, low)) {
			return low;
		} else {
			return high;
		}
	}

	private static boolean feasible(long r, long b, int c, long[][] p, long t) {
		long[] maxBit = new long[c];
		for (int i = 0; i < maxBit.length; i++) {
			maxBit[i] = -Math.min(p[i][0], Math.max(t - p[i][2], 0) / p[i][1]);
		}
		Arrays.sort(maxBit);
		for (int i = 0; i < maxBit.length; i++) {
			b += maxBit[i];
			r--;
			if (r <= 0 && b > 0) {
				return false;
			}
			if (r >= 0 && b <= 0) {
				return true;
			}
		}
		return false;
	}

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
		}
		return arr;
	}

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}
}
