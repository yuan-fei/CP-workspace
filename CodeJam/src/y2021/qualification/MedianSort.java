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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MedianSort {
	public static void main(String[] args) {
		solve();
		// test();
	}

	static Scanner in;

	private static void solve() {
		in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		int n = in.nextInt();
		int q = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			if (solve(n, q) == -1) {
				return;
			}
			// System.err.println(Arrays.asList(askCnt, cnt));
			askCnt = cnt = 0;

			// System.err.println(askCnt + ", " + (ask2Cnt - ask32Cnt) + ", " +
			// ask3Cnt + ", ");
			// askCnt = 0;
			// ask2Cnt = 0;
			// ask3Cnt = 0;
			// ask32Cnt = 0;
		}
		in.close();
	}

	static int cnt = 0;

	private static int solve(int n, int q) {
		List<Integer> l = new ArrayList<>(Arrays.asList(1, 2));
		List<Integer> shuffle = new ArrayList<>();
		for (int x = 3; x <= n; x++) {
			shuffle.add(x);
		}
		for (int i = 0; i < shuffle.size(); i++) {
			handle(l, shuffle.get(i), 0, l.size() - 1);
		}
		int[] ans = l.stream().mapToInt(Integer::intValue).toArray();
		return ask(ans);
	}

	private static void handle(List<Integer> l, int target, int start, int end) {
		if (end - start + 1 == 2) {
			handle2(l, start, target);
		} else if (end - start + 1 == 3) {
			handle3(l, start, target);
		} else {
			int n = end - start + 1;
			int left = start + n / 3;
			int right = end - n / 3;
			int ret = ask(new int[] { l.get(left), l.get(right), target });
			if (ret == target) {
				handle(l, target, left, right);
			} else if (ret == l.get(left)) {
				handle(l, target, start, Math.max(start + 1, left - 1));
			} else {
				handle(l, target, Math.min(right + 1, end - 1), end);
			}
		}
	}

	private static void handle3(List<Integer> l, int start, int target) {
		ask3Cnt++;
		int ans = ask(new int[] { l.get(start), l.get(start + 1), target });
		if (ans == l.get(start)) {
			l.add(start, target);
		} else if (ans == target) {
			l.add(start + 1, target);
		} else {
			ask32Cnt++;
			handle2(l, start + 1, target);
		}

	}

	private static void handle2(List<Integer> l, int start, int target) {
		ask2Cnt++;
		int ans = ask(new int[] { l.get(start), l.get(start + 1), target });
		int insertPoint = -1;
		if (ans == l.get(start)) {
			insertPoint = start;
		} else if (ans == target) {
			insertPoint = start + 1;
		} else {
			insertPoint = start + 2;
		}
		l.add(insertPoint, target);
	}

	static int askCnt = 0;
	static int ask3Cnt = 0;
	static int ask2Cnt = 0;
	static int ask32Cnt = 0;

	private static int ask(int[] tuple) {
		// debug("" + (i+ 1));
		askCnt++;
		out("" + str(tuple));
		return in.nextInt();
	}

	private static void out(String s) {
		System.out.println(s);
		System.out.flush();
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
