package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RepeatingCipher {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		String s = in.next();
		String r = solve(n, s);
		System.out.println(r);
		in.close();
	}

	private static String solve(int n, String s) {
		String r = "";
		int l = 1;
		for (int i = 0; i < s.length();) {
			r += s.charAt(i);
			i += l;
			l++;
		}
		return r;
	}
}
