package r1111;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskD {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		String s = in.next();
		int nq = in.nextInt();
		int[][] q = getIntArr(in, nq, 2);
		long[] r = solve(s, nq, q);
		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i]);
		}

		in.close();
	}

	static long mod = 1000000007;

	private static long[] solve(String s, int nq, int[][] q) {
		long[] r = new long[nq];
		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			int cnt = 0;
			if (map.containsKey(s.charAt(i))) {
				cnt = map.get(s.charAt(i));
			}
			map.put(s.charAt(i), cnt + 1);
		}
		for (int i = 0; i < q.length; i++) {
			r[i] = solve(s.length(), map, s.charAt(q[i][0] - 1), s.charAt(q[i][1] - 1));
		}
		return r;
	}

	private static long solve(int l, Map<Character, Integer> map, char c1, char c2) {
		int n1 = map.get(c1);
		int n2 = map.get(c2);
		if (c1 == c2) {
			n2 = 0;
		}
		if (n1 + n2 <= l / 2) {
			long r = C(n1, l / 2);
			r = (r * C(n2, l / 2 - n1)) % mod;
			l -= n1 + n2;
			for (Character c : map.keySet()) {
				if (c != c1 && c != c2) {
					int n = map.get(c);
					r = (r * C(n, l)) % mod;
					l -= n;
				}
			}
			return (2 * r) % mod;
		}
		return 0;
	}

	public static long C(int m, int n) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = 1;
			for (int i = 2; i <= m; i++) {
				r = (r * getModularMultiplicativeInversePrimeModulo(i, mod)) % mod;
			}
			for (int i = 0; i < m; i++) {
				r = (r * (n - i)) % mod;
			}
			return r;
		}
	}

	/** Per Fermat theorem: Note p must be prime. 1/n = n^(p-2) mod p */
	public static long getModularMultiplicativeInversePrimeModulo(int n, long p) {
		long r = modularExp(n, p - 2, p);
		return r;
	}

	public static long modularExp(int base, long exp, long n) {
		return modularExpRecursive(base, exp, n);
	}

	private static long modularExpRecursive(int base, long exp, long n) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			long l = base * modularExpRecursive(base, exp - 1, n);
			return l % n;
		} else {
			long t = modularExpRecursive(base, exp / 2, n);
			return (t * t) % n;
		}
	}

	int gcd(int a, int b) {
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
