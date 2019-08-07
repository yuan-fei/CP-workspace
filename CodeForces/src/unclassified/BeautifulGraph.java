package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BeautifulGraph {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			Map<Integer, List<Integer>> edges = new HashMap<>();
			for (int j = 0; j < m; j++) {
				int from = in.nextInt();
				int to = in.nextInt();
				if (!edges.containsKey(from)) {
					edges.put(from, new ArrayList<Integer>());
				}
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Integer>());
				}
				edges.get(from).add(to);
				edges.get(to).add(from);
			}
			long r = solve(n, m, edges);
			System.out.println(r);
		}
		in.close();
	}

	static int mod = 998244353;
	static boolean[] visited;
	static int[] type;

	private static long solve1(int n, int m, Map<Integer, List<Integer>> edges) {
		visited = new boolean[n + 1];
		type = new int[n + 1];
		int t1 = 0;
		int t2 = 0;
		if (edges.size() > 0) {
			if (dfs(1, 1, edges)) {
				for (int i = 1; i <= n; i++) {
					if (type[i] == 1) {
						t1++;
					} else if (type[i] == 2) {
						t2++;
					}
				}
				return (fastExp(2, t1) + fastExp(2, t2)) % mod;
			} else {
				return 0;
			}
		} else {
			return 0;
		}

	}

	private static long solve(int n, int m, Map<Integer, List<Integer>> edges) {
		visited = new boolean[n + 1];
		type = new int[n + 1];
		int r = 1;
		int lastUnvisited = 1;
		int lastT1 = 0;
		int lastT2 = 0;
		if (edges.size() > 0) {
			while (lastUnvisited > 0) {
				int t1 = 0;
				int t2 = 0;
				if (dfs(lastUnvisited, 1, edges)) {
					lastUnvisited = 0;
					for (int i = 1; i <= n; i++) {
						if (!visited[i]) {
							lastUnvisited = i;
						}
						if (type[i] == 1) {
							t1++;
						} else if (type[i] == 2) {
							t2++;
						}
					}
					int tmp = (fastExp(2, t1 - lastT1) + fastExp(2, t2 - lastT2)) % mod;
					r = (r * tmp) % mod;
					lastT1 = t1;
					lastT2 = t2;
				} else {
					return 0;
				}
			}
			return r;
		} else {
			return 0;
		}

	}

	private static int fastExp(int base, int c) {
		if (c == 0) {
			return 1;
		}
		if (c % 2 == 1) {
			return (base * fastExp(base, c - 1)) % mod;
		} else {
			int e = fastExp(base, c / 2);
			return (e * e) % mod;
		}
	}

	private static boolean dfs(int u, int t, Map<Integer, List<Integer>> edges) {
		visited[u] = true;
		type[u] = t;
		List<Integer> list = edges.get(u);
		if (list != null) {
			for (int v : edges.get(u)) {
				if (visited[v] && type[v] == t) {
					return false;
				} else if (!visited[v]) {
					if (!dfs(v, 3 - t, edges)) {
						return false;
					}
				}
			}
		}

		return true;
	}

}
