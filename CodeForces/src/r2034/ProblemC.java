package r2034;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ProblemC {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int m = in.nextInt();
			in.nextLine();
			char[][] maze = getCharArr(in, n, m);
			String s = in.nextLine();
			int r = solve(n, m, maze);
			System.out.println(r);

		}

		in.close();
	}

	private static int solve(int n, int m, char[][] maze) {
		int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		Map<Character, int[]> directionToOffs = new HashMap<>();
		directionToOffs.put('U', directions[3]);
		directionToOffs.put('D', directions[2]);
		directionToOffs.put('L', directions[1]);
		directionToOffs.put('R', directions[0]);
		int[][] escapable = new int[n][m];

		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			Arrays.fill(escapable[i], 4);
			q.offer(new int[] { i, -1 });
			q.offer(new int[] { i, m });
		}
		for (int i = 0; i < m; i++) {
			q.offer(new int[] { -1, i });
			q.offer(new int[] { n, i });
		}
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int[] d : directions) {
				int[] nxt = new int[] { d[0] + cur[0], d[1] + cur[1] };
				if (0 <= nxt[0] && nxt[0] < n && 0 <= nxt[1] && nxt[1] < m) {
					if (maze[nxt[0]][nxt[1]] == '?') {
						escapable[nxt[0]][nxt[1]]--;
						if (escapable[nxt[0]][nxt[1]] == 0) {
							q.offer(nxt);
						}
					} else {
						int[] offs = directionToOffs.get(maze[nxt[0]][nxt[1]]);
						if (cur[0] == offs[0] + nxt[0] && cur[1] == offs[1] + nxt[1] && escapable[nxt[0]][nxt[1]] > 0) {
							escapable[nxt[0]][nxt[1]] = 0;
							q.offer(nxt);
						}
					}

				}
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (escapable[i][j] != 0) {
					ans++;
				}
			}
		}
		return ans;
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
