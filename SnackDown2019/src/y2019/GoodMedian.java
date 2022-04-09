package y2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class GoodMedian {

	public static void main(String[] args) {
		run();
	}

	private static void run() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] a = new int[n];
			for (int j = 0; j < a.length; j++) {
				a[j] = in.nextInt();
			}
			long r = solve(n, a);
			System.out.println(r);
		}
		in.close();
	}

	static long mod = 1000000007;
	private static Map<String, Long> subSeqCache = new HashMap<String, Long>();

	private static long solve(int n, int[] a) {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		long r = fastExpMod(2, n - 1, mod);
		for (int i = 0; i < a.length; i++) {
			if (!map.containsKey(a[i])) {
				map.put(a[i], 0);
			}
			map.put(a[i], map.get(a[i]) + 1);
		}
		int leftCnt = 0;
		for (Entry<Integer, Integer> e : map.entrySet()) {
			int cnt = e.getValue();
			if (cnt > 1) {
				String key = cnt + ":" + Math.min(leftCnt, n - leftCnt - cnt) + ":"
						+ Math.max(leftCnt, n - leftCnt - cnt);
				if (subSeqCache.containsKey(key)) {
					long t = subSeqCache.get(key);
					r = (r + t) % mod;
				} else {
					long v = 0;
					for (int i = 0; i < cnt - 1; i++) {
						for (int j = i + 1; j < cnt; j++) {
							long t = countSubSequence(leftCnt + i, n - leftCnt - j - 1, mod);
							v = (v + t) % mod;
							r = (r + t) % mod;
						}
					}
					subSeqCache.put(key, v);
				}

			}
			leftCnt += cnt;
		}
		return r;
	}

	private static long countSubSequence(int left, int right, long mod) {
		long r = 0;
		long t = 0;
		int min = Math.min(left, right);
		int max = Math.max(left, right);
		t = count(min, max, mod) % mod;
		r = (r + t) % mod;
		return r;
	}

	private static Map<Set<Integer>, Long> cntCache = new HashMap<Set<Integer>, Long>();

	private static long count(int min, int max, long mod) {
		Set<Integer> s = new HashSet<Integer>(Arrays.asList(min, max));
		if (cntCache.containsKey(s)) {
			return cntCache.get(s);
		} else {
			long r = 0;
			for (int i = 0; i <= min; i++) {
				long t = (C(i, min, mod) * C(i, max, mod)) % mod;
				r = (r + t) % mod;
			}
			cntCache.put(s, r);
			return r;
		}
	}

	private static Map<Set<Integer>, Long> cCache = new HashMap<Set<Integer>, Long>();
	private static List<Long> factCache = new ArrayList<Long>();
	private static List<Long> iFactCache = new ArrayList<Long>();

	private static long C(int m, int n, long mod) {

		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			Set<Integer> s = new HashSet<Integer>(Arrays.asList(m, n));
			if (cCache.containsKey(s)) {
				return cCache.get(s);
			}
			long r = 1;
			if (iFactCache.size() == 0) {
				iFactCache.add(1L);
				iFactCache.add(1L);
			}
			if (iFactCache.size() >= m + 1) {
				r = iFactCache.get(m);
			} else {
				r = iFactCache.get(iFactCache.size() - 1);
				for (int i = iFactCache.size(); i <= m; i++) {
					r = (r * inverse(i, mod)) % mod;
					iFactCache.add(r);
				}
			}

			for (int i = 0; i < m; i++) {
				r = (r * (n - i)) % mod;
			}
			cCache.put(s, r);
			return r;
		}
	}

	private static long inverse(long n, long mod) {
		return fastExpMod(n, mod - 2, mod);
	}

	private static long fastExpMod(long base, long exp, long mod) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			return (base * fastExpMod(base, exp - 1, mod)) % mod;
		} else {
			long r = fastExpMod(base, exp / 2, mod);
			return (r * r) % mod;
		}
	}

	private static void print() {
		for (int i = 2; i < 100; i++) {
			for (int j = 1; j < i; j++) {
				System.out.println(i + "," + j + "=" + C(j, i, mod));
			}
		}
	}
}
