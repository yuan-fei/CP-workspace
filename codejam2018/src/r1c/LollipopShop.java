package r1c;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class LollipopShop {
	public static void main(String[] args) {

		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.

		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			solve(n, in);
		}
		in.close();
	}

	private static void solve(int n, Scanner in) {
		boolean[] flavors = new boolean[n];
		int[] states = new int[n];
		Arrays.fill(flavors, true);
		Arrays.fill(states, 0);
		for (int i = 0; i < n; i++) {
			int favorCnt = in.nextInt();
			int[] flavorChoices = new int[favorCnt];
			for (int j = 0; j < favorCnt; j++) {
				flavorChoices[j] = in.nextInt();
			}
			output(flavors, states, flavorChoices);
		}
	}

	private static void output(boolean[] flavors, int[] states, int[] flavorChoices) {
		int min = Integer.MAX_VALUE;
		int sellChoice = -1;
		for (int i = 0; i < flavorChoices.length; i++) {
			states[flavorChoices[i]]++;
			if (flavors[flavorChoices[i]]) {
				if (states[flavorChoices[i]] < min) {
					sellChoice = flavorChoices[i];
					min = states[flavorChoices[i]];
				}

			}
		}
		if (sellChoice != -1) {
			flavors[sellChoice] = false;
		}
		System.out.println(sellChoice);
	}

}
