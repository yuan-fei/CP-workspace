package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DiceRolling {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int x = in.nextInt();
			int r = solve(x);
			System.out.println(r);
		}
		in.close();
	}

	private static int solve(int x) {
		return x / 2;
	}
}
