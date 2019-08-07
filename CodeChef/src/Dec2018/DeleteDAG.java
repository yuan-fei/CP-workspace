package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class DeleteDAG {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		Map<Integer, List<Integer>> edges = new HashMap<>();
		for (int i = 0; i < m; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			if (!edges.containsKey(to)) {
				edges.put(to, new ArrayList<Integer>());
			}
			edges.get(to).add(from);
		}
		List<int[]> r = solve(n, m, edges);
		System.out.println(r.size());
		for (int[] ans : r) {
			System.out.println(ans.length + " " + getStr(ans));
		}
		in.close();
	}

	static String getStr(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	private static List<int[]> solve(int n, int m, Map<Integer, List<Integer>> edges) {
		Queue<Integer> q = new LinkedList<Integer>();
		Map<Integer, Integer> indegree = new HashMap<>();
		List<int[]> res = new ArrayList<>();
		for (int to : edges.keySet()) {
			for (int from : edges.get(to)) {
				int d = 0;
				if (indegree.containsKey(from)) {
					d = indegree.get(from);
				}
				indegree.put(from, d + 1);
			}
		}
		for (int i = 1; i <= n; i++) {
			if (!indegree.containsKey(i)) {
				q.offer(i);
			}
		}
		while (!q.isEmpty()) {
			int s = q.size();
			int to = q.poll();
			if (edges.containsKey(to)) {
				for (int from : edges.get(to)) {
					int d = indegree.get(from) - 1;
					indegree.put(from, d);
					if (d == 0) {
						q.offer(from);
					}
				}
			}
			if (s >= 2) {
				int to1 = q.poll();
				if (edges.containsKey(to1)) {
					for (int from : edges.get(to1)) {
						int d = indegree.get(from) - 1;
						indegree.put(from, d);
						if (d == 0) {
							q.offer(from);
						}
					}
				}
				res.add(new int[] { to, to1 });
			} else {
				res.add(new int[] { to });
			}
		}
		return res;
	}
}
