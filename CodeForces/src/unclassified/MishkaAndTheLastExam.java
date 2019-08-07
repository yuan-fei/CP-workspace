package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MishkaAndTheLastExam {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		long[] b = new long[n / 2];
		for (int i = 0; i < n / 2; ++i) {
			b[i] = in.nextLong();
		}
		long[] a = solve(n, b);
		System.out.println(getStr(a));
		in.close();
	}

	private static long[] solve(int n, long[] b) {
		long[] a = new long[n];
		a[0] = 0;
		a[n - 1] = b[0];
		for (int i = 1; i < b.length; i++) {
			if (b[i] > b[i - 1]) {
				a[n - i - 1] = a[n - i];
				a[i] = b[i] - a[n - i - 1];
			} else {
				a[i] = a[i - 1];
				a[n - i - 1] = b[i] - a[i];
			}
		}
		return a;
	}

	static String getStr(long[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}
}
