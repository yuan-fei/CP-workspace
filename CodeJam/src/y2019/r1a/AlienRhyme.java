package y2019.r1a;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AlienRhyme {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String[] w = getStringArr(in, n);
			int r = solve(n, w);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int n, String[] w) {
		Trie t = new Trie();
		for (String s : w) {
			t.insert(new StringBuilder(s).reverse().toString());
		}
		return t.findMaxPair();
	}

	static class Trie {

		static class TrieNode {
			TrieNode[] surfixes;
			boolean isWord;
			int wordCnt = 0;

			TrieNode() {
				surfixes = new TrieNode[26];
			}
		}

		TrieNode root;

		/** Initialize your data structure here. */
		public Trie() {
			root = new TrieNode();
		}

		/** Inserts a word into the trie. */
		public void insert(String word) {
			TrieNode tn = root;
			for (char c : word.toCharArray()) {
				if (tn.surfixes[c - 'A'] == null) {
					tn.surfixes[c - 'A'] = new TrieNode();
				}
				tn = tn.surfixes[c - 'A'];
				tn.wordCnt++;
			}
			tn.isWord = true;
		}

		public int findMaxPair() {
			int total = 0;
			for (int i = 0; i < root.surfixes.length; i++) {
				if (root.surfixes[i] != null) {
					total += findMaxPair(root.surfixes[i]);
				}
			}
			return total;
		}

		private int findMaxPair(TrieNode r) {
			if (r.wordCnt < 2) {
				return 0;
			} else if (r.wordCnt == 2) {
				return 2;
			} else {
				int total = 0;
				for (int i = 0; i < r.surfixes.length; i++) {
					if (r.surfixes[i] != null) {
						total += findMaxPair(r.surfixes[i]);
					}
				}
				if (r.wordCnt - total >= 2) {
					total += 2;
				}
				return total;
			}
		}

		/** Returns if the word is in the trie. */
		public boolean search(String word) {
			TrieNode tn = root;
			for (char c : word.toCharArray()) {
				tn = tn.surfixes[c - 'A'];
				if (tn == null) {
					return false;
				}
			}
			return tn.isWord;
		}

		/**
		 * Returns if there is any word in the trie that starts with the given prefix.
		 */
		public boolean startsWith(String prefix) {
			TrieNode tn = root;
			for (char c : prefix.toCharArray()) {
				tn = tn.surfixes[c - 'A'];
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
}
