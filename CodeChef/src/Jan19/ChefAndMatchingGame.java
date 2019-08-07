package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChefAndMatchingGame {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int[][] v = new int[n][n];
		boolean[][] p = new boolean[n][n];
		for (int j = 0; j < 2 * m; j++) {
			int x = in.nextInt();
			int y = in.nextInt();
			p[x - 1][y - 1] = true;
		}
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < n; k++) {
				v[j][k] = in.nextInt();
			}
		}
		List<int[]>[] r = solve(n, m, p, v);
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < r.length; j++) {
			sb.append(r[j].size());
			for (int k = 0; k < r[j].size(); k++) {
				sb.append(" " + r[j].get(k)[0] + " " + r[j].get(k)[1]);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		in.close();
	}

	private static List<int[]>[] solve(int n, int m, boolean[][] p, int[][] v) {
		List<int[]>[] r = new List[m];
		int k = 0;
		for (int i = 0; i < n; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < n; j++) {
					if (p[i][j]) {
						if (r[k] == null) {
							r[k] = new ArrayList<int[]>();
							r[k].add(new int[] { i + 1, j + 1 });
						} else {
							r[k].add(new int[] { i + 1, j + 1 });
							k++;
							if (k == m) {
								return r;
							}
						}
					} else {
						if (r[k] != null) {
							r[k].add(new int[] { i + 1, j + 1 });
						}
					}
				}
			} else {
				for (int j = n - 1; j >= 0; j--) {
					if (p[i][j]) {
						if (r[k] == null) {
							r[k] = new ArrayList<int[]>();
							r[k].add(new int[] { i + 1, j + 1 });
						} else {
							r[k].add(new int[] { i + 1, j + 1 });
							k++;
							if (k == m) {
								return r;
							}
						}
					} else {
						if (r[k] != null) {
							r[k].add(new int[] { i + 1, j + 1 });
						}
					}
				}
			}
		}
		return r;
	}
}
