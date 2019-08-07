import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class LatestGuests {
	public static void main(String[] args) {
		// generateTestCase();
		// solve();
		solveLarge();
	}

	private static void generateTestCase() {
		int N = 10;
		System.out.println(N);
		for (int i = 0; i < N; i++) {
			generateOne();
		}
	}

	private static void generateOne() {
		Random r = new Random();
		// int n = r.nextInt(8) + 2;
		// int g = r.nextInt(9) + 1;
		// int m = r.nextInt(10);
		int n = r.nextInt(10000 - 2) + 2;
		int g = r.nextInt(10000 - 1) + 1;
		int m = r.nextInt(1000000000);
		int[][] q = new int[g][2];

		for (int i = 0; i < g; i++) {
			q[i][0] = r.nextInt(n) + 1;
			q[i][1] = r.nextInt(2);
		}
		System.out.println(n + " " + g + " " + m);
		System.out.println(str(q));
	}

	private static String str(int[][] q) {
		StringBuilder sb = new StringBuilder();
		for (int[] qr : q) {
			sb.append(qr[0] + " " + ((qr[1] == 0) ? "C" : "A") + "\n");
		}
		return sb.toString();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int g = in.nextInt();
			int m = in.nextInt();

			List<Integer>[][] finalState = new List[n][2];
			int[][] ts = new int[n][2];
			for (int j = 0; j < n; j++) {
				finalState[j] = new List[] { new ArrayList<Integer>(), new ArrayList<Integer>() };
			}
			for (int j = 0; j < g; j++) {
				int start = in.nextInt() - 1;
				if (in.next().equals("C")) {
					finalState[start][0].add(j);
				} else {
					finalState[start][1].add(j);
				}
			}
			for (int j = 1; j <= m; j++) {
				List<Integer> tmp = new ArrayList<>(finalState[n - 1][0]);
				int tmpTs = ts[n - 1][0];
				for (int k = n - 1; k > 0; k--) {
					if (!finalState[k - 1][0].isEmpty() && ts[k - 1][0] == j - 1) {
						finalState[k][0] = new ArrayList<>(finalState[k - 1][0]);
						ts[k][0] = j;
					}

				}
				if (!tmp.isEmpty() && tmpTs == j - 1) {
					finalState[0][0] = tmp;
					ts[0][0] = j;
				}
				tmp = new ArrayList<>(finalState[0][1]);
				tmpTs = ts[0][1];
				for (int k = 0; k < n - 1; k++) {
					if (!finalState[k + 1][1].isEmpty() && ts[k + 1][1] == j - 1) {
						finalState[k][1] = new ArrayList<>(finalState[k + 1][1]);
						ts[k][1] = j;
					}
				}

				if (!tmp.isEmpty() && tmpTs == j - 1) {
					finalState[n - 1][1] = tmp;
					ts[n - 1][1] = j;
				}

			}

			int[] ans = new int[g];
			for (int k = 0; k < n; k++) {
				int maxTs = Math.max(ts[k][0], ts[k][1]);
				for (int j = 0; j < 2; j++) {
					if (ts[k][j] == maxTs) {
						for (int c : finalState[k][j]) {
							ans[c]++;
						}
					}

				}
			}
			System.out.println("Case #" + i + ": " + str(ans));
		}
		in.close();
	}

	private static void solveLarge() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int g = in.nextInt();
			int m = in.nextInt();
			int mod = m % n;

			List<Integer>[][] finalState = new List[3 * n][2];
			for (int j = 0; j < n; j++) {
				finalState[j] = finalState[j + n] = finalState[j + 2 * n] = new List[] { new ArrayList<Integer>(),
						new ArrayList<Integer>() };
			}
			for (int j = 0; j < g; j++) {
				int start = in.nextInt() - 1;
				if (in.next().equals("C")) {
					finalState[(start + mod) % n][0].add(j);
				} else {
					finalState[(start - mod + n) % n][1].add(j);
				}
			}
			int[] res = solve(n, g, m, finalState);
			System.out.println("Case #" + i + ": " + str(res));
		}
		in.close();
	}

	private static int[] solve(int n, int g, int m, List<Integer>[][] finalState) {

		int ws = Math.min(n, m + 1);

		Queue<Integer> rightQ = new LinkedList<>();

		int right = n;
		while (right < 3 * n) {
			if (!finalState[right][0].isEmpty()) {
				rightQ.offer(right);
			}
			right++;
		}
		int left = n;
		while (left > 0) {
			if (!finalState[left][1].isEmpty()) {
				break;
			}
			left--;
		}
		int i = n;
		while (i < 2 * n) {
			while (!rightQ.isEmpty() && rightQ.peek() <= i) {
				rightQ.poll();
			}
			if (!finalState[i][0].isEmpty() || !finalState[i][1].isEmpty()) {
				if (!finalState[i][1].isEmpty()) {
					left = i;
				}

			} else {
				if (i - left >= ws) {
					if (!rightQ.isEmpty() && rightQ.peek() - i < ws) {
						finalState[i][0] = finalState[rightQ.peek()][0];
					}
				} else if (rightQ.isEmpty() || rightQ.peek() - i >= ws) {
					finalState[i][1] = finalState[left][1];
				} else {
					if (i - left < rightQ.peek() - i) {
						finalState[i][1] = finalState[left][1];
					} else if (i - left > rightQ.peek() - i) {
						finalState[i][0] = finalState[rightQ.peek()][0];
					} else {
						finalState[i][1] = finalState[left][1];
						finalState[i][0] = finalState[rightQ.peek()][0];
					}
				}
			}
			i++;
		}
		int[] ans = new int[g];
		for (int k = n; k < 2 * n; k++) {
			for (int j = 0; j < 2; j++) {
				for (int c : finalState[k][j]) {
					ans[c]++;
				}
			}
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
