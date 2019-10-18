package week7;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class Operators {

	static int[] parent;
	static int[] nLeaves;
	static int minDiff = Integer.MAX_VALUE;
	static int total;
	static int max = 0;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			solve(io);
		}
	}

	private static void solve(EdxIO io) {
		int N = io.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = io.nextInt();
		}
		int[][] dp = new int[N][N];
		for (int i = 0; i < N; i++) {
			dp[i][i] = a[i];
			if (i > 0) {
				dp[i - 1][i] = Math.max(dp[i - 1][i - 1] * dp[i][i], dp[i - 1][i - 1] + dp[i][i]);
			}
		}
		for (int l = 3; l <= N; l++) {
			for (int i = 0; i < N - l + 1; i++) {
				for (int j = i; j < i + l - 1; j++) {
					dp[i][i + l - 1] = Math.max(dp[i][i + l - 1], dp[i][j] + dp[j + 1][i + l - 1]);
				}
			}
		}
		io.print(dp[0][N - 1]);
	}

}