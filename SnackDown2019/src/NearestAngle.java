
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class NearestAngle {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int p = in.nextInt();
			int q = in.nextInt();
			int[] a = new int[n];
			for (int j = 0; j < n; j++) {
				a[j] = in.nextInt();
			}
			int[] r = solve(n, p, q, a);
			if (r.length == 0) {
				System.out.println(-1);
			} else {
				System.out.println(r[0] + " " + r[1] + " " + r[2]);
			}
		}
		in.close();
	}

	private static int[] solve(int n, double p, double q, int[] a) {
		int[] res = new int[0];
		double min = Double.MAX_VALUE;
		Arrays.sort(a);
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				for (int k = j + 1; k < a.length; k++) {
					if (a[i] + a[j] > a[k]) {
						double d = cosine(a[i], a[j], a[k]);
						if (d >= p / q && d < min) {
							min = d;
							res = new int[] { i + 1, j + 1, k + 1 };
						}
						d = cosine(a[i], a[k], a[j]);
						if (d >= p / q && d < min) {
							min = d;
							res = new int[] { i + 1, j + 1, k + 1 };
						}
						d = cosine(a[k], a[j], a[i]);
						if (d >= p / q && d < min) {
							min = d;
							res = new int[] { i + 1, j + 1, k + 1 };
						}
					} else {
						break;
					}
				}
			}
		}
		return res;
	}

	static double cosine(double a, double b, double c) {
		return (a * a + b * b - c * c) / (2 * a * b);
	}
}
