package week6;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class ShortestPathsBetweenAllPairs {

	private static int[][] adj;
	static int MAX = (int) 1e9 + 2;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			adj = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {

					adj[i][j] = io.nextInt();
					if (adj[i][j] == -1) {
						adj[i][j] = MAX;
					}
				}
			}
			floydWarshall(N);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sb.append(((adj[i][j] == MAX) ? -1 : adj[i][j]) + " ");
				}
				sb.append("\n");
			}
			io.println(sb.toString());
		}
	}

	private static void floydWarshall(int N) {
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (adj[i][k] < MAX && adj[k][j] < MAX) {
						adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
					}
				}
			}
		}
	}

}