package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class APizzaSlice {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int type = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			int z = in.nextInt();
			long[] r = solve(n, type, x, y, z);
			System.out.println(r[0] + " " + r[1]);
		}
		in.close();
	}

	private static long[] solve(long n, int type, long x, long y, long z) {
		long dn = 2 * n + 1;
		long dm = 0;
		if (type == 3) {
			long t = x;
			x = z;
			z = t;
		}
		if ((y - x) * (z - y) > 0) {
			if (type == 1 || type == 3) {
				dm = dn - z;
			} else {
				dm = dn - 2 * y;
			}
		} else {
			if (type == 1 || type == 3) {
				dm = z;
			} else {
				dm = 0;
			}
		}
		long g = gcd(dm, dn);
		return new long[] { dm / g, dn / g };
	}

	public static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}
}
