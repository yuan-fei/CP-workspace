package y2021.r2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Runaway implements Runnable {

	public static void main(String[] args) throws FileNotFoundException {
//		new Thread(null, new Solution(), "Solution", 1L << 32).start();
		new Runaway().run();
	}

	public void run() {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int m = in.nextInt();
				int[] initial = getIntArr(in, m);
				int[][] shows = getIntArr(in, n, m);
				int r = solve(initial, shows);
				out.println("Case #" + i + ": " + r);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int solve(int[] initial, int[][] shows) {
		int ret = 0;
		Map<Integer, List<Integer>> m = new HashMap<>();
		boolean[] changed = new boolean[initial.length];
		for (int i = 0; i < initial.length; i++) {
			if (!m.containsKey(initial[i])) {
				m.put(initial[i], new LinkedList<Integer>());
			}
			m.get(initial[i]).add(i);
		}
		for (int i = 0; i < shows.length; i++) {
			Map<Integer, List<Integer>> newM = new HashMap<>();
			List<Integer> notSeen = new LinkedList<Integer>();
			for (int x : shows[i]) {
				if (m.containsKey(x)) {
					int id = m.get(x).remove(0);
					if (m.get(x).isEmpty()) {
						m.remove(x);
					}
					if (!newM.containsKey(x)) {
						newM.put(x, new LinkedList<Integer>());
					}
					if (changed[id]) {
						newM.get(x).add(0, id);
					} else {
						newM.get(x).add(newM.get(x).size(), id);
					}

				} else {
					notSeen.add(x);
				}
			}
			for (List<Integer> ids : m.values()) {
				while (!ids.isEmpty()) {
					int id = ids.remove(0);
					int x = notSeen.remove(0);
					if (!newM.containsKey(x)) {
						newM.put(x, new LinkedList<Integer>());
					}
					if (changed[id]) {
						ret++;
					}
					changed[id] = true;
					newM.get(x).add(0, id);
				}
			}
			m = newM;
		}
		return ret;
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

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
