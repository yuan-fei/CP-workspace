package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CreateATeam {
	public static void main(String[] args) throws IOException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int[][] a = getIntArr(in, 3, 3);
			double r = solve(a);
			out.println(r);
		}
	}

	private static double solve(int[][] a) {
		double max = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j != i) {
					for (int k = 0; k < 3; k++) {
						if (k != i && k != j) {
							max = Math.max(max, Math.sqrt(a[0][i] * a[0][i] + a[1][j] * a[1][j] + a[2][k] * a[2][k]));
						}
					}
				}

			}
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

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}
}
