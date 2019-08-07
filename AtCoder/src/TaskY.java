
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskY {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int h = in.nextInt();
		int w = in.nextInt();
		int n = in.nextInt();
		int[][] walls = getIntArr(in, n, 2);
		long r = solve(h, w, n, walls);
		System.out.println(r);
		in.close();
	}

	private static long solve(int h, int w, int n, int[][] walls) {
		walls = Arrays.copyOf(walls, n + 1);
		walls[n] = new int[] { h, w };
		Arrays.sort(walls, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int r = Integer.compare(o1[0], o2[0]);
				if (r == 0) {
					r = Integer.compare(o1[1], o2[1]);
				}
				return r;
			}
		});
		long[] dp = new long[walls.length];
		for (int i = 0; i < walls.length; i++) {
			dp[i] = C(walls[i][0] + walls[i][1] - 2, walls[i][0] - 1);
			for (int j = 0; j < i; j++) {
				if (contains(walls[i], walls[j])) {
					dp[i] = add(dp[i], -mul(dp[j],
							C(walls[i][0] - walls[j][0] + walls[i][1] - walls[j][1], walls[i][0] - walls[j][0])));
				}
			}
		}
		return dp[n];
	}

	private static boolean contains(int[] o1, int[] o2) {
		return o1[0] >= o2[0] && o1[1] >= o2[1];
	}

	static long mod = 1000000007;
	/** set the max N for N! */
	static int maxN = 200002;
	static boolean factCacheInitialized = false;
	static long[] factCache;
	static long[] iFactCache;

	static long C(int n, int m) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = mul(fact2(n), mul(inverseFact(m), inverseFact(n - m)));
			return r;
		}
	}

	static long fact2(int n) {
		if (!factCacheInitialized) {
			initFact(maxN);
			factCacheInitialized = true;
		}
		return factCache[n];
	}

	static long inverseFact(int n) {
		if (!factCacheInitialized) {
			initFact(maxN);
			factCacheInitialized = true;
		}
		return iFactCache[n];
	}

	static void initFact(int n) {
		factCache = new long[n];
		iFactCache = new long[n];
		factCache[0] = 1;
		for (int i = 1; i < n; i++) {
			factCache[i] = mul(factCache[i - 1], i);
		}
		iFactCache[n - 1] = inverse(factCache[n - 1]);
		for (int i = n - 2; i >= 0; i--) {
			iFactCache[i] = mul(iFactCache[i + 1], i + 1);
		}
	}

	/** Fermat theorem: Note p must be prime. 1/n = n^(p-2) mod p */
	static long inverse(long n) {
		long r = exp(n, mod - 2);
		return r;
	}

	private static long exp(long base, long exp) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			return mul(base, exp(base, exp - 1));
		} else {
			long t = exp(base, exp / 2);
			return mul(t, t);
		}
	}

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
