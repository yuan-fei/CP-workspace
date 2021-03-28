package y2021.qualification;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReversortEngineering {
	public static void main(String[] args) {
		solve();
		// test();
	}

	private static void test() {
		int cnt = 0;
		for (int c = 1; c <= 1000; c++) {
			for (int n = 4; n <= 4; n++) {
				int[] a = solve(n, c);
				System.out.println(n + "," + c + ":" + Arrays.toString(a));
				if (a.length > 0) {
					if (solve(n, a) != c) {
						System.out.println(n + "," + c);
					} else {
						System.out.println(n + "," + c);
					}
				}
			}
		}
		// System.out.println(cnt);
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int c = in.nextInt();

			int[] r = solve(n, c);
			if (r.length > 0) {
				System.out.println("Case #" + i + ": " + str(r));
			} else {
				System.out.println("Case #" + i + ": IMPOSSIBLE");
			}

		}
		in.close();
	}

	private static int[] solve(int n, int c) {
		if (c < n - 1 || c > n * (n + 1) / 2 - 1) {
			return new int[0];
		}
		int[] ret = new int[n];
		for (int i = 0; i < n; i++) {
			ret[i] = i + 1;
		}
		List<Integer> steps = getSteps(n, c);
		for (int x : steps) {
			reverse(ret, n - x, n - 1);
		}
		return ret;
	}

	private static int solve(int n, int[] m) {
		int ans = 0;
		for (int i = 0; i < n - 1; i++) {
			int minId = min(m, i);
			ans += minId - i + 1;
			reverse(m, i, minId);
		}
		return ans;
	}

	private static List<Integer> getSteps(int n, int c) {
		c -= n - 1;
		List<Integer> ret = new ArrayList<>();
		for (int x = n - 1; x > 0; x--) {
			if (c >= x) {
				ret.add(x + 1);
				c -= x;
			}
		}
		Collections.reverse(ret);
		return ret;
	}

	static int min(int[] a, int i) {
		int ret = i;
		for (; i < a.length; i++) {
			if (a[i] < a[ret]) {
				ret = i;
			}
		}
		return ret;
	}

	static void reverse(int[] a, int i, int j) {
		while (i < j) {
			swap(a, i++, j--);
		}
	}

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
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
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
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
