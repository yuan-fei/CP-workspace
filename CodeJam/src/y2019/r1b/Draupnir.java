package y2019.r1b;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Draupnir {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		int w = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			solve(in);
		}
		in.close();
	}

	private static void solve(Scanner in) {
		double[] ans = new double[6];
		long[] res = new long[6];
		for (int i = 0; i < 6; i++) {
			ans[i] = getAnswer(in, i + 1);
		}
		double[][] a = new double[][] { new double[] { 2, 1, 1, 1, 1, 1 }, new double[] { 4, 2, 1, 1, 1, 1 },
				new double[] { 8, 2, 2, 1, 1, 1 }, new double[] { 16, 4, 2, 2, 1, 1 },
				new double[] { 32, 4, 2, 2, 2, 1 }, new double[] { 64, 8, 4, 2, 2, 2 }, };
		double[] dres = LUP.solveLinearEquation(a, ans);
		for (int i = 0; i < dres.length; i++) {
			res[i] = (long) dres[i];
		}
		getAnswer(in, res);
	}

	private static long getAnswer(Scanner in, int i) {
		System.out.println(i);
		System.out.flush();
		return in.nextLong();
	}

	private static long getAnswer(Scanner in, long[] res) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < res.length; i++) {
			sb.append(res[i] + " ");
		}
		System.out.println(sb.toString());
		System.out.flush();
		return in.nextLong();
	}

	/**
	 * LUP decomposition for solving linear equation or matrix inversion in O(n^3)
	 */
	static class LUP {

		static class MatrixUtil {
			public static void swapRows(double[][] arr, int i, int j) {
				double[] d = arr[i];
				arr[i] = arr[j];
				arr[j] = d;
			}

			public static double[][] copyOf(double[][] a) {
				double[][] aA = new double[a.length][a[0].length];
				copy(a, aA);
				return aA;
			}

			public static void copy(double[][] src, double[][] dest) {
				for (int i = 0; i < src.length; i++) {
					for (int j = 0; j < src[i].length; j++) {
						dest[i][j] = src[i][j];
					}
				}
			}

			public static double[][] transpose(double[][] matrix) {
				double[][] tMatrix = new double[matrix[0].length][matrix.length];
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						tMatrix[j][i] = matrix[i][j];
					}
				}
				return tMatrix;
			}
		}

		public static double[] solveLinearEquation(double[][] a, double[] b) {
			int n = a.length;
			double[][] l = new double[n][n];
			double[][] u = new double[n][n];
			int[] p = new int[n];
			if (decompose(a, l, u, p)) {
				return solveDecomposedEquation(l, u, p, b);
			}
			return new double[0];
		}

		public static boolean decompose(double[][] a, double[][] l, double[][] u, int[] p) {
			int n = p.length;
			double[][] aA = MatrixUtil.copyOf(a);
			for (int i = 0; i < n; i++) {
				p[i] = i;
			}
			for (int i = 0; i < n; i++) {
				int indexMax = i;
				double absMax = 0d;
				// find pivot
				for (int j = i; j < n; j++) {
					if (Math.abs(aA[j][i]) > absMax) {
						absMax = Math.abs(aA[j][i]);
						indexMax = j;
					}
				}
				if (absMax == 0d) {
					return false;
				}
				swap(p, i, indexMax);
				MatrixUtil.swapRows(aA, i, indexMax);

				// Gaussian eliminate
				for (int j = i + 1; j < n; j++) {
					aA[j][i] /= aA[i][i];
					for (int k = i + 1; k < n; k++) {
						aA[j][k] -= aA[i][k] * aA[j][i];
					}
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (j < i) {
						l[i][j] = aA[i][j];
					} else if (i == j) {
						l[i][j] = 1d;
						u[i][j] = aA[i][j];
					} else {
						u[i][j] = aA[i][j];
					}
				}
			}
			return true;
		}

		private static void swap(int[] arr, int i, int j) {
			int d = arr[i];
			arr[i] = arr[j];
			arr[j] = d;
		}

		/**
		 * L is unit lower triangle matrix, U is upper triangle matrix, P is permutation
		 * matrix
		 */
		public static double[] solveDecomposedEquation(double[][] l, double[][] u, int[] p, double[] b) {
			// Solve Ly=Pb with forward substitution
			double[] y = new double[b.length];
			for (int i = 0; i < l.length; i++) {
				y[i] = b[p[i]];
				for (int j = 0; j <= i - 1; j++) {
					y[i] -= l[i][j] * y[j];
				}
			}

			// Solve Ux=y with backward substitution
			double[] x = new double[b.length];
			for (int i = x.length - 1; i >= 0; i--) {
				x[i] = y[i];
				for (int j = x.length - 1; j >= i + 1; j--) {
					x[i] -= u[i][j] * x[j];
				}
				x[i] /= u[i][i];
			}
			return x;
		}

		private static int[] getPermutationTable(double[][] p) {
			int[] pTable = new int[p.length];
			for (int i = 0; i < pTable.length; i++) {
				for (int j = 0; j < p[i].length; j++) {
					if (p[i][j] == 1d) {
						pTable[i] = j;
					}
				}
			}
			return pTable;
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

}
