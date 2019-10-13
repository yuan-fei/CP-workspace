package week4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import mooc.EdxIO;

public class KBest {
	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int k = io.nextInt();
			Jewel[] j = new Jewel[n];
			for (int i = 0; i < n; i++) {
				j[i] = new Jewel(io.nextLong(), io.nextLong(), i + 1);
			}
			// use value -1 which is smaller then 0 as lower bound to make value
			// 0 reachable
			double low = -1;
			double high = 1000 * 1000 * 1000 + 1;
			int[] ans = new int[0];
			for (int i = 0; i < 50; i++) {
				double mid = low + (high - low) / 2;
				int[] kBest = available(j, k, mid);
				if (kBest.length > 0) {
					ans = kBest;
					low = mid;
				} else {
					high = mid;
				}
			}
			print(io, ans);
		}
	}

	private static void print(EdxIO io, int[] kBest) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < kBest.length; i++) {
			if (i == 0) {
				sb.append(kBest[i]);
			} else {
				sb.append(" " + kBest[i]);
			}
		}
		io.println(sb.toString());
	}

	private static int[] available(Jewel[] j, int k, double r) {
		Arrays.sort(j, (a, b) -> -Double.compare(a.diff(r), b.diff(r)));
		double sum = 0;
		int[] ret = new int[k];
		for (int i = 0; i < k; i++) {
			sum += j[i].diff(r);
			ret[i] = j[i].i;
		}

		if (sum >= 0) {
			return ret;
		} else {
			return new int[0];
		}
	}

	static class Jewel {
		double v;
		double w;
		int i;

		public Jewel(double v, double w, int i) {
			super();
			this.v = v;
			this.w = w;
			this.i = i;
		}

		double diff(double r) {
			return v - w * r;
		}
	}
}
