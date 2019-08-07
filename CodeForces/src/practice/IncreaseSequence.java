package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IncreaseSequence {
	public static void main(String[] args) {
		// solve();
		int[] a = new int[6];
		for (int i = 0; i <= a.length - 1 - i; i++) {
			a[i] = 2000 - i;
			a[a.length - i - 1] = 2000 - i;
		}
		System.out.println(solve(a.length, 2000, a));
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int h = in.nextInt();
		int[] a = getIntArr(in, n);
		long r = solve(n, h, a);
		System.out.println(r);
		in.close();
	}

	private static long solve2(int n, int h, int[] a) {
		long open = 1;
		long close = 0;
		for (int i = 1; i <= n; i++) {
			if (h < a[i - 1]) {
				return 0;
			} else if (i > 1) {
				long tmp = 0;
				if (h - a[i - 1] == h - a[i - 2] - 1) {
					tmp = close;
				} else if (h - a[i - 1] == h - a[i - 2]) {
					tmp = add(open, close);
				} else if (h - a[i - 1] == h - a[i - 2] + 1) {
					tmp = open;
				}
				open = tmp;
				close = mul(h - a[i - 1], open);
			}
		}
		if (h - a[n - 1] > 1) {
			return 0;
		} else {
			return open;
		}
	}

	private static long solve(int n, int h, int[] a) {
		long[] prev = new long[h + 1];
		prev[0] = 1;
		for (int i = 1; i <= n; i++) {
			long[] cur = new long[n + 1];
			if (h >= a[i - 1]) {
				cur[h - a[i - 1]] = add(cur[h - a[i - 1]], prev[h - a[i - 1]]);
				if (h > a[i - 1]) {
					cur[h - a[i - 1]] = add(cur[h - a[i - 1]], prev[h - a[i - 1] - 1]);
					cur[h - a[i - 1] - 1] = mul(h - a[i - 1], cur[h - a[i - 1]]);
				}
			}
			prev = cur;
		}
		if (h - a[n - 1] > 1) {
			return 0;
		} else {
			return prev[h - a[n - 1]];
		}
	}

	static long mod = 1000000007;

	public static long C1(int n, int m) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = 1;
			for (int i = 2; i <= m; i++) {
				r = mul(r, inverse(i));
			}
			for (int i = 0; i < m; i++) {
				r = mul(r, (n - i));
			}
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

	/** set the max N for N! */
	static int maxN = 200002;
	static boolean factCacheInitialized = false;
	static long[] factCache;
	static long[] iFactCache;

	static long C2(int n, int m) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = mul(fact2(n), mul(inverseFact(m), inverseFact(n - m)));
			return r;
		}
	}

	static long fact2(int n) {
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
