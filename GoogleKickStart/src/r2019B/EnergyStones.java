package r2019B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** DP with Exchange argument */
public class EnergyStones {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			Stone[] p = getStone(in, n);
			int r = solve(n, p);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int n, Stone[] p) {
		Arrays.sort(p, new Comparator<Stone>() {
			@Override
			public int compare(Stone o1, Stone o2) {
				int r1 = o1.s * o2.l;
				int r2 = o2.s * o1.l;
				return Integer.compare(r1, r2);
			}
		});
		int maxTime = 10001;
		int[] dp = new int[maxTime + 1];
		for (int i = 0; i < p.length; i++) {
			for (int t = maxTime; t >= p[i].s; t--) {
				int e = Math.max(0, p[i].e - (t - p[i].s) * p[i].l);
				dp[t] = Math.max(dp[t], dp[t - p[i].s] + e);
			}
		}
		int max = 0;
		for (int i = 0; i < dp.length; i++) {
			max = Math.max(max, dp[i]);
		}
		return max;
	}

	static class Stone {
		int s, e, l;

		public Stone(int s, int e, int l) {
			super();
			this.s = s;
			this.e = e;
			this.l = l;
		}

		@Override
		public String toString() {
			return "[" + s + "," + e + "," + l + "]";
		}
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

	static int gcd(int a, int b) {
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

	static Stone getStone(Scanner in) {
		return new Stone(in.nextInt(), in.nextInt(), in.nextInt());
	}

	static Stone[] getStone(Scanner in, int row) {
		Stone[] arr = new Stone[row];
		for (int i = 0; i < row; i++) {
			arr[i] = getStone(in);
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
