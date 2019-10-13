package week5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class AGameOnGraph {

	static int[] firstMoveWinCache;
	static List<Integer>[] adj;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int M = io.nextInt();
			int S = io.nextInt();
			adj = new List[N + 1];
			firstMoveWinCache = new int[N + 1];
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 1; i <= M; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				adj[u].add(v);
			}

			if (firstMoveWin(S)) {
				io.println("Alice");
			} else {
				io.println("Bob");
			}

		}
	}

	private static boolean firstMoveWin(int u) {
		if (firstMoveWinCache[u] == 0) {
			for (int v : adj[u]) {
				if (!firstMoveWin(v)) {
					firstMoveWinCache[u] = 1;
					break;
				}
			}
			if (firstMoveWinCache[u] == 0) {
				firstMoveWinCache[u] = 2;
			}
		}
		return firstMoveWinCache[u] == 1;
	}

}