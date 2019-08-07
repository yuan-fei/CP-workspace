package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class LittlePonyAndHarmonyChest {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[] a = getIntArr(in, n);
		int[] b = solve(n, a);
		System.out.println(str(b));
		in.close();
	}

	private static int[] solve(int n, int[] a) {
		int[] fact = new int[60];
		int[] primes = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59 };
		for (int i = 1; i < fact.length; i++) {
			for (int j = 0; j < primes.length; j++) {
				if (i % primes[j] == 0) {
					fact[i] |= 1 << j;
				}
			}
		}
		int globalMask = (1 << 17) - 1;
		int[][] dp = new int[n + 1][1 << 17];
		int[][] solution = new int[n + 1][1 << 17];
		int[] b = new int[n];
		for (int i = 1; i <= n; i++) {
			Arrays.fill(dp[i], Integer.MAX_VALUE);
			for (int k = 1; k < 60; k++) {
				int maskOtherPrimes = (~fact[k]) & (globalMask);
				int s = maskOtherPrimes;
				int diff = Math.abs(a[i - 1] - k);
				while (s != 0) {
					if (dp[i][fact[k] | s] > dp[i - 1][s] + diff) {
						dp[i][fact[k] | s] = dp[i - 1][s] + diff;
						solution[i][fact[k] | s] = k;
					}
					s = (s - 1) & maskOtherPrimes;
				}
			}
		}
		int min = Integer.MAX_VALUE;
		int mask = 0;
		for (int i = 0; i < dp[n].length; i++) {
			if (min > dp[n][i]) {
				min = dp[n][i];
				b[n - 1] = solution[n][i];
				mask = i;
			}
		}

		for (int i = n - 2; i >= 0; i--) {
			mask = mask & (~fact[b[i + 1]]) & globalMask;
			b[i] = solution[i + 1][mask];
		}
		return b;
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
