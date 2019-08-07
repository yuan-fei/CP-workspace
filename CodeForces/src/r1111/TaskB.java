package r1111;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskB {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int k = in.nextInt();
		int m = in.nextInt();
		Long[] a = getLongArr(in, n);
		double r = solve(n, k, m, a);
		System.out.println(r);
		in.close();
	}

	private static double solve(int n, int k, int m, Long[] a) {
		Arrays.sort(a);
		long[] ps = new long[n + 1];

		for (int i = 1; i <= n; i++) {
			ps[i] = ps[i - 1] + a[i - 1];
		}
		double max = 0;
		for (int d = 0; d <= Math.min(n - 1, m); d++) {
			max = Math.max(max, calc(n, d, k, m, ps));
		}
		return max;
	}

	static double calc(int n, int d, long k, int m, long[] ps) {
		return (1.0 * Math.min((n - d) * k, m - d) + ps[n] - ps[d]) / (n - d);
	}

	int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static Long[] getLongArr(Scanner in, int size) {
		Long[] arr = new Long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Integer>> edges = new HashMap<>();
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<Integer>());
			}
			edges.get(from).add(to);
			if (!directed) {
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Integer>());
				}
				edges.get(to).add(from);
			}

		}
		return edges;
	}
}
