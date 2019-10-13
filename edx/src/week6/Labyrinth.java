package week6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import mooc.EdxIO;

public class Labyrinth {
	private static String[] adj;
	private static int W;
	private static int H;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	private static void generate() throws IOException, FileNotFoundException {
		Random r = new Random();
		int N = 25;
		try (EdxIO io = EdxIO.create()) {
			io.println(N);
			for (int i = 0; i < N; i++) {
				String s = "";
				for (int j = 0; j < N; j++) {
					s += r.nextInt(2);
				}
				io.println(s);
			}
		}
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			W = io.nextInt();
			H = io.nextInt();
			adj = new String[H];
			for (int i = 0; i < H; i++) {
				adj[i] = io.next();
			}
			int[][][][] stop = new int[4][H][W][];
			int[][] dir = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
			boolean[][] seen = new boolean[H][W];
			buildHStop(stop);
			buildVStop(stop);
			Queue<int[]> q = new LinkedList<>();
			q.offer(new int[] { 0, 0 });
			seen[0][0] = true;
			int l = 0;
			while (!q.isEmpty()) {
				for (int i = q.size(); i > 0; i--) {
					int[] cur = q.poll();
					int h = cur[0];
					int w = cur[1];
					if (adj[h].charAt(w) == '2') {
						io.println(l);
						return;
					} else {
						for (int j = 0; j < stop.length; j++) {
							int h1 = h + dir[j][0];
							int w1 = w + dir[j][1];
							int hh = stop[j][h][w][0];
							int ww = stop[j][h][w][1];
							if (w1 >= 0 && w1 < W && h1 >= 0 && h1 < H && adj[h1].charAt(w1) != '1' && !seen[hh][ww]) {
								q.offer(new int[] { hh, ww });
								seen[hh][ww] = true;
							}
						}
					}
				}
				l++;
			}
		}
	}

	static void buildVStop(int[][][][] stop) {
		for (int i = 0; i < W; i++) {
			for (int j = 0; j < H; j++) {
				if (j == 0) {
					stop[2][j][i] = new int[] { j, i };
				} else if (adj[j - 1].charAt(i) == '1') {
					stop[2][j][i] = new int[] { j, i };
				} else if (adj[j - 1].charAt(i) == '2') {
					stop[2][j][i] = new int[] { j - 1, i };
				} else {
					stop[2][j][i] = stop[2][j - 1][i];
				}
			}
			for (int j = H - 1; j >= 0; j--) {
				if (j == H - 1) {
					stop[3][j][i] = new int[] { j, i };
				} else if (adj[j + 1].charAt(i) == '1') {
					stop[3][j][i] = new int[] { j, i };
				} else if (adj[j + 1].charAt(i) == '2') {
					stop[3][j][i] = new int[] { j + 1, i };
				} else {
					stop[3][j][i] = stop[3][j + 1][i];
				}
			}
		}
	}

	static void buildHStop(int[][][][] stop) {
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (j == 0) {
					stop[0][i][j] = new int[] { i, j };
				} else if (adj[i].charAt(j - 1) == '1') {
					stop[0][i][j] = new int[] { i, j };
				} else if (adj[i].charAt(j - 1) == '2') {
					stop[0][i][j] = new int[] { i, j - 1 };
				} else {
					stop[0][i][j] = stop[0][i][j - 1];
				}
			}
			for (int j = W - 1; j >= 0; j--) {
				if (j == W - 1) {
					stop[1][i][j] = new int[] { i, j };
				} else if (adj[i].charAt(j + 1) == '1') {
					stop[1][i][j] = new int[] { i, j };
				} else if (adj[i].charAt(j + 1) == '2') {
					stop[1][i][j] = new int[] { i, j + 1 };
				} else {
					stop[1][i][j] = stop[1][i][j + 1];
				}
			}
		}
	}
}