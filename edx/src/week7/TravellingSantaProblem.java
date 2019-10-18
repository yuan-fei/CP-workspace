package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mooc.EdxIO;

public class TravellingSantaProblem {

	static Map<Integer, List<Integer>> adj;
	static Map<Integer, Integer> cache;
	static Set<Integer> vertices;
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
		adj = new HashMap<>();
		cache = new HashMap<>();
		vertices = new HashSet<>();
		for (int i = 0; i < N; i++) {
			int u = io.nextInt();
			int v = io.nextInt();
			if (!adj.containsKey(u)) {
				adj.put(u, new ArrayList<>());
			}
			adj.get(u).add(v);
			vertices.add(u);
			vertices.add(v);
		}
		int max = 0;
		for (int u : vertices) {
			if (!cache.containsKey(u)) {
				max = Math.max(max, dfs(u));
			}
		}
		io.println(max);
	}

	static int dfs(int u) {
		if (!cache.containsKey(u)) {
			int self = 0;
			int max = 0;
			if (adj.containsKey(u)) {
				for (int v : adj.get(u)) {
					if (u == v) {
						self++;
					} else {
						max = Math.max(max, 1 + dfs(v));
					}
				}
			}
			cache.put(u, max + self);
		}
		return cache.get(u);
	}

}