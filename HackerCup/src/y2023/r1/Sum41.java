package y2023.r1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Sum41 {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int[] r = solve(n);
				out.println("Case #" + i + ": " + str(r));
			}

		}

	}

	private static int[] solve(int n) {
		int MAX = 1 << 22;
		if (n > MAX) {
			return new int[] { -1 };
		}
		Map<Integer, Integer> factors = getAllPrimeFactors(n);
		int sum = 0;
		int cnt = 0;
		for (int k : factors.keySet()) {
			for (int i = 0; i < factors.get(k); i++) {
				sum += k;
			}
			cnt += factors.get(k);
		}
		int cnt1 = 41 - sum;
		if (cnt1 >= 0 && cnt + cnt1 <= 100) {
			int[] ans = new int[cnt + cnt1 + 1];
			int j = 0;
			ans[j++] = cnt + cnt1;
			for (int k : factors.keySet()) {
				for (int i = 0; i < factors.get(k); i++) {
					ans[j++] = k;
				}
			}
			while (j < ans.length) {
				ans[j++] = 1;
			}
			return ans;
		}

		return new int[] { -1 };
	}

	public static Map<Integer, Integer> getAllPrimeFactors(int n) {
		Map<Integer, Integer> pFactors = new HashMap<>();

		int factor = 2;
		while (n % 2 == 0) {
			int cnt = 0;
			if (pFactors.containsKey(factor)) {
				cnt = pFactors.get(factor);
			}
			pFactors.put(2, cnt + 1);
			n /= 2;
		}
		factor += 1;
		while (factor * factor <= n) {
			if (n % factor == 0) {
				int cnt = 0;
				if (pFactors.containsKey(factor)) {
					cnt = pFactors.get(factor);
				}
				pFactors.put(factor, cnt + 1);
				n /= factor;
			} else {
				factor += 2;
			}
		}

		// This condition is to handle the case when
		// n is a prime number greater than 2
		if (n > 2) {
			int cnt = 0;
			if (pFactors.containsKey(n)) {
				cnt = pFactors.get(n);
			}
			pFactors.put(n, cnt + 1);
		}
		return pFactors;
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
		long r = a * b;
		while (r < 0) {
			r += mod;
		}
		return r % mod;
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
