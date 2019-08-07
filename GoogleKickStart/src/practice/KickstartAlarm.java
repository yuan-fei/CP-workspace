package practice;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class KickstartAlarm {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int N = in.nextInt();
			int K = in.nextInt();
			int x1 = in.nextInt();
			int y1 = in.nextInt();
			int C = in.nextInt();
			int D = in.nextInt();
			int E1 = in.nextInt();
			int E2 = in.nextInt();
			int F = in.nextInt();

			long r = solve(N, K, x1, y1, C, D, E1, E2, F);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static long solve(int n, int k, long x1, long y1, int C, int D, int E1, int E2, int F) {
		long xPrev = 0;
		long yPrev = 0;
		long x = x1;
		long y = y1;
		long a = 0;
		long sum = 0;
		long kOrderSum = 0;
		for (int i = 1; i <= n; i++) {
			if (i > 1) {
				x = (((C * xPrev) % F + (D * yPrev) % F) % F + E1) % F;
				y = (((D * xPrev) % F + (C * yPrev) % F) % F + E2) % F;
			}
			a = (x + y) % F;
			xPrev = x;
			yPrev = y;
			kOrderSum = add(kOrderSum, modSumKOrder(i, k));
			sum = add(sum, mul(mul(a, n - i + 1), kOrderSum));
		}
		return sum;
	}

	static long modSumKOrder(int i, int k) {
		if (i == 1) {
			return k;
		}
		return add(exp(i, k + 1), -i) * inverse(i - 1);
	}

	static long mod = 1000000007;
	static int N = 1000000;
	static long[] invCache = new long[N];

	static long inverse(int n) {
		if (invCache[n] == 0) {
			invCache[n] = exp(n, mod - 2);
		}
		return invCache[n];
	}

	private static long exp(long base, long exp) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			return mul(base, exp(base, exp - 1));
		} else {
			long t = exp(base, exp / 2);
			return mul(t, t);
		}
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
}
