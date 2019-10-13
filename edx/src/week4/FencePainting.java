package week4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import mooc.EdxIO;

public class FencePainting {
	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			long k = io.nextLong();
			long[] a = new long[n];
			for (int i = 0; i < n; i++) {
				a[i] = io.nextLong();
			}
			Arrays.sort(a);
			long low = 0;
			long high = a[0];
			while (low + 1 < high) {
				long mid = low + (high - low) / 2;
				if (available(a, k, mid)) {
					low = mid;
				} else {
					high = mid;
				}
			}
			if (available(a, k, high)) {
				io.println(high);
			} else {
				io.println(low);
			}
		}
	}

	private static boolean available(long[] a, long k, long minSatisfaction) {
		long occupied = 0;
		for (int i = 0; i < a.length; i++) {
			occupied += Math.max(minSatisfaction, a[i] - occupied);
			if (occupied > k) {
				return false;
			}
		}
		return true;
	}
}
