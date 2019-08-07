import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SpreadTheWorld {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] m = new int[n];
			for (int j = 0; j < n; j++) {
				m[j] = in.nextInt();
			}
			long r = solve(m);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int[] m) {
		long[] prefixSum = new long[m.length];
		prefixSum[0] = m[0];
		for (int i = 1; i < m.length; i++) {
			prefixSum[i] = prefixSum[i - 1] + m[i];
		}
		int i = 0;
		int cnt = 0;
		while (i < prefixSum.length - 1) {
			cnt++;
			i += Math.min(prefixSum[i], m.length - i);
		}
		return cnt;
	}

}
