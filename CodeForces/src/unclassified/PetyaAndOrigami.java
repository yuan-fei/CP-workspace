package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class PetyaAndOrigami {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		long n = in.nextLong();
		long k = in.nextLong();
		long r = solve(n, k);
		System.out.println(r);
		in.close();
	}

	private static long solve(long n, long k) {
		long r = (long) Math.ceil(2.0 * n / k);
		r += (long) Math.ceil(5.0 * n / k);
		r += (long) Math.ceil(8.0 * n / k);
		return r;
	}
}
