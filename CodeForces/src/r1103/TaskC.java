package r1103;

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
		String t = in.next();
		int[][] r = solve(t);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < r.length; i++) {
			sb.append(r[i][0] + " " + r[i][1]);
			sb.append("\n");
		}
		System.out.println(sb.toString());
		in.close();
	}

	private static int[][] solve(String t) {
		int[][] r = new int[t.length()][];
		boolean v = false;
		boolean h = false;
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == '0') {
				if (!v) {
					r[i] = new int[] { 1, 1 };
				} else {
					r[i] = new int[] { 3, 1 };
				}
				v = !v;
			} else {
				if (!h) {
					r[i] = new int[] { 4, 3 };
				} else {
					r[i] = new int[] { 4, 1 };
				}
				h = !h;
			}
		}
		return r;
	}

	int gcd(int a, int b) {
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
}
