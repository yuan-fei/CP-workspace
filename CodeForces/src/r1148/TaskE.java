package r1148;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class TaskE {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[][] s = getIntArr(in, n);
		int[][] t = getIntArr(in, n);
		List<int[]> r = solve(n, s, t);
		if (r != null) {
			System.out.println("YES");
			System.out.println(r.size());
			System.out.println(str(r));
		} else {
			System.out.println("NO");
		}

		in.close();
	}

	private static List<int[]> solve(int n, int[][] s, int[][] t) {
		List<int[]> res = new ArrayList<>();
		Arrays.sort(s, Comparator.comparingInt(o -> o[0]));
		Arrays.sort(t, Comparator.comparingInt(o -> o[0]));
		Queue<int[]> q = new LinkedList<>();
		int j = 0;
		for (int i = 0; i < t.length; i++) {
			while (j < s.length && s[j][0] < t[i][0]) {
				q.offer(s[j++]);
			}

			if (q.isEmpty()) {
				if (j < s.length && s[j][0] == t[i][0]) {
					j++;
				} else {
					return null;
				}
			} else {
				int[] left = q.poll();
				int k = j;
				while (k < s.length && left[0] < t[i][0]) {
					int d = Math.min(t[i][0] - left[0], s[k][0] - t[i][0]);
					if (d > 0) {
						left[0] += d;
						s[k][0] -= d;
						res.add(new int[] { left[1], s[k][1], d });
					}
					k++;
				}
				if (left[0] < t[i][0]) {
					return null;
				}
			}

		}
		if (!q.isEmpty()) {
			return null;
		}
		return res;
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

	static String str(List<int[]> l) {
		StringBuilder sb = new StringBuilder();
		for (int[] e : l) {
			sb.append(e[0] + " " + e[1] + " " + e[2] + "\n");
		}
		return sb.toString();
	}

	static int[][] getIntArr(Scanner in, int size) {
		int[][] arr = new int[size][];
		for (int i = 0; i < size; i++) {
			arr[i] = new int[] { in.nextInt(), i + 1 };
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
