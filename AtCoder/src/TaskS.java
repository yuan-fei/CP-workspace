
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskS {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		String k = in.next();
		int d = in.nextInt();
		long r = solve(k, d);
		System.out.println(r);
		in.close();
	}

	static long[][][] dpCache;

	private static long solve(String s, int d) {
		int len = s.length();
		dpCache = new long[len + 1][2][d];
		for (int i = 0; i < dpCache.length; i++) {
			for (int j = 0; j < dpCache[i].length; j++) {
				Arrays.fill(dpCache[i][j], -1);
			}
		}
		long r = solve(s, d, 0, false, 0);
		return add(r, -1);
	}

	private static long solve(String s, int d, int pos, boolean isLess, int residual) {
		if (pos == s.length()) {
			return residual != 0 ? 0 : 1;
		}
		if (dpCache[pos][isLess ? 1 : 0][residual] >= 0) {
			return dpCache[pos][isLess ? 1 : 0][residual];
		}
		int v = Integer.parseInt(s.charAt(pos) + "");
		int x = isLess ? 9 : v;
		long r = 0;
		for (int k = 0; k <= x; k++) {
			r = add(r, solve(s, d, pos + 1, isLess || (k != x), (d + residual - k) % d));
		}
		return dpCache[pos][isLess ? 1 : 0][residual] = r;
	}

	private static long solveIterative(String s, int d) {
		int len = s.length();
		long[][][] dp = new long[len + 1][2][d];
		dp[0][0][0] = 1;
		for (int i = 0; i < len; i++) {
			int v = Integer.parseInt(s.charAt(i) + "");
			for (int j = 0; j < 2; j++) {
				int x = (j == 1) ? 9 : v;
				for (int k = 0; k <= x; k++) {
					for (int l = 0; l < d; l++) {
						int m = (k == x) ? 0 : 1;
						dp[i + 1][j | m][(k + l) % d] = add(dp[i + 1][j | m][(k + l) % d], dp[i][j][l]);
					}
				}
			}
		}
		return add(add(dp[len][0][0], dp[len][1][0]), -1);
	}

	private static long solve1(String k, int d) {
		int l = k.length();
		long[][] dp = new long[l + 1][d];
		dp[0][0] = 1;
		for (int i = 1; i <= l; i++) {
			for (int j = 0; j < d; j++) {
				for (int m = 0; m < 10; m++) {
					dp[i][j] = add(dp[i][j], dp[i - 1][(j + d - m % d) % d]);
				}
			}
		}
		long r = 0;
		int offset = 0;
		for (int i = 0; i < l; i++) {
			int v = Integer.parseInt(k.charAt(i) + "");
			for (int j = 0; j < v; j++) {
				r = add(r, dp[l - i - 1][(d - (j + offset) % d) % d]);
			}
			offset = (offset + v) % d;
		}
		if (offset == 0) {
			return r;
		} else {
			return add(r, -1);
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
