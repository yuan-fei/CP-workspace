package qualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GoGopher {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int a = in.nextInt();
			solveLarge(a, in);
		}
		in.close();
	}

	private static void solveLarge(int area, Scanner in) {
		int width = (int) Math.ceil(((double) area) / 3);
		boolean[][] cells = new boolean[3][width];
		int[] core = new int[width];
		for (int i = 1; i < core.length - 1; i++) {
			core[i] = 9;
		}

		while (true) {
			int pos = findMax(core);
			System.out.println("2 " + (pos + 1));
			int x = in.nextInt();
			int y = in.nextInt();
			if (x == 0 && y == 0) {
				return;
			} else {
				boolean updated = updateCells(cells, x - 1, y - 1);
				if (updated) {
					updateCore(core, x - 1, y - 1);
					// StringBuffer sb = new StringBuffer();
					// for (int i = 1; i < core.length - 1; i++) {
					// sb.append(core[i] + ", ");
					// }
					// System.err.println("core: " + sb);
				}
			}
		}

	}

	private static void updateCore(int[] core, int x, int y) {
		for (int i = y - 1; i <= y + 1; i++) {
			if (i > 0 && i < core.length - 1) {
				core[i]--;
			}
		}
	}

	private static int findMax(int[] core) {
		int index = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 1; i < core.length - 1; i++) {
			if (core[i] > max) {
				max = core[i];
				index = i;
			}
		}
		return index;
	}

	private static boolean updateCells(boolean[][] cells, int x, int y) {
		if (cells[x][y]) {
			return false;
		} else {
			cells[x][y] = true;
			return true;
		}
	}

}
