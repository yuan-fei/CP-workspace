package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LuckyNumberGame {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int a = in.nextInt();
			int b = in.nextInt();
			int[] seq = new int[n];
			for (int j = 0; j < n; j++) {
				seq[j] = in.nextInt();
			}
			boolean r = solve(n, a, b, seq);
			System.out.println(r ? "BOB" : "ALICE");
		}
		in.close();
	}

	private static boolean solve(int n, int a, int b, int[] seq) {
		int multiACnt = 0;
		int multiBCnt = 0;
		int multiABCnt = 0;
		for (int i = 0; i < seq.length; i++) {
			int inc = 0;
			if (seq[i] % a == 0) {
				multiACnt++;
				inc++;
			}
			if (seq[i] % b == 0) {
				multiBCnt++;
				inc++;
			}
			if (inc == 2) {
				multiABCnt++;
				multiACnt--;
				multiBCnt--;
			}
		}
		if (multiACnt + (multiABCnt > 0 ? 1 : 0) > multiBCnt) {
			return true;
		} else {
			return false;
		}

	}

}
