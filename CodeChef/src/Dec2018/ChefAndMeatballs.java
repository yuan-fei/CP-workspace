package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChefAndMeatballs {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			solve(in, n);
		}
		in.close();
	}

	static int[] target;

	private static void test() {
		Scanner in = null;
		target = new int[] { 10, 20, 30, 40, 50, 60 };
		solveHelper(0);

		solve(in, target.length);
		System.out.println(cnt);
	}

	static int cnt = 0;

	private static void solveHelper(int i) {
		if (i == target.length) {
			solve(null, target.length);
			// cnt++;
		} else {
			for (int j = i; j < target.length; j++) {
				swap(target, i, j);
				solveHelper(i + 1);
				swap(target, i, j);
			}
		}
	}

	private static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	private static void solve(Scanner in, int n) {
		cnt = 0;
		int[] m = new int[] { 1, 2, 3, 4, 5 };
		int[] r = getAnswer(in, 1, m);
		int ext = r[0];
		int k = 6;
		while (k + 1 <= n) {
			for (int i = 0; i < 5; i++) {
				if (m[i] == r[0] || m[i] == r[1]) {
					m[i] = k;
					k++;
				}
			}
			r = getAnswer(in, 1, m);
		}
		if (k == n) {
			ext = n;
		}
		int max = getMaxOfSix(in, new int[] { m[0], m[1], m[2], m[3], m[4], ext });
		r = getAnswer(in, 2, new int[] { max });
		// System.out.println((cnt - 4) * 2 - n);
		// if (r[0] != 0) {
		//
		// System.out.println(Arrays.toString(target));
		// }
	}

	private static void solve0(Scanner in, int n) {
		int[] m = new int[] { 1, 2, 3, 4, 5, 6 };
		int max = getMaxOfSix(in, m);

		Map<Integer, Integer> map = new HashMap<>();
		int[] arr = new int[m.length];
		for (int i = 0; i < m.length; i++) {
			map.put(target[m[i] - 1], m[i]);
			arr[i] = target[m[i] - 1];
		}
		Arrays.sort(arr);
		if (!(map.get(arr[arr.length - 1]) == max)) {
			System.out.println(Arrays.toString(target));
			cnt++;
		}
		// int k = 7;
		// while (k <= n) {
		// m[3] = k;
		// int[] r = getAnswer(in, 1, m);
		// if (r[0] == m[4] || r[1] == m[4]) {
		// m[4] = k;
		// }
		// k++;
		// }
		// int[] r = getAnswer(in, 2, new int[] { m[4] });
		// System.out.println(r[0]);
	}

	private static int getMaxOfSix(Scanner in, int[] m) {
		int[] res = new int[5];
		int[] a = Arrays.copyOf(m, 5);
		int[] r0 = getAnswer(in, 1, a);
		int a6 = m[5];
		int cmp = -1;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != r0[0] && a[i] != r0[1]) {
				cmp = a[i];
				a[i] = a6;
				break;
			}
		}
		int[] r = getAnswer(in, 1, a);
		if (r[1] == r0[0]) {// x3
			res[4] = cmp;
			res[3] = r0[1];
			res[2] = r0[0];
		} else if (r[0] == r0[0] && r[1] == a6) {// 36
			res[4] = cmp;
			res[3] = r0[1];
			res[2] = a6;
			res[1] = r0[0];
		} else if (r[0] == r0[1]) {// 4x
			if (r[1] != a6) {
				res[4] = a6;
				res[3] = r[1];
				res[2] = r0[1];
				res[1] = r0[0];
			} else {// 46
				int x = 0;
				int y = 0;
				for (int i = 0; i < a.length; i++) {
					if (a[i] != r0[0] && a[i] != r0[1] && a[i] != a6) {
						if (x == 0) {
							x = a[i];
							a[i] = cmp;
						} else {
							y = a[i];
						}
					}
				}
				r = getAnswer(in, 1, a);
				if (r[1] == a6) {
					res[4] = y;
				} else {
					res[4] = x;
				}
				res[3] = a6;
				res[2] = r0[1];
				res[1] = r0[0];
			}
		} else if (r[0] == a6 && r[1] == r0[1]) {// 64
			int x = 0;
			int y = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] != r0[0] && a[i] != r0[1] && a[i] != a6) {
					if (x == 0) {
						x = a[i];
						a[i] = cmp;
					} else {
						y = a[i];
					}
				}
			}
			r = getAnswer(in, 1, a);
			if (r[0] == a6 && r[1] == r0[1]) {
				res[4] = y;
			} else {
				res[4] = x;
			}
			res[3] = r0[1];
			res[2] = a6;
			res[1] = r0[0];
		} else {// 34
			int x = 0;
			int y = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] != r0[0] && a[i] != r0[1] && a[i] != a6) {
					if (x == 0) {
						x = a[i];
						a[i] = cmp;
					} else {
						y = a[i];
					}
				}
			}
			r = getAnswer(in, 1, a);
			if (r[1] == a6) {// 46
				res[4] = cmp;
				res[3] = a6;
				res[2] = r0[1];
				res[1] = r0[0];
			} else if (r[1] == cmp) {// 45
				res[4] = a6;
				res[3] = cmp;
				res[2] = r0[1];
				res[1] = r0[0];
			} else if (r[0] == r0[0] && r[1] == r0[1]) {
				res[4] = y;
				res[3] = r0[1];
				res[2] = r0[0];
			} else {
				res[4] = x;
				res[3] = r0[1];
				res[2] = r0[0];
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < a6; j++) {
				boolean seen = false;
				for (int k = 0; k < 5; k++) {
					if (res[k] == j) {
						seen = true;
					}
				}
				if (!seen) {
					res[i] = j;
				}
			}
		}
		return res[4];
	}

	static int[] getAnswer(Scanner in, int type, int[] m) {

		System.out.println(((type == 1) ? "?" : "!") + " " + getStr(m));
		System.out.flush();

		if (type == 1) {
			return new int[] { in.nextInt(), in.nextInt() };
			// cnt++;
			// Map<Integer, Integer> map = new HashMap<>();
			// int[] arr = new int[m.length];
			// for (int i = 0; i < m.length; i++) {
			// map.put(target[m[i] - 1], m[i]);
			// arr[i] = target[m[i] - 1];
			// }
			// Arrays.sort(arr);
			// int[] r = new int[] { map.get(arr[2]), map.get(arr[3]) };
			// System.out.println(Arrays.toString(r));
			// return r;
		} else {
			// int max = 0;
			// int maxi = 0;
			// for (int i = 0; i < target.length; i++) {
			// if (target[i] > max) {
			// max = target[i];
			// maxi = i + 1;
			// }
			// }
			// return new int[] { m[0] == maxi ? 0 : -1 };
		}
		return new int[0];
	}

	static String getStr(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}
}
