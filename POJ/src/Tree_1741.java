import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Tree_1741 {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int k = in.nextInt();
		while (n != 0 || k != 0) {
			Map<Integer, List<Edge>> a = getEdges(in, n - 1, false);
			long r = solveOptimized(n, k, a);
			System.out.println(r);
			n = in.nextInt();
			k = in.nextInt();
		}

		in.close();
	}

	private static long solve(int n, int K, Map<Integer, List<Edge>> edges) {
		Random rnd = new Random();
		int r = rnd.nextInt(n) + 1;
		while (!edges.containsKey(r)) {
			r = rnd.nextInt(n) + 1;
		}
		return solve(n, K, edges, r, -1);
	}

	private static long solve(int n, int K, Map<Integer, List<Edge>> edges, int r, int p) {
		long count = 0;
		for (Edge e : edges.get(r)) {
			if (e.to != p) {
				long c = solve(n, K, edges, e.to, r);
				count += c;
				// System.out.println(String.format("%d->%d: +%d", r, e.to, c));
			}
		}
		List<Integer> all = new ArrayList<Integer>();
		all.add(0);
		for (Edge e : edges.get(r)) {
			if (e.to != p) {
				List<Integer> subPaths = new ArrayList<Integer>();
				enumeratePaths(K, edges, e.to, r, e.l, subPaths);
				long c = count(subPaths, K);
				count -= c;
				all.addAll(subPaths);
				// System.out.println(subPaths);
				// System.out.println(String.format("%d->%d: -%d", r, e.to, c));
			}
		}
		long c = count(all, K);
		count += c;
		// System.out.println(all);
		// System.out.println(String.format("%d->%d", r, c));
		return count;
	}

	static boolean[] centroidMarked;
	static int[] subTreeSize;

	private static long solveOptimized(int n, int K, Map<Integer, List<Edge>> edges) {
		Random rnd = new Random();
		int r = rnd.nextInt(n) + 1;
		while (!edges.containsKey(r)) {
			r = rnd.nextInt(n) + 1;
		}

		// debug
		// r = 3;

		centroidMarked = new boolean[10005];
		subTreeSize = new int[10005];
		return solveOptimized(n, K, edges, r);
	}

	private static long solveOptimized(int n, int K, Map<Integer, List<Edge>> edges, int r) {
		long count = 0;
		int treeSize = getSubTreeSize(edges, r, -1);
		int centroid = findCentroid(edges, r, -1, treeSize)[0];
		// System.out.println(String.format("centroid %d: %d", r, centroid));
		centroidMarked[centroid] = true;
		for (Edge e : edges.get(centroid)) {
			if (!centroidMarked[e.to]) {
				long c = solveOptimized(n, K, edges, e.to);
				count += c;
				// System.out.println(String.format("%d->%d: +%d", centroid,
				// e.to, c));
			}
		}
		List<Integer> all = new ArrayList<Integer>();
		all.add(0);
		for (Edge e : edges.get(centroid)) {
			if (!centroidMarked[e.to]) {
				List<Integer> subPaths = new ArrayList<Integer>();
				enumeratePaths(K, edges, e.to, centroid, e.l, subPaths);
				long c = count(subPaths, K);
				count -= c;
				all.addAll(subPaths);
				// System.out.println(subPaths);
				// System.out.println(String.format("%d->%d: -%d", centroid,
				// e.to, c));
			}
		}
		long c = count(all, K);
		count += c;
		// System.out.println(all);
		// System.out.println(String.format("%d->%d", r, c));
		centroidMarked[centroid] = false;
		return count;
	}

	private static void enumeratePaths(int K, Map<Integer, List<Edge>> edges, int r, int p, int l,
			List<Integer> paths) {
		if (l <= K) {
			paths.add(l);
		}
		List<Edge> neighbors = edges.get(r);
		if (neighbors != null) {
			for (Edge n : neighbors) {
				if (n.to != p && !centroidMarked[n.to]) {
					enumeratePaths(K, edges, n.to, r, l + n.l, paths);
				}
			}
		}
	}

	private static long count(List<Integer> l, int K) {
		Collections.sort(l);
		int cnt = 0;
		int right = l.size() - 1;
		for (int i = 0; i < l.size(); i++) {
			while (right >= 0 && l.get(i) + l.get(right) > K) {
				right--;
			}
			// don not count 'self' in
			cnt += right + (right < i ? 1 : 0);
		}
		return cnt / 2;
	}

	static int getSubTreeSize(Map<Integer, List<Edge>> edges, int r, int p) {
		int size = 1;
		List<Edge> neighbors = edges.get(r);
		if (neighbors != null) {
			for (Edge n : neighbors) {
				if (n.to != p && !centroidMarked[n.to]) {
					size += getSubTreeSize(edges, n.to, r);
				}
			}
		}
		subTreeSize[r] = size;
		return size;
	}

	/** O(SubTreeSize(r)) with cached subtree size */
	private static int[] findCentroid(Map<Integer, List<Edge>> edges, int r, int p, int totalSize) {
		int centroid = r;
		int totalSubTreeSize = 1;
		int maxSubTreeSize = 0;
		List<Edge> neighbors = edges.get(r);
		if (neighbors != null) {
			for (Edge n : neighbors) {
				if (n.to != p && !centroidMarked[n.to]) {
					// O(1)
					int s = subTreeSize[n.to];
					// System.out.println(String.format("%d->%d: %d", r, n.to,
					// subTreeSize));
					maxSubTreeSize = Math.max(s, maxSubTreeSize);
					totalSubTreeSize += s;
				}
			}
			maxSubTreeSize = Math.max(totalSize - totalSubTreeSize, maxSubTreeSize);
			// System.out.println(String.format("maxSubTreeSize: %d: %d", r,
			// maxSubTreeSize));
			for (Edge n : neighbors) {
				if (n.to != p && !centroidMarked[n.to]) {
					int[] candidate = findCentroid(edges, n.to, r, totalSize);
					// System.out.println(String.format("%d->%d-> %s", r, n.to,
					// Arrays.toString(candidate)));
					if (candidate[1] < maxSubTreeSize) {
						centroid = candidate[0];
						maxSubTreeSize = candidate[1];
						// System.out.println(
						// String.format("candidate change %d->%d-> %s", r,
						// n.to, Arrays.toString(candidate)));
					}
				}
			}
		}
		return new int[] { centroid, maxSubTreeSize };
		// return new int[] { r, 0 };
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

	static class Edge {
		int to;
		int l;

		public Edge(int to, int l) {
			this.to = to;
			this.l = l;
		}

	}

	static Map<Integer, List<Edge>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Edge>> edges = new HashMap<Integer, List<Edge>>();
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			int weight = in.nextInt();
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<Edge>());
			}
			edges.get(from).add(new Edge(to, weight));
			if (!directed) {
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Edge>());
				}
				edges.get(to).add(new Edge(from, weight));
			}

		}
		return edges;
	}
}
