package r2019F;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Flattening {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int N = in.nextInt();
			int K = in.nextInt();
			int[] nums = getIntArr(in, N);
			int r = solve(N, K, nums);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int N, int K, int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
		List<int[]> compression = new ArrayList<>();
		int j = 0;
		for (int i = 0; i < N; i++) {
			if (i > 0 && nums[i] != nums[i - 1]) {
				if (!map.containsKey(nums[i - 1])) {
					map.put(nums[i - 1], map.size());
				}
				compression.add(new int[] { map.get(nums[i - 1]), i - j });
			}
			if (i == 0 || nums[i] != nums[i - 1]) {
				j = i;
			}
		}
		if (!map.containsKey(nums[N - 1])) {
			map.put(nums[N - 1], map.size());
		}
		compression.add(new int[] { map.get(nums[N - 1]), N - j });
		int M = map.size();
		int L = compression.size();
		int[][] pSum = new int[M][L + 1];
		int[] pSumTotal = new int[L + 1];
		for (int i = 1; i <= L; i++) {
			for (int x = 0; x < M; x++) {
				pSum[x][i] = pSum[x][i - 1];
			}
			pSum[compression.get(i - 1)[0]][i] += compression.get(i - 1)[1];
			pSumTotal[i] += pSumTotal[i - 1] + compression.get(i - 1)[1];
		}
		int[][] minRebuild = new int[L + 1][L + 1];
		for (int x = 1; x <= L; x++) {
			for (int y = x; y <= L; y++) {
				minRebuild[x][y] = Integer.MAX_VALUE;
				for (int i = 0; i < M; i++) {
					minRebuild[x][y] = Math.min(minRebuild[x][y],
							(pSumTotal[y] - pSumTotal[x - 1]) - (pSum[i][y] - pSum[i][x - 1]));
				}
			}
		}
		int[][] dp = new int[L + 1][K + 1];
		for (int i = 1; i <= L; i++) {
			dp[i][0] = minRebuild[1][i];
		}
		for (int x = 1; x <= L; x++) {
			for (int k = 1; k <= K; k++) {
				dp[x][k] = 1000;
				for (int y = x - 1; y >= 0; y--) {
					dp[x][k] = Math.min(dp[x][k], dp[y][k - 1] + minRebuild[y + 1][x]);
				}
			}
		}
		return dp[L][K];
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

}
