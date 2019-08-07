package r1185;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskF {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int N = 1 << 10;
		int n = in.nextInt();
		int m = in.nextInt();
		int[] f = new int[N];
		Integer[][] p = new Integer[N][];
		int[] c = new int[m + 1];
		c[0] = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			int nf = in.nextInt();
			int mask = 0;
			for (int j = 0; j < nf; j++) {
				mask |= 1 << (in.nextInt() - 1);
			}
			f[mask]++;
		}
		for (int i = 0; i < m; i++) {
			c[i + 1] = in.nextInt();
			int np = in.nextInt();
			int mask = 0;
			for (int j = 0; j < np; j++) {
				mask |= 1 << (in.nextInt() - 1);
			}
			if (p[mask] == null) {
				p[mask] = new Integer[] { 0, 0, 0 };
			}
			if (p[mask][0] == 0) {
				p[mask][0] = i + 1;
			} else if (p[mask][1] == 0) {
				p[mask][1] = i + 1;
			} else {
				p[mask][2] = i + 1;
			}
			Arrays.sort(p[mask], Comparator.comparingInt(e -> c[e]));
		}
		int[][] p2 = new int[N][];
		for (int i = 0; i < N; i++) {
			for (int j = i; j < N; j++) {
				int mask = i | j;
				if (i == j) {
					if (p[i] != null && p[i][1] != 0) {
						if (p2[mask] == null) {
							p2[mask] = new int[] { p[i][0], p[i][1] };
						}
						if (c[p2[mask][0]] + c[p2[mask][1]] > c[p[i][0]] + c[p[i][1]]) {
							p2[mask] = new int[] { p[i][0], p[i][1] };
						}
					}
				} else {
					if (p[i] != null && p[j] != null) {
						if (p2[mask] == null) {
							p2[mask] = new int[] { p[i][0], p[j][0] };
						}
						if (c[p2[mask][0]] + c[p2[mask][1]] > c[p[i][0]] + c[p[j][0]]) {
							p2[mask] = new int[] { p[i][0], p[j][0] };
						}
					}
				}
			}
		}
		int[] f2 = new int[N];
		for (int i = 0; i < N; i++) {
			for (int x = i; x != 0; x = i & (x - 1)) {
				f2[i] += f[x];
			}
		}
		int max = 0;
		int minCost = Integer.MAX_VALUE;
		int[] r = new int[0];
		for (int i = 0; i < N; i++) {
			if (p2[i] != null) {
				for (int x = i; x != 0; x = i & (x - 1)) {
					if (f2[x] > max) {
						max = f2[x];
						minCost = c[p2[i][0]] + c[p2[i][1]];
						r = p2[i];
					}
					if (f2[x] == max && c[p2[i][0]] + c[p2[i][1]] < minCost) {
						minCost = c[p2[i][0]] + c[p2[i][1]];
						r = p2[i];
					}
				}
			}
		}
		System.out.println(str(r));
		in.close();
	}

	static long mod = 1000000007;

	void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

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
