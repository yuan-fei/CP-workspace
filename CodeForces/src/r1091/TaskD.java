package r1091;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskD {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		long r = solve(n);
		System.out.println(r);
		in.close();

	}

	static long mod = 998244353;

	private static long solve(int n) {
		long[] a = new long[n + 1];
		a[0] = 1;
		long b = 0;
		for (int i = 1; i <= n; i++) {
			a[i] = (a[i - 1] * (n - i + 1)) % mod;
			if (i >= 1 && i <= n - 2) {
				b = (b + a[i]) % mod;
			}
		}
		return (mod + (a[n] * (n - 1)) % mod - b) % mod;
	}

	private static void _getFullPermutations(List<Integer> result, List<Integer> current, int[] source, int pos) {
		if (pos == source.length) {
			result.addAll(new ArrayList<Integer>(current));
		}

		for (int i = pos; i < source.length; i++) {
			// in place swap is used instead of the alternative storage
			// (currentSet)
			swap(source, pos, i);
			current.add(source[pos]);
			_getFullPermutations(result, current, source, pos + 1);
			current.remove(current.size() - 1);
			swap(source, i, pos);
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
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
