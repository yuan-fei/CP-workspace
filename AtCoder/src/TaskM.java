
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * https://atcoder.jp/contests/dp/tasks/dp_m
 * 
 * Problem Statement There are N children, numbered 1,2,…,N.
 * 
 * They have decided to share K candies among themselves. Here, for each i
 * (1≤i≤N), Child i must receive between 0 and ai candies (inclusive). Also, no
 * candies should be left over.
 * 
 * Find the number of ways for them to share candies, modulo 10^9+7. Here, two
 * ways are said to be different when there exists a child who receives a
 * different number of candies.
 * 
 * https://codeforces.com/blog/entry/64250
 */
public class TaskM {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int k = in.nextInt();
		int[] a = getIntArr(in, n);
		long r = solve(n, k, a);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, int k, int[] a) {
		long[][] dp = new long[n + 1][k + 1];
		long[][] prefixSum = new long[n + 1][k + 1];
		for (int i = 0; i <= n; i++) {
			dp[i][0] = 1;
			prefixSum[i][0] = 1;
		}
		for (int i = 0; i <= k; i++) {
			prefixSum[0][i] = 1;
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = prefixSum[i - 1][j];
				if (j >= a[i - 1] + 1) {
					dp[i][j] = (dp[i][j] - prefixSum[i - 1][j - a[i - 1] - 1] + mod) % mod;
				}
				prefixSum[i][j] = (prefixSum[i][j - 1] + dp[i][j]) % mod;
			}
		}
		return dp[n][k];
	}

	static long mod = 1000000007;

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
