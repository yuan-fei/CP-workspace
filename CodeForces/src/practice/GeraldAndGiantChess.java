package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GeraldAndGiantChess {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int h = in.nextInt();
		int w = in.nextInt();
		int n = in.nextInt();
		int[][] b = getIntArr(in, n, 2);
		long r = solve(h, w, n, b);
		System.out.println(r);
		in.close();
	}

	private static long solve(int h, int w, int n, int[][] b) {
		b = Arrays.copyOf(b, b.length + 1);
		b[b.length - 1] = new int[] { h, w };
		Arrays.sort(b, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int r = Integer.compare(o1[0], o2[0]);
				if (r == 0) {
					r = Integer.compare(o1[1], o2[1]);
				}
				return r;
			}
		});
		long[] dp = new long[n + 1];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = count(b[i][0] - 1, b[i][1] - 1);
		}
		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < i; j++) {
				if (contains(b[i], b[j])) {
					dp[i] = add(dp[i], -mul(dp[j], count(b[i][0] - b[j][0], b[i][1] - b[j][1])));
				}
			}
		}
		return dp[n];
	}

	private static boolean contains(int[] p1, int[] p2) {
		return p1[0] >= p2[0] && p1[1] >= p2[1];
	}

	static long mod = 1000000007;

	static long count(int h, int w) {
		return C(h + w, h);
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

	static long C(int n, int m) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = mul(fact(n), mul(inverseFact(m), inverseFact(n - m)));
			return r;
		}
	}

	static long fact1(int n) {
		long r = 1;
		for (int i = 2; i <= n; i++) {
			r = mul(r, i);
		}
		return r;
	}

	static long inverseFact1(int n) {
		return inverse(fact(n));
	}

	static int maxN = 200002;
	static boolean factCacheInitialized = false;
	static long[] factCache;
	static long[] iFactCache;

	static long fact(int n) {
		if (!factCacheInitialized) {
			initFact(maxN);
			factCacheInitialized = true;
		}
		return factCache[n];
	}

	static long inverseFact(int n) {
		if (!factCacheInitialized) {
			initFact(maxN);
			factCacheInitialized = true;
		}
		return iFactCache[n];
	}

	static void initFact(int n) {
		factCache = new long[n];
		iFactCache = new long[n];
		factCache[0] = 1;
		for (int i = 1; i < n; i++) {
			factCache[i] = mul(factCache[i - 1], i);
		}
		iFactCache[n - 1] = inverse(factCache[n - 1]);
		for (int i = n - 2; i >= 0; i--) {
			iFactCache[i] = mul(iFactCache[i + 1], i + 1);
		}
	}

	/** Fermat theorem: Note p must be prime. 1/n = n^(p-2) mod p */
	static long inverse(long n) {
		long r = exp(n, mod - 2);
		return r;
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
