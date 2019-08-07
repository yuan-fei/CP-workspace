package r1111;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskC {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int k = in.nextInt();
		int A = in.nextInt();
		int B = in.nextInt();
		int[] a = getIntArr(in, k);
		long r = solve(n, k, A, B, a);
		System.out.println(r);
		in.close();
	}

	static RangeSumQueryTree s;

	private static long solve(int n, int k, int A, int B, int[] a) {
		s = new RangeSumQueryTree(1 << n);
		for (int i = 0; i < a.length; i++) {
			s.increase(a[i] - 1, 1);
		}
		return solve(n, k, A, B, a, 0, (1 << n) - 1);
	}

	private static long solve(int n, int k, int A, int B, int[] a, int start, int end) {
		long c = s.query(start, end);
		long m = 0;
		if (c == 0) {
			m = A;
		} else {
			m = B * (end - start + 1) * c;
		}
		if (start == end || c == 0) {
			return m;
		}
		long l = solve(n, k, A, B, a, start, start + (end - start) / 2);
		long r = solve(n, k, A, B, a, start + (end - start) / 2 + 1, end);

		return Math.min(m, l + r);
	}

	static class RangeSumQueryTree {
		class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			public long value;

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

		public RangeSumQueryTree(int n) {
			build(n);
		}

		public void build(int n) {
			this.n = n;
			root = build(0, n - 1);
		}

		public Long query(int start, int end) {
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
				r.value = r.left.value + r.right.value;
			}
			return r;
		}

		private long query(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return 0;
			} else if (start > r.end || end < r.start) {
				return 0;
			} else if (start <= r.start && end >= r.end) {
				return r.value;
			} else {
				long leftSum = query(r.left, start, end);
				long rightSum = query(r.right, start, end);
				return leftSum + rightSum;
			}
		}

		private void increase(SegmentTreeNode r, int index, int value) {
			if (r != null && index >= r.start && index <= r.end) {
				if (r.start == index && r.end == index) {
					r.value += value;
				} else {
					if (r.left != null) {
						increase(r.left, index, value);
					}
					if (r.right != null) {
						increase(r.right, index, value);
					}
					r.value = r.left.value + r.right.value;
				}
			}
		}

	}

	int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
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
