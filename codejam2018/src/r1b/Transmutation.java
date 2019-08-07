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
import java.util.stream.LongStream;

public class Transmutation {
	public static void main(String[] args) {
		solve();
		// test();
		// int[][] r = new int[][] { new int[] { 3, 2 }, new int[] { 4, 5 }, new int[] {
		// 4, 2 }, new int[] { 1, 4 },
		// new int[] { 4, 1 } };
		// long[] g = new long[] { 1, 0, 2, 6, 8 };
		// System.out.println(solve(r.length, r, g));
	}

	private static void test() {
		for (int i = 0; i < 100000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt(7) + 2;
		int[][] rr = new int[n][];
		int[] a = new int[n];
		int[] b = new int[n];
		long[] g = new long[n];
		for (int i = 0; i < g.length; i++) {
			a[i] = r.nextInt(n);
			b[i] = (r.nextInt(n - 1) + 1 + a[i]) % n;
			rr[i] = new int[] { a[i] + 1, b[i] + 1 };
			g[i] = r.nextInt(9);
		}
		long r1 = solve(n, rr, g);
		long r2 = new Benchmark().solve(n, a, b, g);
		if (r1 != r2) {
			System.out.println(r1 + "~" + r2 + ": " + n + ", " + Arrays.deepToString(rr) + ", " + Arrays.toString(g));
		}
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[][] r = getIntArr(in, n, 2);
			long[] g = getLongArr(in, n);
			long res = solve(n, r, g);
			System.out.println("Case #" + i + ": " + res);
		}
		in.close();
	}

	private static long solve(int n, int[][] r, long[] g) {
		long start = 0;
		long end = LongStream.of(g).sum();
		long[] gc;
		while (start + 1 < end) {
			long mid = (start + end) / 2;
			gc = Arrays.copyOf(g, g.length);
			gc[0] -= mid;
			if (feasible(n, r, gc, new HashSet<Integer>(), 0)) {
				start = mid;
			} else {
				end = mid;
			}
		}
		gc = Arrays.copyOf(g, g.length);
		gc[0] -= end;
		if (feasible(n, r, gc, new HashSet<Integer>(), 0)) {
			return end;
		} else {
			return start;
		}
	}

	private static boolean feasible(int n, int[][] r, long[] g, Set<Integer> seen, int d) {
		seen.add(d);
		if (g[d] >= 0) {
			seen.remove(d);
			return true;
		}
		int rd0 = r[d][0] - 1;
		int rd1 = r[d][1] - 1;
		if (seen.contains(rd0) || seen.contains(rd1)) {
			return false;
		} else {
			g[rd0] += g[d];
			g[rd1] += g[d];
			g[d] = 0;
			boolean res = feasible(n, r, g, seen, rd0) && feasible(n, r, g, seen, rd1);
			seen.remove(d);
			return res;
		}
	}

	private static int pickDebt(long[] g) {
		for (int i = 0; i < g.length; i++) {
			if (g[i] < 0) {
				return i;
			}
		}
		return -1;
	}

	static class Benchmark {
		int[] a;
		int[] b;
		long[] count;
		int[] color;
		long[] saveCount;

		boolean create(int i, long req) {
			if (count[i] >= req) {
				count[i] -= req;
				return true;
			}
			if (a[i] == 0 || b[i] == 0) {
				return false;
			}
			color[i] = 1;
			req -= count[i];
			count[i] = 0;
			boolean ret = false;
			if (color[a[i]] != 1 && color[b[i]] != 1) {
				ret = create(a[i], req) && create(b[i], req);
			}
			color[i] = 2;
			return ret;
		}

		void save() {
			System.arraycopy(count, 0, saveCount, 0, count.length);
		}

		void revert() {
			System.arraycopy(saveCount, 0, count, 0, count.length);
		}

		public long solve(int n, int[] a, int[] b, long[] count) {
			this.count = count;
			this.a = a;
			this.b = b;
			saveCount = count.clone();

			long l = 0, r = (long) 1e12;
			while (l < r - 1) {
				long m = (l + r) >>> 1;
				save();
				color = new int[n];
				if (create(0, m)) {
					l = m;
				} else {
					r = m;
				}
				revert();
			}
			return l;
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

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
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
