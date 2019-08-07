package y2019.qualification;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class DatBae {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = getCaseNo(in);
		for (int i = 0; i < n; i++) {
			solveLarge(in);
		}

		in.close();
	}

	private static void solveLarge(Scanner in) {
		int[] p = getParameter(in);
		int n = p[0];
		int b = p[1];
		int f = p[2];
		int i = 0;
		Set<Integer> bad = new TreeSet<>();
		Queue<Interval> q = new LinkedList<>();
		char[] trial = new char[n];

		int offset = 1;
		for (int j = 0; j < n; j += b) {
			Arrays.fill(trial, j, Math.min(j + b, n), (char) ('0' + offset));
			offset = 1 - offset;
		}
		String ans = getAnswer(in, new String(trial));
		int curBlock = 0;
		int curOffset = 1;
		int curStart = 0;
		for (int j = 0; j < n - b; j++) {
			if (ans.charAt(j) - '0' == curOffset) {
				if (j - curStart >= Math.min((curBlock + 1) * b, n) - curBlock * b) {
					// miss a block
					for (int k = (curBlock + 1) * b; k < Math.min((curBlock + 2) * b, n); k++) {
						bad.add(k);
					}
					curBlock += 2;
					curStart = j;
				}
			} else {
				if (j - curStart == 0) {
					for (int k = curBlock * b; k < Math.min((curBlock + 1) * b, n); k++) {
						bad.add(k);
					}
				} else if (j - curStart < Math.min((curBlock + 1) * b, n) - curBlock * b) {
					q.offer(new Interval(new int[] { curBlock * b, Math.min((curBlock + 1) * b, n) - 1 },
							new int[] { curStart, j - 1 }));
				}
				curOffset = 1 - curOffset;
				curBlock += 1;
				curStart = j;
			}
		}
		if (curBlock * b < n) {
			if (n - b - curStart < Math.min((curBlock + 1) * b, n) - curBlock * b) {
				q.offer(new Interval(new int[] { curBlock * b, Math.min((curBlock + 1) * b, n) - 1 },
						new int[] { curStart, n - b - 1 }));
			}
			if ((curBlock + 1) * b < n) {
				for (int k = (curBlock + 1) * b; k < Math.min((curBlock + 2) * b, n); k++) {
					bad.add(k);
				}
			}
		}
		while (!q.isEmpty() && bad.size() < b && i < f) {
			Arrays.fill(trial, '0');
			for (Interval itv : q) {
				int mid = (itv.all[0] + itv.all[1]) / 2;
				for (int j = itv.all[0]; j <= mid; j++) {
					trial[j] = '1';
				}
			}
			ans = getAnswer(in, new String(trial));
			i++;
			int cnt = q.size();
			for (int j = 0; j < cnt; j++) {
				Interval itv = q.poll();
				int mid = (itv.all[0] + itv.all[1]) / 2;
				int bound = itv.good[0];
				for (; bound <= itv.good[1]; bound++) {
					if (ans.charAt(bound) == '0') {
						break;
					}
				}
				if (bound == itv.good[0]) {
					for (int k = itv.all[0]; k <= mid; k++) {
						bad.add(k);
					}
					if (itv.all[1] - mid - 1 > itv.good[1] - itv.good[0]) {
						q.offer(new Interval(new int[] { mid + 1, itv.all[1] }, itv.good));
					}
				} else if (bound > itv.good[1]) {
					for (int k = mid + 1; k <= itv.all[1]; k++) {
						bad.add(k);
					}
					if (mid - itv.all[0] > itv.good[1] - itv.good[0]) {
						q.offer(new Interval(new int[] { itv.all[0], mid }, itv.good));
					}
				} else {
					if (mid - itv.all[0] > bound - 1 - itv.good[0]) {
						q.offer(new Interval(new int[] { itv.all[0], mid }, new int[] { itv.good[0], bound - 1 }));
					}
					if (itv.all[1] - mid - 1 > itv.good[1] - bound) {
						q.offer(new Interval(new int[] { mid + 1, itv.all[1] }, new int[] { bound, itv.good[1] }));
					}
				}
			}
		}

		int r = getAnswer(in, bad);

	}

	private static void solve(Scanner in) {
		int[] p = getParameter(in);
		int n = p[0];
		int b = p[1];
		int f = p[2];
		int i = 0;
		Set<Integer> bad = new TreeSet<>();
		Queue<Interval> q = new LinkedList<>();
		q.add(new Interval(new int[] { 0, n - 1 }, new int[] { 0, n - b - 1 }));
		char[] trial = new char[n];
		while (!q.isEmpty() && bad.size() < b && i < f) {

			Arrays.fill(trial, '0');
			for (Interval itv : q) {
				int mid = (itv.all[0] + itv.all[1]) / 2;
				for (int j = itv.all[0]; j <= mid; j++) {
					trial[j] = '1';
				}
			}
			String ans = getAnswer(in, new String(trial));
			i++;
			int cnt = q.size();
			for (int j = 0; j < cnt; j++) {
				Interval itv = q.poll();
				int mid = (itv.all[0] + itv.all[1]) / 2;
				int bound = itv.good[0];
				for (; bound <= itv.good[1]; bound++) {
					if (ans.charAt(bound) == '0') {
						break;
					}
				}
				if (bound == itv.good[0]) {
					for (int k = itv.all[0]; k <= mid; k++) {
						bad.add(k);
					}
					if (itv.all[1] - mid - 1 > itv.good[1] - itv.good[0]) {
						q.offer(new Interval(new int[] { mid + 1, itv.all[1] }, itv.good));
					}
				} else if (bound > itv.good[1]) {
					for (int k = mid + 1; k <= itv.all[1]; k++) {
						bad.add(k);
					}
					if (mid - itv.all[0] > itv.good[1] - itv.good[0]) {
						q.offer(new Interval(new int[] { itv.all[0], mid }, itv.good));
					}
				} else {
					if (mid - itv.all[0] > bound - 1 - itv.good[0]) {
						q.offer(new Interval(new int[] { itv.all[0], mid }, new int[] { itv.good[0], bound - 1 }));
					}
					if (itv.all[1] - mid - 1 > itv.good[1] - bound) {
						q.offer(new Interval(new int[] { mid + 1, itv.all[1] }, new int[] { bound, itv.good[1] }));
					}
				}
			}
		}

		int r = getAnswer(in, bad);

	}

	static class Interval {
		int[] all;
		int[] good;

		public Interval(int[] all, int[] good) {
			this.all = all;
			this.good = good;
		}

	}

	private static int getAnswer(Scanner in, Set<Integer> bad) {
		StringBuilder sb = new StringBuilder();
		for (Integer i : bad) {
			sb.append(i + " ");
		}
		System.out.println(sb.toString());
		System.out.flush();
		return in.nextInt();
	}

	private static String getAnswer(Scanner in, String i) {
		System.out.println(i);
		System.out.flush();
		return in.next();
	}

	private static int[] getParameter(Scanner in) {
		int[] p = new int[3];
		for (int i = 0; i < p.length; i++) {
			p[i] = in.nextInt();
		}
		return p;
	}

	private static int getCaseNo(Scanner in) {
		return in.nextInt();
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
