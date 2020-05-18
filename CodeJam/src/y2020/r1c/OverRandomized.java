package y2020.r1c;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class OverRandomized {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int u = in.nextInt();
			long[] m = new long[10000];
			String[] s = new String[10000];
			for (int j = 0; j < 10000; j++) {
				m[j] = in.nextLong();
				s[j] = in.next();
			}
			String r = solve(u, m, s);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static String solve(int u, long[] m, String[] s) {
		Set<Character> dic = new HashSet<>();
		Set<Character> initialDic = new HashSet<>();
		for (int i = 0; i < 10000; i++) {
			for (char c : s[i].toCharArray()) {
				dic.add(c);
			}
			if (s[i].length() > 1) {
				initialDic.add(s[i].charAt(0));
			}
		}
		Map<Character, Integer> min = new HashMap<>();
		Map<Character, Integer> max = new HashMap<>();
		char init = 'z';
		for (char c : dic) {
			max.put(c, 9);
			if (initialDic.contains(c)) {
				min.put(c, 1);
			} else {
				init = c;
			}
		}
		Map<Character, Integer> freq = new HashMap();
		for (char c : dic) {
			freq.put(c, 0);
		}
		for (int i = 0; i < 10000; i++) {
			char c = s[i].charAt(0);
			int f = freq.get(c) + 1;
			freq.put(c, f);
		}
		dic.remove(init);
		List<Character> l = new ArrayList<>(dic);
		Collections.sort(l, (a, b) -> Integer.compare(freq.get(b), freq.get(a)));
		l.add(0, init);
		String res = "";
		for (char c : l) {
			res += c;
		}
		return res;
	}

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

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
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
