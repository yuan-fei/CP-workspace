package week4;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class RadixSort {
	public static void main(String[] args) throws Exception {
		solve();
		// generateCase();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int m = io.nextInt();
			int k = io.nextInt();
			String[] s = new String[m];
			for (int i = 0; i < m; i++) {
				s[i] = io.next();
			}
			int[] ans = sort(n, m, k, s);
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < ans.length; j++) {
				if (j == 0) {
					sb.append(ans[j] + 1);
				} else {
					sb.append(" " + (ans[j] + 1));
				}
			}
			io.println(sb.toString());
		}
	}

	private static int[] sort(int n, int m, int k, String[] s) {
		int[] sorted = new int[n];
		int[] sortedBak = new int[n];
		int[][] counters = new int[k][26];
		for (int i = 0; i < n; i++) {
			sorted[i] = i;
		}
		for (int i = 0; i < k; i++) {
			radixSort(s[m - i - 1], counters[i], sorted, sortedBak);
		}
		return sorted;
	}

	private static void radixSort(String chars, int[] counter, int[] sorted, int[] sortedBak) {
		for (int i = 0; i < chars.length(); i++) {
			counter[chars.charAt(i) - 'a']++;
		}
		for (int i = 1; i < counter.length; i++) {
			counter[i] += counter[i - 1];
		}
		for (int i = sorted.length - 1; i >= 0; i--) {
			sortedBak[counter[chars.charAt(sorted[i]) - 'a'] - 1] = sorted[i];
			counter[chars.charAt(sorted[i]) - 'a']--;
		}
		System.arraycopy(sortedBak, 0, sorted, 0, sorted.length);
	}

}
