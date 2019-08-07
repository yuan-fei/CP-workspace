package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MaxAndElectricalPanel {
	static int n = 0;
	static int c = 0;
	static int t = 0;

	public static void main(String[] args) {
		solve();
		// test(150000, 150, 149995);
		// System.out.println(r);
		// int max = 0;
		// int ci = -1;
		// int mc = 0;
		// for (int i = 1; i <= 150000; i++) {
		// for (int j = 51; j <= 150; j++) {
		// int t = test(150000, j, i);
		// if (t > max) {
		// max = t;
		// ci = i;
		// mc = j;
		// }
		// if (t > 1000 || !r) {
		// System.out.println(i + ", " + j);
		// }
		// }
		//
		// }
		// System.out.println(ci + ", " + max + ", " + mc);
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		n = in.nextInt();
		c = in.nextInt();
		if (c <= 50) {
			binarySearch(in, n, c);
		} else {
			solve(in, n, c);
		}
		in.close();
	}

	private static int test(int nn, int cc, int target) {
		n = nn;
		c = cc;
		t = target;
		c1 = 0;
		c2 = 0;
		r = false;
		Scanner in = null;
		if (c <= 50) {
			binarySearch(in, n, c);
		} else {
			solve(in, n, c);
		}
		System.out.println(c1 + ", " + c2 + ", " + (c1 + c2 * c));
		return c1 + c2 * c;
	}

	private static void solve(Scanner in, int n, int c) {
		int step = Math.max(n, c) / c;
		int start = 1;
		int prev = start;
		int cur = 1;
		int end = n;
		while (start + 1 < end) {
			cur = Math.min(prev + step, end);
			if (getAnswer(in, 1, cur) == 0) {
				prev = cur;
			} else {
				getAnswer(in, 2, 0);
				end = cur;
				start = prev;
				step = Math.max((end - prev + 1), c) / c;
			}
		}

		if (getAnswer(in, 1, prev) == 1) {
			getAnswer(in, 3, prev);
		} else {
			getAnswer(in, 3, cur);
		}
	}

	private static void binarySearch(Scanner in, int n, int c3) {
		int start = 1;
		int end = n;
		while (start + 1 < end) {
			int mid = (start + end) / 2;
			int r = getAnswer(in, 1, mid);
			if (r == 1) {
				end = mid;
				getAnswer(in, 2, 0);
			} else {
				start = mid;
			}
		}
		if (getAnswer(in, 1, start) == 1) {
			getAnswer(in, 3, start);
		} else {
			getAnswer(in, 3, end);
		}
	}

	static int c1 = 0;
	static int c2 = 0;
	static boolean r = false;

	static int getAnswer(Scanner in, int type, int m) {
		switch (type) {
		case 1:
			System.out.println(1 + " " + m);
			// c1++;
			// return m >= t ? 1 : 0;
			return in.nextInt();
		case 2:
			System.out.println(2);
			// c2++;
			break;
		case 3:
			System.out.println(3 + " " + m);
			r = (m == t);
			break;
		}
		return 0;
	}

}
