package r1148;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskC {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[] t = getIntArr(in, n);
		List<int[]> r = solve(n, t);
		System.out.println(r.size());
		System.out.println(str(r));
		in.close();
	}

	private static List<int[]> solve(int n, int[] t) {
		List<int[]> ret = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			while (t[i - 1] != i) {
				ret.addAll(swap(n, t, t[i - 1], i));
			}
		}
		return ret;
	}

	private static List<int[]> swap(int n, int[] t, int i, int j) {
		List<int[]> ret = new ArrayList<>();
		if (2 * Math.abs(i - j) >= n) {
			ret.add(new int[] { i, j });
		} else {
			int ti = (i <= n / 2) ? n : 1;
			int tj = (j <= n / 2) ? n : 1;
			ret.add(new int[] { i, ti });
			ret.add(new int[] { j, tj });
			if (ti == tj) {
				ret.add(new int[] { i, ti });
			} else {
				ret.add(new int[] { ti, tj });
				ret.add(new int[] { i, ti });
				ret.add(new int[] { j, tj });
			}
		}
		swap(t, i - 1, j - 1);
		return ret;
	}

	private static void swap(int[] t, int i, int j) {
		int tmp = t[i];
		t[i] = t[j];
		t[j] = tmp;
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

	static String str(List<int[]> arr) {
		StringBuilder sb = new StringBuilder();
		for (int[] a : arr) {
			sb.append(a[0] + " " + a[1] + "\n");
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
