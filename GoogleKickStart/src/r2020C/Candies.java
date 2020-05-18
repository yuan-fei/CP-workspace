package r2020C;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Candies {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int q = in.nextInt();
			int[] A = getIntArr(in, n);
			int[][] Q = new int[q][3];
			for (int j = 0; j < Q.length; j++) {
				Q[j][0] = in.next().equals("U") ? 1 : 0;
				Q[j][1] = in.nextInt();
				Q[j][2] = in.nextInt();
			}
			long ans = solve(n, q, A, Q);
			System.out.println("Case #" + i + ": " + ans);
		}
		in.close();

	}

	private static long solve(int n, int q, int[] A, int[][] Q) {
		PointIncrementRangeSumQueryTree s = new PointIncrementRangeSumQueryTree(A.length);
		for (int i = 0; i < A.length; i++) {
			s.update(i, A[i]);
		}
		long res = 0;
		for (int[] query : Q) {
			if (query[0] == 0) {
				int start = query[1] - 1;
				int end = query[2] - 1;
				long coef = start % 2 == 0 ? 1 : -1;
				long r1 = s.query0(start, end);
				long r2 = s.query1(start, end);
				res += coef * (r1 - start * r2);
			} else {
				int index = query[1] - 1;
				int value = query[2];
				s.update(index, value);
			}
		}
		return res;
	}

	static class PointIncrementRangeSumQueryTree {
		static class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			public long value1;
			public long value0;

			public SegmentTreeNode(int start, int end) {
				this.start = start;
				this.end = end;
			}

			@Override
			public String toString() {
				return "[" + start + ", " + end + "] = " + value1 + ", " + value0;
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

		public Long query1(int start, int end) {
			return query1(root, start, end);
		}

		public Long query0(int start, int end) {
			return query0(root, start, end);
		}

		public void update(int index, int value) {
			update(root, index, value);
		}

		private SegmentTreeNode build(int start, int end) {
			SegmentTreeNode r = new SegmentTreeNode(start, end);
			if (start < end) {
				r.left = build(start, start + (end - start) / 2);
				r.right = build(start + (end - start) / 2 + 1, end);
				int coef0 = ((start / 2 + 1) % 2 == 0) ? 1 : -1;
				int coef1 = (((end - start) / 2 + 1) % 2 == 0) ? 1 : -1;
				r.value1 = r.left.value1 + r.right.value1;
				r.value0 = r.left.value0 + r.right.value0;
			}
			return r;
		}

		private long query1(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return 0;
			} else if (start > r.end || end < r.start) {
				return 0;
			} else if (start <= r.start && end >= r.end) {
				return r.value1;
			} else {
				long leftSum = query1(r.left, start, end);
				long rightSum = query1(r.right, start, end);
				// int coef0 = (r.start % 2 == 0 ? 1 : -1);
				// int coef1 = (r.right.start % 2 == 0 ? 1 : -1);
				// return coef0 * leftSum + coef1 * rightSum;
				return leftSum + rightSum;
			}
		}

		private long query0(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return 0;
			} else if (start > r.end || end < r.start) {
				return 0;
			} else if (start <= r.start && end >= r.end) {
				return r.value0;
			} else {
				long leftSum = query0(r.left, start, end);
				long rightSum = query0(r.right, start, end);
				int coef0 = (r.start % 2 == 0 ? 1 : -1);
				int coef1 = (r.right.start % 2 == 0 ? 1 : -1);
				return leftSum + rightSum;
			}
		}

		private void update(SegmentTreeNode r, int index, int value) {
			if (r != null && index >= r.start && index <= r.end) {
				if (r.start == index && r.end == index) {
					int coef = (index % 2 == 0) ? 1 : -1;
					r.value1 = coef * value;
					r.value0 = coef * (index + 1) * value;
				} else {
					update(r.left, index, value);
					update(r.right, index, value);
					int coef0 = (r.start % 2 == 0 ? 1 : -1);
					int coef1 = (r.right.start % 2 == 0 ? 1 : -1);
					r.value1 = r.left.value1 + r.right.value1;
					r.value0 = r.left.value0 + r.right.value0;
				}
			}
		}

		public static void main1(String[] args) {
			int[] a = new int[] { 1, 0, 1, 2, 3, 4, 5 };
			PointIncrementRangeSumQueryTree s = new PointIncrementRangeSumQueryTree(a.length);
			for (int i = 0; i < a.length; i++) {
				s.update(i, a[i]);
			}
			s.print();
			System.out.println(s.query1(0, 6));
			System.out.println(s.query1(0, 2));
			System.out.println(s.query1(2, 6));
			System.out.println(s.query1(4, 6));

			System.out.println(s.query0(0, 1));
			System.out.println(s.query0(0, 6));
			System.out.println(s.query0(0, 2));
			System.out.println(s.query0(2, 6));
			System.out.println(s.query0(4, 6));
			s.update(0, -1);
			s.print();
			System.out.println(s.query1(0, 3));
			System.out.println(s.query0(0, 3));

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

	private static long mod = 1000000007;

	private static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	private static long mul(long a, long b) {
		return (a * b) % mod;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	private static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	private static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	private static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	private static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	private static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	private static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	private static char[][] getStringArr(Scanner in, int r, int c) {
		char[][] ret = new char[r][];
		for (int i = 0; i < r; i++) {
			ret[i] = in.next().toCharArray();
		}
		return ret;
	}

	private static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
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
