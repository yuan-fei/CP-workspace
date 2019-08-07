package hello2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskE {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] a = getIntArr(in, n);
			List<List<Integer>> r = solve(n, a);
			System.out.println(r.size());
			for (List<Integer> l : r) {
				System.out.println(l.size() + " " + str(l));
			}

		}
		in.close();
	}

	private static List<List<Integer>> solve(int n, int[] a) {
		List<List<Integer>> rd = LDS(a);
		int[] b = new int[a.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = -a[i];
		}
		List<List<Integer>> ri = LDS(b);
		if (rd.size() <= ri.size()) {
			return rd;
		} else {
			for (List<Integer> l : ri) {
				for (int i = 0; i < l.size(); i++) {
					l.set(i, -l.get(i));
				}
			}
			return ri;
		}
	}

	public static List<List<Integer>> LDS(int[] a) {
		// state[i] min last element with LIS length i
		int[] state = new int[a.length];
		Map<Integer, List<Integer>> map = new HashMap<>();
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < a.length; i++) {
			max = Math.max(max, a[i]);
		}
		Arrays.fill(state, max + 1);
		for (int i = 0; i < a.length; i++) {
			int index = Arrays.binarySearch(state, a[i]);
			List<Integer> l = map.remove(state[-index - 1]);
			state[-index - 1] = a[i];
			if (l == null) {
				l = new ArrayList<>();
			}
			l.add(a[i]);
			map.put(a[i], l);
		}
		return new ArrayList<List<Integer>>(map.values());
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static String str(List<Integer> a) {
		String[] str = new String[a.size()];
		for (int i = 0; i < a.size(); i++) {
			str[i] = a.get(i) + "";
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
