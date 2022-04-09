package y2021.qualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LuckyNumber {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int a = in.nextInt();
			int b = in.nextInt();
			int c = in.nextInt();

			System.out.println((a == 7 || b == 7 || c == 7) ? "YES" : "NO");
		}
		in.close();
	}
}
