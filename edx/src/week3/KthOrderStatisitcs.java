package week3;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import mooc.EdxIO;

public class KthOrderStatisitcs {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		solve();
		// int[] arr = new int[] { 4, 2, 3 };
		// System.out.println(findKthLargest(arr, 0, 2, 2));
		// System.out.println(Arrays.toString(arr));
		// System.out.println(dualPivotPartition(arr, 0, 2));
		// System.out.println(Arrays.toString(arr));
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			solve(io, io);
		}
	}

	private static void solve(EdxIO in, EdxIO out) {
		int n = in.nextInt();
		int k1 = in.nextInt();
		int k2 = in.nextInt();
		int A = in.nextInt();
		int B = in.nextInt();
		int C = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int[] arr = new int[n];
		arr[0] = a;
		arr[1] = b;
		for (int i = 2; i < arr.length; i++) {
			arr[i] = A * a + B * b + C;
			a = b;
			b = arr[i];
		}
		int leftIdx = findKthLargest(arr, 0, arr.length - 1, k1 - 1);
		int rightIdx = findKthLargest(arr, leftIdx, arr.length - 1, k2 - k1);
		List<Integer> res = new ArrayList<>();
		for (int i = leftIdx; i <= rightIdx; i++) {
			res.add(arr[i]);
		}
		Collections.sort(res);

		StringBuilder sb = new StringBuilder();
		for (int i : res) {
			sb.append(i + " ");
		}

		out.print(sb.toString());
	}

	private static int findKthLargest(int[] arr, int start, int end, int k) {
		while (start < end) {
			int index = dualPivotPartition(arr, start, end);
			// System.out.println(index + "," + k + "," + start + "," + end);
			// System.out.println(Arrays.toString(arr));
			if (index < start + k) {
				k -= (index - start + 1);
				start = index + 1;
			} else {
				end = index;
			}
		}
		return start;
	}

	private static int dualPivotPartition(int[] arr, int start, int end) {
		int mid = (end + start) / 2;
		int pivot = arr[mid];

		while (true) {
			while (arr[start] < pivot) {
				start++;
			}
			while (arr[end] > pivot) {
				end--;
			}
			if (start >= end) {
				return end;
			} else {
				swap(arr, start, end);
				start++;
				end--;
			}
		}
	}

	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	static class FastScanner implements Closeable {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		@Override
		public void close() throws IOException {
			br.close();
		}
	}
}
