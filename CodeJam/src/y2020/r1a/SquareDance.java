package y2020.r1a;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class SquareDance {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int R = in.nextInt();
			int C = in.nextInt();
			int[][] a = getIntArr(in, R, C);
			long r = solve(R, C, a);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	static class Node {
		Node left, right, up, down;
		int[] pos;

		public Node(int[] pos) {
			super();
			this.pos = pos;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(pos);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!Arrays.equals(pos, other.pos))
				return false;
			return true;
		}
	}

	/**
	 * This won't work for [1, 2,...,100000], it will timeout because the object
	 * field access is slow!
	 */
	private static long solve1(int r, int c, int[][] a) {
		int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		long curSum = 0;
		long sum = 0;
		Node[][] nodes = new Node[r][c];
		Set<Node> candidate = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				curSum += a[i][j];
				nodes[i][j] = new Node(new int[] { i, j });
				if (i > 0) {
					nodes[i][j].up = nodes[i - 1][j];
					nodes[i - 1][j].down = nodes[i][j];
				}

				if (j > 0) {
					nodes[i][j].left = nodes[i][j - 1];
					nodes[i][j - 1].right = nodes[i][j];
				}
				candidate.add(nodes[i][j]);
			}
		}
		Set<Node> removed = new HashSet<>();
		long t1 = 0;
		long t2 = 0;
		long t3 = 0;
		for (int i = 0; i <= r * c; i++) {
			sum += curSum;
			// System.out.println(candidate.size());
			// System.out.println(removed.size());
			removed.clear();
			long x1 = System.currentTimeMillis();
			for (Node d : candidate) {
				long x4 = System.currentTimeMillis();
				long localSum = 0;
				int cnt = 0;
				for (Node n : new Node[] { d.left, d.right, d.up, d.down }) {
					if (n != null) {
						localSum += a[n.pos[0]][n.pos[1]];
						cnt++;
					}
				}
				if (cnt > 0 && a[d.pos[0]][d.pos[1]] * cnt < localSum) {
					removed.add(d);
				}
				t3 += System.currentTimeMillis() - x4;
			}
			long x2 = System.currentTimeMillis();
			t1 += x2 - x1;
			if (removed.isEmpty()) {
				// System.out.println(t3 + ", " + t1 + ", " + t2);
				return sum;
			}

			// candidate.clear();
			candidate = new HashSet<>();
			for (Node d : removed) {
				curSum -= a[d.pos[0]][d.pos[1]];
				for (Node n : new Node[] { d.left, d.right, d.up, d.down }) {
					if (n != null && !removed.contains(n)) {
						candidate.add(n);
					}
				}
				if (d.left != null) {
					d.left.right = d.right;
				}
				if (d.right != null) {
					d.right.left = d.left;
				}
				if (d.up != null) {
					d.up.down = d.down;
				}
				if (d.down != null) {
					d.down.up = d.up;
				}
				d.left = null;
				d.right = null;
				d.up = null;
				d.down = null;
			}
			long x3 = System.currentTimeMillis();
			t2 += x3 - x2;
		}
		return -1;
	}

	private static long solve(int r, int c, int[][] a) {
		int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		long curSum = 0;
		long sum = 0;
		int[][][] up = new int[r][c][2];
		int[][][] down = new int[r][c][2];
		int[][][] left = new int[r][c][2];
		int[][][] right = new int[r][c][2];
		boolean[][] dying = new boolean[r][c];
		List<int[]> candidate = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				curSum += a[i][j];
				up[i][j] = new int[] { -1, -1 };
				down[i][j] = new int[] { -1, -1 };
				left[i][j] = new int[] { -1, -1 };
				right[i][j] = new int[] { -1, -1 };
				if (i > 0) {
					up[i][j] = new int[] { i - 1, j };
					down[i - 1][j] = new int[] { i, j };
				}

				if (j > 0) {
					left[i][j] = new int[] { i, j - 1 };
					right[i][j - 1] = new int[] { i, j };
				}
				candidate.add(new int[] { i, j });
			}
		}
		List<int[]> removed = new ArrayList<>();
		for (int i = 0; i <= r * c; i++) {
			sum += curSum;
			// System.out.println(candidate.size());
			// System.out.println(removed.size());
			removed.clear();
			for (int[] d : candidate) {
				long localSum = 0;
				int cnt = 0;
				for (int[] n : new int[][] { left[d[0]][d[1]], right[d[0]][d[1]], up[d[0]][d[1]], down[d[0]][d[1]] }) {
					if (n[0] != -1) {
						localSum += a[n[0]][n[1]];
						cnt++;
					}
				}
				if (cnt > 0 && dying[d[0]][d[1]] == false && a[d[0]][d[1]] * cnt < localSum) {
					removed.add(d);
					dying[d[0]][d[1]] = true;
				}
			}
			if (removed.isEmpty()) {
				return sum;
			}
			candidate.clear();
			for (int[] d : removed) {
				curSum -= a[d[0]][d[1]];
				for (int[] n : new int[][] { left[d[0]][d[1]], right[d[0]][d[1]], up[d[0]][d[1]], down[d[0]][d[1]] }) {
					if (n[0] != -1 && !dying[n[0]][n[1]]) {
						candidate.add(n);
					}
				}
				if (left[d[0]][d[1]][0] != -1) {
					right[left[d[0]][d[1]][0]][left[d[0]][d[1]][1]] = right[d[0]][d[1]];
				}
				if (right[d[0]][d[1]][0] != -1) {
					left[right[d[0]][d[1]][0]][right[d[0]][d[1]][1]] = left[d[0]][d[1]];
				}
				if (up[d[0]][d[1]][0] != -1) {
					down[up[d[0]][d[1]][0]][up[d[0]][d[1]][1]] = down[d[0]][d[1]];
				}
				if (down[d[0]][d[1]][0] != -1) {
					up[down[d[0]][d[1]][0]][down[d[0]][d[1]][1]] = up[d[0]][d[1]];
				}
				left[d[0]][d[1]] = new int[] { -1, -1 };
				right[d[0]][d[1]] = new int[] { -1, -1 };
				up[d[0]][d[1]] = new int[] { -1, -1 };
				down[d[0]][d[1]] = new int[] { -1, -1 };
			}
		}
		return -1;
	}

	static boolean compare(int[][] a, int[][] b) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	static long sum(int[][] a) {
		long sum = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				sum += a[i][j];
			}
		}
		return sum;
	}

	private static void test() {
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			testOnce(1, 5);
		}
	}

	private static void testOnce(int r, int c) {
		Random rnd = new Random();
		int[][] a = new int[r][c];

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = rnd.nextInt(10) + 1;
			}
		}
		// a = new int[][] { { 9, 4, 3, 2, 9 } };
		long r1 = solve(a.length, a[0].length, a);
		long r2 = solve1(a.length, a[0].length, a);
		if (r1 != r2) {
			System.out.println(r1 + ", " + r2);
			System.out.println(Arrays.deepToString(a));
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

	static class FastScanner implements Closeable {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		@Override
		public void close() throws IOException {
			br.close();
		}
	}

}
