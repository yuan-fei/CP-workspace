package y2021.r1c.upsolve;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RoaringYears {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		// isRoaring(122);
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			long n = in.nextLong();
			String r = solve("" + n);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static String solve(String n) {
		String min = null;
		for (int c = 2; c <= 18; c++) {
			String s = getMin(n, c);
			if (min == null) {
				min = s;
			} else {
				if (s.length() == min.length()) {
					int cmp = s.compareTo(min);
					if (cmp < 0) {
						min = s;
					}
				} else if (s.length() < min.length()) {
					min = s;
				}
			}
		}
		return min;
	}

	private static String getMin(String n, int c) {
		int ub = (18 + c - 1) / c;
		long high = (long) Math.pow(10, ub);
		long low = 1;
		while (low + 1 < high) {
			long mid = (high - low) / 2 + low;
			String cur = gen(mid, c);
			if (cur.length() == n.length()) {
				int cmp = cur.compareTo(n);
				if (cmp > 0) {
					high = mid;
				} else {
					low = mid;
				}
			} else if (cur.length() > n.length()) {
				high = mid;
			} else {
				low = mid;
			}
		}
		String cur = gen(low, c);
		if (cur.length() == n.length()) {
			int cmp = cur.compareTo(n);
			if (cmp > 0) {
				return cur;
			} else {
				return gen(high, c);
			}
		} else if (cur.length() > n.length()) {
			return cur;
		} else {
			return gen(high, c);
		}
	}

	static String gen(long pfx, int cnt) {
		String s = "" + pfx;
		for (int i = 0; i < cnt - 1; i++) {
			pfx++;
			s += pfx;
		}
		return s;
	}

	private static void test() {
		for (int i = 0; i < 10000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt();
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

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
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
