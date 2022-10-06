package y2022.r2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class PerfectBalanced {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				String s = in.next();
				int n = in.nextInt();
				int[][] q = getIntArr(in, n, 2);
				int r = solve(s, n, q);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static int solve(String s, int n, int[][] q) {
		TreeMap<Integer, Integer>[] index = new TreeMap[26];
		for (int i = 0; i < index.length; i++) {
			index[i] = new TreeMap<>();
		}
		for (int i = 0; i < s.length(); i++) {
			index[s.charAt(i) - 'a'].put(i, index[s.charAt(i) - 'a'].size() + 1);
		}
		int ans = 0;
		for (int[] a : q) {
			if (a[1] == a[0]) {
				ans++;
			} else if ((a[1] - a[0]) % 2 == 0) {
				a[0]--;
				a[1]--;
				int[] cntL = getCount(index, a[0], a[0] + (a[1] - a[0]) / 2 - 1);
				int[] cntR = getCount(index, a[0] + (a[1] - a[0]) / 2 + 1, a[1]);
				cntL[s.charAt(a[0] + (a[1] - a[0]) / 2) - 'a']++;
				if (diff(cntL, cntR) == 1) {
					ans++;
					continue;
				}
				cntL[s.charAt(a[0] + (a[1] - a[0]) / 2) - 'a']--;
				cntR[s.charAt(a[0] + (a[1] - a[0]) / 2) - 'a']++;
				if (diff(cntL, cntR) == 1) {
					ans++;
				}
				cntR[s.charAt(a[0] + (a[1] - a[0]) / 2) - 'a']--;
			}
		}
		return ans;
	}

	static int count(TreeMap<Integer, Integer> pos, int l, int r) {
		Entry<Integer, Integer> rEntry = pos.floorEntry(r);
		Entry<Integer, Integer> lEntry = pos.floorEntry(l - 1);
		int ub = (rEntry == null) ? 0 : rEntry.getValue();
		int lb = (lEntry == null) ? 0 : lEntry.getValue();
		return ub - lb;
	}

	static int[] getCount(TreeMap<Integer, Integer>[] index, int l, int r) {
		int[] cnt = new int[26];
		for (int i = 0; i < 26; i++) {
			cnt[i] = count(index[i], l, r);
		}
		return cnt;
	}

	static int diff(int[] a, int[] b) {
		int ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += Math.abs(a[i] - b[i]);
		}
		return ret;
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
