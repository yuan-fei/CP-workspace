package y2022.r2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class WorkLifeBalance {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int m = in.nextInt();
				int[] a = getIntArr(in, n);
				int[][] q = getIntArr(in, m, 3);
				long r = solve(n, a, m, q);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static long solve(int n, int[] a, int m, int[][] query) {

		RangeUpdateRangeQueryTree[] rt = new RangeUpdateRangeQueryTree[4];
		for (int i = 1; i <= 3; i++) {
			rt[i] = new RangeUpdateRangeQueryTree(n + 5);
		}
		for (int i = 0; i < n; i++) {
			rt[a[i]].increase(i + 1, i + 1, 1);
		}
		long ans = 0;
		for (int[] q : query) {
			rt[a[q[0] - 1]].increase(q[0], q[0], -1);
			rt[q[1]].increase(q[0], q[0], 1);
			a[q[0] - 1] = q[1];
			long[] l = new long[4];
			long[] r = new long[4];
			long lSum = 0;
			long rSum = 0;
			for (int i = 1; i <= 3; i++) {
				l[i] = rt[i].query(0, q[2]);
				lSum += i * l[i];
				r[i] = rt[i].query(q[2] + 1, n + 1);
				rSum += i * r[i];
			}
			long diff = Math.abs(lSum - rSum);
			if (diff % 2 == 1) {
				ans--;
			} else {
				if (lSum > rSum) {
					ans += check(l, r, lSum - rSum);
				} else if (lSum < rSum) {
					ans += check(r, l, rSum - lSum);
				}
			}

		}
		return ans;
	}

	static long check(long[] a, long[] b, long diff) {
		long cnt = 0;
		diff /= 2;
		long maxDiff2 = Math.min(a[3], b[1]);
		// remove 2 possible
		if (diff >= maxDiff2 * 2) {
			diff -= maxDiff2 * 2;
			a[3] -= maxDiff2;
			b[1] -= maxDiff2;
			cnt += maxDiff2;
		} else {
			long delta = diff - diff % 2;
			diff -= delta;
			delta /= 2;
			a[3] -= delta;
			b[1] -= delta;
			cnt += delta;
		}
		if (diff > 0) {
			long maxDiff1 = Math.min(a[3], b[2]);
			if (diff >= maxDiff1) {
				diff -= maxDiff1;
				cnt += maxDiff1;
				a[3] -= maxDiff1;
				b[2] -= maxDiff1;
			} else {
				a[3] -= diff;
				b[2] -= diff;
				cnt += diff;
				diff = 0;
			}
		}
		if (diff > 0) {
			long maxDiff1 = Math.min(a[2], b[1]);
			if (diff >= maxDiff1) {
				diff -= maxDiff1;
				cnt += maxDiff1;
				a[2] -= maxDiff1;
				b[1] -= maxDiff1;
			} else {
				a[2] -= diff;
				b[1] -= diff;
				cnt += diff;
				diff = 0;
			}
		}
		if (diff != 0) {
			return -1;
		}
		return cnt;
	}

	static class RangeUpdateRangeQueryTree {
		class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			public long base;
			public long increment;

			public SegmentTreeNode(int start, int end) {
				this.start = start;
				this.end = end;
			}

			public void extend() {
				if (left == null) {
					int mid = (start + end) / 2;
					left = new SegmentTreeNode(start, mid);
					right = new SegmentTreeNode(mid + 1, end);
				}
			}

			@Override
			public String toString() {
				return "[" + start + ", " + end + "] = " + base + ", " + increment;
			}
		}

		public RangeUpdateRangeQueryTree(int n) {
			root = new SegmentTreeNode(0, n - 1);
		}

		SegmentTreeNode root;

		public Long query(int start, int end) {
			return query(root, start, end);
		}

		public void increase(int start, int end, int value) {
			increase(root, start, end, value);
		}

		private long query(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return 0;
			} else if (start > r.end || end < r.start) {
				return 0;
			} else if (start <= r.start && r.end <= end) {
				return r.base + (r.end - r.start + 1) * r.increment;
			} else {
				r.extend();
				long leftSum = query(r.left, start, end);
				long rightSum = query(r.right, start, end);
				return leftSum + rightSum + (Math.min(end, r.end) - Math.max(start, r.start) + 1) * r.increment;
			}
		}

		private void increase(SegmentTreeNode r, int start, int end, int value) {
			if (r != null && start <= r.end && r.start <= end) {
				if (start <= r.start && r.end <= end) {
					r.increment += value;
				} else {
					r.extend();
					r.base += (Math.min(end, r.end) - Math.max(start, r.start) + 1) * value;
					increase(r.left, start, end, value);
					increase(r.right, start, end, value);
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
