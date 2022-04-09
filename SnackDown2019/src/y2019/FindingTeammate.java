package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class FindingTeammate {

	public static void main(String[] args) {
		prepare();
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] m = new int[n];
			for (int j = 0; j < n; j++) {
				m[j] = in.nextInt();
			}
			long r = solve(m);
			System.out.println(r);
		}
		in.close();
	}

	static long[] lookup = new long[50001];

	private static long solve(int[] m) {
		Arrays.sort(m);
		long p = 1;
		int start = 0;
		for (int i = 1; i < m.length + 1; i++) {
			if (i == m.length || m[m.length - i - 1] != m[m.length - i]) {
				if (i - start > 1) {
					p = (p * f(start, i - start)) % 1000000007;
				}
				start = i;
			}
		}
		return p;
	}

	private static long solve1(int[] m) {
		Map<Integer, Integer> indexByLevel = new HashMap<Integer, Integer>();
		for (int i = 0; i < m.length; i++) {
			Integer l = indexByLevel.get(m[i]);
			if (l == null) {
				l = 0;
			}
			indexByLevel.put(m[i], l + 1);
		}
		Map<Integer, Integer> indexByCnt = new HashMap<Integer, Integer>();
		for (Entry<Integer, Integer> e : indexByLevel.entrySet()) {
			int cnt = e.getValue();
			if (cnt > 1) {
				Integer r = indexByCnt.get(cnt);
				if (r == null) {
					r = 0;
				}
				indexByCnt.put(cnt, r + 1);
			}
		}
		long p = 0;
		for (Entry<Integer, Integer> e : indexByCnt.entrySet()) {
			p += (f(e.getKey()) * e.getValue()) % 1000000007;
		}
		return p;
	}

	private static void prepare() {
		lookup[0] = 1;
		for (int i = 1; i < lookup.length; i++) {
			lookup[i] = (lookup[i - 1] * (2 * i - 1)) % 1000000007;
		}
	}

	private static long f(int start, int c) {
		if (start % 2 == 1 && c % 2 == 0) {
			long tmp = (lookup[(c - 1) / 2] * c) % 1000000007;
			return (tmp * (c - 1)) % 1000000007;
		}
		return lookup[(c + 1) / 2];
	}

	private static long f(int c) {
		return lookup[(c + 1) / 2];
	}

}
