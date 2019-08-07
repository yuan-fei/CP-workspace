package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LettersRearranging {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			String s = in.next();
			String r = solve(s);
			System.out.println((r == null) ? "-1" : r);
		}
		in.close();
	}

	private static String solve(String s) {
		char[] chars = s.toCharArray();
		if (isPalindrome(chars)) {

			boolean swapped = false;
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] != chars[0]) {
					char t = chars[0];
					chars[0] = chars[i];
					chars[i] = t;
					swapped = true;
					break;
				}
			}
			if (swapped) {
				return String.valueOf(chars);
			} else {
				return null;
			}

		} else {
			return s;
		}
	}

	private static boolean isPalindrome(char[] chars) {
		int left = 0;
		int right = chars.length - 1;
		while (left < right) {
			if (chars[left] != chars[right]) {
				return false;
			}
			left++;
			right--;
		}
		return true;
	}

}
