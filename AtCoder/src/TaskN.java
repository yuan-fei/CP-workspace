
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskN {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		long[] a = getLongArr(in, n);
		long r = solve(a, n);
		System.out.println(r);
		in.close();
	}

	private static long solve(long[] a, int n) {
		long[][] dp = new long[n][n];
		long[] prefixSum = new long[n];
		prefixSum[0] = a[0];
		for (int i = 1; i < a.length; i++) {
			prefixSum[i] = prefixSum[i - 1] + a[i];
		}
		for (int i = 2; i <= n; i++) {
			for (int j = 0; j <= n - i; j++) {
				dp[j][j + i - 1] = Long.MAX_VALUE;
				for (int k = j; k < j + i - 1; k++) {
					dp[j][j + i - 1] = Math.min(dp[j][j + i - 1], dp[j][k] + dp[k + 1][j + i - 1]);
				}
				dp[j][j + i - 1] += prefixSum[j + i - 1];
				if (j > 0) {
					dp[j][j + i - 1] -= prefixSum[j - 1];
				}
			}
		}
		return dp[0][n - 1];
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
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
