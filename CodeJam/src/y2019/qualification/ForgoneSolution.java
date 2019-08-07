package y2019.qualification;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ForgoneSolution {
	public static void main(String[] args) {
		solve();
	}

	private static void test() {
		for (int i = 2; i < 100000001; i++) {
			String[] r = solve(i);
			if (!isValid(Integer.parseInt(r[0])) || !isValid(Integer.parseInt(r[1]))) {
				System.out.println(i);
			}
		}
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			String n = in.next();
			String[] r = solve(n);
			System.out.println("Case #" + i + ": " + r[0] + " " + r[1]);
		}
		in.close();
	}

	private static String[] solve(int n) {
		return solve("" + n);
	}

	private static String[] solve1(int n) {
		String[] r = new String[2];
		for (int i = 1; i <= n; i++) {
			if (isValid(i) && isValid(n - i)) {
				r[0] = "" + i;
				r[1] = "" + (n - i);
			}
		}
		return r;
	}

	private static boolean isValid(int n) {
		while (n != 0) {
			if (n % 10 == 4) {
				return false;
			}
			n /= 10;
		}
		return true;
	}

	private static String[] solve(String n) {
		String[] r = new String[2];
		r[0] = r[1] = "";
		if (n.length() == 1) {
			int d1, d2;
			int d = Integer.parseInt(n);
			d1 = 1;
			if (d == 5) {
				d1 = 2;
			}
			d2 = d - d1;
			r[0] += "" + d1;
			r[1] += "" + d2;
		} else {
			int d = Integer.parseInt(n.substring(0, 2));
			for (int i = 1; i <= d; i++) {
				if (isValid(i) && isValid(d - i)) {
					r[0] += "" + i;
					r[1] += "" + (d - i);
					break;
				}
			}
			for (int i = 2; i < n.length(); i++) {
				d = n.charAt(i) - '0';
				int d1, d2;
				if (d == 4) {
					d1 = 1;
				} else {
					d1 = 0;
				}
				d2 = d - d1;
				r[0] += "" + d1;
				r[1] += "" + d2;
			}
		}

		return r;
	}

	static long mod = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return (a * b) % mod;
	}

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

}
