package y2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Inventry {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String s = in.next();
			long r = solve(s);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(String s) {
		int start = s.indexOf(".");
		int cnt = 0;
		long length = 0;
		if (start == -1) {
			return 0;
		} else {
			int target = start - 1;
			for (int i = start; i < s.length(); i++) {
				if (s.charAt(i) == '#') {
					target = Math.max(target + 2, i);
					if (target >= s.length() - 1) {
						return -1;
					} else {
						length += Math.abs(i - target);
						length += Math.abs(target - start - cnt);
					}
					cnt++;
				}
			}
		}
		return length;
	}

	private static int getDistance(String s, int start) {
		int cur = start;
		int cnt = 0;
		for (int i = start; i < s.length(); i++) {
			if (s.charAt(i) == '#') {
				cnt += i - cur;
				cur++;
			}
		}
		return cnt;
	}
}
