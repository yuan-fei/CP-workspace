package y2019.qualification;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Cryptopangrams {
	public static void main(String[] args) {
		solveLarge();
		// test();
	}

	private static void test() {
		// int[] c = new int[] { 335779, 335779, 169087, 450781, 2554, 1766, 1473727,
		// 1380263, 497027, 1284337, 1767299,
		// 291931, 31417, 21271, 576229, 1449011, 1449011, 4486871, 4118393, 4118393,
		// 1118461, 944171, 331481,
		// 18779, 190193, 1767299, 1993897, 2128913, 901543, 2181877, 2728949, 3078847,
		// 3787681, 3583451, 2328901,
		// 2328901, 4874497, 3976957, 3671753, 1383073, 1304561, 3463321, 444779,
		// 197653, 1539047, 2923631,
		// 2468041, 3357227, 2951197, 1763537, 2826001, 2259473, 2014433, 1631671,
		// 1266137, 136259, 178, 478,
		// 57121, 50429, 221761, 221761, 126811, 421301, 1304561, 3976957, 4267589,
		// 4555157, 2912837, 1303817,
		// 613621, 497027, 1767299, 2951197, 122909, 113653, 2550169, 3057407, 1609081,
		// 2533961, 5152307, 4267589,
		// 217673, 232933, 1886971, 311699, 169087, 335779, 1383073, 3940081, 4419361,
		// 241217, 150529, 2762, 2554,
		// 2728949, 3271747, 1609081, 1073071, 2461631 };
		// int[] c = new int[] { 2533961, 2533961, 2620757, 2174, 2174, 761987, 1073231,
		// 136259, 93539, 251189, 510743,
		// 1886971, 96247, 26051, 114481, 422957, 1742159, 1383073, 1101271, 2621999,
		// 2621999, 3787681, 1449011,
		// 361201, 1185773, 3099583, 2468041, 3476623, 1060027, 287879, 1284337, 4274,
		// 478, 167539, 715721,
		// 1109827, 2144651, 3940081, 3716417, 2923631, 139819, 196957, 196957, 190193,
		// 2728949, 2131313, 3106009,
		// 1900081, 215431, 269447, 611683, 1154869, 4486871, 165629, 175597, 4216301,
		// 1767299, 1830151, 528907,
		// 510743, 2245987, 2397331, 1092599, 956563, 4814767, 3691241, 136259, 96743,
		// 118483, 171239, 554563,
		// 540443, 2405201, 554563, 781189, 3056153, 2304889, 2785561, 352159, 392671,
		// 444779, 330059, 2169551 };
		// solve(10000, c.length, c);
		for (int i = 0; i < 100; i++) {
			System.out.println(i);
			testOnce();
		}

	}

	private static void testOnce() {
		int[] p = new int[] { 2, 89, 109, 211, 239, 353, 479, 601, 701, 827, 883, 1021, 1051, 1087, 1277, 1381, 1531,
				1571, 1669, 1861, 1973, 1997, 2137, 2213, 2281, 2411 };
		Random r = new Random();

		int l = r.nextInt(75) + 26;
		int[] s = new int[l];
		int[] gs = generateString(l + 1);
		String src = "";
		for (int i = 0; i < gs.length; i++) {
			src += (char) ('A' + gs[i]);
		}

		for (int i = 0; i < l; i++) {
			s[i] = p[gs[i]] * p[gs[i + 1]];
		}
		String des = solveSmall(10000, s.length, s);
		if (!des.equals(src)) {
			System.out.println(Arrays.toString(s));
			System.out.println(src);
			System.out.println(des);
		}
	}

	static int[] generateString(int n) {
		Set<Integer> set = new HashSet<>();
		Random r = new Random();
		int[] s = new int[n];
		for (int i = 0; i < 26; i++) {
			s[i] = i;
		}
		for (int i = 26; i < s.length; i++) {
			s[i] = r.nextInt(26);
			set.add(s[i]);
		}
		return shuffle(s);

	}

	public static int[] shuffle(int[] arr) {
		int[] result = arr;
		Random r = new Random();
		for (int i = 0; i < arr.length; i++) {
			int target = r.nextInt(arr.length - i) + i;
			swap(result, i, target);
		}
		return result;
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	private static void solveLarge() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			String n = in.next();
			int l = in.nextInt();
			BigInteger[] c = getStringArr(in, l);
			String r = solveLarge(n, l, c);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static String solveLarge(String n, int l, BigInteger[] c) {
		BigInteger[] seq = new BigInteger[l + 1];
		Set<BigInteger> seenPrimes = new TreeSet<>();
		Map<BigInteger, Character> dict = new HashMap<>();
		int last = 0;
		for (int i = 1; i < l; i++) {
			if (!c[last].equals(c[i])) {
				BigInteger gcd = c[last].gcd(c[i]);
				seq[last] = c[last].divide(gcd);
				seenPrimes.add(seq[last]);
				break;
			}
			last++;
		}
		for (int i = last - 1; i >= 0; i--) {
			seq[i] = c[i].divide(seq[i + 1]);
			seenPrimes.add(seq[i]);
		}
		for (int i = last; i < l; i++) {
			seq[i + 1] = c[i].divide(seq[i]);
			seenPrimes.add(seq[i + 1]);
		}
		int i = 0;
		for (BigInteger p : seenPrimes) {
			dict.put(p, (char) ('A' + i));
			i++;
		}
		String r = "";
		for (int j = 0; j < seq.length; j++) {
			r += dict.get(seq[j]);
		}
		return r;
	}

	private static void solveSmall() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int l = in.nextInt();
			int[] c = getIntArr(in, l);
			String r = solveSmall(n, l, c);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static String solveSmall(int n, int l, int[] c) {
		int[] seq = new int[l + 1];
		Set<Integer> seenPrimes = new TreeSet<>();
		Map<Integer, Character> dict = new HashMap<>();
		int last = 0;
		for (int i = 1; i < l; i++) {
			if (c[last] != c[i]) {
				int gcd = gcd(c[last], c[i]);
				seq[last] = c[last] / gcd;
				seenPrimes.add(seq[last]);
				break;
			}
			last++;
		}
		for (int i = last - 1; i >= 0; i--) {
			seq[i] = c[i] / seq[i + 1];
			seenPrimes.add(seq[i]);
		}
		for (int i = last; i < l; i++) {
			seq[i + 1] = c[i] / seq[i];
			seenPrimes.add(seq[i + 1]);
		}
		int i = 0;
		for (int p : seenPrimes) {
			dict.put(p, (char) ('A' + i));
			i++;
		}
		String r = "";
		for (int j = 0; j < seq.length; j++) {
			r += dict.get(seq[j]);
		}
		return r;
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

	static BigInteger[] getStringArr(Scanner in, int size) {
		BigInteger[] arr = new BigInteger[size];
		for (int i = 0; i < size; i++) {
			arr[i] = new BigInteger(in.next());
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
