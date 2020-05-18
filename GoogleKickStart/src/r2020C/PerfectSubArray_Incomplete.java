package r2020C;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PerfectSubArray_Incomplete {
	public static void main(String[] args) {
		// Scanner in = new Scanner(new BufferedReader(new
		// InputStreamReader(System.in)));
		// int t = in.nextInt();
		// for (int i = 1; i <= t; ++i) {
		// int n = in.nextInt();
		// int[] a = getIntArr(in, n);
		// long ans = solve(n, a);
		// System.out.println("Case #" + i + ": " + ans);
		// }
		// in.close();
		int[] a = new int[100000];
		for (int i = 0; i < a.length; i++) {
			a[i] = 200;
		}
		System.out.println(solve(a.length, a));
	}

	private static long solve(int n, int[] a) {
		int preSum = 0;
		List<Integer> squares = new ArrayList<>();
		Set<Integer> ss = new HashSet<>();
		for (int i = 0; i * i < 20000005; i++) {
			squares.add(i * i);
			ss.add(i * i);
		}
		int[] m = new int[20000005];
		m[0] = 1;
		int cnt = 0;
		for (int i = 0; i < a.length; i++) {
			int x = a[i];
			preSum += x;

			for (int s : squares) {
				if (s <= preSum) {
					cnt += m[preSum - s];
				}
			}

			m[preSum]++;
		}
		return cnt;
	}

	private static long solve1(int n, int[] a) {
		long preSum = 0;
		List<Long> squares = new ArrayList<>();
		Set<Long> ss = new HashSet<>();
		for (long i = 0; i * i < 10000005; i++) {
			squares.add(i * i);
			ss.add(i * i);
		}
		Map<Long, Integer> m = new HashMap<>();
		m.put(0L, 1);
		long cnt = 0;
		for (int i = 0; i < a.length; i++) {
			int x = a[i];
			preSum += x;
			System.out.println(i + ": " + m.size() + ", " + squares.size());
			if (m.size() > squares.size()) {
				for (long s : squares) {
					cnt += m.getOrDefault(preSum - s, 0);
				}
			} else {
				for (long s : m.keySet()) {
					if (ss.contains(preSum - s)) {
						cnt += m.get(s);
					}
				}
			}
			m.put(preSum, m.getOrDefault(preSum, 0) + 1);
		}
		return cnt;
	}

	private static long mod = 1000000007;

	private static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	private static long mul(long a, long b) {
		return (a * b) % mod;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	private static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	private static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	private static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	private static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	private static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	private static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	private static char[][] getStringArr(Scanner in, int r, int c) {
		char[][] ret = new char[r][];
		for (int i = 0; i < r; i++) {
			ret[i] = in.next().toCharArray();
		}
		return ret;
	}

	private static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
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
