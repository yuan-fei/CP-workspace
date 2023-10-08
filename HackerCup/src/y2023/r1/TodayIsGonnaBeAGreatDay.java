package y2023.r1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class TodayIsGonnaBeAGreatDay {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int[] arr = getIntArr(in, n);
				int m = in.nextInt();
				int[][] q = getIntArr(in, m, 2);
				long r = solve(n, arr, m, q);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static long solve(int n, int[] arr, int m, int[][] q) {
		RangeUpdateRangeQueryTree t = new RangeUpdateRangeQueryTree(arr);
//		t.print();
		long ans = 0;
		for (int i = 0; i < m; i++) {
			t.flip(q[i][0] - 1, q[i][1] - 1);
//			t.print();
			ans += t.query(0, n - 1) + 1;
		}
		return ans;
	}

	static class RangeUpdateRangeQueryTree {
		class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			private int maxId = -1;
			private int maxValue = Integer.MAX_VALUE;
			private int maxIdFlipped = -1;
			private int maxValueFlipped = Integer.MAX_VALUE;
			public boolean toFlip = false;

			public int getMaxId() {
				return toFlip ? maxIdFlipped : maxId;
			}

			public int getMaxValue() {
				return toFlip ? maxValueFlipped : maxValue;
			}

			public int getMaxIdFlipped() {
				return !toFlip ? maxIdFlipped : maxId;
			}

			public int getMaxValueFlipped() {
				return !toFlip ? maxValueFlipped : maxValue;
			}

			public SegmentTreeNode(int start, int end) {
				this.start = start;
				this.end = end;
			}

//			public void flip() {
//				int t = maxId;
//				maxId = maxIdFlipped;
//				maxIdFlipped = t;
//				t = maxValue;
//				maxValue = maxValueFlipped;
//				maxValueFlipped = t;
//			}

			public void extend() {
				if (left == null) {
					int mid = (start + end) / 2;
					left = new SegmentTreeNode(start, mid);
					right = new SegmentTreeNode(mid + 1, end);
				}
			}

			@Override
			public String toString() {
				return "[" + start + ", " + end + "] = " + toFlip + ", "
						+ Arrays.asList(maxId, maxValue, maxIdFlipped, maxValueFlipped);
			}
		}

		int[] values;

		public RangeUpdateRangeQueryTree(int[] values) {
			int n = values.length;
			root = new SegmentTreeNode(0, n - 1);
			this.values = values;
			init(root, 0, n - 1);
		}

		SegmentTreeNode root;

		public long query(int start, int end) {
			return query(root, start, end)[0];
		}

		public void flip(int start, int end) {
			flip(root, start, end);
		}

		private void init(SegmentTreeNode r, int start, int end) {
			if (r != null && start <= r.end && r.start <= end) {
				if (start <= r.start && r.end <= end && r.start == r.end) {
					r.maxId = r.start;
					r.maxValue = values[r.start];
					r.maxIdFlipped = r.start;
					r.maxValueFlipped = (int) MOD - values[r.start];
				} else {
					r.extend();
					init(r.left, start, end);
					init(r.right, start, end);
					SegmentTreeNode maxChild = (r.left.maxValue >= r.right.maxValue) ? r.left : r.right;
					r.maxValue = maxChild.maxValue;
					r.maxId = maxChild.maxId;
					maxChild = (r.left.maxValueFlipped >= r.right.maxValueFlipped) ? r.left : r.right;
					r.maxValueFlipped = maxChild.maxValueFlipped;
					r.maxIdFlipped = maxChild.maxIdFlipped;
				}
			}
		}

		private long[] query(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return new long[] { Long.MAX_VALUE, Long.MAX_VALUE };
			} else if (start > r.end || end < r.start) {
				return new long[] { Long.MAX_VALUE, Long.MAX_VALUE };
			} else if (start <= r.start && r.end <= end) {
				return new long[] { r.getMaxId(), r.getMaxValue() };
			} else {
				long[] left = query(r.left, start, end);
				long[] right = query(r.right, start, end);
				long[] max = (left[1] >= right[1]) ? left : right;
				long[] min = (left[1] >= right[1]) ? right : left;
				return r.toFlip ? min : max;
			}
		}

		private void flip(SegmentTreeNode r, int start, int end) {
			if (r != null && start <= r.end && r.start <= end) {
				if (start <= r.start && r.end <= end) {
					r.toFlip = !r.toFlip;
				} else {
					flip(r.left, start, end);
					flip(r.right, start, end);
					SegmentTreeNode maxChild = (r.left.getMaxValue() >= r.right.getMaxValue()) ? r.left : r.right;
					r.maxValue = maxChild.getMaxValue();
					r.maxId = maxChild.getMaxId();
					maxChild = (r.left.getMaxValueFlipped() >= r.right.getMaxValueFlipped()) ? r.left : r.right;
					r.maxValueFlipped = maxChild.getMaxValueFlipped();
					r.maxIdFlipped = maxChild.getMaxIdFlipped();
				}
			}
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

	static long MOD = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += MOD;
		}
		return r % MOD;
	}

	static long mul(long a, long b) {
		long r = a * b;
		while (r < 0) {
			r += MOD;
		}
		return r % MOD;
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
