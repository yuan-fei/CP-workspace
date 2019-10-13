package r2019F;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpectatingVillages {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int V = in.nextInt();
			int[] B = getIntArr(in, V);
			List<Integer>[] adj = getEdges(in, V, V - 1);
			long r = solve(V, B, adj);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static long solve(int V, int[] B, List<Integer>[] adj) {
		State s = dfs(-1, 0, B, adj);
		return Math.max(s.maxWithLightHouse, Math.max(s.maxWithoutLightHouse[0], s.maxWithoutLightHouse[1]));
	}

	static State dfs(int p, int u, int[] B, List<Integer>[] adj) {
		State s = new State();
		s.u = u;
		s.maxWithLightHouse += B[u];
		s.maxWithoutLightHouse[1] = Integer.MIN_VALUE;
		for (int v : adj[u]) {
			if (v != p) {
				State ret = dfs(u, v, B, adj);
				long maxMaxWithoutLightHouse = Math.max(ret.maxWithoutLightHouse[0], ret.maxWithoutLightHouse[1]);
				if (s.maxWithoutLightHouse[1] == Integer.MIN_VALUE) {
					s.maxWithoutLightHouse[1] = ret.maxWithLightHouse;
				} else {
					s.maxWithoutLightHouse[1] = Math.max(s.maxWithoutLightHouse[0] + ret.maxWithLightHouse,
							s.maxWithoutLightHouse[1] + Math.max(maxMaxWithoutLightHouse, ret.maxWithLightHouse));
				}

				s.maxWithoutLightHouse[0] += maxMaxWithoutLightHouse;
				s.maxWithLightHouse += Math.max(ret.maxWithLightHouse,
						Math.max(ret.maxWithoutLightHouse[0] + B[v], ret.maxWithoutLightHouse[1]));
			}
		}
		if (s.maxWithoutLightHouse[1] != Integer.MIN_VALUE) {
			s.maxWithoutLightHouse[1] += B[u];
		}
		// System.out.println(s);
		return s;
	}

	static class State {
		int u;
		long maxWithLightHouse;
		// 0 - unilluminated, 1 - illuminated
		long[] maxWithoutLightHouse = new long[2];

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "" + u + ":" + maxWithLightHouse + "," + maxWithoutLightHouse[0] + "," + maxWithoutLightHouse[1];
		}
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static List<Integer>[] getEdges(Scanner in, int V, int size) {
		List<Integer>[] edges = new List[V];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new ArrayList<>();
		}
		for (int i = 0; i < size; i++) {
			int from = in.nextInt() - 1;
			int to = in.nextInt() - 1;
			edges[from].add(to);
			edges[to].add(from);
		}
		return edges;
	}

}
