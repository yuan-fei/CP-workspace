package y2021.r1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class YetAnotherFlippingProblem {
	public static void main(String[] args) {
		solve();
//		test();
	}

	private static void test() {
		for (int i = 1; i < 100001; i += 2) {
			System.out.println(i);
			if (!validate(i, solve(1, i))) {
				System.out.println(i + ", " + Arrays.toString(solve(1, i)));
			}
		}
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int k = in.nextInt();
			long[] r = solve(n, k);
			if (r == null) {
				System.out.println("NO");
			} else {
				System.out.println("YES");
				System.out.println(r.length);
				if (r.length > 0) {
					System.out.println(str(r));
				}
			}

		}

		in.close();
	}

	private static long[] solve(int n, long k) {
		if (k == 0) {
			return new long[0];
		}
		if (k % 2 == 0) {
			return null;
		}
		long x = 1;
		int m = 0;
		while (x <= k) {
			x <<= 1;
			m++;
		}
		long[] r = new long[m];
		long cnt1 = k;
		x >>= 1;
		long p = 0;
		for (int i = 0; i < m; i++) {
			if (cnt1 >= x) {
				r[m - i - 1] = p + 1;
				cnt1 -= x;
				p += x;
			} else {
				cnt1 = x - cnt1;
				p -= cnt1;
				r[m - i - 1] = p + 1;
			}
			x >>= 1;
		}
		return r;
	}

	static boolean validate(int k, long[] r) {
		int[] a = new int[k];
		int[] b = new int[k];
		Arrays.fill(b, 1);
		for (int i = 0; i < r.length; i++) {
			for (int j = 0; j < (1 << i); j++) {
				a[(int) r[i] - 1 + j] = 1 - a[(int) r[i] - 1 + j];
			}
		}
		return Arrays.equals(a, b);
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
			sb.append(a[i] + "\n");
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
