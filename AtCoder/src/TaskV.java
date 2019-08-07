
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskV {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		Map<Integer, List<Integer>> edges = getEdges(in, n - 1, false);
		long[] r = solve(n, m, edges);
		System.out.println(str(r));
		in.close();
	}

	private static long[] solve(int n, int m, Map<Integer, List<Integer>> edges) {
		long[] up = new long[n + 1];
		long[] sub = new long[n + 1];
		mod = m;
		dfs1(edges, sub, 0, 1);
		dfs3(edges, sub, up, new long[0], new long[0], 0, 1, 0);
		long[] r = new long[n];
		for (int i = 0; i < r.length; i++) {
			r[i] = mul(up[i + 1], sub[i + 1]);
		}
		return r;
	}

	private static long[] solve1(int n, int m, Map<Integer, List<Integer>> edges) {
		long[] dp = new long[n + 1];
		long[] sub = new long[n + 1];
		mod = m;
		dfs1(edges, sub, 0, 1);
		dfs2(edges, sub, dp, 0, 1);
		return Arrays.copyOfRange(dp, 1, dp.length);
	}

	static void dfs1(Map<Integer, List<Integer>> edges, long[] sub, int par, int cur) {
		sub[cur] = 1;
		List<Integer> adjV = edges.get(cur);
		if (adjV != null) {
			for (int v : adjV) {
				if (v != par) {
					dfs1(edges, sub, cur, v);
					sub[cur] = mul(sub[cur], (sub[v] + 1));
				}
			}
		}
	}

	static void dfs2(Map<Integer, List<Integer>> edges, long[] sub, long[] dp, int par, int cur) {
		dp[cur] = sub[cur] * (dp[par] / (sub[cur] + 1) + 1);
		List<Integer> adjV = edges.get(cur);
		if (adjV != null) {
			for (int v : adjV) {
				if (v != par) {
					dfs2(edges, sub, dp, cur, v);
				}
			}
		}
	}

	static void dfs3(Map<Integer, List<Integer>> edges, long[] sub, long[] up, long[] prefixProd, long[] suffixProd,
			int par, int cur, int i) {
		up[cur] = 1;
		if (i > 0) {
			up[cur] = mul(up[cur], prefixProd[i - 1]);
		}
		if (i < prefixProd.length - 1) {
			up[cur] = mul(up[cur], suffixProd[i + 1]);
		}
		up[cur] = add(mul(up[cur], up[par]), 1);
		List<Integer> adjV = edges.get(cur);
		if (adjV != null) {
			adjV.remove((Integer) par);
			prefixProd = new long[adjV.size()];
			suffixProd = new long[adjV.size()];
			for (int j = 0; j < adjV.size(); j++) {
				if (j == 0) {
					prefixProd[j] = add(sub[adjV.get(j)], 1);
					suffixProd[adjV.size() - j - 1] = add(sub[adjV.get(adjV.size() - j - 1)], 1);
				} else {
					prefixProd[j] = mul(add(sub[adjV.get(j)], 1), prefixProd[j - 1]);
					suffixProd[adjV.size() - j - 1] = mul(add(sub[adjV.get(adjV.size() - j - 1)], 1),
							suffixProd[adjV.size() - j]);
				}
			}
			for (int j = 0; j < adjV.size(); j++) {
				dfs3(edges, sub, up, prefixProd, suffixProd, cur, adjV.get(j), j);
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

	static String str(long[] a) {
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
