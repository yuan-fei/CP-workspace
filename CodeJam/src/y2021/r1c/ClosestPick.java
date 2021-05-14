package y2021.r1c;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ClosestPick {
	public static void main(String[] args) {
		// test();
		solve();
	}

	private static void solve() {
		// isRoaring(122);
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int N = in.nextInt();
			int K = in.nextInt();
			int[] P = getIntArr(in, N);
			double r = solve(N, K, P);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static double solve(int n, int k, int[] p) {
		Arrays.sort(p);
		PriorityQueue<Integer> q = new PriorityQueue<>();
		int max2 = 0;
		int fi = p[0] - 1;
		if (fi > 0) {
			q.offer(fi);
			if (q.size() > 2) {
				q.poll();
			}
		}
		for (int i = 1; i < n; i++) {
			int t = countWin(p[i - 1], p[i]);
			if (t > 0) {
				q.offer(t);
				if (q.size() > 2) {
					q.poll();
				}
				max2 = Math.max(max2, p[i] - p[i - 1] - 1);
			}
		}
		int last = k - p[n - 1];
		if (last > 0) {
			q.offer(last);
			if (q.size() > 2) {
				q.poll();
			}
		}
		int totalWin = 0;
		while (!q.isEmpty()) {
			totalWin += q.poll();
		}
		totalWin = Math.max(max2, totalWin);
		return 1.0d * totalWin / k;
	}

	private static int countWin(int left, int right) {
		if (left >= right) {
			return 0;
		} else {
			return (right - left) / 2;
		}
	}

	private static void test() {
		for (int i = 0; i < 1; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		for (int i = 2; i < 100; i++) {
			int max = 0;
			for (int j = 1; j <= i; j++) {
				// max = Math.max(max, (j - 1 - 1) / 2 + (i - j - 1) / 2 + 1);
				int cnt = 0;
				for (int k = 1; k <= i; k++) {
					if (Math.abs(j - k) < Math.abs(1 - k) && Math.abs(j - k) < Math.abs(i - k)) {
						cnt++;
					}
				}
				max = Math.max(max, cnt);
			}
			System.out.println("1, " + i + "=" + max);
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

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
		}
		return sb.toString();
	}

	static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
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

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static char[][] getCharArr(Scanner in, int row, int col) {
		char[][] arr = new char[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getCharArr(in, col);
		}
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
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
