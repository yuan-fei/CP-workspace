package r2019F;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TeachMe {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int N = in.nextInt();
			int S = in.nextInt();
			int[][] nums = new int[N][];
			for (int j = 0; j < N; j++) {
				int c = in.nextInt();
				nums[j] = getIntArr(in, c);
				Arrays.sort(nums[j]);
			}

			long r = solve(N, S, nums);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static long solve(int N, int S, int[][] nums) {
		Map<String, Integer> cnt = new HashMap<>();
		for (int i = 0; i < N; i++) {
			int[] n = nums[i];
			String state = "";
			for (int k = 0; k < n.length; k++) {
				state += n[k] + ",";
			}
			cnt.put(state, cnt.getOrDefault(state, 0) + 1);
		}
		long ans = 0;
		for (int i = 0; i < N; i++) {
			int[] n = nums[i];
			ans += N;
			for (int j = 1; j < (1 << n.length); j++) {
				String state = "";
				for (int k = 0; k < n.length; k++) {
					if ((j & (1 << k)) != 0) {
						state += n[k] + ",";
					}
				}
				ans -= cnt.getOrDefault(state, 0);
			}
		}

		return ans;
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

}
