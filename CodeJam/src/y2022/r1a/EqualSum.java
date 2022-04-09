package y2022.r1a;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EqualSum {
	public static void main(String[] args) {
		solve();
	}

	static Scanner in;

	private static void solve() {
		in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			solve(n);
		}
		in.close();
	}

	private static void solve(int n) {
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			l.add(1 << i);
		}
		// put arbitrary 70 numbers
		List<Integer> lRest = new ArrayList<>();
		for (int i = (1 << 29) - 1; i >= (1 << 29) - 70; i -= 1) {
			lRest.add(i);
			l.add(i);
		}

		System.out.println(str(l));
		for (int i = 0; i < 100; i++) {
			lRest.add(in.nextInt());
		}
		Collections.sort(lRest);
		List<Integer> lFirst = new ArrayList<>();
		int diff = 0;
		for (int i = 0; i < 170; i += 2) {
			diff += lRest.get(i + 1) - lRest.get(i);
			lFirst.add(lRest.get(i + 1));
		}
//		System.err.println(diff);
		int total = (1 << 30) - 1;
		int pick = (total - diff) >> 1;
		for (int i = 0; i < 30; i++) {
			if (((pick >> i) & 1) != 0) {
				lFirst.add(1 << i);
			}
		}
		System.out.println(str(lFirst));
//		System.err.println(str(lFirst));
	}

	static int[] ask(String action, int x) {

		System.out.println(action + " " + x);
		if (action.equals("E")) {
			return new int[0];
		}
		return getIntArr(in, 2);
	}

	private static void test() {
		for (int i = 0; i < 10000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt();
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

	static private List<Integer>[] buildAdj(int n, int[][] edges, boolean biDirectional) {
		List<Integer>[] ans = new List[n];
		for (int i = 0; i < n; i++) {
			ans[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			ans[edges[i][0]].add(i);
			if (biDirectional) {
				ans[edges[i][1]].add(i);
			}
		}
		return ans;
	}

	static private List<Integer>[] buildRootedTree(int n, int[] edges) {
		List<Integer>[] ans = new List[n];
		for (int i = 0; i < n; i++) {
			ans[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			ans[edges[i]].add(i);
		}
		return ans;
	}

}
