
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * https://atcoder.jp/contests/dp/tasks/dp_q
 * 
 * Problem Statement
 * 
 * There are N flowers arranged in a row. For each i (1≤i≤N), the height and the
 * beauty of the i-th flower from the left is hi and ai, respectively. Here,
 * h1,h2,…,hN are all distinct.
 * 
 * Taro is pulling out some flowers so that the following condition is met: The
 * heights of the remaining flowers are monotonically increasing from left to
 * right.
 * 
 * Find the maximum possible sum of the beauties of the remaining flowers.
 */
public class TaskQ {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[] h = getIntArr(in, n);
		int[] a = getIntArr(in, n);
		long r = solve(n, h, a);
		System.out.println(r);
		in.close();
	}

	static int N = 200005;
	static long[] bit = new long[N + 1];

	static void upd(int i, long v) {
		while (i <= N) {
			bit[i] = Math.max(bit[i], v);
			i += i & -i;
		}
	}

	static long max(int i) {
		long r = 0;
		while (i != 0) {
			r = Math.max(r, bit[i]);
			i -= i & -i;
		}
		return r;
	}

	private static long solve(int n, int[] h, int[] a) {
		for (int i = 0; i < n; i++) {
			upd(h[i], max(h[i]) + a[i]);
		}
		return max(N);
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
