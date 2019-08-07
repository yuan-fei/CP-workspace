package r2019B;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BuildingPalindromes {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int q = in.nextInt();
			String s = in.next();
			int[][] questions = getIntArr(in, q, 2);
			int r = solve(n, q, s, questions);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int n, int q, String s, int[][] questions) {
		int[][] cnt = new int[n + 1][26];
		for (int i = 1; i <= n; i++) {
			cnt[i] = Arrays.copyOf(cnt[i - 1], 26);
			cnt[i][s.charAt(i - 1) - 'A']++;
		}
		int res = 0;
		for (int i = 0; i < q; i++) {
			int start = questions[i][0];
			int end = questions[i][1];
			boolean isPal = true;
			int odd = 0;
			for (int j = 0; j < 26; j++) {
				if ((cnt[end][j] - cnt[start - 1][j]) % 2 != 0) {
					if (odd == 0) {
						odd++;
					} else {
						isPal = false;
						break;
					}
				}
			}
			if (isPal) {
				res++;
			}
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
