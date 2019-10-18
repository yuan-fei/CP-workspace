package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class LinearProgramming {
	private static int N;
	private static int M;
	static List<Integer>[] adj;
	static int[] visited;
	static int[] parent;
	static int[] nLeaves;
	static int minDiff = Integer.MAX_VALUE;
	static int total;
	static int max = 0;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	static void generate() {
		try (EdxIO io = EdxIO.create()) {
			N = 3;
			io.println(N);
			for (int i = 1; i < N; i++)
				io.println(1 + " " + (i + 1));
		}
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			solve(io);
		}
	}

	private static void solve(EdxIO io) {
		N = io.nextInt();
		M = io.nextInt();
		adj = new List[2 * N + 1];
		visited = new int[2 * N + 1];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			String sign1 = io.next();
			int u = io.nextInt();
			String sign2 = io.next();
			int v = io.nextInt();
			if (sign1.equals("+") && sign2.equals("-")) {
				adj[u].add(v);
				adj[v + N].add(u + N);
			}
			if (sign1.equals("-") && sign2.equals("+")) {
				adj[v].add(u);
				adj[u + N].add(v + N);
			}
			if (sign1.equals("-") && sign2.equals("-")) {
				adj[v + N].add(u);
				adj[u + N].add(v);
			}
			if (sign1.equals("+") && sign2.equals("+")) {
				adj[v].add(u + N);
				adj[u].add(v + N);
			}
		}

		for (int i = 0; i < 2 * N + 1; i++) {
			if (visited[i] == 0 && dfs(i)) {
				io.print("NO");
				return;
			}
		}
		io.println("YES");
	}

	private static boolean dfs(int r) {
		visited[r] = 1;
		for (int c : adj[r]) {
			if (visited[c] == 1) {
				return true;
			} else if (visited[c] == 0 && dfs(c)) {
				return true;
			}
		}
		visited[r] = 2;
		return false;
	}

}