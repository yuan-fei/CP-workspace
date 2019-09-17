package srm766;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** https://docs.google.com/document/d/e/2PACX-1vRbWqjm89cJmWguX8IalTv8a6B4n5M8RTpot3InHXFsVMf_WWBJ08jiIlUxTGyRiPayrlsuaWc76__l/pub */
public class BannedBook {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(new BannedBook().passAround(new String[] { "YYNNYNYNN", "YYNNNYNNN",
				"NNYNNNNNN", "NNNYNNNNN", "YNNNYNNNY", "NYNNNYNYN", "YNNNNNYNY", "NNNNNYNYN", "NNNNYNYNY" })));
		// [1, 7, 5, 4, 6, 8, 0, 2, 3]
	}

	public int[] passAround(String[] friend) {
		int N = friend.length;
		int[][] adj = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (friend[i].charAt(j) == 'N') {
					adj[i][j] = 1;
				}
			}
		}
		// adj = multiply(adj, multiply(adj, adj));
		boolean[] visited = new boolean[N];
		List<Integer> res = new ArrayList<>();
		int i = 0;
		while (i < N) {
			if (!visited[i]) {
				dfsLast(adj, visited, i, res);
			}
			i++;
		}
		int[] ans = new int[N];
		for (int j = 0; j < N; j++) {
			ans[j] = res.get(j);
		}
		return ans;
	}

	void dfsLast(int[][] adj, boolean[] visited, int u, List<Integer> res) {
		visited[u] = true;
		for (int v = 0; v < adj.length; v++) {
			if (!visited[v] && adj[u][v] == 0) {
				dfsFirst(adj, visited, v, res);
			}
		}
		res.add(u);
	}

	void dfsFirst(int[][] adj, boolean[] visited, int u, List<Integer> res) {
		res.add(u);
		visited[u] = true;
		for (int v = 0; v < adj.length; v++) {
			if (!visited[v] && adj[u][v] == 0) {
				dfsLast(adj, visited, v, res);
			}
		}
	}

}
