package r2019G;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Shifts {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			long h = in.nextLong();
			long[] a = getLongArr(in, n);
			long[] b = getLongArr(in, n);
			long ans = solve(n, h, a, b);
			System.out.println("Case #" + i + ": " + ans);
		}
		in.close();
	}

	private static long solve(int n, long h, long[] a, long[] b) {
		int[] dp = new int[1 << n];
		int[] pb = new int[1 << n];
		for (int i = 0; i < (1 << n); i++) {
			long sumA = 0;
			for (int j = 0; j < n; j++) {
				if ((i & (1 << j)) != 0) {
					sumA += a[j];
				}
			}
			if (sumA >= h) {
				dp[i] = 1;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 1 << n; j++) {
				if (((j >> i) & 1) != 0) {
					dp[j ^ (1 << i)] += dp[j];
				}
			}
		}

		long cnt = 0;
		for (int i = 0; i < (1 << n); i++) {
			long sumB = 0;
			for (int j = 0; j < n; j++) {
				if ((i & (1 << j)) != 0) {
					sumB += b[j];
				}
			}
			if (sumB >= h) {
				cnt += dp[(1 << n) - 1 - i];
			}
		}
		return cnt;
	}

	public static int countSeenFactors(int n, int[] seen) {
		int cnt = 0;
		for (int i = 1; i * i <= n; i++) {
			if (n % i == 0) {
				cnt += seen[i];
				if (i != n / i) {
					cnt += seen[n / i];
				}
			}
		}
		return cnt;
	}

	private static long dfs(int n, long m, int[] cnt, int i, long max) {
		System.out.println(i + "," + max);
		if (i == 0) {
			if (n - cnt[i] <= max) {
				return 1;
			}
			if (cnt[i] <= max) {
				return 0;
			}
			return -1;
		}
		if (n - cnt[i] <= max) {
			long x = dfs(n, m, cnt, i - 1, (max - n + cnt[i]) * 2 + ((m >> (i - 1)) & 1));
			if (x != -1) {
				return (1 << i) | x;
			}
		}
		if (cnt[i] <= max) {
			return dfs(n, m, cnt, i - 1, (max - cnt[i]) * 2 + ((m >> (i - 1)) & 1));
		}
		return -1;
	}

	private static long mod = 1000000007;

	private static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	private static long mul(long a, long b) {
		return (a * b) % mod;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	private static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	private static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	private static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	private static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	private static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	private static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	private static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
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
