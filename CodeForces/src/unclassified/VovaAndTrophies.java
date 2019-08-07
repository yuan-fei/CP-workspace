package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VovaAndTrophies {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		String s = in.next();
		long r = solve(n, s);
		System.out.println(r);
		in.close();
	}

	private static long solve(int n, String s) {
		char[] t = s.toCharArray();
		List<Integer> lg = new ArrayList<>();
		List<Integer> ls = new ArrayList<>();
		int i = 0;
		while (i < t.length && t[i] == 'S') {
			i++;
		}
		if (i < s.length()) {
			int start = i;
			for (; i <= s.length(); i++) {
				if (i == s.length() || t[start] != t[i]) {
					if (t[start] == 'G') {
						lg.add(i - start);
					} else {
						ls.add(i - start);
					}
					start = i;
				}
			}
			if (lg.size() == 1) {
				return lg.get(0);
			}
			int max = 0;
			for (int j = 0; j < lg.size() - 1; j++) {
				if (ls.get(j) > 1) {
					max = Math.max(max, lg.get(j) + 1);
				} else {
					int c = lg.get(j) + lg.get(j + 1);
					if (lg.size() > 2) {
						c++;
					}
					max = Math.max(max, c);
				}
			}
			max = Math.max(max, lg.get(lg.size() - 1) + 1);
			return max;
		} else {
			return 0;
		}
	}
}
