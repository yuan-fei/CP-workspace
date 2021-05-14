package y2021.r1b;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BrokenClock {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			long[] l = getLongArr(in, 3);
			int[] r = solve(l);
			System.out.println("Case #" + i + ": " + str(r));
		}
		in.close();
	}

	static Map<List<Long>, int[]> cache;

	static void preCalc() {
		cache = new HashMap<>();
		long max = 360 * 12 * (long) 1e10;
		for (int h = 0; h < 12; h++) {
			for (int m = 0; m < 60; m++) {
				for (int s = 0; s < 60; s++) {
					long[] ret = getTick(h, m, s);
					Arrays.sort(ret);
					List<Long> diff = new ArrayList<>(Arrays.asList((ret[1] - ret[0]), (ret[2] - ret[1])));
					cache.put(diff, new int[] { h, m, s });
					diff = new ArrayList<>(Arrays.asList((ret[2] - ret[1]), (ret[0] - ret[2] + max) % max));
					cache.put(diff, new int[] { h, m, s });
					diff = new ArrayList<>(Arrays.asList((ret[0] - ret[2] + max) % max, (ret[1] - ret[0])));
					cache.put(diff, new int[] { h, m, s });
				}
			}
		}
	}

	static long[] getTick(int h, int m, int s) {
		long[] ret = new long[3];
		ret[2] = s * 6 * 12 * (long) 1e10;
		ret[1] = m * 6 * 12 * (long) 1e10 + s * 12 * (long) 1e9;
		ret[0] = h * 30 * 12 * (long) 1e10 + m * 6 * (long) 1e10 + s * (long) 1e9;
		return ret;
	}

	private static int[] solve(long[] l) {
		if (cache == null) {
			preCalc();
		}
		long[] ll = Arrays.copyOf(l, l.length);
		Arrays.sort(ll);
		long d0 = ll[1] - ll[0];
		long d1 = ll[2] - ll[1];
		long d2 = 360 * 12 * (long) 1e10 - ll[1] + ll[0];
		int[] time = cache.get(new ArrayList<>(Arrays.asList(d0, d1)));
		if (time != null) {

		} else {
			time = cache.get(new ArrayList<>(Arrays.asList(d1, d2)));
			if (time != null) {

			} else {
				time = cache.get(new ArrayList<>(Arrays.asList(d2, d0)));
			}
		}
		return new int[] { time[0], time[1], time[2], 0 };
	}

	static int[] states = new int[1 << 25];

	private static void test() {
		for (int i = 0; i < 10000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt();
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

	static String str(long[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
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

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
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

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void setIntArr(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
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
