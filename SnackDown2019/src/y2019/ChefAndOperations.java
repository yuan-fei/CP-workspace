package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChefAndOperations {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] a = new int[n];
			int[] b = new int[n];
			for (int j = 0; j < n; j++) {
				a[j] = in.nextInt();
			}
			for (int j = 0; j < n; j++) {
				b[j] = in.nextInt();
			}
			boolean r = solve(a, b);
			String outStr = r ? "TAK" : "NIE";
			System.out.println(outStr);
		}
		in.close();
	}

	private static boolean solve(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			b[i] -= a[i];
		}
		int i = 0;
		while (i < b.length) {
			if (b[i] != 0) {
				if (b[i] < 0) {
					return false;
				} else {
					for (int j = 3; j > 0; j--) {
						if (i + j - 1 < b.length) {
							b[i + j - 1] -= (b[i] * j);
						} else {
							return false;
						}
					}
				}
			}
			i++;
		}
		return true;
	}

}
