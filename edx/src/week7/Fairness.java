package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import mooc.EdxIO;

public class Fairness {
	private static String s;
	private static int N;
	private static int K;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			N = io.nextInt();
			K = io.nextInt();
			int[] a = new int[N];
			for (int i = 0; i < a.length; i++) {
				a[i] = io.nextInt();
			}
			Arrays.sort(a);
			int start = 0;
			int minDiff = Integer.MAX_VALUE;
			for (int i = 0; i < a.length - K + 1; i++) {
				if (minDiff > a[i + K - 1] - a[i]) {
					minDiff = a[i + K - 1] - a[i];
					start = i;
				}
			}
			StringBuilder sb = new StringBuilder();
			for (int i = start; i < start + K; i++) {
				sb.append(a[i] + " ");
			}
			io.print(sb.toString());
		}
	}

}