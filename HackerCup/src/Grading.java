
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Grading {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int h = in.nextInt();
				int s = in.nextInt();
				int k = in.nextInt();
				String[] a = getStringArr(in, h);
				int[] c = getIntArr(in, k);
				int[] r = solve(h, s, k, a, c);
				out.println("Case #" + i + ": " + str(r));
			}

		}

	}

	private static int[] solve(int h, int s, int k, String[] a, int[] c) {
		int[][] stacks = new int[s][h];
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < h; j++) {
				stacks[i][j] = a[j].charAt(i) - 'A';
			}
		}
		int[][][] dp = new int[s][2][];
		int[] contetSwitch = new int[h + 1];
		Arrays.fill(contetSwitch, Integer.MAX_VALUE);
		for (int initialLetter = 0; initialLetter < 2; initialLetter++) {
			for (int i = 0; i < s; i++) {
				dp[i][initialLetter] = dpForStack(stacks[i], h, initialLetter);
			}
			for (int i = 0; i <= h; i++) {
				int sum = 0;
				for (int j = 0; j < s; j++) {
					sum += dp[j][initialLetter][i];
				}
				contetSwitch[i] = Math.min(sum, contetSwitch[i]);
			}
		}
		int[] total = new int[s * h + 1];
		Arrays.fill(total, h);
		for (int i = 0; i < contetSwitch.length; i++) {
			total[contetSwitch[i]] = i;
		}
		for (int i = 1; i < total.length; i++) {
			total[i] = Math.min(total[i], total[i - 1]);
		}
		int[] ans = new int[c.length];
		for (int i = 0; i < c.length; i++) {
			ans[i] = total[c[i]];
		}
		return ans;
	}

	static int[] dpForStack(int[] stack, int h, int initialLetter) {
		int MAX = h + 5;
		int[][][] dp = new int[h + 1][h + 2][2];
		for (int i = 0; i < dp.length; i++) {
			for (int c = 0; c < dp[i].length; c++) {
				for (int last = 0; last < 2; last++) {
					dp[i][c][last] = MAX;
					if (i == 0 && last == initialLetter) {
						dp[i][c][last] = 0;
					}
				}
			}
		}
		for (int i = 0; i < h; i++) {
			for (int c = 0; c <= h; c++) {
				for (int last = 0; last < 2; last++) {
					int cur = stack[i];
					int d = dp[i][c][last];
					if (cur == last) {
						dp[i + 1][c][last] = Math.min(dp[i + 1][c][last], d); // grade
					} else {
						dp[i + 1][c + 1][1 - last] = Math.min(dp[i + 1][c + 1][1 - last], d); // grade
					}
					dp[i + 1][c][last] = Math.min(dp[i + 1][c][last], d + 1); // discard

				}
			}
		}
		int[] ans = new int[h + 1];
		for (int i = 0; i <= h; i++) {
			ans[i] = Math.min(dp[h][i][0], dp[h][i][1]);
		}
		return ans;
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

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
		}
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

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
