package r1185;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskD {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		long[][] b = getLongArr(in, n);
		long r = solve(n, b);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, long[][] b) {
		if (n <= 3) {
			return 1;
		}
		Arrays.sort(b, Comparator.comparingLong(e -> e[0]));
		// check b[0]
		long[][] bCopy = new long[b.length - 1][];
		System.arraycopy(b, 1, bCopy, 0, b.length - 1);
		if (isProgression(bCopy)) {
			return b[0][1];
		}
		// check b[1]
		bCopy[0] = b[0];
		System.arraycopy(b, 2, bCopy, 1, b.length - 2);
		if (isProgression(bCopy)) {
			return b[1][1];
		}

		// bad>1
		long d = b[1][0] - b[0][0];
		return findBad(b, d);
	}

	private static boolean isProgression(long[][] arr) {
		long d = arr[1][0] - arr[0][0];
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i + 1][0] - arr[i][0] != d) {
				return false;
			}
		}
		return true;
	}

	static long findBad(long[][] b, long d) {
		int bad = -1;
		for (int i = 1; i < b.length;) {
			if (b[i][0] - b[i - 1][0] != d) {
				if (bad == -1 && (i == b.length - 1 || b[i + 1][0] - b[i - 1][0] == d)) {
					bad = i;
					i += 2;
				} else {
					return -1;
				}
			} else {
				i++;
			}
		}
		return b[bad][1];
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

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
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

	static long[][] getLongArr(Scanner in, int size) {
		long[][] arr = new long[size][2];
		for (int i = 0; i < size; i++) {
			arr[i][0] = in.nextLong();
			arr[i][1] = i + 1;
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
