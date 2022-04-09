package y2022.ioForWomen;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class InterestingOuting {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[][] edges = getIntArr(in, n - 1, 3);
			long r = solve(n, edges);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	static List<Integer>[] adj;
	static int[][] edges;

	private static long solve(int n, int[][] allEdges) {
		adj = buildAdj(n, allEdges);
		edges = allEdges;
		long sum = 0;
		for (int[] e : edges) {
			sum += e[2];
		}
		sum *= 2;
		long ans = Long.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			long[] cost = new long[n + 1];
			buildCost(i, -1, 0, cost);
			for (long c : cost) {
				ans = Math.min(ans, sum - c);
			}
		}
		return ans;
	}

	static void buildCost(int u, int p, long cur, long[] cost) {
		cost[u] = cur;
		for (int e : adj[u]) {
			int v = edges[e][0] + edges[e][1] - u;
			if (v != p) {
				buildCost(v, u, cur + edges[e][2], cost);
			}
		}
	}

	static List<Integer>[] buildAdj(int n, int[][] edges) {
		List<Integer>[] adj = new List[n + 1];
		for (int i = 0; i <= n; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			adj[edges[i][0]].add(i);
			adj[edges[i][1]].add(i);
		}
		return adj;
	}

	private static void test() {
		for (int i = 0; i < 10000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt();
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
