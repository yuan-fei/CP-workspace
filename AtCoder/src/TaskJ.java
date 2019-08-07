
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskJ {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[] a = getIntArr(in, n);
		double r = solve(n, a);
		System.out.println(r);
		in.close();
	}

	private static double solve(int m, int[] a) {
		int[] n = new int[3];
		for (int i = 0; i < a.length; i++) {
			n[a[i] - 1]++;
		}
		double[][][] dp = new double[m + 1][m + 1][m + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= m; k++) {
					int total = i + j + k;
					if (total == 0 || total > m) {
						continue;
					}
					if (i > 0) {
						dp[i][j][k] += 1.0 * dp[i - 1][j + 1][k] * i / total;
					}
					if (j > 0) {
						dp[i][j][k] += 1.0 * dp[i][j - 1][k + 1] * j / total;
					}
					if (k > 0) {
						dp[i][j][k] += 1.0 * dp[i][j][k - 1] * k / total;
					}
					dp[i][j][k] += 1.0 * m / total;
				}
			}
		}
		return dp[n[2]][n[1]][n[0]];
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
