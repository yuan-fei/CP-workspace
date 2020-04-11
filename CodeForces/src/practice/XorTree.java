package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** https://codeforces.com/problemset/problem/429/A */
public class XorTree {
	static int n;
	static List<Integer>[] adj;
	static int[] value, target;
	static int[] r;

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		n = in.nextInt();
		adj = getEdges(in, n, n - 1);
		value = getIntArr(in, n);
		target = getIntArr(in, n);
		r = new int[n + 1];
		solve(1, 0, 0, 0);
		int cnt = 0;
		for (int j = 0; j < r.length; j++) {
			cnt += r[j];
		}
		System.out.println(cnt);
		System.out.println(str(r));
		in.close();
	}

	private static void solve(int u, int p, int flip, int flipNext) {
		r[u] = value[u] ^ flip ^ target[u];
		for (int v : adj[u]) {
			if (v != p) {
				solve(v, u, flipNext, r[u] ^ flip);
			}
		}
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

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {

			sb.append(a.get(i) + " ");
		}
		return sb.toString();
	}

	static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < a.length; i++) {
			if (a[i] > 0) {
				sb.append(i + "\n");
			}
		}
		return sb.toString();
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size + 1];
		for (int i = 1; i <= size; i++) {
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

	static char[][] getCharArr(Scanner in, int row, int col) {
		char[][] arr = new char[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getCharArr(in, col);
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

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}

	static List<Integer>[] getEdges(Scanner in, int n, int size) {
		List<Integer>[] edges = new List[n + 1];
		for (int i = 0; i <= n; i++) {
			edges[i] = new ArrayList<>();
		}
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			edges[from].add(to);
			edges[to].add(from);
		}
		return edges;
	}

}
