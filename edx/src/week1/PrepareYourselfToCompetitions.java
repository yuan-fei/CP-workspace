package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PrepareYourselfToCompetitions {
	public static void main(String[] args) throws IOException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int n = in.nextInt();
			int[] t = getIntArr(in, n);
			int[] p = getIntArr(in, n);
			int r = solve(n, t, p);
			out.println(r);
		}
	}

	private static int solve(int n, int[] t, int[] p) {
		int max = 0;
		int minDiff = Integer.MAX_VALUE;
		boolean hasP = false;
		boolean hasT = false;
		for (int i = 0; i < n; i++) {
			if (t[i] > p[i]) {
				hasT = true;
			} else {
				hasP = true;
			}
			max += Math.max(t[i], p[i]);
			minDiff = Math.min(minDiff, Math.abs(t[i] - p[i]));
		}
		if (!hasP || !hasT) {
			max -= minDiff;
		}
		return max;
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}
}
