package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mooc.EdxIO;

public class EdgeBiconnectivityComponents {

	static boolean[] isBridge;
	static boolean[] visited;
	static int[] from;
	static int[] to;
	static List<Integer>[] adj;
	static int[] inTime;
	static int[] component;
	static int counter = 0;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			adj = new List[N + 1];
			visited = new boolean[N + 1];
			isBridge = new boolean[M + 1];
			from = new int[M + 1];
			to = new int[M + 1];
			inTime = new int[N + 1];
			component = new int[N + 1];
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
			for (int i = 1; i <= N; i++) {
				if (!visited[i]) {
					findBridge(0, i);
				}
			}

			counter = 0;
			Arrays.fill(visited, false);
			for (int i = 1; i <= N; i++) {
				if (!visited[i]) {
					counter++;
					fillBiconnectedComponent(0, i);
				}
			}
			io.println(counter);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= N; i++) {
				sb.append(component[i] + "\n");
			}
			io.println(sb.toString());
		}
	}

	private static int findBridge(int pe, int u) {
		visited[u] = true;
		inTime[u] = counter++;
		int minReachedInTime = inTime[u];
		for (int e : adj[u]) {
			if (e != pe) {
				int v = from[e] + to[e] - u;
				if (visited[v]) {
					minReachedInTime = Math.min(minReachedInTime, inTime[v]);
				} else {
					minReachedInTime = Math.min(minReachedInTime, findBridge(e, v));
				}
			}
		}
		if (minReachedInTime >= inTime[u]) {
			isBridge[pe] = true;
		}
		return minReachedInTime;
	}

	static void fillBiconnectedComponent(int pe, int u) {
		visited[u] = true;
		component[u] = counter;
		for (int e : adj[u]) {
			int v = from[e] + to[e] - u;
			if (!visited[v]) {
				if (isBridge[e]) {
					// leave it to next dfs round
					continue;
				} else {
					fillBiconnectedComponent(e, v);
				}

			}
		}
	}

}
