package y2022.qualification;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ChainReactions {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] weights = getIntArr(in, n);
			int[] edges = getIntArr(in, n);
			long r = solve(n, weights, edges);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	static List<Integer>[] adj;
	static int[] weights;

	private static long solve(int n, int[] allWeights, int[] edges) {
		adj = buildRootedTree(n + 1, edges);
		weights = allWeights;

		long ret = 0;
		for (int i = 0; i < n; i++) {
			if (edges[i] == 0) {
				ret += dfs(i)[0];
			}
		}
		return ret;
	}

	static long[] dfs(int r) {
		if (adj[r].isEmpty()) {
			return new long[] { weights[r], weights[r] };
		}
		long[] ret = new long[2];
		long min = Long.MAX_VALUE;
		for (int c : adj[r]) {
			long[] cRet = dfs(c);
			ret[0] += cRet[0];
			min = Math.min(min, cRet[1]);
		}
		if (weights[r] >= min) {
			ret[0] -= min;
			ret[0] += weights[r];
			ret[1] = weights[r];
		} else {
			ret[1] = min;
		}
//		debug(r + "->" + Arrays.toString(ret));
		return ret;
	}

	static private List<Integer>[] buildRootedTree(int n, int[] edges) {
		List<Integer>[] ans = new List[n];
		for (int i = 0; i < n; i++) {
			ans[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			if (edges[i] > 0) {
				ans[edges[i] - 1].add(i);
			}
		}
		return ans;
	}

	static private List<Integer>[] buildAdj(int n, int[][] edges, boolean biDirectional) {
		List<Integer>[] ans = new List[n];
		for (int i = 0; i < n; i++) {
			ans[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			ans[edges[i][0]].add(i);
			if (biDirectional) {
				ans[edges[i][1]].add(i);
			}
		}
		return ans;
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

	static void debug(Object o) {
		System.err.println(o);
	}

}
