import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int k = in.nextInt();
			long[] a = getLongArr(in, n);
			long r = solve(n, k, a);
			System.out.println(r);
		}

		in.close();
	}

	private static long solve(int n, int k, long[] a) {
		// dp[i] = min{dp[j]+pSum[i]-pSum[j]-(i-j)*a[j]} 0<=j<=i-k
		// dp[i] = min{-a[j]*i + dp[j]-pSum[j]+j*a[j]}+pSum[i] 0<=j<=i-k

		long[] pSum = new long[n + 1];
		for (int i = 1; i <= n; i++) {
			pSum[i] = pSum[i - 1] + a[i - 1];
		}
		long MAX = 250000000005L;
		long[] dp = new long[n + 1];
		// Arrays.fill(dp, MAX);
		// dp[0] = 0;
		Deque<Line> dq = new LinkedList<Line>();
		// add first valid dp solution
		dq.offer(new Line(0, -a[0], 0));
		for (int i = k; i <= n; i++) {

			// add other valid dp solution, valid solutions starts from the kth element
			if (i - k >= k) {
				Line newLine = new Line(i - k, -a[i - k], dp[i - k] - pSum[i - k] + (i - k) * a[i - k]);
				while (dq.size() >= 2) {
					Line l2 = dq.pollLast();
					Line l1 = dq.peekLast();
					long[] interX13 = newLine.interX(l1);
					long[] interX12 = l2.interX(l1);

					// interX13<=interX12
					if (interX13[1] * interX12[0] > interX13[0] * interX12[1]) {
						dq.offerLast(l2);
						break;
					}
				}
				dq.offerLast(newLine);
			}

			while (dq.size() >= 2) {
				Line first = dq.pollFirst();
				Line second = dq.peekFirst();
				if (first.value(i) < second.value(i)) {
					dq.offerFirst(first);
					break;
				}
			}

			Line best = dq.peekFirst();
			dp[i] = best.value(i) + pSum[i];
		}

		return dp[n];
	}

	static class Line {
		int i;
		long k, b;

		public Line(int i, long k, long b) {
			this.i = i;
			this.k = k;
			this.b = b;
		}

		long value(long x) {
			return k * x + b;
		}

		long[] interX(Line other) {
			long dk = other.k - k;
			long db = b - other.b;
			if (dk < 0) {
				// make sure dk>0
				dk *= -1;
				db *= -1;
			}
			return new long[] { dk, db };
		}

		@Override
		public String toString() {
			return i + "," + k + "," + b;
		}
	}

	private static int solveSmall(int n, int k, int[] a) {
		// dp[i] = min{dp[j]+a[i-1]-a[j]+...a[j+1]-a[j]} 0<=j<=i-k
		// dp[i] = min{dp[j]+pSum[i]-pSum[j]-(i-j)*a[j]} 0<=j<=i-k
		int[] dp = new int[n + 1];
		int[] pSum = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			pSum[i] = pSum[i - 1] + a[i - 1];
		}
		int MAX = 500001;
		for (int i = 1; i < dp.length; i++) {
			dp[i] = MAX;
			for (int j = i - k; j >= 0; j--) {
				dp[i] = Math.min(dp[i], dp[j] + pSum[i] - pSum[j] - (i - j) * a[j]);
			}
		}

		return dp[n];
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

	static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>();
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
