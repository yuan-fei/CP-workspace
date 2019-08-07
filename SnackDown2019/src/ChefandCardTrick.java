import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChefandCardTrick {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] cards = new int[n];
			for (int j = 0; j < n; j++) {
				cards[j] = in.nextInt();
			}
			boolean r = solve(n, cards);
			System.out.println(r ? "YES" : "NO");
		}
		in.close();
	}

	private static boolean solve(int n, int[] cards) {
		boolean breakFound = false;
		for (int i = 1; i < cards.length; i++) {
			if (cards[i] < cards[i - 1]) {
				if (!breakFound) {
					breakFound = true;
					if (cards[i] > cards[0]) {
						return false;
					}
				} else {
					return false;
				}
			} else {
				if (breakFound && cards[i] > cards[0]) {
					return false;
				}
			}
		}
		return true;
	}
}
