
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BinaryStrings {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int q = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		String[] s = new String[n];
		String[] x = new String[q];
		int[] qs = new int[q];
		int[] qe = new int[q];
		for (int i = 0; i < n; ++i) {
			s[i] = in.next();
		}
		for (int i = 0; i < q; ++i) {
			qs[i] = in.nextInt();
			qe[i] = in.nextInt();
			x[i] = in.next();
		}
		int[] r = solve(n, q, s, qs, qe, x);
		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i]);
		}
		in.close();
	}

	private static int[] solve(int n, int q, String[] s, int[] qs, int[] qe, String[] x) {
		long[] sl = new long[n];
		for (int i = 0; i < n; i++) {
			try {
				sl[i] = Long.parseLong(s[i], 2);
			} catch (Exception e) {
			}
		}
		long[] xl = new long[q];
		for (int i = 0; i < q; i++) {
			try {
				xl[i] = Long.parseLong(x[i], 2);
			} catch (Exception e) {
			}
		}
		int[] res = new int[q];
		for (int i = 0; i < q; i++) {
			res[i] = solve(sl, qs[i], qe[i], xl[i]);
		}
		return res;
	}

	private static int solve(long[] l, int s, int e, long x) {
		long max = -1;
		int res = -1;
		for (int i = s; i <= e; i++) {
			long t = l[i - 1] ^ x;
			if (t > max) {
				max = t;
				res = i;
			}
		}
		return res;
	}
}
