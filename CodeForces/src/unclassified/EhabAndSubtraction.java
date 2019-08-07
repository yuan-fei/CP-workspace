package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class EhabAndSubtraction {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int k = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; ++i) {
			a[i] = in.nextInt();
		}
		solve(n, k, a);
		in.close();
	}

	private static void solve(int n, int k, int[] a) {
		Arrays.sort(a);
		int last = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] - last != 0) {
				System.out.println(a[i] - last);
				k--;
				if (k == 0) {
					return;
				}
			}
			last = a[i];
		}
		for (; k > 0; k--) {
			System.out.println(0);
		}
	}
}
