package Dec2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class ChefAndInteractiveXOR {
	public static void main(String[] args) {
		test();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			solve(in, n);
		}
		in.close();
	}

	static int[] target;

	private static void test() {
		Scanner in = null;
		// int t = 500;
		// Random r = new Random();
		// for (int i = 0; i < t; i++) {
		// int n = r.nextInt(50000 - 8) + 8;
		// target = new int[n];
		// for (int j = 0; j < target.length; j++) {
		// target[j] = r.nextInt(1 << 30 - 1) + 1;
		// }
		// solve(in, target.length);
		// }
		target = new int[] { 1 << 30, 1, 1, 1, 1 << 30, 1, 1, 1, 1 };
		solve(in, target.length);
	}

	private static void solve(Scanner in, int n) {
		int[] xor = new int[n];
		int[] a = new int[n];
		if (n % 2 == 0) {
			for (int i = 0; i < n / 2; i++) {
				xor[2 * i] = getAnswer(in, 1, new int[] { 2 * i + 1, 2 * i + 2, (2 * i + 2) % n + 1 });
				xor[2 * i + 1] = getAnswer(in, 1, new int[] { 2 * i + 1, 2 * i + 2, (2 * i + 3) % n + 1 });
			}
			int prev = xor[0] ^ xor[1];
			for (int i = 1; i < n / 2 + 1; i++) {
				a[(2 * i + 2) % n] = prev ^ xor[(2 * i) % n];
				a[(2 * i + 3) % n] = prev ^ xor[(2 * i + 1) % n];
				prev = a[(2 * i + 2) % n] ^ a[(2 * i + 3) % n];
			}
		} else {
			for (int i = 0; i < n / 2 - 1; i++) {
				xor[2 * i] = getAnswer(in, 1, new int[] { 2 * i + 1, 2 * i + 2, (2 * i + 2) % n + 1 });
				xor[2 * i + 1] = getAnswer(in, 1, new int[] { 2 * i + 1, 2 * i + 2, (2 * i + 3) % n + 1 });
			}
			xor[n - 3] = getAnswer(in, 1, new int[] { n - 2, n - 1, n });
			xor[n - 2] = getAnswer(in, 1, new int[] { n - 2, n, 1 });
			xor[n - 1] = getAnswer(in, 1, new int[] { n - 1, n, 2 });
			int prev = xor[0] ^ xor[1];
			for (int i = 1; i < n / 2 - 1; i++) {
				a[(2 * i + 2) % n] = prev ^ xor[(2 * i) % n];
				a[(2 * i + 3) % n] = prev ^ xor[(2 * i + 1) % n];
				prev = a[(2 * i + 2) % n] ^ a[(2 * i + 3) % n];
			}
			a[n - 1] = prev ^ xor[n - 3];
			a[0] = a[n - 3] ^ a[n - 1] ^ xor[n - 2];
			a[1] = a[n - 2] ^ a[n - 1] ^ xor[n - 1];
			prev = a[0] ^ a[1];
			a[2] = prev ^ xor[0];
			a[3] = prev ^ xor[1];
		}
		if (getAnswer(in, 2, a) == -1) {
			System.exit(n);
		}
	}

	static int getAnswer(Scanner in, int type, int[] m) {
		// System.out.println(type + " " + getStr(m));
		// return in.nextInt();
		if (type == 1) {
			return target[m[0] - 1] ^ target[m[1] - 1] ^ target[m[2] - 1];
		} else {
			boolean r = Arrays.equals(target, m);
			if (!r) {
				System.out.println(Arrays.toString(target));
				System.out.println(Arrays.toString(m));
				System.out.println();
			}
			return r ? 0 : -1;
		}
	}

	static String getStr(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}
}
