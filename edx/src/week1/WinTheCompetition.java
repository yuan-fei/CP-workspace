package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class WinTheCompetition {
	public static void main(String[] args) throws IOException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int n = in.nextInt();
			int[] t = getIntArr(in, n);
			int r = solve(t);
			out.println(r);
		}
	}

	private static int solve(int[] a) {
		Arrays.sort(a);
		int total = 18000;
		int n = 0;
		for (int i = 0; i < a.length; i++) {
			total -= a[i];
			if (total < 0) {
				break;
			} else {
				n++;
			}
		}
		return n;
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
