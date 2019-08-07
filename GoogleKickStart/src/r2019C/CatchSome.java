package r2019C;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CatchSome {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int k = in.nextInt();
			int[] p = getIntArr(in, n);
			int[] a = getIntArr(in, n);
			int r = solve(n, k, p, a);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int n, int k, int[] p, int[] a) {
		int[][] d = new int[n][];

		for (int i = 0; i < n; i++) {
			d[i] = new int[] { p[i], a[i] };
		}
		Arrays.sort(d, Comparator.comparingInt(i -> i[0]));
		Map<Integer, List<Integer>> cd = new HashMap<>();
		for (int i = 0; i < d.length; i++) {
			if (!cd.containsKey(d[i][1])) {
				cd.put(d[i][1], new ArrayList<>());
			}
			List<Integer> t = cd.get(d[i][1]);
			t.add(d[i][0]);
		}
		// dp1: all round trips
		int[] dp1 = new int[k + 1];
		// dp2: all round trips except 1
		int[] dp2 = new int[k + 1];
		for (int i = 0; i < k + 1; i++) {
			dp1[i] = dp2[i] = (int) 2e9;
		}
		dp1[0] = 0;
		dp2[0] = 0;
		for (List<Integer> x : cd.values()) {
			for (int i = k; i >= 1; i--) {
				for (int j = 0; j < x.size(); j++) {
					if (i >= j + 1) {
						dp1[i] = Math.min(dp1[i], dp1[i - j - 1] + 2 * x.get(j));
						dp2[i] = Math.min(dp2[i], dp1[i - j - 1] + x.get(j));
						dp2[i] = Math.min(dp2[i], dp2[i - j - 1] + 2 * x.get(j));
					}
				}
			}
		}
		return dp2[k];
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

	static int gcd(int a, int b) {
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
