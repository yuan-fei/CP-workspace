package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FancyQuotes {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		in.nextLine();
		for (int i = 1; i <= t; ++i) {
			String s = in.nextLine();
			boolean r = solve(s);
			System.out.println(r ? "Real Fancy" : "regularly fancy");

		}
		in.close();
	}

	private static boolean solve(String s) {
		String[] words = s.split("\\W");
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("not")) {
				return true;
			}
		}
		return false;
	}
}
