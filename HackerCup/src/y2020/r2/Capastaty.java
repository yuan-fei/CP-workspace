package y2020.r2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Capastaty {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int N = in.nextInt();
				int K = in.nextInt();
				int[] S = new int[N];
				int[] X = new int[N];
				int[] Y = new int[N];
				for (int j = 0; j < K; j++) {
					S[j] = in.nextInt();
				}
				int[] Sabcd = getIntArr(in, 4);
				for (int j = 0; j < K; j++) {
					X[j] = in.nextInt();
				}
				int[] Xabcd = getIntArr(in, 4);
				for (int j = 0; j < K; j++) {
					Y[j] = in.nextInt();
				}
				int[] Yabcd = getIntArr(in, 4);
				long r = solve(N, K, S, X, Y, Sabcd, Xabcd, Yabcd);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static long solve(int n, int k, int[] s, int[] x, int[] y, int[] sabcd, int[] xabcd, int[] yabcd) {
		fill(s, sabcd, k, n);
		fill(x, xabcd, k, n);
		fill(y, yabcd, k, n);
		long totalMoveOut = 0;
		long totalMoveIn = 0;
		for (int i = 0; i < n; i++) {
			if (s[i] < x[i]) {
				totalMoveIn += x[i] - s[i];
			}
			if (s[i] > x[i] + y[i]) {
				totalMoveOut += s[i] - x[i] - y[i];
			}
		}
		if (totalMoveOut == totalMoveIn) {
			return totalMoveOut;
		} else if (totalMoveOut > totalMoveIn) {
			totalMoveIn = 0;
			for (int i = 0; i < n; i++) {
				totalMoveIn += Math.max(x[i] + y[i] - s[i], 0);
				if (totalMoveIn >= totalMoveOut) {
					return totalMoveOut;
				}
			}
			return -1;
		} else {
			totalMoveOut = 0;
			for (int i = 0; i < n; i++) {
				totalMoveOut += Math.max(s[i] - x[i], 0);
				if (totalMoveOut >= totalMoveIn) {
					return totalMoveIn;
				}
			}
			return -1;
		}
	}

	private static void fill(int[] a, int[] abcd, int k, int n) {
		for (int i = k; i < n; i++) {
			a[i] = compute(a[i - 2], a[i - 1], abcd);
		}
	}

	private static int compute(long x1, long x2, int[] abcd) {
		long ret = 0;
		ret = (abcd[0] * x1) % abcd[3];
		ret = (ret + (abcd[1] * x2) % abcd[3]) % abcd[3];
		ret = (ret + abcd[2]) % abcd[3];
		return (int) ret;
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

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
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

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
