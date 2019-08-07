package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class DirectingEdges {
	public static void main(String[] args) {
		test();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int j = 0; j < t; j++) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[][] edges = new int[m][2];
			for (int i = 0; i < m; i++) {
				int from = in.nextInt();
				int to = in.nextInt();
				edges[i][0] = from;
				edges[i][1] = to;
			}
			int[] r = solve(n, m, edges);
			System.out.println(getStr(r));
		}

		in.close();
	}

	private static void test() {
		int[][] edgeInput = new int[][] { new int[] { 1, 2 }, new int[] { 3, 2 }, new int[] { 4, 1 },
				new int[] { 5, 3 }, new int[] { 6, 4 }, new int[] { 7, 2 }, new int[] { 8, 2 }, new int[] { 4, 5 },
				new int[] { 5, 6 }, new int[] { 7, 8 } };
		edgeInput = new int[][] { new int[] { 1, 2 }, new int[] { 3, 1 }, new int[] { 4, 3 }, new int[] { 5, 4 },
				new int[] { 6, 5 }, new int[] { 7, 6 }, new int[] { 8, 7 }, new int[] { 2, 7 }, new int[] { 1, 4 },
				new int[] { 6, 8 } };
		int n = 4;
		int m = edgeInput.length;
		int[] r = solve(n, m, edgeInput);
		System.out.println(Arrays.toString(r));
		System.out.println(verify(n, m, edgeInput, r));

		// for (int n = 8; n <= 8; n++) {
		// for (int m = n - 1; m <= n * (n - 1) / 2; m++) {
		// for (int i = 0; i < 1000; i++) {
		// edgeInput = generateGraph(n, m);
		// // System.out.println("G: " + Arrays.deepToString(edgeInput));
		// int[] r = solve(n, m, edgeInput);
		// // System.out.println("D: " + Arrays.toString(r));
		// if (!verify(n, m, edgeInput, r)) {
		// System.out.println("Opps! " + Arrays.deepToString(edgeInput));
		// System.out.println("D: " + Arrays.toString(r));
		// }
		// }
		// }
		// }

	}

	private static int[][] generateGraph(int n, int m) {
		Random r = new Random();
		int[][] res = new int[m][2];
		Set<State> s = new HashSet<State>();
		res[0][0] = 1;
		res[0][1] = 2;
		int i = 1;
		int v = 3;
		while (v <= n) {
			int from = v;
			int to = r.nextInt(v - 1) + 1;
			State st = new State(Math.min(from, to), Math.max(from, to), 0);
			// System.out.println(n + " " + m + " " + st);
			s.add(st);
			res[i][0] = from;
			res[i][1] = to;
			v++;
			i++;
		}
		while (i < m) {
			int from = r.nextInt(n - 1) + 1;
			int to = r.nextInt(n - from) + from + 1;
			State st = new State(from, to, 0);
			if (!s.contains(st)) {
				s.add(st);
				res[i][0] = from;
				res[i][1] = to;
				i++;
			}
		}
		return res;
	}

	private static boolean verify(int n, int m, int[][] edgeInput, int[] direction) {
		if (m % 2 == 1) {
			return direction[0] == -1;
		}
		Map<Integer, Integer> indgree = new HashMap<>();
		for (int i = 0; i < direction.length; i++) {
			int to = edgeInput[i][1];
			if (direction[i] == 1) {
				to = edgeInput[i][0];
			}
			int ind = 0;
			if (indgree.containsKey(to)) {
				ind = indgree.get(to);
			}
			indgree.put(to, ind + 1);
		}
		for (int i : indgree.values()) {
			if (i % 2 == 1) {
				return false;
			}
		}
		return true;
	}

	private static int[] solve(int n, int m, int[][] edgeInput) {
		if (m % 2 == 1) {
			return new int[] { -1 };
		}
		int[] res = new int[m];
		Arrays.fill(res, -1);
		Map<Integer, List<Edge>> edges = build(edgeInput);
		Map<Integer, Integer> indgree = new HashMap<>();
		Map<Integer, Integer> ndegree = new HashMap<>();
		PriorityQueue<State> q = new PriorityQueue<>(new Comparator<State>() {
			@Override
			public int compare(State o1, State o2) {
				int r = Integer.compare(o1.d, o2.d);
				if (r == 0) {
					r = Integer.compare(o1.nd, o2.nd);
				}
				return r;
			}
		});
		for (int k : edges.keySet()) {
			int nd = 0;
			for (Edge t : edges.get(k)) {
				nd += edges.get(t.to).size();
			}
			q.offer(new State(k, edges.get(k).size(), nd));
			indgree.put(k, 0);
			ndegree.put(k, nd);
		}
		while (!q.isEmpty()) {
			State s = q.poll();
			int ind = indgree.get(s.v);
			for (Edge e : edges.get(s.v)) {
				if (ind % 2 == 0) {
					indgree.put(e.to, indgree.get(e.to) + 1);
					res[e.id] = directing(edgeInput[e.id], s.v);
				} else {
					res[e.id] = directing(edgeInput[e.id], e.to);
					ind++;
				}
				q.remove(new State(e.to, edges.get(e.to).size(), 0));
				edges.get(e.to).remove(e.reverse);
				q.offer(new State(e.to, edges.get(e.to).size(), ndegree.get(e.to)));
			}
			indgree.remove(s.v);
			edges.remove(s.v);
		}
		return res;
	}

	private static int directing(int[] e, int from) {
		if (e[0] == from) {
			return 0;
		} else {
			return 1;
		}
	}

	private static Map<Integer, List<Edge>> build(int[][] edgeInput) {
		Map<Integer, List<Edge>> edges = new HashMap<>();
		for (int i = 0; i < edgeInput.length; i++) {
			int from = edgeInput[i][0];
			int to = edgeInput[i][1];
			Edge e1 = new Edge(to, i);
			Edge e2 = new Edge(from, i);
			e1.reverse = e2;
			e2.reverse = e1;
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<>());
			}
			edges.get(from).add(e1);
			if (!edges.containsKey(to)) {
				edges.put(to, new ArrayList<>());
			}
			edges.get(to).add(e2);
		}

		return edges;
	}

	static String getStr(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static class Edge {
		int to;
		int id;
		Edge reverse;

		public Edge(int to, int id) {
			this.to = to;
			this.id = id;
		}

		@Override
		public String toString() {

			return to + ", " + id;
		}
	}

	static class State {
		int v;
		int d;
		int nd;

		public State(int v, int d, int nd) {
			this.v = v;
			this.d = d;
			this.nd = nd;
		}

		@Override
		public boolean equals(Object obj) {
			State that = (State) obj;
			return that.v == this.v && that.d == this.d;
		}

		@Override
		public int hashCode() {
			return v * 37 + d;
		}

		@Override
		public String toString() {
			return v + "|" + d + "|" + nd;
		}
	}
}
