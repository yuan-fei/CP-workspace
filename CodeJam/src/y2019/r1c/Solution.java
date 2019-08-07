package y2019.r1c;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {
		// solve();
		test();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int r = in.nextInt();
			int c = in.nextInt();
			String[] a = getStringArr(in, r);
			long res = solve(r, c, a);
			System.out.println("Case #" + i + ": " + res);
		}
		in.close();
	}

	// static boolean[] isRowDead;
	// static boolean[] isColDead;
	static String[] a;

	private static long solve(int r, int c, String[] arr) {
		a = arr;
		int cs = 0;
		int ce = c;
		int rs = 0;
		int re = r;
		long res = 0;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (!isRowDeadEnd(i, cs, ce) && (solve(rs, i, cs, ce) == solve(i + 1, re, cs, ce))) {
					res++;
				}
				if (!isColDeadEnd(j, rs, re) && (solve(rs, re, cs, j) == solve(rs, re, j + 1, ce))) {
					res++;
				}
			}
		}

		return res;
	}

	private static boolean solve(int rs, int re, int cs, int ce) {
		if (rs >= re || cs >= ce) {
			return false;
		}
		for (int i = rs; i < re; i++) {
			for (int j = cs; j < ce; j++) {
				if (a[i].charAt(j) == '.') {
					if (!isRowDeadEnd(i, cs, ce) && (solve(rs, i, cs, ce) == solve(i + 1, re, cs, ce))) {
						// System.out.println("r: " + rs + "," + re + "," + cs + "," + ce);
						return true;
					}
					if (!isColDeadEnd(j, rs, re) && (solve(rs, re, cs, j) == solve(rs, re, j + 1, ce))) {
						// System.out.println("c: " + rs + "," + re + "," + cs + "," + ce);
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean isRowDeadEnd(int i, int cs, int ce) {
		for (int k = cs; k < ce; k++) {
			if (a[i].charAt(k) == '#') {
				return true;
			}
		}
		return false;
	}

	private static boolean isColDeadEnd(int j, int rs, int re) {
		for (int k = rs; k < re; k++) {
			if (a[k].charAt(j) == '#') {
				return true;
			}
		}
		return false;
	}

	private static void test() throws IOException {
		for (int i = 0; i < 10; i++) {
			testOnce();
		}
	}

	private static void testOnce() throws IOException {
		Random rnd = new Random();
		int r = rnd.nextInt(4) + 1;
		int c = rnd.nextInt(4) + 1;
		String[] ss = new String[r];
		for (int i = 0; i < r; i++) {
			char[] ca = new char[c];
			for (int j = 0; j < c; j++) {
				if (rnd.nextInt() % 3 == 0) {
					ca[j] = '#';
				} else {
					ca[j] = '.';
				}
				// ca[j] = '#';
			}
			// System.out.println(Arrays.toString(ca));
			ss[i] = new String(ca);
		}
		r = 2;
		c = 3;
		ss = new String[] { "..#,", "..." };
		// System.out.println(Arrays.toString(ss));
		long r1 = solve(r, c, ss);
		long r2 = Benchmark.solve(r, c, ss);
		if (r1 != r2) {
			System.out.println(Arrays.toString(ss));
			System.out.println(r1 + ", " + r2);
		}
		System.out.println();
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

	static class FastScanner implements Closeable {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		@Override
		public void close() throws IOException {
			br.close();
		}
	}

	static class Benchmark {
		static int n, m;
		static char[][] arr;
		static int[][][][] memo;

		static ArrayList<Integer> goodRows(int r1, int r2, int c1, int c2) {
			ArrayList<Integer> res = new ArrayList<>();
			for (int row = r1; row <= r2; row++) {
				boolean good = true;
				for (int col = c1; col <= c2; col++) {
					good &= arr[row][col] == '.';
				}
				if (good)
					res.add(row);
			}
			return res;
		}

		static ArrayList<Integer> goodCols(int r1, int r2, int c1, int c2) {
			ArrayList<Integer> res = new ArrayList<>();
			for (int col = c1; col <= c2; col++) {
				boolean good = true;
				for (int row = r1; row <= r2; row++) {
					good &= arr[row][col] == '.';
				}
				if (good)
					res.add(col);
			}
			return res;
		}

		static int grundy(int r1, int r2, int c1, int c2) {
			if (r1 > r2 || c1 > c2)
				return 0;
			if (memo[r1][r2][c1][c2] != -1)
				return memo[r1][r2][c1][c2];

			ArrayList<Integer> goodRows = goodRows(r1, r2, c1, c2);
			ArrayList<Integer> goodCols = goodCols(r1, r2, c1, c2);

			HashSet<Integer> grundies = new HashSet<>();
			for (int row : goodRows) {
				grundies.add(grundy(r1, row - 1, c1, c2) ^ grundy(row + 1, r2, c1, c2));
			}
			for (int col : goodCols) {
				grundies.add(grundy(r1, r2, c1, col - 1) ^ grundy(r1, r2, col + 1, c2));
			}

			for (int i = 0;; i++) {
				if (!grundies.contains(i)) {
					return memo[r1][r2][c1][c2] = i;
				}
			}
		}

		private static long solve(int r, int c, String[] s) throws IOException {
			Benchmark.n = r;
			Benchmark.m = c;
			arr = new char[n][m];
			memo = new int[16][16][16][16];
			for (int[][][] m : memo)
				for (int[][] mm : m)
					for (int[] mmm : mm)
						Arrays.fill(mmm, -1);

			for (int i = 0; i < n; i++) {
				arr[i] = s[i].toCharArray();
			}

			ArrayList<Integer> goodRows = goodRows(0, n - 1, 0, m - 1);
			ArrayList<Integer> goodCols = goodCols(0, n - 1, 0, m - 1);

			int res = 0;
			for (int row : goodRows) {
				if ((grundy(0, row - 1, 0, m - 1) ^ grundy(row + 1, n - 1, 0, m - 1)) == 0)
					res += m;
			}
			for (int col : goodCols) {
				if ((grundy(0, n - 1, 0, col - 1) ^ grundy(0, n - 1, col + 1, m - 1)) == 0)
					res += n;
			}
			return res;
		}

	}

}
