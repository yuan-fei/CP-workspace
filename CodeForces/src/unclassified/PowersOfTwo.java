package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PowersOfTwo {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int k = in.nextInt();
		List<Integer> r = solve(n, k);
		if (r.size() > 0) {
			System.out.println("YES");
			System.out.println(getStr(r));
		} else {
			System.out.println("NO");
		}

		in.close();
	}

	private static List<Integer> solve(int n, int k) {
		int c = Integer.bitCount(n);
		if (n < k || c > k) {
			return new ArrayList<>();
		} else {
			List<Integer> res = new ArrayList<>();
			boolean enough = false;
			while (n != 0) {
				int b = n & (-n);
				if (enough) {
					res.add(b);
					k--;
				} else if (b + c - 1 <= k) {
					for (int i = 0; i < b; i++) {
						res.add(1);
					}
					k -= b;
				} else {
					add(b, k - c + 1, res);
					k = c - 1;
					enough = true;
				}
				c--;
				n -= b;
			}
			return res;
		}
	}

	private static void add(int b, int k, List<Integer> res) {
		int d = b;
		while (d > k) {
			d >>= 1;
		}
		int s = b / d;
		for (int i = 0; i < k - d; i++) {
			res.add(s / 2);
			res.add(s / 2);
		}
		for (int i = k - d; i < d; i++) {
			res.add(s);
		}
	}

	static String getStr(List<Integer> a) {
		String[] str = new String[a.size()];
		for (int i = 0; i < a.size(); i++) {
			str[i] = a.get(i) + "";
		}
		return String.join(" ", str);
	}
}
