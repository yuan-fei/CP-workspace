package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class VertexBiconnectivityComponents {

	static boolean[] visited;
	static int[] from;
	static int[] to;
	static List<Integer>[] adj;
	static int[] disc;
	static int[] low;
	static int[] component;
	static int counter = 0;
	static int[] parent;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			adj = new List[N + 1];
			visited = new boolean[N + 1];
			from = new int[M + 1];
			to = new int[M + 1];
			disc = new int[N + 1];
			low = new int[N + 1];
			parent = new int[M + 1];
			component = new int[M + 1];
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 1; i <= M; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				from[i] = u;
				to[i] = v;
				adj[u].add(i);
				adj[v].add(i);
			}
			for (int i = 0; i < parent.length; i++) {
				add(i);
			}
			for (int i = 1; i <= N; i++) {
				if (!visited[i]) {
					dfs(0, i);
				}
			}

			counter = 1;
			fillBiconnectedComponent();
			io.println(counter - 1);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= M; i++) {
				sb.append(component[i] + "\n");
			}
			io.println(sb.toString());
		}
	}

	private static void dfs(int pe, int u) {
		visited[u] = true;
		disc[u] = low[u] = counter++;
		int minTime = disc[u];
		for (int e : adj[u]) {
			if (e != pe) {
				int v = from[e] + to[e] - u;
				if (!visited[v]) {
					dfs(e, v);
				}
				if (low[v] < disc[u]) {
					// find a back edge
					union(pe, e);
					minTime = Math.min(minTime, low[v]);
				}
			}
		}
		low[u] = minTime;
	}

	static void add(int u) {
		parent[u] = u;
	}

	static int findSet(int u) {
		int p = parent[u];
		if (p != u) {
			parent[u] = findSet(p);
		}
		return parent[u];
	}

	static void union(int u, int v) {
		int ru = findSet(u);
		int rv = findSet(v);
		if (rv != ru) {
			parent[ru] = rv;
		}
	}

	static void fillBiconnectedComponent() {
		for (int i = 1; i < component.length; i++) {
			if (component[i] == 0) {
				fill(i);
			}
		}
	}

	private static void fill(int u) {
		int p = parent[u];
		if (component[p] == 0) {
			if (u == p) {
				component[p] = counter++;
			} else {
				fill(p);
			}
		}
		component[u] = component[p];
	}

}