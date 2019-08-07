package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class YetAnotherProblemAboutSequences {
	public static void main(String[] args) {
		prepare();
		solve();
	}

	private static void test() {
		// System.out.println(Arrays.toString(Arrays.copyOf(full, 45125)));
		// System.out.println(Arrays.toString(solve(45125)));
		for (int i = 3333; i < 50001; i++) {
			if (!verify(solve(i))) {
				System.out.println(i);
				return;
			}
		}
	}

	private static void solve() {

		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] r = solve(n);
			System.out.println(str(r));
		}
		in.close();
	}

	static int[] primes = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
			79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191,
			193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311,
			313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439,
			443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577,
			587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709,
			719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857,
			859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009,
			1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117,
			1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259,
			1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409,
			1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511, 1523,
			1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637,
			1657, 1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 1747, 1753, 1759, 1777, 1783, 1787,
			1789, 1801, 1811, 1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 1907, 1913, 1931, 1933,
			1949, 1951, 1973, 1979, 1987, 1993, 1997, 1999, 2003, 2011, 2017, 2027, 2029, 2039, 2053, 2063, 2069, 2081,
			2083, 2087, 2089, 2099, 2111, 2113, 2129, 2131, 2137, 2141, 2143, 2153, 2161, 2179, 2203, 2207, 2213, 2221,
			2237, 2239, 2243, 2251, 2267, 2269, 2273, 2281, 2287, 2293, 2297, 2309, 2311, 2333, 2339, 2341, 2347,
			2351 };

	static int[] full = new int[52000];
	static int maxL = 341;
	// static int maxL = 9;

	private static int[] solve(int n) {
		int[] r = Arrays.copyOf(full, n);
		// System.arraycopy(full, 0, r, 0, n - 2);
		int g = gcd(r[n - 1], r[n - 2]);
		r[n - 1] = g * 2347;
		return r;
	}

	private static void prepare() {
		full[0] = 2347 * 2351;
		full[1] = 2 * 2351;
		int idx = 2;
		for (int d = 1; d <= maxL / 2; d++) {
			int i = 0;
			do {
				full[idx] = primes[i] * primes[(i + d) % maxL];
				// System.out.println(i + "," + (i + d) % maxL);
				i = (i + d) % maxL;
				idx++;
			} while (i != 0);
		}
		// System.out.println(idx);

	}

	private static int gcd(int i, int j) {
		if (j == 0) {
			return i;
		} else {
			return gcd(j, i % j);
		}
	}

	static boolean verify(int[] r) {
		int n = r.length;
		for (int i = 0; i < n; i++) {
			if (r[i] < 1) {
				return false;
			}
			// int g = gcd(r[i], r[(i + 1) % n]);
			// if (g == 1 || gcd(g, r[(i + 2) % n]) != 1) {
			// return false;
			// }
		}
		return true;
	}

	static String str(int[] a) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; ++i) {
			sb.append(a[i]);
			sb.append(" ");
		}
		return sb.toString();
	}
}
