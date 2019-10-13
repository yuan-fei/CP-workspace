package week4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import mooc.EdxIO;

public class IntegerSort {
	public static void main(String[] args) throws Exception {
		solve();
		// generateCase();
	}

	private static void generateCase() throws IOException, FileNotFoundException {
		Random r = new Random();
		try (EdxIO io = EdxIO.create()) {
			for (int i = 0; i < 6000; i++) {
				io.print(" " + r.nextInt(40001));
			}
		}
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int m = io.nextInt();
			int[] A = new int[n];
			int[] B = new int[m];
			for (int i = 0; i < n; i++) {
				A[i] = io.nextInt();
			}
			for (int i = 0; i < m; i++) {
				B[i] = io.nextInt();
			}
			int[] C = new int[n * m];
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < B.length; j++) {
					C[i * m + j] = A[i] * B[j];
				}
			}
			radixSort(C);
			long sum = 0;
			for (int i = 0; i < C.length; i += 10) {
				sum += C[i];
			}
			io.println(sum);
		}
	}

	private static void radixSort(int[] C) {
		sort(C);
	}

	private static void sort(int[] a) {
		int[] aBak = new int[a.length];
		int[][] counters = new int[4][256];
		for (int i = 0; i < 4; i++) {
			getArrayOfDigit(a, i * 8, counters[i]);
			sortWithSatellites(i * 8, 255, counters[i], a, aBak);
		}
	}

	private static void getArrayOfDigit(int[] a, int i, int[] counter) {
		Arrays.stream(a).forEach(n -> counter[getDigit(n, i)]++);
		for (int j = 1; j < counter.length; j++) {
			counter[j] += counter[j - 1];
		}
	}

	private static int getDigit(int n, int i) {
		return (n >> i) & 255;
	}

	public static void sortWithSatellites(int offset, int maxDigit, int[] counter, int[] a, int[] aBak) {
		for (int i = a.length - 1; i >= 0; i--) {
			int digit = getDigit(a[i], offset);
			aBak[counter[digit] - 1] = a[i];
			counter[digit]--;
		}
		System.arraycopy(aBak, 0, a, 0, a.length);
	}
}
