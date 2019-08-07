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

public class XOrWhat {
	public static void main(String[] args) {
		// generateTestCase();
		solve();
	}

	private static void generateTestCase() {
		int N = 10;
		System.out.println(N);
		for (int i = 0; i < N; i++) {
			generateOne();
		}
	}

	private static void generateOne() {
		Random r = new Random();
		int n = r.nextInt(10) + 1;
		int[] a = new int[n];
		int[][] q = new int[n][2];
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(1024);
		}
		for (int i = 0; i < n; i++) {
			q[i][0] = r.nextInt(n);
			q[i][1] = r.nextInt(1024);
		}
		System.out.println(n + " " + n);
		System.out.println(str(a));
		System.out.println(str(q));
	}

	private static String str(int[][] q) {
		StringBuilder sb = new StringBuilder();
		for (int[] qr : q) {
			sb.append(qr[0] + " " + qr[1] + "\n");
		}
		return sb.toString();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int q = in.nextInt();
			int[] a = getIntArr(in, n);
			int[][] qr = getIntArr(in, q, 2);
			int[] res = solve(n, q, a, qr);
			System.out.println("Case #" + i + ": " + str(res));
		}
		in.close();
	}

	private static int[] solve(int n, int q, int[] a, int[][] qr) {
		int[] ans = new int[q];
		boolean[] prefix = new boolean[n + 1];
		for (int i = 1; i <= n; i++) {
			prefix[i] = (xorEven(0, a[i - 1]) == prefix[i - 1]);
		}
		RangeIncrementRangeQueryTree t = new RangeIncrementRangeQueryTree(prefix);
		int[] r = t.query(0, n);
		int last = Math.max(r[0] - r[2], r[1] - r[3]);
		for (int i = 0; i < q; i++) {
			if (!xorEven(qr[i][1], a[qr[i][0]])) {
				t.flip(qr[i][0] + 1, n);
				r = t.query(0, n);
				last = Math.max(r[0] - r[2], r[1] - r[3]);
			}
			a[qr[i][0]] = qr[i][1];
			ans[i] = last;
		}
		return ans;
	}

	static boolean xorEven(int a, int b) {
		return (Integer.bitCount(a) + Integer.bitCount(b)) % 2 == 0;
	}

	static class RangeIncrementRangeQueryTree {

		class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			public int maxTrue = Integer.MIN_VALUE;
			public int maxFalse = Integer.MIN_VALUE;
			public int minTrue = Integer.MAX_VALUE;
			public int minFalse = Integer.MAX_VALUE;
			public boolean flip;

			public SegmentTreeNode(int start, int end) {
				this.start = start;
				this.end = end;
				this.left = this.right = null;
			}

			@Override
			public String toString() {
				return "[" + start + ", " + end + "] = [" + maxTrue + ", " + maxFalse + ", " + minTrue + ", " + minFalse
						+ ", " + flip + "]";
			}
		}

		SegmentTreeNode root;

		public RangeIncrementRangeQueryTree(boolean[] nums) {
			build(nums);
		}

		public RangeIncrementRangeQueryTree(int n) {
			build(new boolean[n]);
		}

		private void build(boolean[] nums) {
			if (nums.length > 0) {
				root = build(0, nums.length - 1, nums);
			}
		}

		public int[] query(int start, int end) {
			return query(root, start, end);
		}

		public void flip(int start, int end) {
			flip(root, start, end);
		}

		private SegmentTreeNode build(int start, int end, boolean[] nums) {
			SegmentTreeNode r = new SegmentTreeNode(start, end);
			if (start == end) {
				if (nums[start]) {
					r.minTrue = r.maxTrue = start;
				} else {
					r.minFalse = r.maxFalse = start;
				}
			} else {
				r.left = build(start, start + (end - start) / 2, nums);
				r.right = build(start + (end - start) / 2 + 1, end, nums);
				r.maxTrue = Math.max(r.left.maxTrue, r.right.maxTrue);
				r.maxFalse = Math.max(r.left.maxFalse, r.right.maxFalse);
				r.minTrue = Math.min(r.left.minTrue, r.right.minTrue);
				r.minFalse = Math.min(r.left.minFalse, r.right.minFalse);
			}
			return r;
		}

		private int[] query(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
			} else if (start > r.end || end < r.start) {
				return new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
			} else if (start <= r.start && r.end <= end) {
				int[] ret = new int[] { r.maxTrue, r.maxFalse, r.minTrue, r.minFalse };
				// System.out.println(r.start + ", " + r.end + "= " + Arrays.toString(ret));
				return ret;
			} else {
				push(r);
				int[] leftSum = query(r.left, start, end);
				int[] rightSum = query(r.right, start, end);
				int[] ret = new int[] { Math.max(leftSum[0], rightSum[0]), Math.max(leftSum[1], rightSum[1]),
						Math.min(leftSum[2], rightSum[2]), Math.min(leftSum[3], rightSum[3]) };
				// System.out.println(r.start + ", " + r.end + "= " + Arrays.toString(ret));
				return ret;
			}
		}

		private void push(SegmentTreeNode r) {
			if (r.flip) {
				if (r.left != null) {
					swap(r.left);
					r.left.flip = !r.left.flip;
				}
				if (r.right != null) {
					swap(r.right);
					r.right.flip = !r.right.flip;
				}
			}
			r.flip = false;
		}

		private void flip(SegmentTreeNode r, int start, int end) {
			if (r != null && start <= r.end && r.start <= end) {
				if (start <= r.start && r.end <= end) {
					swap(r);
					r.flip = !r.flip;
					// System.out.println(r);
				} else {
					push(r);
					flip(r.left, start, end);
					flip(r.right, start, end);
					r.maxTrue = Math.max(r.left.maxTrue, r.right.maxTrue);
					r.maxFalse = Math.max(r.left.maxFalse, r.right.maxFalse);
					r.minTrue = Math.min(r.left.minTrue, r.right.minTrue);
					r.minFalse = Math.min(r.left.minFalse, r.right.minFalse);
					// System.out.println(r);
				}
			}
		}

		private void swap(SegmentTreeNode r) {
			int t = r.maxFalse;
			r.maxFalse = r.maxTrue;
			r.maxTrue = t;
			t = r.minFalse;
			r.minFalse = r.minTrue;
			r.minTrue = t;
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
		return (a * b) % mod;
	}

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
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
