package qualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SavingTheUniverseAgain {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int d = in.nextInt();
			String p = in.next();
			int result = solveLarge(d, p);
			String output = (result < 0) ? "IMPOSSIBLE" : "" + result;
			System.out.println("Case #" + i + ": " + output);
		}
		in.close();

	}

	private static int solveSmall(int d, String p) {
		int cIndex = p.indexOf('C');
		int len = p.length();
		if (cIndex < 0) {
			if (len <= d) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if (d < len - 1) {
				return -1;
			} else {
				int minPos = 2 * (len - 1) - d;
				return Math.max(minPos - cIndex, 0);
			}

		}
	}

	private static int solveLarge(int d, String p) {
		int len = p.length();
		List<Integer> indexes = new ArrayList<Integer>();
		indexes.add(-1);
		for (int index = 0;; index += 1) {
			index = p.indexOf('C', index);
			if (index != -1) {
				indexes.add(index);
			} else {
				break;
			}
		}
		indexes.add(len);
		if (d >= len - indexes.size()) {
			int total = 0;
			int lastIndex = indexes.get(0);
			int harm = 0;
			for (int i = 1; i < indexes.size(); i++) {
				harm = harm > 0 ? harm << 1 : 1;
				total += (indexes.get(i) - lastIndex - 1) * harm;
				lastIndex = indexes.get(i);
			}
			if (total <= d) {
				return 0;
			} else {
				int swap = 0;
				int diff = total - d;
				harm = harm >> 1;

				for (int i = indexes.size() - 2; i >= 0; i--) {
					int cnt = (len + 1 - indexes.get(i) - indexes.size() + i);
					int decrease = cnt * harm;
					if (diff > decrease) {
						swap += cnt;
						diff -= decrease;
						harm = harm >> 1;
					} else {
						swap += Math.ceil(((double) diff) / harm);
						return swap;
					}
				}
				return -1;
			}
		}
		return -1;
	}

}
