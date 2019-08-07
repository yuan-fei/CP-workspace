package r1185;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskE {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();

		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int m = in.nextInt();
			char[][] s = getCharArr(in, n, m);
			int[][] r = solve(n, m, s);
			if (r != null) {
				System.out.println("YES");
				System.out.println(r.length);
				for (int j = 0; j < r.length; j++) {
					System.out.println(str(r[j]));
				}
			} else {
				System.out.println("NO");
			}

		}

		in.close();
	}

	private static int[][] solve(int n, int m, char[][] s) {
		Map<Character, int[][]> index = new HashMap<>();
		int min = 'z' + 1;
		int max = 'a' - 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				char c = s[i][j];
				if (c != '.') {
					if (!index.containsKey(c)) {
						max = Math.max(max, c);
						min = Math.min(min, c);
						index.put(c, new int[][] { new int[] { i + 1, j + 1 }, new int[] { i + 1, j + 1 } });
					}
					int[][] range = index.get(c);
					if (range[0][0] != i + 1 && range[0][1] != j + 1) {
						return null;
					}
					if ((range[0][0] != i + 1 || range[1][0] != i + 1)
							&& (range[0][1] != j + 1 || range[1][1] != j + 1)) {
						return null;
					}
					range[1] = new int[] { i + 1, j + 1 };
				}
			}
		}
		int[][] r = new int[max - 'a' + 1][];
		if (max < 'a') {
			return new int[0][];
		}
		int[] maxStart = index.get((char) max)[0];
		for (int i = 'a'; i <= max; i++) {
			char c = (char) i;
			if (index.containsKey(c)) {
				int[][] range = index.get(c);
				if (range[0][0] == range[1][0]) {
					for (int j = range[0][1]; j <= range[1][1]; j++) {
						if (s[range[0][0] - 1][j - 1] < c) {
							return null;
						}
					}
				} else {
					for (int j = range[0][0]; j <= range[1][0]; j++) {
						if (s[j - 1][range[0][1] - 1] < c) {
							return null;
						}
					}
				}
				r[i - 'a'] = new int[] { range[0][0], range[0][1], range[1][0], range[1][1] };
			} else {
				r[i - 'a'] = new int[] { maxStart[0], maxStart[1], maxStart[0], maxStart[1] };
			}
		}
		return r;
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
