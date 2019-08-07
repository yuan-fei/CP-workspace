package r1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class WaffleChoppers {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int r = in.nextInt();
			int c = in.nextInt();
			int h = in.nextInt();
			int v = in.nextInt();
			char[][] matrix = new char[r][c];
			for (int j = 0; j < r; j++) {
				matrix[j] = in.next().toCharArray();
			}
			boolean ok = solveLarge(matrix, r, c, h, v);
			String result = ok ? "POSSIBLE" : "IMPOSSIBLE";
			System.out.println("Case #" + i + ": " + result);
		}
		in.close();
	}

	private static boolean solveSmall(char[][] matrix, int r, int c, int h, int v) {
		for (int j = 0; j < r - 1; j++) {
			for (int k = 0; k < c - 1; k++) {
				if (checkSmall(matrix, r, c, j, k)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkSmall(char[][] matrix, int r, int c, int rp, int cp) {
		int[] cnt = new int[4];
		for (int j = 0; j < r; j++) {
			for (int k = 0; k < c; k++) {
				if (matrix[j][k] == '@')
					if (j <= rp) {
						if (k <= cp) {
							cnt[0]++;
						} else {
							cnt[1]++;
						}
					} else {
						if (k <= cp) {
							cnt[2]++;
						} else {
							cnt[3]++;
						}
					}
			}
		}
		if (cnt[0] == cnt[1] && cnt[0] == cnt[2] && cnt[0] == cnt[3]) {
			return true;
		} else {
			return false;
		}

	}

	private static boolean solveLarge(char[][] matrix, int r, int c, int h, int v) {
		int[][] cnt = new int[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (matrix[i][j] == '@') {
					cnt[i][j] += 1;
				}
				if (i - 1 >= 0) {
					cnt[i][j] += cnt[i - 1][j];
				}
				if (j - 1 >= 0) {
					cnt[i][j] += cnt[i][j - 1];
				}
				if (i - 1 >= 0 && j - 1 >= 0) {
					cnt[i][j] -= cnt[i - 1][j - 1];
				}
			}
		}
		int total = cnt[r - 1][c - 1];
		if (total % ((v + 1) * (h + 1)) != 0) {
			return false;
		}
		if (total == 0) {
			return true;
		}
		int[] lh = new int[h];
		int[] lv = new int[v];
		int hSliceCnt = total / (h + 1);
		int vSliceCnt = total / (v + 1);
		int bSliceCnt = total / (v + 1) / (h + 1);
		int hSlice = 0;
		for (int i = 0; i < r && hSlice < h; i++) {
			if (cnt[i][c - 1] % hSliceCnt == 0 && cnt[i][c - 1] / hSliceCnt == hSlice + 1) {
				lh[hSlice] = i;
				hSlice++;
			}
		}
		if (hSlice != h) {
			return false;
		}

		int vSlice = 0;
		for (int i = 0; i < c && vSlice < v; i++) {
			if (cnt[r - 1][i] % vSliceCnt == 0 && cnt[r - 1][i] / vSliceCnt == vSlice + 1) {
				lv[vSlice] = i;
				vSlice++;
			}
		}
		if (vSlice != v) {
			return false;
		}

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < v; j++) {
				if (!(cnt[lh[i]][lv[j]] == ((i + 1) * (j + 1)) * bSliceCnt)) {
					return false;
				}
			}
		}
		return true;
	}
}
