package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class HarryPotterAndTheRailroad {

	static boolean[] visited;
	static List<Integer>[] adj;
	static int[] from;
	static int[] to;
	static int[] label;
	static int root;
	static int counter = 1;

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
			label = new int[M + 1];
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

			if (!findRoot()) {
				io.println("IMPOSSIBLE");
			} else {
				dfs(root);
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < label.length; i++) {
					sb.append(label[i] + "\n");
				}
				io.println(sb.toString());
			}
		}
	}

	private static void dfs(int u) {
		visited[u] = true;
		for (int e : adj[u]) {
			if (label[e] == 0) {
				label[e] = counter++;
				int v = from[e] + to[e] - u;
				if (!visited[v]) {
					dfs(v);
				}
			}
		}
	}

	private static boolean findRoot() {
		root = 1;
		if (adj.length - 1 == 2) {
			return true;
		}
		boolean seenSingle = false;
		for (int i = 0; i < adj.length; i++) {
			if (adj[i].size() == 1) {
				if (!seenSingle) {
					root = i;
					seenSingle = true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

}
