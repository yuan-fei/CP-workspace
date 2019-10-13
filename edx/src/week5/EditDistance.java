package week5;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class EditDistance {

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			char[] s1 = io.next().toCharArray();
			char[] s2 = io.next().toCharArray();
			int ans = minEditDist(s1, s2);
			io.println(ans);
		}
	}

	private static int minEditDist(char[] s1, char[] s2) {
		int[][] dp = new int[s1.length + 1][s2.length + 1];
		for (int i = 0; i <= s1.length; i++) {
			dp[i][0] = i;
		}
		for (int i = 0; i <= s2.length; i++) {
			dp[0][i] = i;
		}
		for (int i = 1; i <= s1.length; i++) {
			for (int j = 1; j <= s2.length; j++) {
				dp[i][j] = Integer.MAX_VALUE;
				if (s1[i - 1] == s2[j - 1]) {
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
				} else {
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + 1);
				}
				dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
			}
		}
		return dp[s1.length][s2.length];
	}

}