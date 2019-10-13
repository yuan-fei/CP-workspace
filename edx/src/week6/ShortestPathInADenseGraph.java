package week6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import mooc.EdxIO;

public class ShortestPathInADenseGraph {

	private static long[][] adj;
	private static long[] dist;
	static long MAX = (long) 2e12 + 2;
	private static boolean[] done;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int S = io.nextInt();
			int F = io.nextInt();
			adj = new long[N + 1][N + 1];
			dist = new long[N + 1];
			done = new boolean[N + 1];
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					adj[i][j] = io.nextLong();
				}
			}
			dijkstra(N, S, F);
			io.println(dist[F] >= MAX ? -1 : dist[F]);
		}
	}

	private static void dijkstra(int N, int s, int f) {
		Arrays.fill(dist, MAX);
		int minV = s;
		long minDist = MAX;
		dist[s] = 0;
		done[s] = true;
		while (minV != f) {
			for (int i = 1; i <= N; i++) {
				if (minV != i && adj[minV][i] != -1) {
					dist[i] = Math.min(dist[minV] + adj[minV][i], dist[i]);
				}
			}
			minV = -1;
			minDist = MAX;
			for (int i = 1; i <= N; i++) {
				if (!done[i] && dist[i] < minDist) {
					minV = i;
					minDist = dist[i];
				}
			}
			if (minV == -1) {
				return;
			}
			done[minV] = true;
		}
	}

}