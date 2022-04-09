package y2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BuddyNim {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[] A = new int[n];
			int[] B = new int[m];
			for (int j = 0; j < n; j++) {
				A[j] = in.nextInt();
			}
			for (int j = 0; j < m; j++) {
				B[j] = in.nextInt();
			}
			boolean r = solve(n, m, A, B);
			System.out.println(r ? "Alice" : "Bob");
		}
		in.close();
	}

	private static boolean solve(int n, int m, int[] a, int[] b) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0) {
				Integer c = map.get(a[i]);
				if (c == null) {
					c = 0;
				}
				map.put(a[i], c + 1);
			}
		}
		for (int i = 0; i < b.length; i++) {
			if (b[i] != 0) {
				Integer c = map.get(b[i]);
				if (c == null) {
					return true;
				}
				if (c - 1 == 0) {
					map.remove(b[i]);
				} else {
					map.put(b[i], c - 1);
				}
			}
		}
		return !map.isEmpty();
	}
}
