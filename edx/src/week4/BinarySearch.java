package week4;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class BinarySearch {
	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			int N = io.nextInt();
			int[] nums = new int[N];
			for (int i = 0; i < N; i++) {
				nums[i] = io.nextInt();
			}
			int M = io.nextInt();
			for (int i = 0; i < M; i++) {
				int[] res = binarySearch(io.nextInt(), nums);
				io.println(res[0] + " " + res[1]);
			}

		}
	}

	private static int[] binarySearch(int t, int[] nums) {
		int[] ret = new int[2];
		int low = 0;
		int high = nums.length - 1;
		while (low + 1 < high) {
			int mid = low + (high - low) / 2;
			if (nums[mid] >= t) {
				high = mid;
			} else {
				low = mid;
			}
		}
		if (nums[low] == t) {
			ret[0] = low + 1;
		} else if (nums[high] == t) {
			ret[0] = high + 1;
		} else {
			return new int[] { -1, -1 };
		}

		low = 0;
		high = nums.length - 1;
		while (low + 1 < high) {
			int mid = low + (high - low) / 2;
			if (nums[mid] > t) {
				high = mid;
			} else {
				low = mid;
			}
		}
		if (nums[high] == t) {
			ret[1] = high + 1;
		} else if (nums[low] == t) {
			ret[1] = low + 1;
		} else {
			return new int[] { -1, -1 };
		}
		return ret;
	}

}
