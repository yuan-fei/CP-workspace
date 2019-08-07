package r1c;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class AWholeNewWord {

	public static void main(String[] args) throws Exception {
		// solve();
		// Set<String> s = new HashSet<>();
		// s.add("AB");
		// // s.add("BC");
		// System.out.println(solve(s, 2));
		test();
	}

	private static void test() throws Exception {
		for (int i = 0; i < 10000; i++) {
			test1();
			test2();
		}
	}

	private static void test2() throws Exception {
		Random r = new Random();
		List<String> ls = new ArrayList<>();
		for (char i = 'A'; i <= 'Z'; i++) {
			for (char j = 'A'; j <= 'Z'; j++) {
				ls.add("" + i + j);
			}
		}
		int n = r.nextInt(26 * 26) + 1;
		Set<String> s = new HashSet<>();
		for (int i = 0; i < n; i++) {
			int c = r.nextInt(n - i);
			s.add(ls.remove(c));
		}

		String res = solve(s, 2);
		if (s.contains(res)) {
			System.out.println("Opps");
		}
	}

	private static void test1() throws Exception {
		Random r = new Random();
		List<String> ls = new ArrayList<>();
		for (char i = 'A'; i <= 'Z'; i++) {
			ls.add("" + i);
		}
		int n = r.nextInt(26) + 1;
		Set<String> s = new HashSet<>();
		for (int i = 0; i < n; i++) {
			int c = r.nextInt(n - i);
			s.add(ls.remove(c));
		}
		String res = solve(s, 1);
		if (s.contains(res)) {
			System.out.println("Opps");
		}
	}

	private static void solve() throws Exception {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int l = in.nextInt();
			Set<String> words = new HashSet<>();
			for (int j = 0; j < n; j++) {
				words.add(in.next());
			}
			String result = solve(words, l);
			System.out.println("Case #" + i + ": " + result);
		}
		in.close();
	}

	private static String solve(Set<String> words, int l) throws Exception {
		result = "";
		Set<Character>[] cSet = new HashSet[l];
		for (int i = 0; i < l; i++) {
			cSet[i] = new HashSet<>();
			for (String w : words) {
				cSet[i].add(w.charAt(i));
			}
		}
		int cnt = 1;
		for (int i = 0; i < cSet.length; i++) {
			cnt *= cSet[i].size();
			if (cnt > words.size()) {
				break;
			}
		}
		if (cnt <= words.size()) {
			return "-";
		} else {
			if (getNewWord(l, words, cSet, 0, "")) {
				return result;
			} else {
				throw new Exception("Impossible");
			}
		}
	}

	static String result = "";

	private static boolean getNewWord(int l, Set<String> words, Set<Character>[] cSet, int i, String cur) {
		if (i == l) {
			if (!words.contains(cur)) {
				result = cur;
				return true;
			} else {
				return false;
			}
		} else {
			for (char c : cSet[i]) {
				if (getNewWord(l, words, cSet, i + 1, cur + c)) {
					return true;
				}
			}
			return false;
		}
	}

	static class Trie {

		static class TrieNode {
			TrieNode[] surfixes;
			boolean isWord;

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
			}
			tn.isWord = true;
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

		public String findUniquePath() {
			for (int i = 0; i < root.surfixes.length; i++) {
				if (root.surfixes[i] != null) {
					for (int j = 0; j < i; j++) {
						if (root.surfixes[j] != null) {
							String path = findUniquePath(root.surfixes[i], root.surfixes[j]);
							if (!path.equals("-")) {
								if (isLeft) {
									return ((char) ('A' + i)) + path;
								} else {
									return ((char) ('A' + j)) + path;
								}
							}
						}
					}
				}
			}
			return "-";
		}

		boolean isLeft = true;

		public String findUniquePath(TrieNode r1, TrieNode r2) {
			if (r1 == null && r2 == null) {
				return "-";
			} else {
				for (int i = 0; i < r1.surfixes.length; i++) {
					if ((r1.surfixes[i] == null && r2.surfixes[i] != null)) {
						isLeft = true;
						return ((char) ('A' + i)) + tail(r2.surfixes[i]);
					} else if ((r2.surfixes[i] == null && r1.surfixes[i] != null)) {
						isLeft = false;
						return ((char) ('A' + i)) + tail(r1.surfixes[i]);
					} else {
						String path = findUniquePath(r1.surfixes[i], r2.surfixes[i]);
						if (!path.equals("-")) {
							return ((char) ('A' + i)) + path;
						}
					}
				}
				return "-";
			}
		}

		private String tail(TrieNode r1) {
			for (int i = 0; i < r1.surfixes.length; i++) {
				if (r1.surfixes[i] != null) {
					return ((char) ('A' + i)) + tail(r1.surfixes[i]);
				}
			}
			return "";
		}
	}

	private static String solve(Set<String> words) {
		Trie trie = new Trie();
		for (String s : words) {
			trie.insert(s);
		}

		return trie.findUniquePath();
	}

}
