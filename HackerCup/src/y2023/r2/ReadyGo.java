package y2023.r2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReadyGo {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int m = in.nextInt();
				char[][] board = getCharArr(in, n, m);
				int r = solve(n, m, board);
				out.println("Case #" + i + ": " + ((r > 0) ? "YES" : "NO"));
//				out.println("Case #" + i + ": " + r);
			}

		}

	}

	static char[][] board;
	static int n;
	static int m;

	private static int solve(int n, int m, char[][] board) {
		ReadyGo.board = board;
		ReadyGo.n = n;
		ReadyGo.m = m;
		int res = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (board[i][j] == '.') {
					board[i][j] = 'B';
					int r = bfs();
					res = Math.max(r, res);
					board[i][j] = '.';
				}
			}
		}
		return res;
	}

	static int bfs() {
		boolean[][] seen = new boolean[n][m];
		int ret = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (!seen[i][j] && board[i][j] == 'W') {
					int t = dfs(new int[] { i, j }, seen);
					ret += t;
				}
			}
		}
		return ret;
	}

	private static int dfs(int[] cur, boolean[][] seen) {
		int ret = 0;
		seen[cur[0]][cur[1]] = true;
		boolean invalid = false;
		for (int[] d : dirs) {
			int[] nxt = new int[] { cur[0] + d[0], cur[1] + d[1] };
			if (0 <= nxt[0] && nxt[0] < n && 0 <= nxt[1] && nxt[1] < m && !seen[nxt[0]][nxt[1]]) {
				switch (board[nxt[0]][nxt[1]]) {
				case '.':
					invalid = true;
					break;
				case 'B':
					break;
				case 'W':
					int r = dfs(nxt, seen);
					if (r == 0) {
						invalid = true;
					}
					ret += r;
					break;
				}

			}
		}
		return invalid ? 0 : ret + 1;
	}

	static int[][] dirs = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };
	static long MOD = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += MOD;
		}
		return r % MOD;
	}

	static long mul(long a, long b) {
		long r = a * b;
		while (r < 0) {
			r += MOD;
		}
		return r % MOD;
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
			arr[i] = in.next().toCharArray();
		}

		return arr;
	}

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
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

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
