package y2019.r1c;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RobotProgrammingStrategy {
	public static void main(String[] args) {
		solve();
		// test();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String[] s = getStringArr(in, n);
			String r = solve(n, s);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static String solve(int n, String[] s) {
		Trie t = new Trie();
		for (int i = 0; i < s.length; i++) {
			String ps = s[i];
			if (s[i].length() < 256) {
				ps = padding(s[i], 256);
			}
			t.insert(ps);
		}
		String r = t.getWin();
		return r == null ? "IMPOSSIBLE" : r;
	}

	private static String padding(String s, int length) {
		StringBuilder sb = new StringBuilder();
		int l = 0;
		while (sb.length() < length) {
			sb.append(s);
		}
		return sb.toString();
	}

	private static void test() {
		for (int i = 0; i < 1; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt(128) + 1;
		String[] ss = new String[n];
		for (int i = 0; i < n; i++) {
			char[] ca = new char[r.nextInt(500) + 1];
			for (int j = 0; j < ca.length; j++) {
				int rd = r.nextInt(3);
				switch (rd % 3) {
				case 0:
					ca[j] = 'S';
					break;

				case 1:
					ca[j] = 'R';
					break;

				case 2:
					ca[j] = 'P';
					break;
				}
			}
			ss[i] = new String(ca);
		}
		ss = new String[] {
				"RSRPPSSSSRPRSPPRPRPSPPPSPRRRSRPPPRSPSSRPRRSSSPSSPSRPSPPPSPRRRRRSSPRPSRSSRRPRSRRSPRSSRSSRRRRRSPPPSPSSSSSRRSRRRPRRSSRRPSPRPSRPSRPSRSRPRPRSPRRRSRRSPRSRSRSSRPPRSRSPRRPRRRRRRSPPRSSPPSSRSRSRSPSRPRRPSSSRSRPSRPRPSRSPRRPSRRPSSPRPPPRRPPRSSRSPSPSPSSSRSRRRPPRPRPRPRSSSSPSPSSPSRRSPRPRRRPSRPPRSPPSPRRRRRPRRSRSSPRSRPPPSPRSPPRPPPRRPPSSPPRPRSSSPPPSSRSRRPRRRSPSPSSRPRSRSRPPRPPSSPPPPRPPPSSRRPSPPRRP",

				"SRRRRPSPSPSSRPPPPPPSPSPSSPPSPSRRSPRPSRSPPPPPPSRSRRRRSPSSSSPSSRRRSRSSSRSSPSRPSRSPRRRRPRSSRPPSRRSPSRRRPPRSPPRRPRSRSPSPSRRSSRSSSSSSRRPSSRRRSPPRPRPSSPPRPPSPSPPPSRPPRRPPPSPPPRSPRRSRRPRSRPSPPRSSPRPSPPRRRPRPSSPRPRPRRPPRSRPPRPRRPSSPSSRSSRSSRSSPRPPRRSSSPSPPPSRPRSPRRRPRSSPPRRSRRRPRSPPPSRRPPSSRRRPRPPPPSRPSPRSPPSSPPPPPRSRRRRPRPRRSSPRPSRPPPPPRSRSSPPPPPSSRPPRRSSPSRRPRPSSSSPRPSSSRRSSPRRSRSRRPSRSSPRPSSPRPRRRPSRPPPPPSSRRPPPPRPSPRSPRPSSRPSSRSPRRSRPSSPRRRRRSSRSRPRSPS",

				"SRSRRRPSSPPPSRPPPPRSSRRRSRSRSPPSRRRPSRSPSRSRSPSSPSPSRPRSPRRSSRRSPSRPSPSSPPPPPPRPSPPPRPPSPPPSPRPRRSSPSSSPSSRSPRRSPSRSPSPRRSPRSSSPRPSRPSPPRPRRRPPSRPRSRRRRRPPSPRSSSSRRPRRPPPSPRRSRSRPSRSRSSRPRSSSSRSRSRRPPRRSRPSPSRSPRSRPSRPSSRRRRPSSRPPRRRSSSRPSPPRSSPSSPSPRSSRPRRPPRSPRRSRSSRSRSSRPRPSPRSRSRSSSRPPRPSRR",

				"SPRSPPSSSPPRPRSPSSPRRRPSPRPPRPSRPSPPSPSRSRRRRPPSRPPPPPPSRPSPPRPRPPSPRRPPPPSSRRSSRRPSSSSPPRSRRSPSSSSPPRRRPSSRSPSSSPPP",

				"SPRPSRSPRSPRRSRRPPPPRRRRPSPRRRSSRRRPSPPSPSPRPSSPPRSSSRPPSRRPP",

				"SSSPPPSRPPRSPPPPPSPPRSRRRPPRSPPSRPSPSSSRPPSRRPRRPSSPSPRPRRRPPPPSPSPRSPSPSPSRPRRPRSSPSPRRRSRRPSRSPPRSRPRPSRSPRRRRSRPPPPPRSPRRSRSRPPRRPRPPRPPPSSSPSSSSRRRRRSSSRRSRPRRSPRSPRPRPSRSPPSRSRPPSRSSPPPPPRPSSRSPSSPPSPRPRSRSR" };
		n = ss.length;
		String s = solve(n, ss);
		if (s.length() > 500) {
			System.out.println(s);
		}
		if (!s.equals("IMPOSSIBLE") && s.length() <= 500) {
			for (int i = 0; i < ss.length; i++) {
				if (!win(s, ss[i])) {
					System.out.println(s);
					System.out.println(ss[i]);
				}
			}
		}
	}

	private static boolean win(String s, String ss) {
		for (int i = 0; i < s.length(); i++) {
			if (win(s.charAt(i), ss.charAt(i % ss.length()))) {
				return true;
			}
			if (lose(s.charAt(i), ss.charAt(i % ss.length()))) {
				return false;
			}
		}
		return false;
	}

	private static boolean win(char c1, char c2) {
		return (c2 == 'P' && c1 == 'S') || (c2 == 'S' && c1 == 'R') || (c2 == 'R' && c1 == 'P');
	}

	private static boolean lose(char c1, char c2) {
		return (c1 == 'P' && c2 == 'S') || (c1 == 'S' && c2 == 'R') || (c1 == 'R' && c2 == 'P');
	}

	static class Trie {
		Map<Character, Integer> c2i;

		static class TrieNode {
			TrieNode[] surffixes;
			boolean isWord;

			TrieNode() {
				surffixes = new TrieNode[3];
			}
		}

		TrieNode root;

		/** Initialize your data structure here. */
		public Trie() {
			root = new TrieNode();
			c2i = new HashMap<>();
			c2i.put('S', 0);
			c2i.put('R', 1);
			c2i.put('P', 2);
		}

		/** Inserts a word into the trie. */
		public void insert(String word) {
			TrieNode tn = root;
			for (char c : word.toCharArray()) {
				if (tn.surffixes[c2i.get(c)] == null) {
					tn.surffixes[c2i.get(c)] = new TrieNode();
				}
				tn = tn.surffixes[c2i.get(c)];
			}
			tn.isWord = true;
		}

		/** Returns if the word is in the trie. */
		public boolean search(String word) {
			TrieNode tn = root;
			for (char c : word.toCharArray()) {
				tn = tn.surffixes[c2i.get(c)];
				if (tn == null) {
					return false;
				}
			}
			return tn.isWord;
		}

		public String getWin() {
			TrieNode tn = root;
			StringBuilder sb = new StringBuilder();
			while (tn != null) {
				if (tn.surffixes[0] != null && tn.surffixes[1] != null && tn.surffixes[2] != null) {
					return null;
				} else if (tn.surffixes[0] != null && tn.surffixes[1] != null) {
					sb.append('R');
					tn = tn.surffixes[1];
				} else if (tn.surffixes[1] != null && tn.surffixes[2] != null) {
					sb.append('P');
					tn = tn.surffixes[2];
				} else if (tn.surffixes[0] != null && tn.surffixes[2] != null) {
					sb.append('S');
					tn = tn.surffixes[0];
				} else if (tn.surffixes[0] != null) {
					sb.append('R');
					break;
				} else if (tn.surffixes[1] != null) {
					sb.append('P');
					break;
				} else if (tn.surffixes[2] != null) {
					sb.append('S');
					break;
				} else {
					break;
				}

			}
			return sb.toString();
		}

		/**
		 * Returns if there is any word in the trie that starts with the given prefix.
		 */
		public boolean startsWith(String prefix) {
			TrieNode tn = root;
			for (char c : prefix.toCharArray()) {
				tn = tn.surffixes[c2i.get(c)];
				if (tn == null) {
					return false;
				}
			}
			return true;
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
