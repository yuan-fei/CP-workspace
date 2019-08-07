
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskF {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = 1;
		for (int i = 1; i <= t; ++i) {
			String a = in.next();
			String b = in.next();
			String r = solve(a, b);
			System.out.println(r);
		}
		in.close();
	}

	private static String solve(String a, String b) {
		int[][] path = new int[a.length() + 1][b.length() + 1];
		int[][] state = new int[a.length() + 1][b.length() + 1];
		for (int i = 1; i <= a.length(); i++) {
			for (int j = 1; j <= b.length(); j++) {
				state[i][j] = state[i - 1][j];
				path[i][j] = 0;
				if (state[i][j] < state[i][j - 1]) {
					state[i][j] = state[i][j - 1];
					path[i][j] = 1;
				}
				if (a.charAt(i - 1) == b.charAt(j - 1) && state[i][j] < state[i - 1][j - 1] + 1) {
					state[i][j] = state[i - 1][j - 1] + 1;
					path[i][j] = 2;
				}
			}
		}
		String r = getStr(a, b, path, a.length(), b.length());
		return r;
	}

	private static String getStr(String a, String b, int[][] path, int i, int j) {
		if (i == 0 || j == 0) {
			return "";
		} else if (path[i][j] == 2) {
			String r = getStr(a, b, path, i - 1, j - 1);
			if (a.charAt(i - 1) == b.charAt(j - 1)) {
				r += a.charAt(i - 1);
			}
			return r;
		} else if (path[i][j] == 0) {
			return getStr(a, b, path, i - 1, j);
		} else {
			return getStr(a, b, path, i, j - 1);
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
