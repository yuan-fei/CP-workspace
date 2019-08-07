package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EhabAndA2OperationTask {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < n; ++i) {
			a[i] = in.nextInt();
		}
		long[][] r = solve(n, a);
		System.out.println(r.length);
		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i][0] + " " + r[i][1] + " " + r[i][2]);
		}
		in.close();
	}

	private static long[][] solve(int n, long[] a) {

		long[][] r = new long[n + 1][3];
		long dd = 0;
		for (int i = 0; i < a.length; i++) {
			long d = ((a.length - i - 1 - (a[a.length - i - 1] + dd) % n) + n) % n;
			dd += d;
			r[i] = new long[] { 1, a.length - i, d };
		}
		r[n] = new long[] { 2, n, n };
		return r;
	}
}
