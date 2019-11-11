package r2019G;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/** Greedy */
public class TheEquation {
	public static void main(String[] args) {
		solve();
		// generateMultiple(20);
	}

	static void generateMultiple(int n) {
		System.out.println(n);
		for (int i = 0; i < n; i++) {
			generate();
		}
	}

	static void generate() {
		Random r = new Random();
		long mod = 1000000000000000L;
		mod = 1000;
		int n = r.nextInt(10) + 1;
		long m = Math.abs(r.nextLong() % mod);
		System.out.println(n + " " + m);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(Math.abs(r.nextLong() % mod));
			sb.append(" ");
		}
		System.out.println(sb.toString());
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			long m = in.nextLong();
			long[] a = getLongArr(in, n);
			long ans = solve(n, m, a);
			System.out.println("Case #" + i + ": " + ans);
		}
		in.close();
	}

	private static long solve(int n, long m, long[] a) {
		int[] cnt = new int[64];
		for (int i = 0; i < n; i++) {
			for (int b = 0; b < 50; b++) {
				if ((a[i] & (1L << b)) != 0) {
					cnt[b]++;
				}
			}
		}

		long min = 0;
		long k = 0;
		for (int i = 0; i < 50; i++) {
			if (cnt[i] >= n - cnt[i]) {
				k |= 1L << i;
			}
			min += (1L << i) * Math.min(cnt[i], n - cnt[i]);
		}
		if (min > m) {
			return -1;
		}
		for (int i = 50; i >= 0; i--) {
			if (((k >> i) & 1) == 0) {
				// try to set ith bit of k to 1
				long more = min + (1L << i) * Math.abs(n - 2 * cnt[i]);
				if (more <= m) {
					min = more;
					k |= 1L << i;
				}
			}

		}
		return k;
	}

	static long c = 0;

	private static long dfs(int n, long m, int[] cnt, int i, long max) {
		c++;
		// System.out.println(i + "," + max);
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
