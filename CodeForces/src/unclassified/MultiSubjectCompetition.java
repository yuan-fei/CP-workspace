package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MultiSubjectCompetition {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int[][] sr = new int[n][2];
		for (int i = 1; i <= n; ++i) {
			sr[i - 1][0] = in.nextInt() - 1;
			sr[i - 1][1] = in.nextInt();
		}
		long res = solve(n, m, sr);
		System.out.println(res);
		in.close();
	}

	private static long solve(int n, int m, int[][] sr) {
		Arrays.sort(sr, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return -Integer.compare(o1[1], o2[1]);
			}
		});
		List<Integer>[] map = new List[m];
		for (int i = 0; i < map.length; i++) {
			map[i] = new ArrayList<Integer>();
		}
		int maxCount = 0;
		for (int i = 0; i < sr.length; i++) {
			map[sr[i][0]].add(sr[i][1]);
			maxCount = Math.max(maxCount, map[sr[i][0]].size());
		}
		Arrays.sort(map, new Comparator<List<Integer>>() {

			@Override
			public int compare(List<Integer> o1, List<Integer> o2) {
				return -Integer.compare(o1.size(), o2.size());
			}
		});
		long max = Integer.MIN_VALUE;
		long[][] maxSum = new long[maxCount + 1][map.length + 1];
		for (int i = 0; i < maxCount; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[j] != null && map[j].size() > i) {

				}
			}
		}

		return Math.max(max, 0);
	}
}
