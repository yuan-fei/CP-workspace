package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayStabilization {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		int[] a = new int[t];
		for (int i = 0; i < t; ++i) {
			a[i] = in.nextInt();
		}
		int r = solve(t, a);
		System.out.println(r);
		in.close();
	}

	private static int solve(int t, int[] a) {
		Arrays.sort(a);
		return Math.min(a[t - 1] - a[1], a[t - 2] - a[0]);
	}
}
