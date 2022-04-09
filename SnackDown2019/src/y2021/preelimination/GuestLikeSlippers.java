package y2021.preelimination;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class GuestLikeSlippers {
	public static void main(String[] args) {
		solve();
//		test();
//		System.out.println(solve(5, new long[] { 1, 2, 3, 4, 1000 }));
//		System.out.println(solveSlow(5, new long[] { 1, 2, 3, 4, 1000 }));
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
//		int t = in.nextInt();
		int t = 1;
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int[] a = getIntArr(in, n);
			long r = solve(n, a);
			System.out.println(r);
		}

		in.close();
	}

	private static long solve(int n, int[] a) {
		long res = 0;
		PointIncrementRangeSumQueryTree s = new PointIncrementRangeSumQueryTree(200005);
		s.increase(0, 1);
		for (int i = 0; i < a.length; i++) {
			int v = s.query(0, a[i] - 1);
//			System.out.println(v);
			s.increase(a[i], v);
		}

		return s.query(0, 200005);
	}

	static void dec(TreeMap<Long, Integer> tm, long key) {
		int v = tm.get(key) - 1;
		if (v > 0) {
			tm.put(key, v);
		} else {
			tm.remove(key);
		}
	}

	static void inc(TreeMap<Long, Integer> tm, long key) {
		tm.put(key, tm.getOrDefault(key, 0) + 1);
	}

	static Random r = new Random();

	static long[] generateCase(int n, int x) {
		long[] ret = new long[n];
		for (int i = 0; i < n; i++) {
			ret[i] = x % 10;
			x /= 10;
		}
		return ret;
	}

	static long[] generateCaseRandom(int n) {
		long[] ret = new long[n];
		for (int i = 0; i < n; i++) {
			ret[i] = r.nextInt(21) - 10;
		}
		return ret;
	}

	static long solveSlow(int n, long[] r) {
		long ret = Long.MAX_VALUE;
		for (int i = 0; i < (1 << n); i++) {
			if (i != 0 || i != (1 << n) - 1) {
				long[][] g = { { Long.MIN_VALUE, Long.MAX_VALUE }, { Long.MIN_VALUE, Long.MAX_VALUE } };
				for (int j = 0; j < n; j++) {
					g[(i >> j) & 1][0] = Math.max(g[(i >> j) & 1][0], r[j]);
					g[(i >> j) & 1][1] = Math.min(g[(i >> j) & 1][1], r[j]);
				}
				long diff = Math.abs(g[0][0] - g[0][1] - (g[1][0] - g[1][1]));
				ret = Math.min(ret, diff);
			}
		}
		return ret;
	}

	static int MAX = (int) 1e9 + 8;

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

	static String str(long[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
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

class PointIncrementRangeSumQueryTree {
	long MOD = 998244353L;

	class SegmentTreeNode {
		public int start, end;
		public SegmentTreeNode left, right;
		public int value;

		public SegmentTreeNode(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public String toString() {
			return "[" + start + ", " + end + "] = " + value;
		}
	}

	SegmentTreeNode root;
	int n;

	public PointIncrementRangeSumQueryTree(int n) {
		build(n);
	}

	public void build(int n) {
		this.n = n;
		root = build(0, n - 1);
	}

	public int query(int start, int end) {
		return query(root, start, end);
	}

	public void increase(int index, int value) {
		increase(root, index, value);
	}

	private SegmentTreeNode build(int start, int end) {
		SegmentTreeNode r = new SegmentTreeNode(start, end);
		if (start < end) {
			r.left = build(start, start + (end - start) / 2);
			r.right = build(start + (end - start) / 2 + 1, end);
			r.value = add(r.left.value, r.right.value);
		}
		return r;
	}

	private int query(SegmentTreeNode r, int start, int end) {
		if (r == null) {
			return 0;
		} else if (start > r.end || end < r.start) {
			return 0;
		} else if (start <= r.start && end >= r.end) {
			return r.value;
		} else {
			int leftSum = query(r.left, start, end);
			int rightSum = query(r.right, start, end);
			return add(leftSum, rightSum);
		}
	}

	private void increase(SegmentTreeNode r, int index, int value) {
		if (r != null && index >= r.start && index <= r.end) {
			if (r.start == index && r.end == index) {
				r.value = add(r.value, value);
			} else {
				increase(r.left, index, value);
				increase(r.right, index, value);
				r.value = add(r.left.value, r.right.value);
			}
		}
	}

	private int add(int a, int b) {
		long r = 0;
		r += a;
		r += b;
		r %= MOD;
		return (int) r;
	}

	public static void main(String[] args) {
		int[] a = new int[] { -1, 0, 1, 2, 3, 4, 5 };
		PointIncrementRangeSumQueryTree s = new PointIncrementRangeSumQueryTree(a.length);
		for (int i = 0; i < a.length; i++) {
			s.increase(i, a[i]);
		}
		s.print();
		System.out.println(s.query(0, 6));
		System.out.println(s.query(0, 2));
		System.out.println(s.query(2, 6));
		System.out.println(s.query(4, 6));
		s.increase(0, 1);
		s.print();
		System.out.println(s.query(0, 7));

	}

	public void print() {
		Queue<SegmentTreeNode> q = new LinkedList<SegmentTreeNode>();
		q.offer(root);
		while (!q.isEmpty()) {
			int cnt = q.size();
			String s = "";
			for (int i = 0; i < cnt; i++) {
				SegmentTreeNode n = q.poll();
				s += (n + ",");
				if (n != null) {
					q.offer(n.left);
					q.offer(n.right);
				}
			}
			System.out.println(s);
		}
		System.out.println();
	}
}