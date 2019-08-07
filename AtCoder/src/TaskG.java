
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskG {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = 1;
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			Map<Integer, List<Integer>> edge = getEdges(in, m, true);
			int r = solve(n, m, edge);
			System.out.println(r);
		}
		in.close();
	}

	private static int solve(int n, int m, Map<Integer, List<Integer>> edge) {
		int[] maxD = new int[n + 1];
		Arrays.fill(maxD, -1);
		for (int i = 1; i <= n; i++) {
			if (maxD[i] == -1) {
				dfs(i, edge, maxD);
			}
		}

		int max = 0;
		for (int i = 0; i < maxD.length; i++) {
			max = Math.max(max, maxD[i]);
		}
		return max;
	}

	static void dfs(int u, Map<Integer, List<Integer>> edge, int[] maxD) {
		maxD[u] = 0;
		List<Integer> l = edge.get(u);
		if (l != null) {
			for (int v : l) {
				if (maxD[v] < 0) {
					dfs(v, edge, maxD);
				}
				maxD[u] = Math.max(maxD[u], maxD[v] + 1);
			}
		}
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Integer>> edges = new HashMap<>();
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<Integer>());
			}
			edges.get(from).add(to);
			if (!directed) {
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Integer>());
				}
				edges.get(to).add(from);
			}

		}
		return edges;
	}
}
