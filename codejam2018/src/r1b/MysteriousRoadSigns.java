package r1b;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class MysteriousRoadSigns {
	public static void main(String[] args) {
		solve();
		// test();

		// int[][] s = new int[][] { new int[] { 1, 511278, 574176 }, new int[] { 1,
		// 892255, 203611 },
		// new int[] { 1, 529187, 785061 }, new int[] { 1, 611439, 785061 }, new int[] {
		// 1, 668252, 195914 } };
		// s = new int[][] { new int[] { 1, 866374, 823610 }, new int[] { 1, 229435,
		// 220374 },
		// new int[] { 1, 231285, 220374 }, new int[] { 1, 81551, 819183 } };
		// System.out.println(Arrays.toString(solve(s.length, s)));
		// System.out.println(Arrays.toString(solveLargeRec(s)));
	}

	private static void test() {
		for (int i = 0; i < 100000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int l = r.nextInt(300) + 1;
		int[][] s = new int[l][3];
		for (int i = 0; i < s.length; i++) {
			s[i] = new int[] { 1, r.nextInt(1000000), r.nextInt(1000000) };
		}
		int[] r1 = solve(l, s);
		int[] r2 = solveLargeRec(s);
		if (!Arrays.equals(r1, r2)) {
			System.out.println(Arrays.deepToString(s));
		}
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[][] ss = getIntArr(in, n, 3);
			int[] r = solveLargeRec(ss);
			System.out.println("Case #" + i + ": " + r[0] + " " + r[1]);
		}
		in.close();
	}

	private static int[] solveLargeRec(int[][] ss) {
		int[][] s = new int[ss.length][2];
		for (int j = 0; j < s.length; j++) {
			s[j][0] = ss[j][0] + ss[j][1];
			s[j][1] = ss[j][0] - ss[j][2];
		}
		return solveLargeRec(0, s.length - 1, s);
	}

	private static int[] solveLargeRec(int left, int right, int[][] s) {
		if (left > right) {
			return new int[] { 0, 0 };
		}
		int mid = (left + right) / 2;
		int[] r = getLongestSet(left, right, mid, s);
		int[] lr = solveLargeRec(left, mid - 1, s);
		int[] rr = solveLargeRec(mid + 1, right, s);
		if (lr[0] > r[0]) {
			r = lr;
		} else if (lr[0] == r[0]) {
			r[1] += lr[1];
		}

		if (rr[0] > r[0]) {
			r = rr;
		} else if (rr[0] == r[0]) {
			r[1] += rr[1];
		}
		return r;
	}

	private static int[] getLongestSet(int left, int right, int mid, int[][] s) {
		int[] direction = new int[] { 0, 1 };
		int[] offset = new int[] { -1, 1 };
		List<int[]> pairs = new ArrayList<>();
		int max = 0;
		int maxGroup = 0;
		for (int i = 0; i < direction.length; i++) {
			for (int j = 0; j < offset.length; j++) {
				int[] pair = getPair(left, right, mid, offset[j], direction[i], s);
				int k = 0;
				for (; k < pairs.size(); k++) {
					if (pair[0] == pairs.get(k)[0] && pair[1] == pairs.get(k)[1]) {
						break;
					}
				}
				if (k == pairs.size()) {
					pairs.add(pair);
				}
			}
		}
		Set<Integer> startSet = new HashSet<>();
		for (int[] pair : pairs) {
			int start = getMax(left, right, mid, offset[0], pair, s);
			int l = start + getMax(left, right, mid, offset[1], pair, s) - 1;
			if (l > max) {
				max = l;
				maxGroup = 1;
				startSet.clear();
				startSet.add(start);
			} else if (l == max) {
				if (!startSet.contains(start)) {
					maxGroup++;
					startSet.add(start);
				}
			}
		}
		return new int[] { max, maxGroup };
	}

	private static int[] getPair(int left, int right, int mid, int offset, int direction, int[][] s) {
		int[] ret = Arrays.copyOf(s[mid], 2);
		for (int i = mid; i <= right && i >= left; i += offset) {
			if (s[mid][direction] != s[i][direction]) {
				ret[1 - direction] = s[i][1 - direction];
				break;
			}
		}
		return ret;
	}

	private static int getMax(int left, int right, int mid, int offset, int[] limit, int[][] s) {
		int i = mid;
		for (; i <= right && i >= left; i += offset) {
			if (s[i][0] != limit[0] && s[i][1] != limit[1]) {
				break;
			}
		}
		return Math.abs(i - mid);
	}

	private static int[] solveLarge(int l, int[][] s) {
		int max = 0;
		int maxGroup = 0;
		List<Candidate> candidates = new ArrayList<Candidate>();
		int start = 0;
		int i = start;
		while (i < s.length) {
			int n = s[i][0] + s[i][1];
			int m = s[i][0] - s[i][2];
			Candidate lastRemoved = null;
			if (candidates.isEmpty()) {
				candidates.add(new Candidate(new int[] { n, 0 }, new boolean[] { true, false }, i));
				candidates.add(new Candidate(new int[] { 0, m }, new boolean[] { false, true }, i));
			} else {
				for (int j = candidates.size() - 1; j >= 0; j--) {
					Candidate c = candidates.get(j);
					if (!c.merge(new int[] { n, m }, i)) {
						lastRemoved = candidates.remove(j);
					}
				}
			}

			if (candidates.isEmpty()) {
				Candidate newStart = lastRemoved.getLatestFirstOccur();
				start = newStart.lastOccur[0];
				candidates.add(newStart);
			} else {
				if (i - start + 1 >= max) {
					if (i - start + 1 > max) {
						maxGroup = 1;
					} else {
						maxGroup++;
					}
					max = i - start + 1;
				}
				i++;
			}

		}
		return new int[] { max, maxGroup };
	}

	private static int[] solve(int l, int[][] s) {
		int max = 0;
		int maxGroup = 0;
		for (int i = 0; i < s.length; i++) {
			int curMax = getMax(i, s);
			if (curMax >= max) {
				if (curMax > max) {
					maxGroup = 1;
				} else {
					maxGroup++;
				}
				max = curMax;
			}
		}

		return new int[] { max, maxGroup };
	}

	static int getMax(int start, int[][] s) {
		List<Candidate> candidates = new ArrayList<Candidate>();
		int i = start;
		for (; i < s.length; i++) {
			int n = s[i][0] + s[i][1];
			int m = s[i][0] - s[i][2];
			if (candidates.isEmpty()) {
				candidates.add(new Candidate(new int[] { n, 0 }, new boolean[] { true, false }, i));
				candidates.add(new Candidate(new int[] { 0, m }, new boolean[] { false, true }, i));
			} else {
				for (int j = candidates.size() - 1; j >= 0; j--) {
					Candidate c = candidates.get(j);
					if (!c.merge(new int[] { n, m }, i)) {
						candidates.remove(j);
					}
				}
			}
			if (candidates.isEmpty()) {
				break;
			}
		}
		return i - start;
	}

	static class Candidate {
		int[] limit;
		boolean[] fixed;
		int[] lastOccur;

		public Candidate(int[] limit, boolean[] fixed, int i) {
			this.limit = limit;
			this.lastOccur = new int[] { i, i };
			this.fixed = fixed;
		}

		public Candidate getLatestFirstOccur() {
			if (lastOccur[0] > lastOccur[1]) {
				return new Candidate(new int[] { limit[0], 0 }, new boolean[] { true, false }, lastOccur[1] + 1);
			} else {
				return new Candidate(new int[] { 0, limit[1] }, new boolean[] { false, true }, lastOccur[0] + 1);
			}
		}

		public boolean merge(int[] limit, int cur) {
			boolean b1 = merge(limit, 0, cur);
			boolean b2 = merge(limit, 1, cur);
			return b1 || b2;
		}

		private boolean merge(int[] limit, int i, int cur) {
			if (fixed[i]) {
				if (limit[i] == this.limit[i]) {
					// if (this.fixed[1 - i] == false) {
					// if (this.limit[1 - i] != limit[1 - i]) {
					// this.limit[1 - i] = limit[1 - i];
					// firstOccur[1 - i] = cur;
					//
					// }
					// lastOccur[1 - i] = cur;
					// }
					if (fixed[1 - i] && this.limit[1 - i] != limit[1 - i]) {
						lastOccur[i] = cur;
					}

					return true;
				} else {
					return false;
				}
			} else {
				if (this.limit[1 - i] != limit[1 - i]) {
					fixed[i] = true;
					lastOccur[i] = cur;
					if (this.limit[i] == limit[i]) {
						lastOccur[1 - i]--;
					}

				} else {
					if (this.limit[i] != limit[i]) {
						lastOccur[1 - i] = cur;
					}

				}
				// if (this.limit[i] != limit[i]) {
				// firstOccur[i] = cur;
				// }
				// lastOccur[i] = cur;
				this.limit[i] = limit[i];
				return true;
			}
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
}
