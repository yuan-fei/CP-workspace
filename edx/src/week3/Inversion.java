package week3;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class Inversion {

	public static void main(String[] args) throws Exception {
		// generateLookup();
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		// try (FastScanner in = new FastScanner(new File("input.txt"));
		// PrintWriter out
		// = new PrintWriter("output.txt")) {
		// solve(in, out);
		// }
		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int[] nums = new int[N];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = io.nextInt();
			}
			long res = inversion(N, nums);
			io.println(res);
		}
	}

	private static long inversion(int n, int[] nums) {
		int[] bak = new int[n];
		return mergeSort(nums, bak, 0, n - 1);
	}

	private static long mergeSort(int[] arr, int[] arrBak, int start, int end) {
		long sum = 0;
		if (start < end) {
			int mid = (start + end) / 2;
			sum += mergeSort(arr, arrBak, start, mid);
			sum += mergeSort(arr, arrBak, mid + 1, end);
			sum += merge(arr, arrBak, start, mid, end);
		}
		return sum;
	}

	private static long merge(int[] arr, int[] arrBak, int start, int mid, int end) {
		System.arraycopy(arr, start, arrBak, start, end - start + 1);
		long totalInversion = 0;
		long curInversion = 0;

		int i = start;
		int j = mid + 1;
		int k = start;
		while (i <= mid && j <= end) {
			if (arrBak[i] <= arrBak[j]) {
				totalInversion += curInversion;
				arr[k] = arrBak[i];
				i++;
			} else {
				curInversion++;
				arr[k] = arrBak[j];
				j++;
			}
			k++;
		}
		while (i <= mid) {
			totalInversion += curInversion;
			arr[k] = arrBak[i];
			i++;
			k++;
		}
		while (j <= end) {
			arr[k] = arrBak[j];
			j++;
			k++;
		}
		return totalInversion;
	}

}
