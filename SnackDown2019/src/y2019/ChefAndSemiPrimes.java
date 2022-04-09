package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ChefAndSemiPrimes {

	public static void main(String[] args) {
		twoHalfPrimeSums = prepareTwoHalfPrimeSums();
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			boolean r = solve(n);
			if (r) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
		in.close();
	}

	static Set<Integer> twoHalfPrimeSums;

	private static boolean solve(int n) {
		return twoHalfPrimeSums.contains(n);
	}

	private static Set<Integer> prepareTwoHalfPrimeSums() {
		int[] primes = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79,
				83, 89, 97 };
		List<Integer> hp = new ArrayList<Integer>();
		for (int i = 0; i < primes.length; i++) {
			for (int j = i + 1; j < primes.length; j++) {
				int t = primes[i] * primes[j];
				if (t <= 200) {
					hp.add(t);
				}
			}
		}
		Set<Integer> s = new HashSet<Integer>();
		for (int i = 0; i < hp.size(); i++) {
			for (int j = i; j < hp.size(); j++) {
				int t = hp.get(i) + hp.get(j);
				if (t <= 200) {
					s.add(t);
				}
			}
		}
		return s;
	}

}
