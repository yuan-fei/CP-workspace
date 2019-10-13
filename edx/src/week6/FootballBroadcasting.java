package week6;

import java.io.FileNotFoundException;
import java.io.IOException;

import mooc.EdxIO;

public class FootballBroadcasting {
	private static Node[][] grid;
	private static int MAX = (int) 1e9 + 2;
	private static int[][] frame;

	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			int W = io.nextInt();
			int H = io.nextInt();
			int N = io.nextInt();

			frame = new int[N][];
			for (int i = 0; i < N; i++) {
				frame[i] = new int[] { io.nextInt(), io.nextInt(), io.nextInt(), io.nextInt() };
			}
			findPath(W, H, N, frame);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= W; i++) {
				for (int j = 1; j <= H; j++) {
					if (grid[i][j] != null) {
						Node n = grid[i][j];
						while (n != null) {
							sb.append(n + "\n");
							n = n.next;
						}
						io.println(sb.toString());
						return;
					}
				}
			}
			io.println("Impossible");
		}
	}

	static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	private static void findPath(int W, int H, int N, int[][] frames) {
		grid = new Node[W + 1][H + 1];
		Node[][] gridBak = new Node[W + 1][H + 1];
		for (int k = N - 1; k >= 0; k--) {
			for (int i = 1; i <= W; i++) {
				for (int j = 1; j <= H; j++) {
					gridBak[i][j] = null;
					if (!inFrame(i, j, frames[k])) {
						for (int[] d : dir) {
							int x = i + d[0];
							int y = j + d[1];
							if (x >= 1 && x <= W && y >= 1 && y <= H && (k == N - 1 || grid[x][y] != null)) {
								gridBak[i][j] = new Node(i, j, grid[x][y]);
								break;
							}
						}
					}
				}
			}
			Node[][] t = grid;
			grid = gridBak;
			gridBak = t;
		}

	}

	private static boolean inFrame(int x, int y, int[] frame) {
		return x >= frame[0] && x <= frame[2] && y >= frame[1] && y <= frame[3];
	}

	static class Node {
		int x;
		int y;
		Node next;

		public Node(int x, int y, Node next) {
			super();
			this.x = x;
			this.y = y;
			this.next = next;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}
}