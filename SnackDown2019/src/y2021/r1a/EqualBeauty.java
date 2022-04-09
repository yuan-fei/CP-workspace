package y2021.r1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class EqualBeauty {
	public static void main(String[] args) {
		solve();
//		test();
//		System.out.println(solve(5, new long[] { 1, 2, 3, 4, 1000 }));
//		System.out.println(solveSlow(5, new long[] { 1, 2, 3, 4, 1000 }));
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			long[] a = getLongArr(in, n);
			long r = solve(n, a);
			System.out.println(r);
		}

		in.close();
	}

	private static long solve(int n, long[] a) {
		if (a.length == 2) {
			return 0;
		}
		TreeMap<Long, Integer> tm = new TreeMap<>();
		for (long x : a) {
			inc(tm, x);
		}
		Long minKey = tm.firstKey();
		Long maxKey = tm.lastKey();
		dec(tm, minKey);
		dec(tm, maxKey);
		long sum = minKey + maxKey;
		long min = Long.MAX_VALUE;
		Arrays.sort(a);
		for (int i = 1; i < n - 1; i++) {
			dec(tm, a[i]);
			Long ceiling = tm.ceilingKey(sum - a[i]);
			Long floor = tm.floorKey(sum - a[i]);
			if (ceiling != null) {
				min = Math.min(min, Math.abs(sum - a[i] - ceiling));
			}
			if (floor != null) {
				min = Math.min(min, Math.abs(sum - a[i] - floor));
			}
			inc(tm, a[i]);
		}
		long sumMin = 0;
		long sumMax = 0;
		for (int i = 0; i < n - 1; i++) {
			sumMin += Math.abs(a[i] - a[(n - 1) / 2]);
		}
		for (int i = 1; i < n; i++) {
			sumMax += Math.abs(a[i] - a[n / 2]);
		}
		min = Math.min(min, sumMin);
		min = Math.min(min, sumMax);
		return min;
	}

	static void dec(TreeMap<Long, Integer> tm, long key) {
		int v = tm.get(key) - 1;
		if (v > 0) {
			tm.put(key, v);
		} else {
			tm.remove(key);
		}
	}

	static void inc(TreeMap<Long, Integer> tm, long key) {
		tm.put(key, tm.getOrDefault(key, 0) + 1);
	}

	static Random r = new Random();

	static long[] generateCase(int n, int x) {
		long[] ret = new long[n];
		for (int i = 0; i < n; i++) {
			ret[i] = x % 10;
			x /= 10;
		}
		return ret;
	}

	static long[] generateCaseRandom(int n) {
		long[] ret = new long[n];
		for (int i = 0; i < n; i++) {
			ret[i] = r.nextInt(21) - 10;
		}
		return ret;
	}

	static long solveSlow(int n, long[] r) {
		long ret = Long.MAX_VALUE;
		for (int i = 0; i < (1 << n); i++) {
			if (i != 0 || i != (1 << n) - 1) {
				long[][] g = { { Long.MIN_VALUE, Long.MAX_VALUE }, { Long.MIN_VALUE, Long.MAX_VALUE } };
				for (int j = 0; j < n; j++) {
					g[(i >> j) & 1][0] = Math.max(g[(i >> j) & 1][0], r[j]);
					g[(i >> j) & 1][1] = Math.min(g[(i >> j) & 1][1], r[j]);
				}
				long diff = Math.abs(g[0][0] - g[0][1] - (g[1][0] - g[1][1]));
				ret = Math.min(ret, diff);
			}
		}
		return ret;
	}

	static void testOnce(int n) {
		for (int i = 0; i < Math.pow(10, n); i++) {
			long[] a = generateCase(n, i);
			long r1 = solve(n, a);
			long r2 = solveSlow(n, a);
			if (r1 != r2) {
				System.out.println(Arrays.toString(a) + ", " + r1 + ", " + r2);
			}
		}

	}

	static void test() {
		for (int i = 0; i < 1; i++) {
			testOnce(4);
		}
	}

	static int MAX = (int) 1e9 + 8;

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

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
		}
		return sb.toString();
	}

	static String str(long[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
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

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static char[][] getCharArr(Scanner in, int row, int col) {
		char[][] arr = new char[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getCharArr(in, col);
		}
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
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
