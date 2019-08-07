package r1182;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class TaskC {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		String[] t = getStringArr(in, n);
		List<String> r = solve(n, t);
		System.out.println(r.size() / 2);
		System.out.println(str(r));
		in.close();
	}

	private static List<String> solve(int n, String[] t) {
		Map<Character, Integer> dic = new HashMap<>();
		dic.put('a', 0);
		dic.put('e', 1);
		dic.put('i', 2);
		dic.put('o', 3);
		dic.put('u', 4);
		Map<Integer, Map<Character, Queue<String>>> map = new HashMap<>();
		Queue<String[]> fi = new LinkedList<>();
		Queue<String[]> se = new LinkedList<>();
		List<String> res = new ArrayList<>();
		for (String w : t) {
			int[] r = parse(w);
			if (r != null) {
				Map<Character, Queue<String>> subMap = map.getOrDefault(r[0], new HashMap<>());
				Queue<String> q = subMap.getOrDefault((char) r[1], new LinkedList<>());
				if (!q.isEmpty()) {
					se.offer(new String[] { q.poll(), w });
				} else {
					q.offer(w);
				}
				map.put(r[0], subMap);
				subMap.put((char) r[1], q);
			}
		}
		for (Map<Character, Queue<String>> subMap : map.values()) {
			String last = null;
			for (Queue<String> q : subMap.values()) {
				if (!q.isEmpty()) {
					if (last != null) {
						fi.offer(new String[] { last, q.poll() });
						last = null;
					} else {
						last = q.poll();
					}
				}
			}
		}

		while (!fi.isEmpty() && !se.isEmpty()) {
			String[] f = fi.poll();
			String[] s = se.poll();
			res.add(f[0] + " " + s[0]);
			res.add(f[1] + " " + s[1]);
		}
		while (!se.isEmpty()) {
			String[] f = se.poll();
			if (!se.isEmpty()) {
				String[] s = se.poll();
				res.add(f[0] + " " + s[0]);
				res.add(f[1] + " " + s[1]);
			}
		}
		return res;
	}

	static Set<Character> dic = new HashSet<>();
	static {
		dic.add('a');
		dic.add('e');
		dic.add('i');
		dic.add('o');
		dic.add('u');
	}

	private static int[] parse(String w) {
		int[] r = new int[2];
		for (char c : w.toCharArray()) {
			if (dic.contains(c)) {
				r[0]++;
				r[1] = c;
			}
		}
		if (r[0] == 0) {
			return null;
		}
		return r;
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

	static String str(List<String> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + "\n");
		}
		return sb.toString();
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

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
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
}
