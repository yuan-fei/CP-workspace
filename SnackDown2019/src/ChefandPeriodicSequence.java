import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChefandPeriodicSequence {

	public static void main(String[] args) {
		process();
		// System.out.println(solve(5, new int[] { 1, -1, -1, 4, 1 })); // 4
		// System.out.println(solve(4, new int[] { -1, -1, -1, -1 })); // -1
		// System.out.println(solve(4, new int[] { 4, 6, 7, -1 })); // -2
		// System.out.println(solve(4, new int[] { 1, -1, -1, 2 })); // 2
		// System.out.println(solve(4, new int[] { 1, -1, -1, -1, 2 }));// 3
		// System.out.println(solve(4, new int[] { 1, -1, -1, -1, 1 }));// 4
		// System.out.println(solve(4, new int[] { 8, -1, -1, -1, 8 }));// -2
		// System.out.println(solve(4, new int[] { 2, -1, -1, -1, 2 }));// 4
		// System.out.println(solve(4, new int[] { 2, -1, -1, -1, 2, -1, 1, -1 }));// -2
		// System.out.println(solve(4, new int[] { 5, -1, -1, -1, 2, 3, -1, -1 }));// 7
		// System.out.println(solve(4, new int[] { 5, -1, 2, -1, 4, -1, 6 }));// -2
		// System.out.println(solve(4, new int[] { 4, -1, 2, -1, 4, -1, 2 }));// 4
		// System.out.println(solve(4, new int[] { 4, 5 }));// -1
		// System.out.println(solve(4, new int[] { 4, -1, 5 }));// -2
		// System.out.println(solve(4, new int[] { 4, -1, 6 }));// -1
	}

	private static void process() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] cards = new int[n];
			for (int j = 0; j < n; j++) {
				cards[j] = in.nextInt();
			}
			int r = solve(n, cards);
			String str = "";
			if (r > 0) {
				str = "" + r;
			} else if (r == -1) {
				str = "inf";
			} else {
				str = "impossible";
			}
			System.out.println(str);
		}
		in.close();
	}

	private static int solve(int n, int[] a) {
		int start = -1;
		Condition c = new Condition(-1, -1);
		for (int i = 0; i < a.length; i++) {
			if (a[i] != -1) {
				if (start != -1) {
					Condition ci = processRange(a, start, i);
					c = c.intersect(ci);
					if (c == null || !c.isValid()) {
						return -2;
					}
				}
				start = i;
			}
		}
		return c.maxPossibleValue();
	}

	private static Condition processRange(int[] n, int start, int end) {
		int a = n[start];
		int b = n[end];
		int d = end - start;
		if (b > a) {
			if (b - a > d) {
				return null;
			} else if (b - a < d) {
				return new Condition(d - b + a, b);
			} else {
				return new Condition(-1, b);
			}
		} else if (b <= a) {
			if (d < b) {
				return null;
			} else {
				return new Condition(d - b + a, a);
			}
		}
		return null;
	}

	static class Condition {
		int nP;
		int min;

		public Condition(int i, int min) {
			super();
			this.nP = i;
			this.min = min;
		}

		@Override
		public String toString() {
			return nP + ", " + min;
		}

		public boolean isValid() {
			if (nP == -1) {
				return true;
			}
			return nP >= min;
		}

		public Condition intersect(Condition c) {
			if (c == null) {
				return null;
			} else if (nP == -1 || c.nP == -1) {
				return new Condition(Math.max(nP, c.nP), Math.max(min, c.min));
			} else {
				return new Condition(gcd(nP, c.nP), Math.max(min, c.min));
			}
		}

		private static int gcd(int a, int b) {
			if (b == 0) {
				return a;
			} else {
				return gcd(b, a % b);
			}
		}

		public int maxPossibleValue() {
			if (isValid()) {
				if (nP == -1) {
					return -1;
				}
				return nP;
			}
			return -2;
		}
	}
}
