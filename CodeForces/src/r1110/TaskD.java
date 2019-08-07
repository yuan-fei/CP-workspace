package r1110;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskD {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int[] a = getIntArr(in, n);
		int r = solve(n, m, a);
		System.out.println(r);
		in.close();
	}

	private static int solve(int n, int m, int[] a) {
		int[] cnt = new int[m];
		for (int i = 0; i < a.length; i++) {
			cnt[a[i] - 1]++;
		}
		int[][] dp = new int[3][3];
		for (int c : cnt) {
			int[][] new_dp = new int[3][3];
			for (int cntAsLast = 0; cntAsLast < 3; cntAsLast++) {
				for (int cntAsMiddle = 0; cntAsMiddle < 3; cntAsMiddle++) {
					for (int cntAsFirst = 0; cntAsFirst < 3; cntAsFirst++) {
						if (cntAsLast + cntAsMiddle + cntAsFirst <= c) {
							new_dp[cntAsMiddle][cntAsFirst] = Math.max(new_dp[cntAsMiddle][cntAsFirst], dp[cntAsLast][cntAsMiddle] + cntAsFirst + (c - cntAsLast - cntAsMiddle - cntAsFirst) / 3);
						}
					}
				}
			}
			int[][] tmp = new_dp;
			new_dp = dp;
			dp = tmp;
		}
		return dp[0][0];
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
