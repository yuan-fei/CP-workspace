import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class WaitinginQueue {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			long m = in.nextLong();
			long k = in.nextLong();
			long l = in.nextLong();
			long[] a = new long[n];
			for (int j = 0; j < n; j++) {
				a[j] = in.nextLong();
			}
			long r = solve(n, m, k, l, a);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int n, long m, long k, long l, long[] a) {
		Arrays.sort(a);
		long r = Long.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			long t = Math.min(a[i], k);
			if (1 + m + i - t / l < 0) {
				return 0;
			} else {
				long wt = (1 + m + i - t / l) * l - t % l;
				r = Math.min(r, wt);
				if (a[i] >= k) {
					break;
				}
			}
		}
		if (k > a[a.length - 1]) {
			if (1 + m + a.length - k / l < 0) {
				return 0;
			} else {
				long wt = (1 + m + a.length - k / l) * l - k % l;
				r = Math.min(r, wt);
			}
		}
		return r;
	}

}
