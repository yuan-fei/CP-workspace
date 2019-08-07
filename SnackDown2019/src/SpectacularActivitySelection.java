
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class SpectacularActivitySelection {
	public static void main(String[] args) {
		System.out.println(solve(2, 2, 1, 997));
	}

	private static void run() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			int k = in.nextInt();
			long mod = in.nextInt();
			long r = solve(n, m, k, mod);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int n, int m, int k, long mod) {
		return solve(n, m, k, mod, 0, new int[k], -1);
	}

	private static long solve(int n, int m, int k, long mod, int tc, int[] l, int total) {
		if (tc == k) {
			return getPossibleCombinationCount(n, m - k, l, mod);
		}
		long cnt = 0;
		for (int i = total + 1; i + k - tc <= n; i++) {
			l[tc] = i - total;
			cnt = (cnt + solve(n, m, k, mod, tc + 1, l, i)) % mod;
			l[tc] = 0;
		}
		return cnt;
	}

	static long getPossibleCombinationCount(int n, int k, int[] l, long mod) {
		System.out.println(Arrays.toString(l));
		long r = 1;
		int prefixSum = 0;
		long allExcActivity = 0;
		for (int i = 0; i < l.length; i++) {
			r = (r * l[i]) % mod;
			prefixSum += l[i];
			allExcActivity += l[i] * (n - prefixSum) + l[i] - 1;
		}
		if (allExcActivity >= k) {
			allExcActivity = C(allExcActivity, k) % mod;
			r = (r * allExcActivity) % mod;
			return r;
		} else {
			return 0;
		}

	}

	static long C(long a, long b) {
		long r = 1L;
		for (long i = a - b + 1, j = 1; j <= b; i++, j++) {
			r *= i;
			r /= j;
		}
		return r;
	}
}
