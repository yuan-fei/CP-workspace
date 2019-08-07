package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MashaAndTwoFriends {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[] white = new int[4];
			for (int j = 0; j < white.length; j++) {
				white[j] = in.nextInt();
			}
			int[] black = new int[4];
			for (int j = 0; j < white.length; j++) {
				black[j] = in.nextInt();
			}
			long[] r = solve(n, m, white, black);
			System.out.println(r[0] + " " + r[1]);
		}
		in.close();
	}

	private static long[] solve(int n, int m, int[] white, int[] black) {
		long whiteDelta = 0;
		long[] raw = getCount(new int[] { 1, 1, m, n });
		long[] wRegion = getCount(white);
		whiteDelta += wRegion[1];
		long[] bRegion = getCount(black);
		whiteDelta -= bRegion[0];
		int[] intersection = new int[] { Math.max(white[0], black[0]), Math.max(white[1], black[1]),
				Math.min(white[2], black[2]), Math.min(white[3], black[3]) };
		if (intersection[0] <= intersection[2] && intersection[1] <= intersection[3]) {
			whiteDelta -= getCount(intersection)[1];
		}

		return new long[] { raw[0] + whiteDelta, raw[1] - whiteDelta };
	}

	private static long[] getCount(int[] range) {
		long l = range[2] - range[0] + 1;
		long w = range[3] - range[1] + 1;
		long total = l * w;
		if (total % 2 == 0) {
			return new long[] { total / 2, total / 2 };
		} else {
			if ((range[0] + range[3]) % 2 == 0) {
				return new long[] { total / 2 + 1, total / 2 };
			} else {
				return new long[] { total / 2, total / 2 + 1 };
			}
		}
	}
}
