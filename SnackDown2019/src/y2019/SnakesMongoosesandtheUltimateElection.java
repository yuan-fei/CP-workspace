package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SnakesMongoosesandtheUltimateElection {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int r = solve(in.next());
			String out = "";
			if (r > 0) {
				out = "mongooses";
			} else if (r == 0) {
				out = "tie";
			} else {
				out = "snakes";
			}
			System.out.println(out);
		}
		in.close();
	}

	private static int solve(String s) {
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == 'm') {
				if (i > 0 && chars[i] != chars[i - 1] && chars[i - 1] != 0) {
					if (chars[i] == 's') {
						chars[i] = 0;
					} else {
						chars[i - 1] = 0;
					}
				} else if (i < chars.length - 1 && chars[i] != chars[i + 1]) {
					if (chars[i] == 's') {
						chars[i] = 0;
					} else {
						chars[i + 1] = 0;
					}
				}
			}
		}
		int r = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == 'm') {
				r++;
			} else if (chars[i] == 's') {
				r--;
			}
		}
		return r;
	}

}
