import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		// generateTestCase();
		solve();
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
		int n = r.nextInt(10) + 1;
		int[] a = new int[n];
		int[][] q = new int[n][2];
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(1024);
		}
		for (int i = 0; i < n; i++) {
			q[i][0] = r.nextInt(n);
			q[i][1] = r.nextInt(1024);
		}
		System.out.println(n + " " + n);
		System.out.println(str(a));
		System.out.println(str(q));
	}

	private static String str(int[][] q) {
		StringBuilder sb = new StringBuilder();
		for (int[] qr : q) {
			sb.append(qr[0] + " " + qr[1] + "\n");
		}
		return sb.toString();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int k = in.nextInt();
			int n = in.nextInt();
			long[] x = getLongArr(in, n);
			long[] c = getLongArr(in, n);
			long res = solve(k, n, x, c);
			System.out.println("Case #" + i + ": " + res);
		}
		in.close();
	}

	private static long solve(int k, int n, long[] x, long[] c) {
		k++;
		Spot[] spots = new Spot[n];
		for (int i = 0; i < n; i++) {
			spots[i] = new Spot(x[i], c[i]);
		}
		Arrays.sort(spots, (a, b) -> Long.compare(a.x, b.x));
		long[] minNegativeSum = new long[n];
		long[] minPositiveSum = new long[n];
		PriorityQueue<Long> q = new PriorityQueue<>((a, b) -> -Long.compare(a, b));
		long sum = 0;
		for (int i = 0; i < n; i++) {
			q.offer(spots[i].negative());
			sum += spots[i].negative();
			if (q.size() > k / 2) {
				sum -= q.poll();
			}
			minNegativeSum[i] = sum;
		}
		sum = 0;
		q.clear();
		for (int i = n - 1; i >= 0; i--) {
			q.offer(spots[i].positive());
			sum += spots[i].positive();
			if (q.size() > k / 2) {
				sum -= q.poll();
			}
			minPositiveSum[i] = sum;
		}
		long minCost = Long.MAX_VALUE;
		if (k % 2 == 0) {
			for (int i = k / 2; i + k / 2 <= n; i++) {
				minCost = Math.min(minCost, minNegativeSum[i - 1] + minPositiveSum[i]);
			}
		} else {
			for (int i = k / 2; i + 1 + k / 2 <= n; i++) {
				minCost = Math.min(minCost, minNegativeSum[i - 1] + minPositiveSum[i + 1] + spots[i].c);
			}
		}

		return minCost;
	}

	static class Spot {
		long x;
		long c;

		public Spot(long x, long c) {
			this.x = x;
			this.c = c;
		}

		public long negative() {
			return c - x;
		}

		public long positive() {
			return c + x;
		}

		@Override
		public String toString() {
			return x + "|" + c;
		}
	}

	private static long solveSmall(int k, int n, long[] x, long[] c) {
		long minCost = Long.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			PriorityQueue<Long> q = new PriorityQueue<>();
			for (int j = 0; j < n; j++)
				if (i != j) {
					q.offer(Math.abs(x[j] - x[i]) + c[j]);
				}
			long cost = c[i];
			for (int l = 0; l < k; l++) {
				cost += q.poll();
			}
			minCost = Math.min(minCost, cost);
		}
		return minCost;
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

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
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
