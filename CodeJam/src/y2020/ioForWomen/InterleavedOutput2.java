package y2020.ioForWomen;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InterleavedOutput2 {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			String s = in.next();
			int r = solve(s);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	static int max = 0;
	static int lastio = -1;
	static int lastiO = -1;
	static int lastIo = -1;
	static int lastIO = -1;

	private static int solveSmall(String s) {
		max = 0;
		lastio = -1;
		lastiO = -1;
		lastIo = -1;
		lastIO = -1;
		int[] match = new int[s.length()];
		Arrays.fill(match, -1);
		dfs(0, 0, match, s);
		return max;
	}

	private static void dfs(int pos, int cntIO, int[] match, String s) {
		if (pos == s.length()) {
			max = Math.max(max, cntIO);
			return;
		}
		switch (s.charAt(pos)) {
		case 'o':
		case 'O':
			dfs(pos + 1, cntIO, match, s);
			break;
		case 'i':
		case 'I':
			for (int j = pos; j < s.length(); j++) {
				if ((Character.toUpperCase(s.charAt(j)) == 'O') && match[j] == -1) {
					int newCntIO = cntIO;
					if (s.charAt(pos) == 'I' && s.charAt(j) == 'O') {
						if (lastIO < pos) {
							int t = lastIO;
							lastIO = j;
							match[pos] = j;
							match[j] = pos;
							dfs(pos + 1, cntIO + 1, match, s);
							match[pos] = -1;
							match[j] = -1;
							lastIO = t;
						}

					}
					if (s.charAt(pos) == 'i' && s.charAt(j) == 'O') {
						if (lastiO < pos) {
							int t = lastiO;
							lastiO = j;
							match[pos] = j;
							match[j] = pos;
							dfs(pos + 1, cntIO, match, s);
							match[pos] = -1;
							match[j] = -1;
							lastiO = t;
						}
					}
					if (s.charAt(pos) == 'I' && s.charAt(j) == 'o') {
						if (lastIo < pos) {
							int t = lastIo;
							lastIo = j;
							match[pos] = j;
							match[j] = pos;
							dfs(pos + 1, cntIO, match, s);
							match[pos] = -1;
							match[j] = -1;
							lastIo = t;
						}
					}
					if (s.charAt(pos) == 'i' && s.charAt(j) == 'o') {
						if (lastio < pos) {
							int t = lastio;
							lastio = j;
							match[pos] = j;
							match[j] = pos;
							dfs(pos + 1, newCntIO, match, s);
							match[pos] = -1;
							match[j] = -1;
							lastio = t;
						}
					}
				}
			}
			break;
		}
	}

	private static int solve2(String s) {
		Map<String, Integer> m = new HashMap<>();
		m.put("io", 0);
		m.put("Io", 1);
		m.put("iO", 2);
		m.put("IO", 3);

		Map<Character, int[]> char2word = new HashMap<>();
		char2word.put('i', new int[] { 0, 2 });
		char2word.put('I', new int[] { 1, 3 });
		char2word.put('o', new int[] { 0, 1 });
		char2word.put('O', new int[] { 2, 3 });

		Map<Character, Integer> next = new HashMap<>();
		next.put('i', 0);
		next.put('I', 0);
		next.put('o', 1);
		next.put('O', 1);

		int[] dp = new int[16];
		Queue<Integer> state = new LinkedList<Integer>();
		state.offer(0);
		for (char c : s.toCharArray()) {
			int[] newDp = new int[16];
			Arrays.fill(newDp, -1);
			for (int l = state.size(); l > 0; l--) {
				int x = state.poll();
				for (int offset : char2word.get(c)) {
					if ((((x >> offset) & 1) == next.get(c))) {
						int nextState = (x ^ (1 << offset));
						if (newDp[nextState] == -1) {
							state.offer(nextState);
						}
						int adjust = 0;
						if (c == 'O' && m.get("IO") == offset) {
							adjust = 1;
						}
						newDp[nextState] = Math.max(newDp[nextState], dp[x] + adjust);
					}
				}
			}
			dp = newDp;
		}
		return dp[0];
	}

	private static int solve(String s) {
		Map<String, Integer> m = new HashMap<>();
		m.put("io", 0);
		m.put("Io", 1);
		m.put("iO", 2);
		m.put("IO", 3);

		Map<Character, int[]> char2word = new HashMap<>();
		char2word.put('i', new int[] { 0, 2 });
		char2word.put('I', new int[] { 1, 3 });
		char2word.put('o', new int[] { 0, 1 });
		char2word.put('O', new int[] { 2, 3 });

		Map<Character, Integer> next = new HashMap<>();
		next.put('i', 0);
		next.put('I', 0);
		next.put('o', 1);
		next.put('O', 1);

		int[] dp = new int[16];
		dp[0] = 0;
		for (char c : s.toCharArray()) {
			int[] newDp = new int[16];
			Arrays.fill(newDp, -1);
			for (int x = 0; x < 16; x++) {
				if (dp[x] != -1) {
					for (int offset : char2word.get(c)) {
						if ((((x >> offset) & 1) == next.get(c))) {
							int nextState = (x ^ (1 << offset));
							int adjust = 0;
							if (c == 'O' && m.get("IO") == offset) {
								adjust = 1;
							}
							newDp[nextState] = Math.max(newDp[nextState], dp[x] + adjust);
						}
					}
				}
			}
			dp = newDp;
		}
		return dp[0];
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
