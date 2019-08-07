package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MaximumDiameterGraph {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		int[] d = new int[n];
		for (int i = 1; i <= n; ++i) {
			d[i] = in.nextInt();
			solve(n, d);
		}
		in.close();
	}

	private static void solve(int n, int[] d) {

		int[][] arr = new int[n][2];
		for (int i = 0; i < arr.length; i++) {
			arr[i][0] = d[i];
			arr[i][1] = i;
		}
		Arrays.sort(arr, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}
		});
		int oneCount = 0;
		int moreThenOneCount = 0;
		for (int i = 0; i < d.length; i++) {
			if (d[i] == 1) {
				oneCount++;
			} else {
				moreThenOneCount += d[i];
			}
		}
		if (oneCount > moreThenOneCount) {
			System.out.println("NO");
		}
		int left = 1;
		int right = arr.length - 1;
		List<int[]> e = new ArrayList<int[]>();
		if (oneCount > 1) {
			arr[0][0]--;
			arr[arr.length - 1][0]--;
			e.add(new int[] { arr[0][1], arr[arr.length - 1][1] });
		} else {
			while (left < right) {
				if (arr[left][0] == 1) {
				}

			}
		}

	}
}
