package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class BipartiteGraph {
	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			List<Integer>[] adj = new List[N + 1];
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 0; i < M; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				adj[u].add(v);
				adj[v].add(u);
			}

			boolean ans = isBipartite(N, M, adj);
			io.println(ans ? "YES" : "NO");
		}
	}

	static int[] color;
	static List<Integer>[] adj;

	private static boolean isBipartite(int n, int m, List<Integer>[] adjList) {
		color = new int[n + 1];
		adj = adjList;
		for (int i = 1; i <= n; i++) {
			if (color[i] == 0) {
				if (!dfs(i, 1)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean dfs(int u, int c) {
		color[u] = c;
		for (int v : adj[u]) {
			if (color[v] == 0) {
				if (!dfs(v, 3 - c)) {
					return false;
				}
			} else if (color[v] == c) {
				return false;
			}
		}
		return true;
	}
}
