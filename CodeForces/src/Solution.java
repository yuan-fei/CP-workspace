
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int[] a = getIntArr(in, n);
			int r = 0;
			System.out.println(r);
		}

		in.close();
	}

	static void debug(Object... o) {
		System.out.println(Arrays.deepToString(o));
	}

	static final long MOD = 1000000007;

	// Math
	static long add(long a, long b) {
		return Math.floorMod(a + b, MOD);
	}

	static long mul(long a, long b) {
		return Math.floorMod(a * b, MOD);
	}

	static long div(long a, long b) {
		return mul(a, inv(b));
	}

	static long inv(long x) {
		return modularMultiplicativeInverse(x, MOD);
	}

	static long pow(long b, long e) {
		return fastPow(b, e, MOD);
	}

	// prerequisite: m is prime
	static long modularMultiplicativeInverse(long x, long m) {
		// fermet theory: x ^ (m - 1) = 1 (mod m)
		return fastPow(x, m - 2, m);
	}

	static long fastPow(long base, long exp, long m) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			long l = base * fastPow(base, exp - 1, m);
			return l % m;
		} else {
			long t = fastPow(base, exp / 2, m);
			return (t * t) % m;
		}
	}

	static long fastPow(long base, long exp) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			long l = base * fastPow(base, exp - 1);
			return l;
		} else {
			long t = fastPow(base, exp / 2);
			return t * t;
		}
	}

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	public static int lowerBound(int[] nums, int target) {
		if (nums.length == 0) {
			return -1;
		}
		int start = 0;
		int end = nums.length - 1;
		while (start + 1 < end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] >= target) {
				end = mid;
			} else if (nums[mid] < target) {
				start = mid;
			}
		}

		if (nums[end] < target || (nums[end] == target && nums[start] < target)) {
			return end;
		} else if (nums[start] <= target) {
			return start;
		} else {
			return -1;
		}
	}

	public static int upperBound(int[] nums, int target) {
		if (nums.length == 0) {
			return -1;
		}
		int start = 0;
		int end = nums.length - 1;
		while (start + 1 < end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] <= target) {
				start = mid;
			} else if (nums[mid] > target) {
				end = mid;
			}
		}

		if (nums[start] > target || (nums[start] == target && nums[end] > target)) {
			return start;
		} else if (nums[end] >= target) {
			return end;
		} else {
			return -1;
		}
	}

	boolean isPrime(long n) {
		if (n == 1)
			return false;
		if (n <= 3)
			return true;
		if (n % 2 == 0)
			return false;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	// sieve
	List<Integer> primes(int n) {
		boolean arr[] = new boolean[n + 1];
		Arrays.fill(arr, true);
		arr[1] = false;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (!arr[i])
				continue;
			for (int j = 2 * i; j <= n; j += i) {
				arr[j] = false;
			}
		}
		LinkedList<Integer> ll = new LinkedList<Integer>();
		for (int i = 1; i <= n; i++) {
			if (arr[i])
				ll.add(i);
		}
		return ll;
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

	/** graph */
	static List<Integer>[] buildAdj(int n, int[][] edges, boolean directed) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int[] e : edges) {
			adj[e[0]].add(e[1]);
			if (!directed) {
				adj[e[1]].add(e[0]);
			}
		}
		return adj;
	}

	static List<Integer>[] buildAdjWithEdgeIndex(int n, int[][] edges, boolean directed) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < edges.length; i++) {
			int[] e = edges[i];
			adj[e[0]].add(i);
			if (!directed) {
				adj[e[1]].add(i);
			}
		}
		return adj;
	}

	static <K> void inc(Map<K, Integer> m, K k) {
		m.put(k, m.getOrDefault(k, 0) + 1);
	}

	static <K> void dec(Map<K, Integer> m, K k) {
		m.put(k, m.getOrDefault(k, 0) - 1);
		if (m.get(k) <= 0) {
			m.remove(k);
		}
	}

}

class DSU {
	int[] parent;

	public DSU(int N) {
		this.parent = new int[N];
		for (int i = 0; i < N; i++) {
			add(i);
		}
	}

	public void add(int x) {
		parent[x] = x;
	}

	public int find(int x) {
		if (parent[x] != x)
			parent[x] = find(parent[x]);
		return parent[x];
	}

	public void union(int x, int y) {
		if (find(x) != find(y)) {
			parent[find(x)] = parent[find(y)];
		}
	}
}
