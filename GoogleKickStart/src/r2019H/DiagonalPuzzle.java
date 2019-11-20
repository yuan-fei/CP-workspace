package r2019H;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Graph 2-coloring: diagonal as vertex
 * 
 * https://codeforces.com/blog/entry/71373
 */
public class DiagonalPuzzle {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			char[][] puzzle = new char[n][];
			for (int j = 0; j < n; j++) {
				puzzle[j] = getCharArr(in, n);
			}
			int ans = solve(n, puzzle);
			System.out.println("Case #" + i + ": " + ans);
		}
		in.close();
	}

	private static int solve(int n, char[][] puzzle) {
		List<int[]>[] adj = new List[4 * n - 2];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int u = j - i + n - 1;
				int v = j + i + 2 * n - 1;
				adj[u].add(new int[] { v, (puzzle[i][j] == '.') ? 1 : 0 });
				adj[v].add(new int[] { u, (puzzle[i][j] == '.') ? 1 : 0 });
			}
		}
		boolean[] visited = new boolean[4 * n - 2];
		int min = 0;
		for (int i = 0; i < adj.length; i++) {
			if (!visited[i]) {
				int[] colorCount = new int[2];
				dfs(visited, adj, colorCount, i, 0);
				min += Math.min(colorCount[0], colorCount[1]);
			}
		}
		return min;
	}

	static void dfs(boolean[] visited, List<int[]>[] adj, int[] colorCount, int u, int color) {
		visited[u] = true;
		colorCount[color]++;
		for (int[] v : adj[u]) {
			if (!visited[v[0]]) {
				dfs(visited, adj, colorCount, v[0], (color + v[1]) % 2);
			}
		}
	}

	private static long mod = 1000000007;

	private static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	private static long mul(long a, long b) {
		return (a * b) % mod;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	private static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	private static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	private static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	private static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	private static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	private static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	private static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
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
