package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TreeMap;

public class QualifyingToPreElimination {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int k = in.nextInt();
			int[] m = new int[n];
			for (int j = 0; j < n; j++) {
				m[j] = in.nextInt();
			}
			int r = solve(m, k);
			System.out.println(r);
		}
		in.close();

	}

	private static int solve(int[] m, int k) {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int i = 0; i < m.length; i++) {
			map.put(m[i], map.getOrDefault(m[i], 0) + 1);
		}
		int cnt = 0;
		while (!map.isEmpty() && cnt < k) {
			cnt += map.pollLastEntry().getValue();
		}
		return cnt;
	}

}
