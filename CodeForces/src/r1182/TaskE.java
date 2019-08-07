package r1182;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskE {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		long n = in.nextLong();
		long[] fi = getLongArr(in, 3);
		long c = in.nextLong();
		long r = solve(n, fi, c);
		System.out.println(r);
		in.close();
	}

	private static long solve(long n, long[] fi, long c) {
		long[][] a = new long[][] { new long[] { 0, 0, 1 }, new long[] { 1, 0, 1 }, new long[] { 0, 1, 1 } };
		long[][] exp = fastMatrixExp(a, n - 1);
		long ret = 1;
		for (int i = 0; i < fi.length; i++) {
			long term = mul(modularExp(fi[i], exp[i][0], mod), modularExp(c, mul(i + 1, exp[i][0], mod - 1), mod));
			ret = mul(ret, term);
		}

		long mi = modularExp(getModularMultiplicativeInversePrimeModulo(c, mod), n, mod);
		ret = mul(ret, mi);
		return ret;
	}

	public static long getModularMultiplicativeInversePrimeModulo(long n, long p) {
		long r = modularExp(n, p - 2, p);
		return r;
	}

	private static long modularExp(long base, long exp, long n) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			long l = base * modularExp(base, exp - 1, n);
			return l % n;
		} else {
			long t = modularExp(base, exp / 2, n);
			return (t * t) % n;
		}
	}

	/** a^n in O(m^3 * logn) */
	public static long[][] fastMatrixExp(long[][] a, long exp) {
		if (exp == 0) {
			long[][] b = new long[a.length][a.length];
			for (int i = 0; i < a.length; i++) {
				b[i][i] = 1;
			}
			return b;
		} else if (exp % 2 == 1) {
			return multiplyMatrice(a, fastMatrixExp(a, exp - 1));
		} else {
			long[][] half = fastMatrixExp(a, exp / 2);
			return multiplyMatrice(half, half);
		}
	}

	private static long[][] multiplyMatrice(long[][] a, long[][] b) {
		long[][] mul = new long[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < a[i].length; k++) {
					mul[i][j] = add(mul[i][j], mul(a[i][k], b[k][j], mod - 1), mod - 1);
					// mul[i][j] = add(mul[i][j], mul(a[i][k], b[k][j], mod), mod);
				}
			}
		}
		return mul;
	}

	static long mod = 1000000007;

	static long add(long a, long b) {
		return add(a, b, mod);
	}

	static long add(long a, long b, long mod) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return mul(a, b, mod);
	}

	static long mul(long a, long b, long mod) {
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
