package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mooc.EdxIO;

public class StronglyConnectedComponents {

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static boolean[] visited;
	private static List<Integer>[] adj;
	private static List<Integer>[] reverseAdj;
	private static int[] component;
	private static List<Integer> topoList;
	private static int componentCounter = 1;

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			adj = new List[N + 1];
			reverseAdj = new List[N + 1];
			visited = new boolean[N + 1];
			component = new int[N + 1];
			topoList = new ArrayList<Integer>();
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
				reverseAdj[i] = new ArrayList<>();
			}
			for (int i = 0; i < M; i++) {
				int from = io.nextInt();
				int to = io.nextInt();
				adj[from].add(to);
				reverseAdj[to].add(from);
			}
			for (int i = 1; i <= N; i++) {
				if (!visited[i]) {
					topologicalSort(i);
				}
			}
			Arrays.fill(visited, false);
			for (int i = topoList.size() - 1; i >= 0; i--) {
				if (!visited[topoList.get(i)]) {
					dfs(topoList.get(i), componentCounter++);
				}
			}
			io.println(componentCounter - 1);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= N; i++) {
				sb.append(component[i] + "\n");
			}
			io.println(sb.toString());
		}
	}

	private static void topologicalSort(int u) {
		visited[u] = true;
		for (int v : adj[u]) {
			if (!visited[v]) {
				topologicalSort(v);
			}
		}
		topoList.add(u);
	}

	private static void dfs(int u, int n) {
		visited[u] = true;
		component[u] = n;
		for (int v : reverseAdj[u]) {
			if (!visited[v]) {
				dfs(v, n);
			}
		}
	}

}
