package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SerejaAndIntervals {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int x = in.nextInt();
		long r = solve(n, m, x);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, int m, int x) {
		if (n > m) {
			return 0;
		}
		long[][] dp = new long[n + 1][n + 1];
		dp[0][0] = 1;
		for (int i = 1; i <= m; i++) {
			long[][] new_dp = new long[n + 1][n + 1];
			for (int j = 0; j <= Math.min(i, n); j++) {
				for (int k = 0; k <= n - j; k++) {
					if (i != x) {
						new_dp[j][k] = dp[j][k];
						// close prev
						if (j < n && k > 0) {
							new_dp[j][k] = add(new_dp[j][k], dp[j + 1][k - 1]);
						}
					}
					// open i
					if (j > 0) {
						new_dp[j][k] = add(new_dp[j][k], dp[j - 1][k]);
					}
					if (k > 0) {
						// j>0: close prev, open i
						// j==0: open i, close i
						new_dp[j][k] = add(new_dp[j][k], dp[j][k - 1]);
					}
				}
			}
			dp = new_dp;
		}
		return mul(dp[0][n], fact(n));
	}

	static long mod = 1000000007;

	static long fact(int n) {
		long r = 1;
		for (int i = 2; i <= n; i++) {
			r = mul(r, i);
		}
		return r;
	}

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
