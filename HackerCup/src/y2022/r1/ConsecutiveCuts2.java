package y2022.r1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsecutiveCuts2 implements Runnable {

	public static void main(String[] args) throws FileNotFoundException {
//		new Thread(null, new Solution(), "Solution", 1L << 32).start();
		new ConsecutiveCuts2().run();
	}

	public void run() {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int k = in.nextInt();
				int[] a = getIntArr(in, n);
				int[] b = getIntArr(in, n);
				boolean r = solve(n, k, a, b);
				out.println("Case #" + i + ": " + (r ? "YES" : "NO"));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean solve(int n, int k, int[] a, int[] b) {
		if (n == 2) {
			return solve2(n, k, a, b);
		}
		long p1 = mod;
		long p2 = 873720983;

		long ha1 = getHash(a, p1);
		long hb1 = getHash(b, p1);
		long ha2 = getHash(a, p2);
		long hb2 = getHash(b, p2);
		long fac1 = 1;
		long fac2 = 1;
		for (int i = 0; i < n - 1; i++) {
			fac1 *= p1;
			fac2 *= p2;
		}
		if (k == 0) {
//			return ha1 == hb1 && ha2 == hb2;
			return ha1 == hb1;
		}
		boolean sameDeck = false;
		for (int i = 0; i < n; i++) {
			ha1 -= a[i] * fac1;
			ha1 *= p1;
			ha1 += a[i];
			ha2 -= a[i] * fac2;
			ha2 *= p2;
			ha2 += a[i];
//			if (ha1 == hb1 && ha2 == hb2) {
			if (ha1 == hb1) {
				sameDeck = true;
			}
		}
		return sameDeck;
	}

	private boolean solve2(int n, int k, int[] a, int[] b) {
		if (k % 2 == 0) {
			return a[0] == b[0] && a[1] == b[1];
		} else {
			return a[0] == b[1] && a[1] == b[0];
		}
	}

	long getHash(int[] a, long p) {
		long ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret *= p;
			ret += a[i];
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

	static String str(List<String> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + "\n");
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

	static char[][] getStringArr(Scanner in, int size) {
		char[][] arr = new char[size][];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next().toCharArray();
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
