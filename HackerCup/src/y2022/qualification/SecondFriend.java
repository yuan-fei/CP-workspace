package y2022.qualification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class SecondFriend implements Runnable {

	public static void main(String[] args) throws FileNotFoundException {
//		new Thread(null, new Solution(), "Solution", 1L << 32).start();
		new SecondFriend().run();
	}

	public void run() {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int r = in.nextInt();
				int c = in.nextInt();
				char[][] paint = getStringArr(in, r);
				char[][] res = solve(r, c, paint);
				if (res == null) {
					out.println("Case #" + i + ": Impossible");
				} else {
					out.println("Case #" + i + ": Possible");
					out.print(str(res));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private char[][] solve(int r, int c, char[][] paint) {
		char[][] ret = new char[r][c];
		Queue<int[]> q = new LinkedList<>();
		int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				ret[i][j] = paint[i][j];
				if (ret[i][j] == '.') {
					ret[i][j] = '^';
				}
			}
		}
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (ret[i][j] == '^' && getDegree(new int[] { i, j }, ret) < 2) {
					if (paint[i][j] == '^') {
						return null;
					} else {
						ret[i][j] = '.';
						q.offer(new int[] { i, j });
					}
				}
			}
		}
		while (!q.isEmpty()) {
			int[] p = q.poll();
			for (int[] d : dirs) {
				int[] adj = new int[] { p[0] + d[0], p[1] + d[1] };
				if (0 <= adj[0] && adj[0] < r && 0 <= adj[1] && adj[1] < c) {
					if (ret[adj[0]][adj[1]] == '^' && getDegree(new int[] { adj[0], adj[1] }, ret) < 2) {
						if (paint[adj[0]][adj[1]] == '^') {
							return null;
						} else {
							ret[adj[0]][adj[1]] = '.';
							q.offer(new int[] { adj[0], adj[1] });
						}
					}
				}
			}
		}

		return ret;
	}

	int getDegree(int[] p, char[][] paint) {
		int r = paint.length;
		int c = paint[0].length;
		int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
		int cnt = 0;
		for (int[] d : dirs) {
			int[] adj = new int[] { p[0] + d[0], p[1] + d[1] };
			if (0 <= adj[0] && adj[0] < r && 0 <= adj[1] && adj[1] < c) {
				if (paint[adj[0]][adj[1]] == '^') {
					cnt++;
				}
			}
		}
		return cnt;
	}

	final static long mod = 1000000007;

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

	static String str(char[][] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(String.valueOf(a[i]) + "\n");
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

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
		}
		return arr;
	}

	static char[][] getStringArr(Scanner in, int size) {
		char[][] arr = new char[size][];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next().toCharArray();
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
