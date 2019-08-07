
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class TaskW {
	public static void main(String[] args) {
		// LazyPropSegmentTree s = new LazyPropSegmentTree(4);
		// s.print();
		// System.out.println(s.query(0, 6));
		// System.out.println(s.query(0, 2));
		// System.out.println(s.query(2, 6));
		// System.out.println(s.query(4, 6));
		// s.increase(0, 0, 1);
		// s.increase(0, 0, -2);
		// s.print();
		// System.out.println(s.query(0, 0));
		// System.out.println(s.query(0, 2));
		// System.out.println(s.query(2, 6));
		// System.out.println(s.query(4, 6));
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = 1;
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[][] a = getIntArr(in, m, 3);
			long r = solve(n, m, a);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int n, int m, int[][] a) {
		List<int[]>[] delta = new List[n + 2];
		for (int i = 0; i < a.length; i++) {
			if (delta[a[i][0]] == null) {
				delta[a[i][0]] = new ArrayList<int[]>();
			}
			delta[a[i][0]].add(new int[] { a[i][0] - 1, a[i][2] });
			if (delta[a[i][1] + 1] == null) {
				delta[a[i][1] + 1] = new ArrayList<int[]>();
			}
			delta[a[i][1] + 1].add(new int[] { a[i][0] - 1, -a[i][2] });
		}
		long max = 0;
		LazyPropSegmentTree t = new LazyPropSegmentTree(n + 1);
		long acc = 0;
		for (int i = 1; i <= n; i++) {
			if (delta[i] != null) {
				for (int[] intv : delta[i]) {
					acc += intv[1];
					t.increase(0, intv[0], intv[1]);
				}
			}
			long x = t.query(0, i - 1);
			long maxi = Math.max(x, acc);
			t.increase(i, i, maxi);
			max = Math.max(max, maxi);
		}
		return max;
	}

	static class LazyPropSegmentTree {

		class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			public long base;
			public long increment;

			public SegmentTreeNode(int start, int end) {
				this.start = start;
				this.end = end;
				this.left = this.right = null;
			}

			@Override
			public String toString() {
				return "[" + start + ", " + end + "] = " + base + ", " + increment;
			}
		}

		SegmentTreeNode root;

		public LazyPropSegmentTree(int[] nums) {
			build(nums);
		}

		public LazyPropSegmentTree(int n) {
			build(new int[n]);
		}

		private void build(int[] nums) {
			if (nums.length > 0) {
				root = build(0, nums.length - 1, nums);
			}
		}

		public long query(int start, int end) {
			return query(root, start, end);
		}

		public void increase(int start, int end, long value) {
			increase(root, start, end, value);
		}

		private SegmentTreeNode build(int start, int end, int[] nums) {
			SegmentTreeNode r = new SegmentTreeNode(start, end);
			if (start == end) {
				r.base = nums[start];
			} else {
				r.left = build(start, start + (end - start) / 2, nums);
				r.right = build(start + (end - start) / 2 + 1, end, nums);
				r.base = Math.max(r.left.base, r.right.base);
			}
			return r;
		}

		private long query(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return Long.MIN_VALUE;
			} else if (start > r.end || end < r.start) {
				return Long.MIN_VALUE;
			} else if (start <= r.start && r.end <= end) {
				return r.base + r.increment;
			} else {
				long leftSum = query(r.left, start, end);
				long rightSum = query(r.right, start, end);
				return Math.max(leftSum, rightSum) + r.increment;
			}
		}

		private void increase(SegmentTreeNode r, int start, int end, long value) {
			if (r != null && start <= r.end && r.start <= end) {
				if (start <= r.start && r.end <= end) {
					r.increment += value;
				} else {
					increase(r.left, start, end, value);
					increase(r.right, start, end, value);
					r.base = Math.max(r.left.base + r.left.increment, r.right.base + r.right.increment);
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

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
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

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
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
}
