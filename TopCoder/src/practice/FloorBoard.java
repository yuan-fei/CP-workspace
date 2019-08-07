package practice;
import java.util.Arrays;

/**
 * Built using CHelper plug-in Actual solution is at the top
 */
public class FloorBoard {
	public static void main(String[] args) {
		String[] rooms = new String[] { ".....", ".....", ".....", ".....", "....." };
		System.out.println(layout(rooms));
		rooms = new String[] { "." };
		System.out.println(layout(rooms));
		rooms = new String[] { "#" };
		System.out.println(layout(rooms));
		rooms = new String[] { ".......", ".#..##.", ".#.....", ".#####.", ".##..#.", "....###" };
		System.out.println(layout(rooms));
		rooms = new String[] { "...#..", "##....", "#.#...", ".#....", "..#...", "..#..#" };
		System.out.println(layout(rooms));
		rooms = new String[] { ".#....", "..#...", ".....#", "..##..", "......", ".#..#." };
		// rooms = new String[] { ".#.", "..#", "..." };
		System.out.println(layout(rooms));
		rooms = new String[] { "#.", ".." };
		System.out.println(layout(rooms));
		rooms = new String[] { "####", "####", "####", "####" };
		System.out.println(layout(rooms));

	}

	static int layout(String[] room) {
		int n = room.length;
		int m = room[0].length();
		int oo = 987654321;
		boolean[][] isBlocked = new boolean[n][m];
		int[][] cur = new int[2][1 << m];
		int[][] prev = new int[2][1 << m];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				isBlocked[i][j] = (room[i].charAt(j) == '#');
			}
		}
		for (int i = 0; i < cur.length; i++) {
			for (int j = 0; j < cur[i].length; j++) {
				cur[i][j] = oo;
				prev[i][j] = oo;
			}
		}
		cur[0][0] = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				int curR = i;
				int curC = j;
				int[][] tmp = cur;
				cur = prev;
				prev = tmp;
				Arrays.fill(cur[0], oo);
				Arrays.fill(cur[1], oo);
				for (int mask = 0; mask < (1 << m); mask++) {
					if (!isBlocked[i - 1][j - 1]) {
						if (curC > 1) {
							if (((mask >> (curC - 1)) & 1) > 0) {
								// v
								cur[0][mask] = Math.min(cur[0][mask], prev[0][mask]);
								cur[0][mask] = Math.min(cur[0][mask], prev[1][mask]);

								// h
								cur[1][mask & ~(1 << (curC - 1))] = Math.min(cur[1][mask & ~(1 << (curC - 1))],
										prev[1][mask]);
								cur[1][mask & ~(1 << (curC - 1))] = Math.min(cur[1][mask & ~(1 << (curC - 1))],
										1 + prev[0][mask]);
							} else {
								// h
								cur[1][mask] = Math.min(cur[1][mask], prev[1][mask]);
								cur[1][mask] = Math.min(cur[1][mask], 1 + prev[0][mask]);

								// v
								cur[0][mask | (1 << (curC - 1))] = Math.min(cur[0][mask | (1 << (curC - 1))],
										1 + prev[1][mask]);
								cur[0][mask | (1 << (curC - 1))] = Math.min(cur[0][mask | (1 << (curC - 1))],
										1 + prev[0][mask]);
							}
						} else {
							if (((mask >> (curC - 1)) & 1) > 0) {
								// v
								cur[0][mask] = Math.min(cur[0][mask], prev[0][mask]);
								cur[0][mask] = Math.min(cur[0][mask], prev[1][mask]);

								// h
								cur[1][mask & ~(1 << (curC - 1))] = Math.min(cur[1][mask & ~(1 << (curC - 1))],
										1 + prev[0][mask]);
								cur[1][mask & ~(1 << (curC - 1))] = Math.min(cur[1][mask & ~(1 << (curC - 1))],
										1 + prev[1][mask]);

							} else {
								// h
								cur[1][mask] = Math.min(cur[1][mask], 1 + prev[0][mask]);
								cur[1][mask] = Math.min(cur[1][mask], 1 + prev[1][mask]);

								// v
								cur[0][mask | (1 << (curC - 1))] = Math.min(cur[0][mask | (1 << (curC - 1))],
										1 + prev[1][mask]);
								cur[0][mask | (1 << (curC - 1))] = Math.min(cur[0][mask | (1 << (curC - 1))],
										1 + prev[0][mask]);
							}
						}
					} else {
						cur[0][mask & ~(1 << (curC - 1))] = Math.min(cur[0][mask & ~(1 << (curC - 1))], prev[0][mask]);
						cur[0][mask & ~(1 << (curC - 1))] = Math.min(cur[0][mask & ~(1 << (curC - 1))], prev[1][mask]);
					}
				}
				// System.out.print(curR + ", " + curC + ", 0 -> ");
				// for (int mask = 0; mask < (1 << m); mask++) {
				// System.out.print(Integer.toBinaryString(mask) + ": " + cur[0][mask] + ", ");
				// }
				// System.out.println();
				// System.out.print(curR + ", " + curC + ", 1 -> ");
				// for (int mask = 0; mask < (1 << m); mask++) {
				// System.out.print(Integer.toBinaryString(mask) + ": " + cur[1][mask] + ", ");
				// }
				// System.out.println();
			}
		}
		int r = Integer.MAX_VALUE;
		for (int mask = 0; mask < (1 << m); mask++) {
			r = Math.min(r, cur[0][mask]);
			r = Math.min(r, cur[1][mask]);
		}
		return r;
	}

	static int layout1(String[] room) {
		int n = room.length;
		int m = room[0].length();
		int oo = 987654321;
		boolean[][] isBlocked = new boolean[n][m];
		int[][][][] dp = new int[2][n + 1][m + 1][1 << m];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				isBlocked[i][j] = (room[i].charAt(j) == '#');
			}
		}
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {
				for (int k = 0; k < dp[i][j].length; k++) {
					for (int l = 0; l < dp[i][j][k].length; l++) {
						dp[i][j][k][l] = oo;
						// if (i == 0 && j == 0) {
						// dp[i][j][k][l] = 0;
						// }
					}
				}
			}
		}
		dp[0][0][m][0] = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				int curR = i;
				int curC = j;
				int lastR = (j == 1) ? i - 1 : i;
				int lastC = (j == 1) ? m : j - 1;
				for (int mask = 0; mask < (1 << m); mask++) {
					if (!isBlocked[i - 1][j - 1]) {
						if (curC > 1) {
							if (((mask >> (curC - 1)) & 1) > 0) {
								// v
								dp[0][curR][curC][mask] = Math.min(dp[0][curR][curC][mask], dp[0][lastR][lastC][mask]);
								dp[0][curR][curC][mask] = Math.min(dp[0][curR][curC][mask], dp[1][lastR][lastC][mask]);

								// h
								dp[1][curR][curC][mask & ~(1 << (curC - 1))] = Math
										.min(dp[1][curR][curC][mask & ~(1 << (curC - 1))], dp[1][lastR][lastC][mask]);
								dp[1][curR][curC][mask & ~(1 << (curC - 1))] = Math.min(
										dp[1][curR][curC][mask & ~(1 << (curC - 1))], 1 + dp[0][lastR][lastC][mask]);
							} else {
								// h
								dp[1][curR][curC][mask] = Math.min(dp[1][curR][curC][mask], dp[1][lastR][lastC][mask]);
								dp[1][curR][curC][mask] = Math.min(dp[1][curR][curC][mask],
										1 + dp[0][lastR][lastC][mask]);

								// v
								dp[0][curR][curC][mask | (1 << (curC - 1))] = Math.min(
										dp[0][curR][curC][mask | (1 << (curC - 1))], 1 + dp[1][lastR][lastC][mask]);
								dp[0][curR][curC][mask | (1 << (curC - 1))] = Math.min(
										dp[0][curR][curC][mask | (1 << (curC - 1))], 1 + dp[0][lastR][lastC][mask]);
							}
						} else {
							if (((mask >> (curC - 1)) & 1) > 0) {
								// v
								dp[0][curR][curC][mask] = Math.min(dp[0][curR][curC][mask], dp[0][lastR][lastC][mask]);
								dp[0][curR][curC][mask] = Math.min(dp[0][curR][curC][mask], dp[1][lastR][lastC][mask]);

								// h
								dp[1][curR][curC][mask & ~(1 << (curC - 1))] = Math.min(
										dp[1][curR][curC][mask & ~(1 << (curC - 1))], 1 + dp[0][lastR][lastC][mask]);
								dp[1][curR][curC][mask & ~(1 << (curC - 1))] = Math.min(
										dp[1][curR][curC][mask & ~(1 << (curC - 1))], 1 + dp[1][lastR][lastC][mask]);

							} else {
								// h
								dp[1][curR][curC][mask] = Math.min(dp[1][curR][curC][mask],
										1 + dp[0][lastR][lastC][mask]);
								dp[1][curR][curC][mask] = Math.min(dp[1][curR][curC][mask],
										1 + dp[1][lastR][lastC][mask]);

								// v
								dp[0][curR][curC][mask | (1 << (curC - 1))] = Math.min(
										dp[0][curR][curC][mask | (1 << (curC - 1))], 1 + dp[1][lastR][lastC][mask]);
								dp[0][curR][curC][mask | (1 << (curC - 1))] = Math.min(
										dp[0][curR][curC][mask | (1 << (curC - 1))], 1 + dp[0][lastR][lastC][mask]);
							}
						}
					} else {
						dp[0][curR][curC][mask & ~(1 << (curC - 1))] = Math
								.min(dp[0][curR][curC][mask & ~(1 << (curC - 1))], dp[0][lastR][lastC][mask]);
						dp[0][curR][curC][mask & ~(1 << (curC - 1))] = Math
								.min(dp[0][curR][curC][mask & ~(1 << (curC - 1))], dp[1][lastR][lastC][mask]);
					}
				}
				System.out.print(curR + ", " + curC + ", 0 -> ");
				for (int mask = 0; mask < (1 << m); mask++) {
					System.out.print(Integer.toBinaryString(mask) + ": " + dp[0][curR][curC][mask] + ", ");
				}
				System.out.println();
				System.out.print(curR + ", " + curC + ", 1 -> ");
				for (int mask = 0; mask < (1 << m); mask++) {
					System.out.print(Integer.toBinaryString(mask) + ": " + dp[1][curR][curC][mask] + ", ");
				}
				System.out.println();
			}
		}
		int r = Integer.MAX_VALUE;
		for (int mask = 0; mask < (1 << m); mask++) {
			r = Math.min(r, dp[0][n][m][mask]);
			r = Math.min(r, dp[1][n][m][mask]);
		}
		return r;
	}

}
