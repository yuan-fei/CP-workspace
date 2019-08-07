package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GroupProjects {
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
		Arrays.sort(a);
		long[][] prev = new long[n + 1][k + 1];
		prev[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			long[][] cur = new long[n + 1][k + 1];
			int d = 0;
			if (i > 1) {
				d = a[i - 1] - a[i - 2];
			}
			for (int j = 0; j <= i; j++) {
				int dj = d * j;
				for (int l = 0; l <= k - dj; l++) {
					// i as single group
					cur[j][l + dj] = add(cur[j][l + dj], prev[j][l]);
					long dlj = mul(prev[j][l], j);
					// put i in one of the open groups
					cur[j][l + dj] = add(cur[j][l + dj], dlj);
					// add i to new group
					if (j < n) {
						cur[j + 1][l + dj] = add(cur[j + 1][l + dj], prev[j][l]);
					}
					// close a group
					if (j > 0) {
						cur[j - 1][l + dj] = add(cur[j - 1][l + dj], dlj);
					}
				}
			}
			prev = cur;
		}
		long r = 0;
		for (int i = 0; i <= k; i++) {
			r = add(r, prev[0][i]);
		}
		return r;
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
