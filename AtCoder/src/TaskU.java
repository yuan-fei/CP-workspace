
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskU {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[][] a = getIntArr(in, n, n);
		long r = solve(n, a);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, int[][] a) {
		long[] subSetSum = new long[1 << n];
		long[] dp = new long[1 << n];
		for (int mask = 0; mask < 1 << n; mask++) {
			int subMask = mask & (mask - 1);
			int newComer = -1;
			if (subMask != 0) {
				subSetSum[mask] += subSetSum[subMask];
				for (int i = 0; i < n; i++) {
					if ((mask >> i & 1) > 0) {
						if (newComer == -1) {
							newComer = i;
						} else {
							subSetSum[mask] += a[newComer][i];
						}
					}
				}
			}
		}
		for (int mask = 0; mask < 1 << n; mask++) {
			dp[mask] = subSetSum[mask];
			int x = mask;
			while (x != 0) {
				dp[mask] = Math.max(dp[mask], dp[x] + dp[mask & (~x)]);
				x = mask & (x - 1);
			}
		}
		return dp[(1 << n) - 1];
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
