import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class Grapes {
	static final int MAX_SIZE = 1_000_005;

	private static int N;
	static int[][] adj;
	static int[] parent;
	static int[] nLeaves;
	static int minDiff = Integer.MAX_VALUE;
	static int total;
	static int max = 0;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			N = io.nextInt();

			int[][] edges = new int[N - 1][2];
			for (int i = 0; i < edges.length; i++) {
				edges[i][0] = io.nextInt();
				edges[i][1] = io.nextInt();
			}

			int[] degrees = new int[N + 1];
			for (int[] edge : edges) {
				degrees[edge[0]]++;
				degrees[edge[1]]++;
			}

			adj = new int[N + 1][];
			for (int i = 1; i <= N; i++) {
				adj[i] = new int[degrees[i]];
			}

			total = 0;
			for (int i = 2; i <= N; i++) {
				if (degrees[i] == 1) {
					total++;
				}
			}

			nLeaves = new int[N + 1];
			for (int i = 0; i <= N; i++) {
				nLeaves[i] = 0;
			}
			for (int[] edge : edges) {
				int u = edge[0];
				int v = edge[1];

				degrees[u]--;
				adj[u][degrees[u]] = v;

				degrees[v]--;
				adj[v][degrees[v]] = u;
			}

			stacklessDFS();
			for (int i = 1; i <= N; i++) {
				minDiff = Math.min(minDiff, Math.abs(total - 2 * nLeaves[i]));
			}
			io.println((total - minDiff) / 2);

		}
	}

	private static void stacklessDFS() {
		int[][] stack = new int[MAX_SIZE][2];
		int stackSize = 0;

		stack[stackSize][0] = 1;
		stack[stackSize][1] = 0;
		stackSize++;

		nLeaves[1] = 0;
		while (stackSize != 0) {
			int r = stack[stackSize - 1][0];
			int p = stack[stackSize - 1][1];
			if (nLeaves[r] == 0) {
				boolean isLeaf = true;
				for (int c : adj[r]) {
					if (c != p) {
						stack[stackSize][0] = c;
						stack[stackSize][1] = r;
						stackSize++;

						isLeaf = false;
					}
				}
				if (isLeaf) {
					nLeaves[r] = 1;
					nLeaves[p] += nLeaves[r];

					stackSize--;
				}
			} else if (nLeaves[r] > 0) {
				nLeaves[p] += nLeaves[r];

				stackSize--;
			}
		}
	}
}