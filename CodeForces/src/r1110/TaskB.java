package r1110;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TaskB {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int[] b = getIntArr(in, n);
		int r = solve(n, m, k, b);
		System.out.println(r);
		in.close();
	}

	private static int solve(int n, int m, int k, int[] b) {
		if (k >= n) {
			return n;
		}
		PriorityQueue<Integer> q = new PriorityQueue<>();
		for (int i = 1; i < b.length; i++) {
			q.offer(b[i] - b[i - 1] - 1);
		}
		int sum = 0;
		while (q.size() >= k) {
			sum += q.poll();
		}
		return sum + n;
	}

	private static int solve2(int n, int m, int k, int[] b) {
		if (k >= n) {
			return n;
		}
		int[] dp = new int[k + 1];
		for (int j = 0; j <= k; j++) {
			dp[j] = Integer.MAX_VALUE;
		}
		dp[0] = 1;
		dp[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int j = Math.min(i, k); j >= 1; j--) {
				if (dp[j] != Integer.MAX_VALUE) {
					dp[j] = dp[j] + b[i - 1] - b[i - 2];
				}
				if (j > 1 && dp[j - 1] != Integer.MAX_VALUE) {
					dp[j] = Math.min(dp[j - 1] + 1, dp[j]);
				}
			}
		}
		return dp[k];
	}

	private static int solve1(int n, int m, int k, int[] b) {
		if (k >= n) {
			return n;
		}
		int[][] dp = new int[n + 1][k + 1];
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {
				dp[i][j] = Integer.MAX_VALUE;
			}
		}
		for (int i = 0; i <= k; i++) {
			dp[1][i] = 1;
		}
		for (int i = 2; i <= n; i++) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = dp[i - 1][j] + b[i - 1] - b[i - 2];
				if (j > 1) {
					dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, dp[i][j]);
				}
			}
		}
		return dp[n][k];
	}

	int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
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
