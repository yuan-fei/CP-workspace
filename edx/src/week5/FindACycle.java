package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mooc.EdxIO;

public class FindACycle {
	static int[] visited;
	static List<Integer>[] adj;
	static List<Integer> cycle;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			adj = new List[N + 1];
			visited = new int[N + 1];
			cycle = new ArrayList<>();
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 1; i <= M; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				adj[u].add(v);
			}

			for (int i = 1; i <= N; i++) {
				if (visited[i] == 0) {
					if (findCycle(i, new ArrayList<>())) {
						break;
					}
				}
			}
			if (cycle.isEmpty()) {
				io.println("NO");
			} else {
				io.println("YES");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < cycle.size(); i++) {
					if (i == 0) {
						sb.append(cycle.get(i));
					} else {
						sb.append(" " + cycle.get(i));
					}

				}
				io.println(sb.toString());
			}
		}
	}

	private static boolean findCycle(int u, List<Integer> path) {
		visited[u] = 1;
		path.add(u);
		for (int v : adj[u]) {
			if (visited[v] == 0) {
				if (findCycle(v, path)) {
					return true;
				}
			} else if (visited[v] == 1) {
				for (int i = path.size() - 1; i >= 0; i--) {
					cycle.add(path.get(i));
					if (path.get(i) == v) {
						break;
					}
				}
				Collections.reverse(cycle);
				return true;
			}
		}
		path.remove(path.size() - 1);
		visited[u] = 2;
		return false;
	}

}
