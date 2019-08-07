package r1096;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SubstringRemoval {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = 1;
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String s = in.next();
			long r = solve(n, s);
			System.out.println(r);
		}
		in.close();
	}

	static long mod = 998244353;

	private static long solve(int n, String s) {
		long sl = 0;
		long sr = 0;
		for (int i = 0; i < n; i++) {
			if (i > 0 && s.charAt(i) != s.charAt(i - 1)) {
				sl = i;
				break;
			}
		}
		for (int i = 0; i < n; i++) {
			if (i > 0 && s.charAt(n - i - 1) != s.charAt(n - i)) {
				sr = i;
				break;
			}
		}

		long r = 0;
		if (s.charAt(0) == s.charAt(n - 1)) {
			r = (r + (sl + 1) * (sr + 1) % mod) % mod;
		} else {
			r = (r + (sl + sr) % mod + 1) % mod;
		}
		return r;
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
