package y2022.r1c;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LetterBlocks {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String[] towers = getStringArr(in, n);
			String r = solve(n, towers);
			System.out.println("Case #" + i + ": " + (r == null ? "IMPOSSIBLE" : r));
		}
		in.close();
	}

	private static String solve(int n, String[] towers) {
		StringBuilder[] same = new StringBuilder[26];
		int[] start = new int[26];
		int[] end = new int[26];
		boolean[] done = new boolean[n];
		Arrays.fill(start, -1);
		Arrays.fill(end, -1);
		for (int i = 0; i < 26; i++) {
			same[i] = new StringBuilder();
		}
		for (int i = 0; i < n; i++) {
			String s = towers[i];
			if (isSame(s)) {
				same[s.charAt(0) - 'A'].append(s);
				done[i] = true;
			} else {
				if (s.charAt(0) == s.charAt(s.length() - 1)) {
					return null;
				}
				if (start[s.charAt(0) - 'A'] != -1) {
					return null;
				}
				start[s.charAt(0) - 'A'] = i;
				if (end[s.charAt(s.length() - 1) - 'A'] != -1) {
					return null;
				}
				end[s.charAt(s.length() - 1) - 'A'] = i;
			}
		}

		boolean allSame = false;
		StringBuilder sb = new StringBuilder();
		while (!allSame) {
			int cur = -1;
			allSame = true;
			for (int i = 0; i < start.length; i++) {
				if (start[i] != -1 && end[i] == -1) {
					cur = start[i];
					allSame = false;
					break;
				} else {
					allSame &= (start[i] == -1 && end[i] == -1);
				}
			}

			if (cur == -1) {
				if (!allSame) {
					return null;
				}
			} else {
				sb.append(towers[cur]);
				start[towers[cur].charAt(0) - 'A'] = -1;
				end[towers[cur].charAt(towers[cur].length() - 1) - 'A'] = -1;
				done[cur] = true;
				while (start[towers[cur].charAt(towers[cur].length() - 1) - 'A'] != -1) {
					cur = start[towers[cur].charAt(towers[cur].length() - 1) - 'A'];
					sb.append(towers[cur]);
					start[towers[cur].charAt(0) - 'A'] = -1;
					end[towers[cur].charAt(towers[cur].length() - 1) - 'A'] = -1;
					done[cur] = true;
				}
			}
		}

		for (boolean d : done) {
			if (!d) {
				return null;
			}
		}
		String t = sb.toString();
		sb = new StringBuilder();
		boolean[] seen = new boolean[26];
		char last = 'a';
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) != last) {
				if (seen[t.charAt(i) - 'A']) {
					return null;
				} else {
					sb.append(same[t.charAt(i) - 'A']);
					same[t.charAt(i) - 'A'] = null;
				}
			}
			last = t.charAt(i);
			seen[t.charAt(i) - 'A'] = true;
			sb.append(t.charAt(i));
		}
		for (StringBuilder s : same) {
			if (s != null) {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	private static boolean isSame(String s) {

		for (char c : s.toCharArray()) {
			if (c != s.charAt(0)) {
				return false;
			}
		}
		return true;
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

	static char[][] getCharArr(Scanner in, int row, int col) {
		char[][] arr = new char[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getCharArr(in, col);
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

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}

	static private List<Integer>[] buildAdj(int n, int[][] edges, boolean biDirectional) {
		List<Integer>[] ans = new List[n];
		for (int i = 0; i < n; i++) {
			ans[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			ans[edges[i][0]].add(i);
			if (biDirectional) {
				ans[edges[i][1]].add(i);
			}
		}
		return ans;
	}

	static private List<Integer>[] buildRootedTree(int n, int[] edges) {
		List<Integer>[] ans = new List[n];
		for (int i = 0; i < n; i++) {
			ans[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			ans[edges[i]].add(i);
		}
		return ans;
	}

}
