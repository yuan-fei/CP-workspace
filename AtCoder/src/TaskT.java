
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskT {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		String s = in.next();
		long r = solve(n, s);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, String s) {
		long[][] dp = new long[n][n + 1];
		long[][] prefixSum = new long[n][n + 1];
		dp[0][1] = 1;
		prefixSum[0][1] = 1;
		// for (int i = 1; i <= n; i++) {
		// dp[0][i] = 1;
		// prefixSum[0][i] = prefixSum[0][i - 1] + dp[0][i];
		// }
		for (int i = 1; i <= n - 1; i++) {
			for (int j = 1; j <= i + 1; j++) {
				if (s.charAt(i - 1) == '<') {
					dp[i][j] = add(dp[i][j], prefixSum[i - 1][j - 1]);
				} else {
					if (j <= i) {
						dp[i][j] = add(dp[i][j], add(prefixSum[i - 1][i], -prefixSum[i - 1][j - 1]));
					}
				}
				prefixSum[i][j] = add(prefixSum[i][j - 1], dp[i][j]);
			}
		}
		long r = 0;
		for (int i = 1; i <= n; i++) {
			r = add(r, dp[n - 1][i]);
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
