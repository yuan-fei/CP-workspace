package r1284;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TaskD {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int n = in.nextInt();
		int[][] a = new int[n][2];
		int[][] b = new int[n][2];
		for (int i = 0; i < n; i++) {
			a[i][0] = in.nextInt();
			a[i][1] = in.nextInt();
			b[i][0] = in.nextInt();
			b[i][1] = in.nextInt();
		}

		boolean r = solve(n, a, b);
		System.out.println(r ? "YES" : "NO");
		in.close();
	}

	static class Event {
		int t;
		int s, e;
		int isIn;

		public Event(int t, int s, int e, int isIn) {
			super();
			this.t = t;
			this.s = s;
			this.e = e;
			this.isIn = isIn;
		}

	}

	private static boolean solve(int n, int[][] a, int[][] b) {
		return check(n, a, b) && check(n, b, a);
	}

	private static boolean check(int n, int[][] a, int[][] b) {
		Event[] events = new Event[2 * n];
		for (int i = 0; i < n; i++) {
			events[2 * i] = new Event(a[i][0], b[i][0], b[i][1], 1);
			events[2 * i + 1] = new Event(a[i][1] + 1, b[i][0], b[i][1], 0);
		}
		Arrays.sort(events,
				(x, y) -> Integer.compare(x.t, y.t) != 0 ? Integer.compare(x.t, y.t) : Integer.compare(x.isIn, y.isIn));
		TreeMap<Integer, Integer> begin = new TreeMap<>();
		TreeMap<Integer, Integer> end = new TreeMap<>();
		for (Event e : events) {
			if (e.isIn == 1) {
				if (!begin.isEmpty()) {
					int maxS = begin.lastKey();
					int minE = end.firstKey();
					int intersectionS = Math.max(maxS, e.s);
					int intersectionE = Math.min(minE, e.e);
					if (intersectionS > intersectionE) {
						return false;
					}
				}
				add(begin, e.s);
				add(end, e.e);
			} else {
				remove(begin, e.s);
				remove(end, e.e);
			}
		}
		return true;
	}

	static void remove(TreeMap<Integer, Integer> m, int k) {
		if (m.containsKey(k)) {
			int cnt = m.get(k) - 1;
			if (cnt == 0) {
				m.remove(k);
			} else {
				m.put(k, cnt);
			}
		}

	}

	static void add(TreeMap<Integer, Integer> m, int k) {
		int cnt = m.getOrDefault(k, 0) + 1;
		m.put(k, cnt);
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
