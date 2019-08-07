package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class GenerateTests {
	public static void main(String[] args) throws IOException {
		// generateLookup();
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int k = in.nextInt();
			int r = solve(k);
			out.println(r);
		}
	}

	static TreeSet<Integer> indexWithMaxDivisors = new TreeSet<>(Arrays.asList(1, 2, 4, 6, 12, 24, 36, 48, 60, 120, 180,
			240, 360, 720, 840, 1260, 1680, 2520, 5040, 7560, 10080, 15120, 20160, 25200, 27720, 45360, 50400, 55440,
			83160, 110880, 166320, 221760, 277200, 332640, 498960, 554400, 665280, 720720, 1081080, 1441440, 2162160,
			2882880, 3603600, 4324320, 6486480, 7207200, 8648640));

	private static int solve(int k) {
		return k - indexWithMaxDivisors.floor(k) + 1;
	}

	private static void generateLookup() {
		int k = 10000000;
		int[] divisorCnt = new int[k + 1];

		for (int i = 1; i <= k; i++) {
			for (int j = i; j <= k; j += i) {
				divisorCnt[j] += 1;
			}
		}
		List<Integer> maxIdx = new ArrayList<>();
		int max = 0;
		for (int i = 1; i <= k; i++) {
			if (divisorCnt[i] > max) {
				maxIdx.add(i);
				max = divisorCnt[i];
			}
		}
		System.out.println(maxIdx);
	}

	private static int getFactors(int n) {
		int cnt = 0;
		for (int i = 1; i * i <= n; i++) {
			if (n % i == 0) {
				if (i * i == n) {
					cnt += 1;
				} else {
					cnt += 2;
				}

			}
		}
		return cnt;
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
}
