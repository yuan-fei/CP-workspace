package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import mooc.EdxIO;

public class InTheDark {

	static int[] parent;
	static int[] nLeaves;
	static int minDiff = Integer.MAX_VALUE;
	static int total;
	static int max = 0;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			solve(io);
		}
	}

	private static void solve(EdxIO io) {
		int N = io.nextInt();
		char[][] a = new char[N][];
		for (int i = 0; i < N; i++) {
			a[i] = io.next().toCharArray();
		}
		int[] start = new int[2];
		int[] end = new int[2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (a[i][j] == 'A') {
					start = new int[] { i, j };
				}
				if (a[i][j] == 'B') {
					end = new int[] { i, j };
				}
			}
		}
		int[][] dir = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		int[][][] pre = new int[N][N][];
		q.offer(start);
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int x = cur[0];
			int y = cur[1];
			if (a[x][y] == 'B') {
				break;
			}
			for (int i = 0; i < dir.length; i++) {
				int xx = x + dir[i][0];
				int yy = y + dir[i][1];
				if (xx >= 0 && xx < N & yy >= 0 && yy < N && a[xx][yy] != 'W' && !visited[xx][yy]) {
					visited[xx][yy] = true;
					q.offer(new int[] { xx, yy });
					pre[xx][yy] = new int[] { x, y };
				}
			}
		}
		int[] cur = pre[end[0]][end[1]];
		while (a[cur[0]][cur[1]] != 'A') {
			a[cur[0]][cur[1]] = 'P';
			cur = pre[cur[0]][cur[1]];
		}
		for (int i = 0; i < N; i++) {
			io.print(new String(a[i]) + "\n");
		}
	}

}