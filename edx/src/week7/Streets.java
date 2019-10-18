package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mooc.EdxIO;

public class Streets {

	static List<Integer>[] adj;
	static int[] nLeaves;
	static int minDiff = Integer.MAX_VALUE;
	static int total;
	static int max = 0;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			solve(io);
		}
	}

	private static void solve(EdxIO io) {
		int N = io.nextInt();
		int M = io.nextInt();
		int K = io.nextInt();
		int[] lT = new int[N];
		int[] rT = new int[M];
		int[] I = new int[K];
		adj = new List[N + M + 2];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}

		for (int i = 0; i < N; i++) {
			lT[i] = io.nextInt();
			adj[i].add(i + 1);
			adj[i + 1].add(i);
		}
		for (int i = 0; i < M; i++) {
			rT[i] = io.nextInt();
			adj[i + N + 1].add(i + 1 + N + 1);
			adj[i + 1 + N + 1].add(i + N + 1);
		}
		for (int i = 0; i < K; i++) {
			I[i] = io.nextInt();
		}
		int f = io.nextInt();
		Arrays.sort(lT);
		Arrays.sort(rT);
		Arrays.sort(I);
		for (int i = 0; i < I.length; i++) {
			int u = Arrays.binarySearch(lT, I[i]);
			if (u < 0) {
				u = -u - 1;
			}
			int v = Arrays.binarySearch(rT, I[i]);
			if (v < 0) {
				v = -v - 1;
			}
			v += N + 1;
			adj[u].add(v);
			adj[v].add(u);
		}

		int t = Arrays.binarySearch(rT, f);
		if (t < 0) {
			t = -t - 1;
		}
		t += N + 1;

		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[N + M + 2];
		visited[0] = true;
		q.offer(0);
		int l = 0;
		while (!q.isEmpty()) {
			for (int i = q.size(); i > 0; i--) {
				int cur = q.poll();
				if (cur == t) {
					io.print(l);
					return;
				} else {
					for (int nxt : adj[cur]) {
						if (!visited[nxt]) {
							visited[nxt] = true;
							q.offer(nxt);
						}
					}
				}
			}
			l++;
		}

	}

}