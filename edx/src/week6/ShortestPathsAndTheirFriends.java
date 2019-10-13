package week6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mooc.EdxIO;

public class ShortestPathsAndTheirFriends {

	static int[] firstMoveWinCache;
	static List<Integer>[] adj;
	private static long[] dist;
	private static long[] cost;
	private static int[] from;
	private static int[] to;
	private static boolean[] negCircle;
	static long MAX = (long) 2e18 + 2;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			int S = io.nextInt();
			adj = new List[N + 1];
			dist = new long[N + 1];
			from = new int[M + 1];
			to = new int[M + 1];
			cost = new long[M + 1];
			negCircle = new boolean[N + 1];
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 1; i <= M; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				long c = io.nextLong();
				adj[u].add(i);
				from[i] = u;
				to[i] = v;
				cost[i] = c;
			}
			bellmanFord(N, M, S);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < dist.length; i++) {
				if (dist[i] >= MAX) {
					sb.append("*\n");
				} else {
					if (negCircle[i]) {
						sb.append("-\n");
					} else {
						sb.append(dist[i] + "\n");
					}
				}
			}
			io.println(sb.toString());
		}
	}

	private static void bellmanFord(int N, int M, int s) {
		Arrays.fill(dist, MAX);
		dist[s] = 0;
		for (int i = 1; i <= N; i++) {
			for (int e = 1; e <= M; e++) {
				int u = from[e];
				int v = to[e];
				if (dist[u] < MAX) {
					if (dist[v] > dist[u] + cost[e]) {
						// in case negative cycle overflow
						dist[v] = Math.max(-MAX, dist[u] + cost[e]);
					}
				}
			}
		}
		for (int e = 1; e <= M; e++) {
			int u = from[e];
			int v = to[e];
			if (dist[u] < MAX) {
				if (dist[v] > dist[u] + cost[e] && !negCircle[v]) {
					markNegCycle(v);
				}
			}
		}
	}

	static void markNegCycle(int u) {
		negCircle[u] = true;
		for (int e : adj[u]) {
			int v = to[e];
			if (!negCircle[v]) {
				markNegCycle(v);
			}
		}
	}

}