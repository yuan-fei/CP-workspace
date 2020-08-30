package r2020D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BeautyOfTree {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int a = in.nextInt();
			int b = in.nextInt();
			int[] p = new int[n];
			p[0] = -1;
			for (int j = 1; j < n; j++) {
				p[j] = in.nextInt() - 1;
			}
			double ans = solve(n, a, b, p);
			System.out.println("Case #" + i + ": " + ans);
		}
		in.close();

	}

	private static int[][] ancestors;
	static int MAXLOG = 20;

	private static double solve(int n, int a, int b, int[] parent) {
		buildSparseTable(n, parent);
		int[] probA = getProb(n, a);
		int[] probB = getProb(n, b);
		double exp = 0;
		for (int i = 0; i < n; i++) {
			exp += 1 - (1 - 1.0d * probA[i] / n) * (1 - 1.0d * probB[i] / n);
		}
		return exp;
	}

	private static int[] getProb(int n, int k) {
		int[] prob = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			prob[i] += 1;
			int kth = getKthAncestor(i, k);
			if (kth >= 0) {
				prob[kth] += prob[i];
			}
		}
		return prob;
	}

	private static void buildSparseTable(int n, int[] parent) {
		ancestors = new int[n][MAXLOG];
		for (int j = 0; j < n; j++) {
			ancestors[j][0] = parent[j];
		}
		for (int i = 1; i < MAXLOG; i++) {
			for (int j = 0; j < n; j++) {
				int k = ancestors[j][i - 1];
				if (k >= 0) {
					ancestors[j][i] = ancestors[k][i - 1];
				} else {
					ancestors[j][i] = -1;
				}
			}
		}
	}

	private static int getKthAncestor(int node, int k) {
		return getKthAncestor(node, k, 0);
	}

	private static int getKthAncestor(int node, int k, int b) {
		if (b == 31 || node < 0) {
			return node;
		}
		if (((k >> b) & 1) != 0) {
			return getKthAncestor(ancestors[node][b], k, b + 1);
		} else {
			return getKthAncestor(node, k, b + 1);
		}
	}

	private static int signum(int n) {
		if (n == 0) {
			return 0;
		}
		if (n > 0) {
			return 1;
		} else {
			return -1;
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

	private static char[][] getStringArr(Scanner in, int r, int c) {
		char[][] ret = new char[r][];
		for (int i = 0; i < r; i++) {
			ret[i] = in.next().toCharArray();
		}
		return ret;
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
