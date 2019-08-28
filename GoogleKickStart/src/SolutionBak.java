import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class SolutionBak {
	public static void main(String[] args) {
		// test();
		// generateTestCase();
		solve();
	}

	private static void test() {
		int N = 10000;
		Random r = new Random();
		for (int i = 1; i <= N; i++) {
			int j = i + r.nextInt(100);
			long r1 = solve(i, j);
			long r2 = solveSmall(i, j);
			if (r1 != r2) {
				System.out.println(i + ", " + j + " =" + r1 + "," + r2);
			}
		}
	}

	private static void generateTestCase() {
		int N = 100;
		System.out.println(N);
		for (int i = 0; i < N; i++) {
			generateOne(i);
		}
	}

	private static void generateOne(int i) {
		// Random r = new Random();
		i++;
		System.out.println(i + " " + i);
	}

	public static long solveSmall(long L, long R) {
		long ans = 0;
		for (long i = L; i <= R; i++) {
			int diff = 0;
			for (long f : getAllFactors(i)) {
				diff += (f % 2 == 0) ? 1 : -1;
			}
			if (Math.abs(diff) <= 2) {
				ans++;
			}
		}
		return ans;
	}

	public static List<Long> getAllFactors(long n) {
		List<Long> res = new ArrayList<>();
		for (long i = 1; i * i <= n; i++) {
			if (n % i == 0) {
				res.add(i);
				if (i != n / i) {
					res.add(n / i);
				}
			}
		}
		return res;
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
			int L = in.nextInt();
			int R = in.nextInt();
			long res = solve(L, R);
			System.out.println("Case #" + i + ": " + res);
		}
		in.close();
	}

	private static long solve(long L, long R) {
		long ans = 0;
		ans += getDoubleOdd(L, R);
		ans += getSegmentPrime(L, R);
		// '2' is duplicated
		if (L <= 2 && 2 <= R) {
			ans--;
		}
		ans += getSegmentPrime((L + 3) / 4, R / 4);
		return ans;
	}

	private static long getDoubleOdd(long l, long r) {
		if (l % 2 == 1) {
			l++;
		}
		l /= 2;
		if (r % 2 == 1) {
			r--;
		}
		r /= 2;
		if (l > r) {
			return 0;
		}
		if ((r - l) % 2 == 1) {
			return (r - l + 1) / 2;
		} else {
			return (r - l) / 2 + l % 2;
		}

	}

	public static long getSegmentPrime(long start, long end) {
		if (end == 0) {
			return 0;
		}
		int smallPrimesEnd = (int) Math.sqrt(end);
		boolean[] isSmallComposite = new boolean[smallPrimesEnd + 1];
		boolean[] isLargeComposite = new boolean[(int) (end - start + 1)];
		for (int i = 0; start + i < 2; i++) {
			if (start + i != 1) {
				isLargeComposite[i] = true;
			}
		}
		for (int i = 2; i <= smallPrimesEnd; i++) {
			if (!isSmallComposite[i]) {
				for (int j = i * i; j < smallPrimesEnd; j += i) {
					isSmallComposite[j] = true;
				}
			}
			for (long j = Math.max(i, (start + i - 1) / i) * i; j <= end; j += i) {
				isLargeComposite[(int) (j - start)] = true;
			}
		}
		long ans = 0;
		for (int i = 0; i < isLargeComposite.length; i++) {
			if (isLargeComposite[i] == false) {
				ans++;
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
