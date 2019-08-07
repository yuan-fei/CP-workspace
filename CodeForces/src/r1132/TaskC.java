package r1132;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskC {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int[][] q = getIntArr(in, m, 2);
		long r = solve(n, m, q);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, int m, int[][] q) {
		long[] cnt = new long[n + 2];
		for (int i = 0; i < q.length; i++) {
			cnt[q[i][0]] += 1;
			cnt[q[i][1] + 1] -= 1;
		}
		for (int i = 1; i <= n; i++) {
			cnt[i] += cnt[i - 1];
		}
		long[] s1 = new long[n + 1];
		long[] s2 = new long[n + 1];
		long total = 0;
		for (int i = 1; i <= n; i++) {
			s1[i] = s1[i - 1];
			s2[i] = s2[i - 1];
			if (cnt[i] == 1) {
				s1[i] += 1;
			}
			if (cnt[i] == 2) {
				s2[i] += 1;
			}
			if (cnt[i] > 0) {
				total += 1;
			}
		}
		long min = Long.MAX_VALUE;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < i; j++) {
				long c1i = s1[q[i][1]] - s1[q[i][0] - 1];
				long c1j = s1[q[j][1]] - s1[q[j][0] - 1];
				int l = Math.max(q[i][0], q[j][0]);
				int r = Math.min(q[i][1], q[j][1]);
				long c2 = 0;
				if (l <= r) {
					c2 = s2[r] - s2[l - 1];
				}
				min = Math.min(c1i + c1j + c2, min);
			}
		}
		return total - min;
	}

	static long mod = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return (a * b) % mod;
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

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
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
