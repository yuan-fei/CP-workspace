
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				String s = in.next();
				int k = in.nextInt();
				String r = solve(s, k);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static String solve(String s, int k) {
		long nDecodings = maxDecodings(s.toCharArray());
		String kth = getKth(s.toCharArray(), k);
		return kth + " " + nDecodings;
	}

	static long maxDecodings(char[] chars) {
		long[] dp = { 1, 1 };
		for (int i = 0; i < chars.length; i++) {
			long[] newDp = new long[2];
			newDp[0] = dp[1];
			if (chars[i] == '?') {
				chars[i] = '1';
			}
			if (chars[i] == '0') {
				newDp[1] = mAdd(newDp[1], dp[0]);
			} else {
				newDp[1] = mAdd(newDp[1], dp[1]);
				if (i > 0 && chars[i - 1] != '0' && 26 >= Integer.parseInt("" + chars[i - 1] + chars[i])) {
					newDp[1] = mAdd(newDp[1], dp[0]);
				}
			}
			dp = newDp;
		}
		return dp[1];
	}

	static String getKth(char[] chars, int k) {
		k--;
		int qmCnt = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '?') {
				qmCnt++;
			} else if (i > 0 && chars[i - 1] != '?' && chars[i] != '?') {
				int x = chars[i] - '0';
				if (x > 6) {
					chars[i - 1] = '1';
					qmCnt--;
				}
			}
		}
		int unit = 1;
		if (chars[chars.length - 1] == '?') {
			if (chars.length == 1) {
				return "" + (char) ('9' - k - 1);
			}
			if (chars[chars.length - 2] == '?') {
				int t = (26 - k % 26 - 1);
				chars[chars.length - 1] = (char) ('0' + t % 10);
				chars[chars.length - 2] = (char) ('0' + t / 10);
				unit = 26;
				qmCnt -= 2;
			} else {
				if (chars[chars.length - 2] == '1') {
					chars[chars.length - 1] = (char) ('9' - k % 9 - 1);
					unit = 9;
					qmCnt--;
				} else if (chars[chars.length - 2] == '2') {
					chars[chars.length - 1] = (char) ('6' - k % 6 - 1);
					unit = 6;
					qmCnt--;
				} else {
					chars[chars.length - 1] = (char) ('9' - k % 9 - 1);
					unit = 9;
					qmCnt--;
				}
			}
		}
		k /= unit;
		List<Integer> qPos = new ArrayList<>();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '?') {
				qPos.add(i);
			}
		}
		for (int i = 0; i < qmCnt; i++) {
			int d = ((k >> (qmCnt - i - 1)) & 1);
			chars[qPos.get(i)] = ((d == 0) ? '2' : '1');
		}

		return String.valueOf(chars);
	}

	static long mod = 998244353;

	static long mAdd(long a, long b) {
		return Math.floorMod(a + b, mod);
	}

	static long mMul(long a, long b) {
		return Math.floorMod(a * b, mod);
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

	/** graph */
	static List<Integer>[] buildAdj(int n, int[][] edges, boolean directed) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int[] e : edges) {
			adj[e[0]].add(e[1]);
			if (!directed) {
				adj[e[1]].add(e[0]);
			}
		}
		return adj;
	}

	static List<Integer>[] buildAdjWithEdgeIndex(int n, int[][] edges, boolean directed) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			int[] e = edges[i];
			adj[e[0]].add(i);
			if (!directed) {
				adj[e[1]].add(i);
			}
		}
		return adj;
	}

	private static class DSU {
		int[] parent;

		public DSU(int N) {
			this.parent = new int[N];
			for (int i = 0; i < N; i++) {
				add(i);
			}
		}

		public void add(int x) {
			parent[x] = x;
		}

		public int find(int x) {
			if (parent[x] != x)
				parent[x] = find(parent[x]);
			return parent[x];
		}

		public void union(int x, int y) {
			if (find(x) != find(y)) {
				parent[find(x)] = parent[find(y)];
			}
		}
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static <K> void inc(Map<K, Integer> m, K k) {
		m.put(k, m.getOrDefault(k, 0) + 1);
	}

	static <K> void dec(Map<K, Integer> m, K k) {
		m.put(k, m.getOrDefault(k, 0) - 1);
		if (m.get(k) <= 0) {
			m.remove(k);
		}
	}
}
