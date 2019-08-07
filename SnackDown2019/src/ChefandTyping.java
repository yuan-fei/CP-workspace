import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChefandTyping {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			String[] words = new String[n];
			for (int j = 0; j < n; j++) {
				words[j] = in.next();
			}
			long r = solve(n, words);
			System.out.println(r);
		}
		in.close();
	}

	private static long solve(int n, String[] words) {
		Map<String, Long> wCost = new HashMap<String, Long>();
		long total = 0;
		for (int i = 0; i < words.length; i++) {
			Long c = wCost.get(words[i]);
			if (c == null) {
				c = getCost(words[i]);
				wCost.put(words[i], c);
			} else {
				c /= 2;
			}
			total += c;
		}
		return total;
	}

	private static long getCost(String s) {
		long c = s.length() * 2;
		for (int i = 1; i < s.length(); i++) {
			if (sameHand(s.charAt(i), s.charAt(i - 1))) {
				c += 2;
			}
		}
		return c;
	}

	private static boolean sameHand(char c1, char c2) {
		return Math.abs(c1 - c2) <= 2;
	}

}
