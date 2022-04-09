package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CheflandandAverageDistance {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			String[] houses = new String[n];
			for (int j = 0; j < n; j++) {
				houses[j] = in.next();
			}
			print(solve2(n, m, houses));
		}
		in.close();
	}

	private static int[] solve(int n, int m, String[] houses) {
		int[] r = new int[n + m - 2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (houses[i].charAt(j) == '1') {
					for (int k = i + (j + 1) / m; k < n; k++) {
						for (int l = 0; l < m; l++) {
							if ((l > j || k > i) && houses[k].charAt(l) == '1') {
								r[Math.abs(k - i) + Math.abs(l - j) - 1]++;
							}
						}
					}
				}
			}
		}
		return r;
	}

	private static int[] solve1(int n, int m, String[] houses) {
		int[] r = new int[n + m - 2];
		ArrayList<Integer>[] dense = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			dense[i] = new ArrayList<Integer>();
			for (int j = 0; j < m; j++) {
				if (houses[i].charAt(j) == '1') {
					dense[i].add(j);
				}
			}
		}
		for (int i = 0; i < dense.length; i++) {
			for (int j = 0; j < dense[i].size(); j++) {
				for (int k = i + (j + 1) / dense[i].size(); k < n; k++) {
					for (int l = 0; l < dense[k].size(); l++) {
						if ((l > j || k > i)) {
							r[Math.abs(k - i) + Math.abs(dense[i].get(j) - dense[k].get(l)) - 1]++;
						}
					}
				}
			}
		}
		return r;
	}

	private static int[] solve2(int n, int m, String[] houses) {
		int[] r = new int[n + m - 2];
		for (int i = 0; i < r.length; i++) {
			r[i] = getCount(n, m, houses, i + 1);
		}
		return r;
	}

	private static int getCount(int n, int m, String[] h, int d) {
		int cnt = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (h[i].charAt(j) == '1') {
					for (int k = 0; k < Math.min(d + 1, n - i); k++) {
						if (j + d - k < m && h[i + k].charAt(j + d - k) == '1') {
							cnt++;
						}
						if (j - d + k >= 0 && k != 0 && d != k && h[i + k].charAt(j - (d - k)) == '1') {
							cnt++;
						}
					}
				}
			}
		}
		return cnt;
	}

	static void print(int[] a) {
		String s = "" + a[0];
		for (int i = 1; i < a.length; i++) {
			s += " " + a[i];
		}
		System.out.println(s);
	}

}
