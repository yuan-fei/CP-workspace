package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class AlmostRegularBracketSequence {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		String s = in.next();
		int r = solve(n, s);
		System.out.println(r);
		in.close();
	}

	private static int solve(int n, String str) {
		int[] pref = new int[n + 1];
		int[] suf = new int[n + 1];
		boolean[] okp = new boolean[n + 1];
		boolean[] oks = new boolean[n + 1];
		char[] s = str.toCharArray();
		char[] rs = new char[n];

		for (int i = 0; i < s.length; i++) {
			rs[n - i - 1] = (s[i] == '(') ? ')' : '(';
		}

		okp[0] = oks[n] = true;
		for (int i = 0; i < n; i++) {
			pref[i + 1] = pref[i] + (s[i] == '(' ? 1 : -1);
			okp[i + 1] = okp[i] && (pref[i + 1] >= 0);
			suf[n - i - 1] = suf[n - i] + (rs[i] == '(' ? +1 : -1);
			oks[n - i - 1] = oks[n - i] && (suf[n - i - 1] >= 0);
		}
		System.out.println(s);
		System.out.println(Arrays.toString(pref));
		System.out.println(rs);
		System.out.println(Arrays.toString(suf));

		int ans = 0;
		for (int i = 0; i < n; ++i) {
			if (!okp[i] || !oks[i + 1]) {
				continue;
			}
			if (s[i] == '(') {
				if (pref[i] > 0 && pref[i] - 1 - suf[i + 1] == 0) {
					++ans;
				}
			} else {
				if (pref[i] + 1 - suf[i + 1] == 0) {
					++ans;
				}
			}
		}
		return ans;
	}

	private static int solve1(int n, String str) {
		Stack<Integer> s = new Stack<>();
		char[] chars = str.toCharArray();
		int pos = Integer.MIN_VALUE;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '(') {
				s.push(i + 1);
			} else {
				if (!s.isEmpty() && s.peek() > 0) {
					s.pop();
				} else {
					s.push(-i - 1);
				}
			}
		}
		if (s.size() == 2) {
			int r = s.pop();
			int l = s.pop();
			if (l < 0 && r < 0) {
				int cnt = 0;
				for (int i = -l - 1; i >= 0; i--) {
					if (chars[i] == ')') {
						cnt++;
					}
				}
				return cnt;
			}
			if (l > 0 && r > 0) {
				int cnt = 0;
				for (int i = r - 1; i < chars.length; i++) {
					if (chars[i] == '(') {
						cnt++;
					}
				}
				return cnt;
			}
			return 0;
		}
		return 0;
	}

	static String getStr(long[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}
}
