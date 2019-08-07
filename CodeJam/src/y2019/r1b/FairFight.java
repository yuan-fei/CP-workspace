package y2019.r1b;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FairFight {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int k = in.nextInt();
			int[] c = getIntArr(in, n);
			int[] d = getIntArr(in, n);
			long r = solveLarge(n, k, c, d);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static long solve(int n, int k, int[] c, int[] d) {
		int cnt = 0;
		for (int i = 0; i < n; i++) {
			int maxC = c[i];
			int maxD = d[i];
			for (int j = i; j < n; j++) {
				maxC = Math.max(maxC, c[j]);
				maxD = Math.max(maxD, d[j]);
				if (Math.abs(maxC - maxD) <= k) {
					cnt++;
				}
			}
		}
		return cnt;
	}

	static RangeMaxQueryTree crt;
	static RangeMaxQueryTree drt;

	private static long solveLarge(int n, int k, int[] c, int[] d) {
		crt = new RangeMaxQueryTree(c);
		drt = new RangeMaxQueryTree(d);
		long r = 0;
		for (int i = 0; i < c.length; i++) {
			if (c[i] - k <= d[i] && d[i] <= c[i] + k) {
				int lb, rb;
				int b1 = getLBound(0, i, crt);
				int b2 = getLBound(0, i, drt, c[i] + k);
				int b3 = getLBound(0, i, drt, c[i] - k - 1);
				lb = Math.max(b1, b2) - Math.max(b1, b3);
				b1 = getRBound(i, c.length - 1, crt);
				b2 = getRBound(i, d.length - 1, drt, c[i] + k);
				b3 = getRBound(i, d.length - 1, drt, c[i] - k - 1);
				rb = Math.min(b1, b2) - Math.min(b1, b3);
				r += (i - lb + 1) * (rb - i + 1);
			}
		}
		return r;
	}

	private static int getLBound(int start, int end, RangeMaxQueryTree rt) {
		int pivot = end;
		while (start + 1 < end) {
			int mid = (start + end) / 2;
			if (rt.query(mid, pivot)[0] == pivot) {
				end = mid;
			} else {
				start = mid;
			}
		}
		if (rt.query(start, pivot)[0] == pivot) {
			return start;
		} else {
			return end;
		}
	}

	private static int getRBound(int start, int end, RangeMaxQueryTree rt) {
		int pivot = start;
		while (start + 1 < end) {
			int mid = (start + end) / 2;
			if (rt.query(pivot, mid)[0] == pivot) {
				start = mid;
			} else {
				end = mid;
			}
		}
		if (rt.query(end, pivot)[0] == pivot) {
			return end;
		} else {
			return start;
		}
	}

	private static int getLBound(int start, int end, RangeMaxQueryTree rt, int limit) {
		int pivot = end;
		while (start + 1 < end) {
			int mid = (start + end) / 2;
			if (rt.query(mid, pivot)[1] <= limit) {
				end = mid;
			} else {
				start = mid;
			}
		}
		if (rt.query(start, pivot)[1] <= limit) {
			return start;
		} else if (rt.query(end, pivot)[1] <= limit) {
			return end;
		} else {
			return end + 1;
		}

	}

	private static int getRBound(int start, int end, RangeMaxQueryTree rt, int limit) {
		int pivot = start;
		while (start + 1 < end) {
			int mid = (start + end) / 2;
			if (rt.query(pivot, end)[1] <= limit) {
				start = mid;
			} else {
				end = mid;
			}
		}
		if (rt.query(end, pivot)[1] <= limit) {
			return end;
		} else if (rt.query(start, pivot)[1] <= limit) {
			return start;
		} else {
			return start - 1;
		}
	}

	/** Process RMQ */
	static class RangeMaxQueryTree {
		class SegmentTreeNode {
			public int start, end;
			public SegmentTreeNode left, right;
			public int value;
			public int idx;

			public SegmentTreeNode(int start, int end) {
				this.start = start;
				this.end = end;
				this.left = this.right = null;
			}

			@Override
			public String toString() {
				return "[" + start + ", " + end + "] = " + value;
			}
		}

		SegmentTreeNode root;

		public RangeMaxQueryTree(int[] nums) {
			build(nums);
		}

		public void build(int[] nums) {
			if (nums.length > 0) {
				root = build(0, nums.length - 1, nums);
			}
		}

		public int[] query(int start, int end) {
			return query(root, start, end);
		}

		public void update(int index, int value) {
			update(root, index, value);
		}

		private SegmentTreeNode build(int start, int end, int[] nums) {
			SegmentTreeNode r = new SegmentTreeNode(start, end);
			if (start == end) {
				r.value = nums[start];
			} else {
				r.left = build(start, start + (end - start) / 2, nums);
				r.right = build(start + (end - start) / 2 + 1, end, nums);
				r.value = Math.max(r.left.value, r.right.value);
			}
			return r;
		}

		private int[] query(SegmentTreeNode r, int start, int end) {
			if (r == null) {
				return new int[] { -1, Integer.MAX_VALUE };
			} else if (start > r.end || end < r.start) {
				return new int[] { -1, Integer.MAX_VALUE };
			} else if (start <= r.start && end >= r.end) {
				return new int[] { r.idx, r.value };
			} else {
				int[] leftR = query(r.left, start, end);
				int[] rightR = query(r.right, start, end);
				if (leftR[1] > rightR[1]) {
					return leftR;
				} else {
					return rightR;
				}
			}
		}

		private void update(SegmentTreeNode r, int index, int value) {
			if (r != null && index >= r.start && index <= r.end) {
				if (r.start == index && r.end == index) {
					r.value = value;
					r.idx = index;
				} else {
					if (r.left != null) {
						update(r.left, index, value);
					}
					if (r.right != null) {
						update(r.right, index, value);
					}
					if (r.left.value > r.right.value) {
						r.value = r.left.value;
						r.idx = r.left.idx;
					} else {
						r.value = r.right.value;
						r.idx = r.left.idx;
					}
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

	static class FastScanner implements Closeable {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		@Override
		public void close() throws IOException {
			br.close();
		}
	}

}
