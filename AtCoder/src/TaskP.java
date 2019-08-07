
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskP {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		Map<Integer, List<Integer>> edges = getEdges(in, n - 1, false);
		long r = solve(n, edges);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, Map<Integer, List<Integer>> edges) {
		long[] dp = dfs(edges, 0, 1);
		return add(dp[0], dp[1]);
	}

	private static long[] dfs(Map<Integer, List<Integer>> edges, int par, int cur) {
		List<Integer> children = edges.get(cur);
		if (children == null) {
			return new long[] { 1, 1 };
		} else {
			long[] r = new long[] { 1, 1 };
			for (int i : children) {
				if (i != par) {
					long[] rChild = dfs(edges, cur, i);
					r[0] = mul(r[0], add(rChild[0], rChild[1]));
					r[1] = mul(r[1], rChild[0]);
				}
			}
			return r;
		}
	}

	private static long solve1(int n, Map<Integer, List<Integer>> edges) {
		long[][] dp = new long[n + 1][2];
		dfs1(edges, dp, 0, 1);
		return add(dp[1][0], dp[1][1]);
	}

	private static void dfs1(Map<Integer, List<Integer>> edges, long[][] dp, int par, int cur) {
		List<Integer> children = edges.get(cur);
		if (children == null) {
			dp[cur][0] = 1;
			dp[cur][1] = 1;
		} else {
			for (int i : children) {
				if (i != par) {
					dfs1(edges, dp, cur, i);
				}
			}
			dp[cur][0] = 1;
			dp[cur][1] = 1;
			for (int i : children) {
				if (i != par) {
					dp[cur][0] = mul(dp[cur][0], add(dp[i][0], dp[i][1]));
					dp[cur][1] = mul(dp[cur][1], dp[i][0]);
				}
			}
		}
	}

	static long mod = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return (a * b) % mod;
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
