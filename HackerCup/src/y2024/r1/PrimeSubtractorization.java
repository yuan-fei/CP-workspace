package y2024.r1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PrimeSubtractorization {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int r = solve(n);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	static int MAX = 10000000;
	static List<Integer> subtractorizations;

	private static int solve(int n) {
		if (subtractorizations == null) {
			subtractorizations = getSubtractorizations(MAX);
		}
		int idx = Collections.binarySearch(subtractorizations, n - 2);
		if (idx < 0) {
			idx = -idx - 1;
		} else {
			idx++;
		}
		return idx;
	}

	private static List<Integer> getSubtractorizations(int n) {
		List<Integer> primes = linearSieve(n);
		Set<Integer> primeSet = new HashSet<>(primes);
		List<Integer> subtractorizations = new ArrayList<>();
		for (int p : primes) {
			if (p == 2 || primeSet.contains(p + 2)) {
				subtractorizations.add(p);
			}
		}
		return subtractorizations;
	}

	private static List<Integer> linearSieve(int n) {
		List<Integer> primes = new ArrayList<>();
		boolean[] isComposite = new boolean[n + 1];
		for (int i = 2; i <= n; i++) {
			if (!isComposite[i]) {
				primes.add(i);
			}
			for (int j = 0; j < primes.size() && 1L * i * primes.get(j) <= n; j++) {
				isComposite[i * primes.get(j)] = true;
				if (i % primes.get(j) == 0) {
					break;
				}
			}
		}
		return primes;
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
		long r = a * b;
		while (r < 0) {
			r += mod;
		}
		return r % mod;
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

	/** graph */
	static List<Integer>[] buildAdj(int n, int[][] edges, boolean directed) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int[] e : edges) {
			adj[e[0]].add(e[1]);
			if (!directed) {
				adj[e[1]].add(e[0]);
			}
		}
		return adj;
	}

	static List<Integer>[] buildAdjWithEdgeIndex(int n, int[][] edges, boolean directed) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			int[] e = edges[i];
			adj[e[0]].add(i);
			if (!directed) {
				adj[e[1]].add(i);
			}
		}
		return adj;
	}

	private static class DSU {
		int[] parent;

		public DSU(int N) {
			this.parent = new int[N];
			for (int i = 0; i < N; i++) {
				add(i);
			}
		}

		public void add(int x) {
			parent[x] = x;
		}

		public int find(int x) {
			if (parent[x] != x)
				parent[x] = find(parent[x]);
			return parent[x];
		}

		public void union(int x, int y) {
			if (find(x) != find(y)) {
				parent[find(x)] = parent[find(y)];
			}
		}
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static <K> void inc(Map<K, Integer> m, K k) {
		m.put(k, m.getOrDefault(k, 0) + 1);
	}

	static <K> void dec(Map<K, Integer> m, K k) {
		m.put(k, m.getOrDefault(k, 0) - 1);
		if (m.get(k) <= 0) {
			m.remove(k);
		}
	}
}
