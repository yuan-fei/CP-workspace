package week4;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class Drying {
	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			long[] a = new long[n];
			for (int i = 0; i < n; i++) {
				a[i] = io.nextLong();
			}
			long k = io.nextLong();
			long low = 0;
			long high = 1000 * 1000 * 1000;
			while (low + 1 < high) {
				long mid = low + (high - low) / 2;
				if (available(a, k, mid)) {
					high = mid;
				} else {
					low = mid;
				}
			}
			if (available(a, k, low)) {
				io.println(low);
			} else {
				io.println(high);
			}
		}
	}

	private static boolean available(long[] a, long k, long minDryingTime) {
		long dryerTimes = 0;
		for (int i = 0; i < a.length; i++) {
			if (k == 1) {
				dryerTimes = a[i];
			} else {
				dryerTimes += Math.max(0, Math.ceil(1.0d * (a[i] - minDryingTime) / (k - 1)));
			}
			if (dryerTimes > minDryingTime) {
				return false;
			}
		}
		return true;
	}
}
