package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MaximizeProduct {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			long n = in.nextLong();
			long k = in.nextLong();
			long r = solve(n, k);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(long n, long k) {
		if (n < k * (k + 1) / 2) {
			return -1L;
		} else {
			long low = (2 * n / k - (k - 1)) / 2;
			long high = low + k - 1;
			long r = 1;
			long reminder = n - (low + high) * k / 2;
			for (; high >= low; high--) {
				long t = high;
				if (reminder > 0) {
					t = high + 1;
					reminder--;
				}
				r = (r * t) % 1000000007L;
				r = (r * (t - 1)) % 1000000007L;
			}
			return r;
		}
	}

}
