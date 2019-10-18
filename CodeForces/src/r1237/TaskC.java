package r1237;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TaskC {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = 1;
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int[][] a = getIntArr(in, n, 3);
			solve(n, a);
			System.out.println(sb.toString());
		}

		in.close();
	}

	static StringBuilder sb = new StringBuilder();

	private static void solve(int n, int[][] a) {
		List<Integer> ids = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ids.add(i);
		}
		dfs(a, ids, 0);
	}

	static int dfs(int[][] a, List<Integer> ids, int k) {
		if (k == 3) {
			return ids.get(0);
		}
		Map<Integer, List<Integer>> m = new TreeMap<>();
		for (int id : ids) {
			List<Integer> l = m.getOrDefault(a[id][k], new ArrayList<>());
			l.add(id);
			m.put(a[id][k], l);
		}
		List<Integer> singles = new ArrayList<>();
		for (int key : m.keySet()) {
			int single = dfs(a, m.get(key), k + 1);
			if (single != -1)
				singles.add(single);
		}
		for (int i = 0; i + 1 < singles.size(); i += 2) {
			sb.append((singles.get(i) + 1) + " " + (singles.get(i + 1) + 1) + "\n");
		}
		if (singles.size() % 2 != 0) {
			return singles.get(singles.size() - 1);
		} else {
			return -1;
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

	static String str(Integer[] r) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < r.length; i += 2) {
			sb.append(r[i] + " " + r[i + 1] + "\n");
		}
		return sb.toString();
	}

	static String str(int[][] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i][0] + " " + a[i][1] + "\n");
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
