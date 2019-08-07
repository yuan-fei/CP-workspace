package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class ColorblindFeast {
	public static void main(String[] args) {
		test();
	}

	static boolean test = false;

	private static void test() {
		test = true;
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			int k = r.nextInt(900000000) + 100000000;
			int n = r.nextInt(5000) + 1;
			int[][] q = new int[n][3];
			for (int j = 0; j < n - 1; j++) {
				int s = r.nextInt() % 4;
				if (s == 0) {
					q[j][0] = 1;
					q[j][1] = r.nextInt(1000) + 1;
					q[j][2] = r.nextInt(1000) + 1;
				} else if (s == 1) {
					q[j][0] = 2;
					q[j][1] = r.nextInt(1000) + 1;
					q[j][2] = r.nextInt(1000) + 1;
				} else {
					q[j][0] = 3;
					q[j][1] = r.nextInt(1000) + 1;
				}

			}
			q[n - 1][0] = 3;
			q[n - 1][1] = r.nextInt(1000000000) + 1;
			int[] res = solve(n, k, q);
			if (!Arrays.equals(solve1(n, k, q), res)) {
				System.out.println(n);
				System.out.println(k);
				System.out.println(Arrays.deepToString(q));
			}
		}
	}

	static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int q = in.nextInt();
		int k = in.nextInt();
		int[][] questions = new int[q][3];
		boolean cPositive = true;
		for (int i = 0; i < q; ++i) {
			questions[i][0] = in.nextInt();
			questions[i][1] = in.nextInt();
			if (questions[i][0] != 3) {
				questions[i][2] = in.nextInt();
				if (questions[i][2] < 0) {
					cPositive = false;
				}
			}
		}
		int[] ans;
		if (cPositive) {
			ans = solve(q, k, questions);
		} else {
			ans = solve1(q, k, questions);
		}
		for (int i = 0; i < ans.length; i++) {
			System.out.println(ans[i]);
			System.out.flush();
		}
		in.close();
	}

	private static int[] solve1(int q, int k, int[][] questions) {

		int cnt = 0;
		int max = 0;
		for (int i = 0; i < questions.length; i++) {
			if (questions[i][0] == 3) {
				cnt++;
			} else {
				max = Math.max(max, questions[i][1]);
			}
		}
		int[] ans = new int[cnt];
		cnt = 0;
		// RangeSumQueryTree rst = new RangeSumQueryTree(max + 1);
		Deque<int[]> dq = new ArrayDeque<int[]>();
		for (int i = 0; i < q; i++) {
			int c = questions[i][1];
			int d = 0;
			switch (questions[i][0]) {
			case 1:
				d = questions[i][2];
				dq.offerFirst(new int[] { c - 1, d });
				// rst.increase(c - 1, d);
				break;
			case 2:
				d = questions[i][2];
				dq.offerLast(new int[] { c - 1, d });
				// rst.increase(c - 1, d);
				break;
			case 3:
				int xor = 0;
				if (cnt > 0) {
					xor = ans[cnt - 1];
				}

				if (!test) {
					c ^= xor;
				}
				ans[cnt] = maxSum(dq, c - k - 1, c + k - 1);
				cnt++;
				break;
			}
		}
		return ans;
	}

	private static int[] solve(int q, int k, int[][] questions) {

		int cnt = 0;
		TreeSet<Integer> s = new TreeSet<>();
		for (int i = 0; i < questions.length; i++) {
			if (questions[i][0] == 3) {
				cnt++;
			} else {
				s.add(questions[i][1]);
			}
		}
		int[] allD = new int[s.size()];
		Map<Integer, Integer> index = new HashMap<>();
		int z = 0;
		for (int e : s) {
			allD[z] = e;
			index.put(e, z);
			z++;
		}
		int[] ans = new int[cnt];
		cnt = 0;

		RangeSumQueryTree rst = new RangeSumQueryTree(allD.length);
		for (int i = 0; i < q; i++) {
			int c = questions[i][1];
			int d = 0;
			switch (questions[i][0]) {
			case 1:
				d = questions[i][2];
				rst.increase(index.get(questions[i][1]), d);
				break;
			case 2:
				d = questions[i][2];
				rst.increase(index.get(questions[i][1]), d);
				break;
			case 3:
				int xor = 0;
				if (cnt > 0) {
					xor = ans[cnt - 1];
				}
				if (!test) {
					c ^= xor;
				}
				int start = Arrays.binarySearch(allD, c - k);
				if (start < 0) {
					start = -start - 1;
				}
				int end = Arrays.binarySearch(allD, c + k);
				if (end < 0) {
					end = -end - 2;
				}
				ans[cnt] = rst.query(start, end);
				cnt++;
				break;
			}
		}
		return ans;
	}

	private static int maxSum(Queue<int[]> q, int l, int r) {
		int last = 0;
		List<Integer> ll = new ArrayList<>();
		ll.add(last);
		for (int[] ci : q) {
			if (l <= ci[0] && ci[0] <= r) {
				last += ci[1];
				ll.add(last);
			}
		}
		int min = Integer.MAX_VALUE;
		int maxSum = Integer.MIN_VALUE;
		for (int i = 0; i < ll.size(); i++) {
			min = Math.min(min, ll.get(i));
			maxSum = Math.max(maxSum, ll.get(i) - min);
		}
		return Math.max(maxSum, 0);
	}

	static String getStr(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	public static class RangeSumQueryTree {
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

		public RangeSumQueryTree(int n) {
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
				r.value = r.left.value + r.right.value;
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
}
