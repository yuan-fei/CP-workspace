package r2019H;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 1. cnt[i]>20 then chop to 20 or 21
 * 
 * 2. DP on each (+/-) combination of a number
 * 
 * https://codeforces.com/blog/entry/71373
 * 
 */
public class Elevanagram {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int[] cnt = getIntArr(in, 9);
			boolean ans = solve(cnt);
			System.out.println("Case #" + i + ": " + (ans ? "YES" : "NO"));
		}
		in.close();
	}

	private static boolean solve(int[] cnt) {
		int sum = 0;
		for (int i = 0; i < cnt.length; i++) {
			if (cnt[i] > 20) {
				cnt[i] = cnt[i] % 2 + 20;
			}
			sum += cnt[i];
		}
		boolean[][] dp = new boolean[505][11];
		dp[0][0] = true;
		for (int i = 0; i < 9; i++) {
			boolean[][] newDp = new boolean[505][11];
			// for (int j = 0; j < dp.length; j++) {
			// newDp[j] = Arrays.copyOf(dp[j], dp[j].length);
			// }
			for (int curTaken = 0; curTaken <= cnt[i]; curTaken++) {
				for (int prevTaken = 0; prevTaken + curTaken <= sum; prevTaken++) {
					for (int mod = 0; mod < 11; mod++) {
						int diff = (curTaken + 10 * (cnt[i] - curTaken)) * (i + 1);
						newDp[prevTaken + curTaken][(mod + diff) % 11] |= dp[prevTaken][mod];
					}
				}
			}
			dp = newDp;
		}

		return dp[(sum + 1) / 2][0];
	}

	static void dfs(boolean[] visited, List<int[]>[] adj, int[] colorCount, int u, int color) {
		visited[u] = true;
		colorCount[color]++;
		for (int[] v : adj[u]) {
			if (!visited[v[0]]) {
				dfs(visited, adj, colorCount, v[0], (color + v[1]) % 2);
			}
		}
	}

	private static long mod = 1000000007;

	private static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	private static long mul(long a, long b) {
		return (a * b) % mod;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	private static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	private static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	private static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	private static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	private static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	private static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	private static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
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
