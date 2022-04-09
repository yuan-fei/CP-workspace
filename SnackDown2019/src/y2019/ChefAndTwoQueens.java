package y2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChefAndTwoQueens {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			long r = solve(n, m, x, y);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int n, int m, int x, int y) {
		long c = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (i == x && j == y) {
					continue;
				}
				long cnt = (n - 1) * (m - 1) - getDiagonal(n, m, i, j, x, y);
				if (i == x) {
					if (y > j) {
						cnt += m - y;
					} else {
						cnt += y - 1;
					}
				} else if (j == y) {
					if (x > i) {
						cnt += n - x;
					} else {
						cnt += x - 1;
					}
				} else if (Math.abs(i - x) != Math.abs(j - y)) {
					cnt -= 1;
				}
				c += cnt;
			}
		}
		return c;
	}

	static int getDiagonal(int n, int m, int i, int j, int x, int y) {

		int cnt = Math.min(i - 1, j - 1) + Math.min(n - i, j - 1) + Math.min(i - 1, m - j) + Math.min(n - i, m - j);
		if (Math.abs(i - x) == Math.abs(j - y)) {
			if (i < x && j < y) {
				cnt -= Math.min(n - x, m - y);
			} else if (i < x && j > y) {
				cnt -= Math.min(n - x, y - 1);
			} else if (i > x && j < y) {
				cnt -= Math.min(x - 1, m - y);
			} else {
				cnt -= Math.min(x - 1, y - 1);
			}
		}
		return cnt;
	}
}
