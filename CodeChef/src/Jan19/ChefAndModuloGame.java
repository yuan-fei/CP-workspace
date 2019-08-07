package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChefAndModuloGame {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int p = in.nextInt();
			long r = solve(n, p);
			sb.append(r);
			sb.append(" ");
		}
		System.out.println(sb.toString());
		in.close();
	}

	private static long solve(long n, long p) {
		if (n == 1) {
			return p * p * p;
		}
		long x = (n % 2 == 0) ? (n / 2 - 1) : (n / 2);
		long p_n = p - n;
		long p_x = p - x;
		long term = p_n * p_n;
		long r = ((p_n + p_x) * p_x + term);
		if (n == 2) {
			r = (r << 1) + term * p_n;
		}
		return r;
	}
}
