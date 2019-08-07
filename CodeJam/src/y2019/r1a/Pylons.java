package y2019.r1a;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Pylons {
	public static void main(String[] args) {
		solve();
		// test();
	}

	private static void test() {
		for (int i = 2; i <= 5; i++) {
			for (int j = 2; j <= 5; j++) {
				List<int[]> res = solve(i, j);
				System.out.println(i + ", " + j);
				System.out.println(str(res));
				if (!check(res)) {
					System.out.println("Wrong");
					return;
				}
			}
		}

	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int r = in.nextInt();
			int c = in.nextInt();
			List<int[]> res = solve(r, c);
			if (res.size() == 0) {
				System.out.println("Case #" + i + ": IMPOSSIBLE");
			} else {
				System.out.println("Case #" + i + ": POSSIBLE");
				System.out.print(str(res));
			}

		}
		in.close();
	}

	private static List<int[]> solve(int r, int c) {
		if (r == 4 && c == 4) {
			return Arrays.asList(new int[] { 1, 1 }, new int[] { 2, 3 }, new int[] { 3, 1 }, new int[] { 4, 3 },
					new int[] { 1, 2 }, new int[] { 2, 4 }, new int[] { 3, 2 }, new int[] { 4, 4 }, new int[] { 1, 3 },
					new int[] { 2, 1 }, new int[] { 3, 3 }, new int[] { 4, 1 }, new int[] { 3, 4 }, new int[] { 4, 2 },
					new int[] { 1, 4 }, new int[] { 2, 2 });
		} else if (r == 3 && c == 4) {
			return Arrays.asList(new int[] { 1, 1 }, new int[] { 2, 3 }, new int[] { 3, 1 }, new int[] { 1, 2 },
					new int[] { 2, 4 }, new int[] { 3, 2 }, new int[] { 1, 3 }, new int[] { 2, 1 }, new int[] { 3, 3 },
					new int[] { 1, 4 }, new int[] { 2, 2 }, new int[] { 3, 4 });
		} else if (r == 4 && c == 3) {
			return reverse(Arrays.asList(new int[] { 1, 1 }, new int[] { 2, 3 }, new int[] { 3, 1 }, new int[] { 1, 2 },
					new int[] { 2, 4 }, new int[] { 3, 2 }, new int[] { 1, 3 }, new int[] { 2, 1 }, new int[] { 3, 3 },
					new int[] { 1, 4 }, new int[] { 2, 2 }, new int[] { 3, 4 }));
		} else if (r <= 4 && c <= 4) {
			return new ArrayList<>();
		} else {
			if (c < 5) {
				return reverse(solveLarge(c, r));
			} else {
				return solveLarge(r, c);
			}
		}

	}

	private static List<int[]> solveLarge(int r, int c) {
		List<int[]> res = new ArrayList<>();
		if (r % 2 == 0) {
			for (int i = 0; i < r; i += 2) {
				res.addAll(solveEven(c, i));
			}
		} else {
			res.addAll(solveOdd(c));
			for (int i = 3; i < r; i += 2) {
				res.addAll(solveEven(c, i));
			}
		}
		return res;
	}

	private static List<int[]> solveOdd(int c) {
		List<int[]> res = new ArrayList<>();
		int col = 0;
		for (int i = 0; i < c; i++) {
			res.add(new int[] { 1, col + 1 });
			res.add(new int[] { 2, (col + c - 2) % c + 1 });
			res.add(new int[] { 3, col + 1 });
			col = (col + 1) % c;
		}
		return res;
	}

	private static List<int[]> solveEven(int c, int rows) {
		List<int[]> res = new ArrayList<>();
		int row = rows;
		int col = 0;
		for (int i = 0; i < c; i++) {
			res.add(new int[] { row + 1, col + 1 });
			res.add(new int[] { row + 2, (col - 2 + c) % c + 1 });
			col = (col + 1) % c;
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

	static boolean check(List<int[]> a) {
		Set<Integer> s = new HashSet<>();
		for (int i = 1; i < a.size(); i++) {
			int[] cur = a.get(i);
			int[] prev = a.get(i - 1);

			if (s.contains(cur[0] * 1000 + cur[1]) || cur[0] + cur[1] == prev[0] + prev[1]
					|| cur[0] - cur[1] == prev[0] - prev[1]) {
				System.out.println(cur[0] + ", " + cur[1] + "|" + prev[0] + ", " + prev[1]);
				return false;
			}
			s.add(cur[0] * 1000 + cur[1]);
		}
		return true;
	}

	static List<int[]> reverse(List<int[]> a) {
		List<int[]> r = new ArrayList<>();
		for (int i = 0; i < a.size(); i++) {
			r.add(new int[] { a.get(i)[1], a.get(i)[0] });
		}
		return r;
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static String str(List<int[]> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i)[0] + " " + a.get(i)[1] + "\n");
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
