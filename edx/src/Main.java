
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class Main {
	private static int N;
	static List<Integer>[] adj;
	static int[] parent;
	static int[] nLeaves;
	static int minDiff = Integer.MAX_VALUE;
	static int total;
	static int max = 0;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	static void generate() {
		try (EdxIO io = EdxIO.create()) {
			N = 1000000;
			io.println(N);
			for (int i = 1; i < N; i++)
				io.println(1 + " " + (i + 1));
		}
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			N = io.nextInt();
			adj = new List[N + 1];
			parent = new int[N + 1];
			nLeaves = new int[N + 1];
			total = N;
			for (int i = 0; i < adj.length; i++) {
				adj[i] = new ArrayList<>();
			}
			for (int i = 0; i < N - 1; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				adj[u].add(v);
				adj[v].add(u);
				if (adj[u].size() == 2) {
					total--;
				}
				if (adj[v].size() == 2) {
					total--;
				}
			}
			if (adj[1].size() == 1) {
				total--;
			}
			// dfs(0, 1);
			postOrder();
			io.println(max);
			// for (int i = 1; i <= N; i++) {
			// minDiff = Math.min(minDiff, Math.abs(total - 2 * nLeaves[i]));
			// }
			// io.println((total - minDiff) / 2);

		}
	}

	private static void dfs(int p, int r) {
		for (int c : adj[r]) {
			if (c != p) {
				dfs(r, c);
				nLeaves[r] += nLeaves[c];
			}
		}
		if (nLeaves[r] == 0) {
			nLeaves[r] = 1;
		}
	}

	static class MyStack {
		int[][] arr;

		public MyStack(int max) {
			arr = new int[max][2];
		}

		int top = -1;

		void push(int r, int ith) {
			++top;
			arr[top][0] = r;
			arr[top][1] = ith;
		}

		boolean isEmpty() {
			return top == -1;
		}

		int[] pop() {
			return arr[top--];
		}

		int[] peek() {
			return arr[top];
		}
	}

	private static void postOrder() {
		MyStack s = new MyStack(N + 5);
		int[] n = { 1, 0 };
		int[] lastPopped = { -1, 0 };
		int[] nextTopChild = new int[2];
		while (n[0] != -1 || !s.isEmpty()) {
			if (n[0] != -1) {
				s.push(n[0], n[1]);
				int cur = n[0];
				int par = parent[cur];
				int first = -1;
				int idx = 0;
				while (adj[cur].size() > idx) {
					if (adj[cur].get(idx) != par) {
						first = adj[cur].get(idx);
						parent[first] = cur;
						break;
					}
					idx++;
				}
				n[0] = first;
				n[1] = idx;
			} else {
				int cur = s.peek()[0];
				int lastIdx = lastPopped[1];
				int par = parent[s.peek()[0]];
				int idx = lastIdx + 1;
				int next = -1;
				while (adj[cur].size() > idx) {
					if (adj[cur].get(idx) != par) {
						next = adj[cur].get(idx);
						parent[next] = cur;
						break;
					}
					idx++;
				}
				nextTopChild[0] = next;
				nextTopChild[1] = idx;
				if (next == -1) {
					lastPopped = s.pop();
					if (visit(lastPopped)) {
						return;
					}
				} else {
					n = nextTopChild;
				}
			}
		}
	}

	private static boolean visit(int[] lastPopped) {
		int u = lastPopped[0];
		if (nLeaves[u] == 0) {
			nLeaves[u]++;
		}
		nLeaves[parent[u]] += nLeaves[u];
		max = Math.max(Math.min(total - nLeaves[u], nLeaves[u]), max);
		if (max == total / 2) {
			return true;
		}
		return false;
	}
}