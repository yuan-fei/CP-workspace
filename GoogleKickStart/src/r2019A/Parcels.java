package r2019A;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Parcels {
	public static void main(String[] args) {
		solve();
	}

	private static void test() {
		boolean[][] m = new boolean[250][250];
		m[0][0] = true;
		m[0][249] = true;
		m[249][0] = true;
		m[249][249] = true;
		System.out.println(solve(250, 250, m));
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int r = in.nextInt();
			int c = in.nextInt();
			String[] a = getStringArr(in, r);
			long res = solve(r, c, a);
			System.out.println("Case #" + i + ": " + res);
		}
		in.close();
	}

	private static long solve(int r, int c, String[] a) {
		boolean[][] m = new boolean[r][c];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length(); j++) {
				m[i][j] = (a[i].charAt(j) - '0' == 1);
			}
		}
		return solve(r, c, m);
	}

	private static long solve(int r, int c, boolean[][] m) {

		int[][] dist = calulateDist(r, c, m);
		int lb = 0;
		int ub = r + c;
		while (lb + 1 < ub) {
			int mid = (lb + ub) / 2;
			if (feasible(mid, dist)) {
				ub = mid;
			} else {
				lb = mid;
			}
		}
		if (feasible(lb, dist)) {
			return lb;
		} else {
			return ub;
		}
	}

	/**
	 * |x1-x2| + |y1-y2| <= maxD <=>
	 * 
	 * any 1 of 4 followings
	 * 
	 * 1. (x1+y1) - (x2+y2) <= maxD <=> (x1+y1) <= x2+y2+maxD: find min(x2+y2+maxD)
	 * 
	 * 2. -(x1+y1) + (x2+y2) <= maxD <=> (x1+y1) >= x2+y2-maxD: find max(x2+y2-maxD)
	 * 
	 * 3. (x1-y1) - (x2-y2) <= maxD <=> (x1-y1) <= x2-y2+maxD: find min(x2-y2+maxD)
	 * 
	 * 4. (x1-y1) + (x2-y2) <= maxD <=> (x1-y1) <= x2-y2-maxD: find max(x2-y2-maxD)
	 * 
	 * 
	 */
	private static boolean feasible(int maxD, int[][] dist) {
		int minSum = Integer.MAX_VALUE;
		int maxSum = Integer.MIN_VALUE;
		int minDiff = Integer.MAX_VALUE;
		int maxDiff = Integer.MIN_VALUE;
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[i].length; j++) {
				if (dist[i][j] > maxD) {
					minSum = Math.min(i + j + maxD, minSum);
					maxSum = Math.max(i + j - maxD, maxSum);
					minDiff = Math.min(i - j + maxD, minDiff);
					maxDiff = Math.max(i - j - maxD, maxDiff);
				}
			}
		}

		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[i].length; j++) {
				if (i + j <= minSum && i + j >= maxSum && i - j <= minDiff && i - j >= maxDiff) {
					return true;
				}
			}
		}
		return false;
	}

	static int[][] calulateDist(int r, int c, boolean[][] m) {
		int[][] dist = new int[r][c];
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[i].length; j++) {
				dist[i][j] = Integer.MAX_VALUE;
			}
		}
		int[][] direction = new int[][] { new int[] { 0, 1 }, new int[] { 0, -1 }, new int[] { 1, 0 },
				new int[] { -1, 0 } };
		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (m[i][j]) {
					q.offer(new int[] { i, j });
					dist[i][j] = 0;
				}
			}
		}
		int d = 0;
		while (!q.isEmpty()) {
			int n = q.size();
			for (int i = 0; i < n; i++) {
				int[] p = q.poll();
				int x = p[0];
				int y = p[1];
				for (int j = 0; j < direction.length; j++) {
					int dx = x + direction[j][0];
					int dy = y + direction[j][1];
					if (dx >= 0 && dx < r && dy >= 0 && dy < c && dist[dx][dy] == Integer.MAX_VALUE) {
						dist[dx][dy] = Math.min(dist[dx][dy], d + 1);
						q.offer(new int[] { dx, dy });
					}
				}
			}
			d++;
		}
		return dist;
	}

	private static long solve1(int r, int c, String[] a) {
		boolean[][] m = new boolean[r][c];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length(); j++) {
				m[i][j] = (a[i].charAt(j) - '0' == 1);
			}
		}
		int minMax = Integer.MAX_VALUE;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (!m[i][j]) {
					m[i][j] = true;
					minMax = Math.min(calAll(r, c, m), minMax);
					m[i][j] = false;
				}
			}
		}
		if (minMax == Integer.MAX_VALUE) {
			minMax = 0;
		}
		return minMax;
	}

	private static int calAll(int r, int c, boolean[][] m) {
		int maxD = 0;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (!m[i][j]) {
					maxD = Math.max(calOne(r, c, m, i, j), maxD);
				}
			}
		}
		return maxD;
	}

	private static int calOne(int r, int c, boolean[][] m, int x, int y) {
		int minD = Integer.MAX_VALUE;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (m[i][j]) {
					minD = Math.min(Math.abs(i - x) + Math.abs(j - y), minD);
				}
			}
		}
		return minD;
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
