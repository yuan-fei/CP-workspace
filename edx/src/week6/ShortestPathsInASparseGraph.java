package week6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import mooc.EdxIO;

public class ShortestPathsInASparseGraph {

	static List<Integer>[] adj;
	private static int[] dist;
	private static int[] cost;
	private static int[] from;
	private static int[] to;
	static int MAX = (int) 1e9 + 2;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			adj = new List[N + 1];
			dist = new int[N + 1];
			from = new int[M + 1];
			to = new int[M + 1];
			cost = new int[M + 1];
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 1; i <= M; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				int c = io.nextInt();
				adj[u].add(i);
				adj[v].add(i);
				from[i] = u;
				to[i] = v;
				cost[i] = c;
			}
			dijkstra(N, M, 1);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < dist.length; i++) {
				sb.append(dist[i] + "\n");
			}
			io.println(sb.toString());
		}
	}

	private static void dijkstra(int N, int M, int s) {
		Arrays.fill(dist, MAX);
		PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
		q.offer(new int[] { s, 0 });
		int done = 0;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			if (dist[cur[0]] > cur[1]) {
				dist[cur[0]] = cur[1];
				done++;
				if (done == N) {
					return;
				} else {
					for (int e : adj[cur[0]]) {
						int v = from[e] + to[e] - cur[0];
						q.offer(new int[] { v, dist[cur[0]] + cost[e] });
					}
				}
			}
		}
	}

}