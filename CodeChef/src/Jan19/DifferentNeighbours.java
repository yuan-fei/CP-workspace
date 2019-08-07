package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DifferentNeighbours {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			k = 0;
			int[][] r;
			if (n > m) {
				r = solve(m, n);
				r = transpose(r);
			} else {
				r = solve(n, m);
			}

			System.out.println(k);
			for (int j = 0; j < r.length; j++) {
				System.out.println(str(r[j]));
			}
		}
		in.close();
	}

	static int k = 0;

	private static int[][] solve(int n, int m) {
		int[][] res = new int[n][m];
		if (n == 1) {
			// 1 1 2 2 1 1 ...
			for (int i = 0; i < m; i++) {
				if (i % 4 < 2) {
					res[0][i] = 1;
				} else {
					res[0][i] = 2;
				}
				k = Math.max(k, res[0][i]);
			}
			k = m > 2 ? 2 : 1;
		} else if (n == 2) {
			// 1 2 3 1 2 3 ...
			// 1 2 3 1 2 3 ...
			for (int i = 0; i < m; i++) {
				res[0][i] = i % 3 + 1;
				res[1][i] = i % 3 + 1;
				k = Math.max(k, res[0][i]);
			}
		} else {
			// 1 2 3 4 1 2 3 ...
			// 1 2 3 4 1 2 3 ...
			// 3 4 1 2 3 4 1...
			// 3 4 1 2 3 4 1...
			for (int i = 0; i < n; i++) {
				int ofs = 0;
				if (i % 4 >= 2) {
					ofs = 2;
				}
				for (int j = 0; j < m; j++) {
					res[i][j] = (j + ofs) % 4 + 1;
				}
			}
			k = 4;
		}
		return res;
	}

	private static int[][] transpose(int[][] a) {
		int[][] b = new int[a[0].length][a.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				b[j][i] = a[i][j];
			}
		}
		return b;
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}
}
