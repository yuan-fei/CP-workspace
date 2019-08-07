import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ChefandKSegments {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int k = in.nextInt();
			Interval[] itv = new Interval[n];
			for (int j = 0; j < n; j++) {
				itv[j] = new Interval(in.nextInt(), in.nextInt());
			}
			int l = solve(itv, k);
			System.out.println(l);
		}
		in.close();
	}

	private static int solve(Interval[] itv, int k) {
		Arrays.sort(itv, Comparator.comparing(i -> i.left));
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		int l = 0;
		for (int i = 0; i < itv.length; i++) {
			if (q.size() < k || q.peek() < itv[i].right) {
				q.poll();
				q.offer(itv[i].right);
			}
			if (i >= k - 1) {
				l = Math.max(l, q.peek() - itv[i].left);
			}
		}
		return l;
	}

	static class Interval {
		int left, right;

		public Interval(int left, int right) {
			super();
			this.left = left;
			this.right = right;
		}

	}
}
