package y2021.r1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** run this with -Xss256M */
public class Blockchain implements Runnable {

	public static void main(String[] args) throws FileNotFoundException {
//		new Thread(null, new Solution(), "Solution", 1L << 32).start();
		new Blockchain().run();
	}

	public void run() {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int[][] weights = getIntArr(in, n - 1, 3);
//				if (i == 17) {
//					out.println(n);
//					StringBuilder sb = new StringBuilder();
//					for (int[] w : weights) {
//						sb.append(w[0] + " " + w[1] + " " + w[2] + "\n");
//					}
//					out.println(sb.toString());
//					System.exit(0);
//				}
				long r = solve(n, weights);
				out.println("Case #" + i + ": " + r);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final static int MAXW = 20;
	final static long mod = 1000000007;
	static int n;
	static int[][] weights;
	static List<Integer>[] adj;
	static long[][] allPathsCnt;
	static long[] subTreeSum;
	static long ans = 1;

	private static long solve(int nn, int[][] w) {
		ans = 1;
		n = nn;
		weights = w;
		allPathsCnt = new long[n][MAXW + 1];
		subTreeSum = new long[n];
		adj = buildAdj();
		dfs1(0, -1);
		dfs2(0, -1, -1);
		return ans;
	}

	private static void dfs1(int u, int p) {
//		System.out.println(u + "," + p);
		for (int e : adj[u]) {
			int v = weights[e][0] - 1 + weights[e][1] - 1 - u;
			int w = weights[e][2];
			if (v != p) {
				dfs1(v, u);

				long leftCnt = 0;
				long totalCnt = 0;
				long leftWeight = 0;
				long totalWeight = 0;
				long[] curSubTreePathCnt = new long[MAXW + 1];
				for (int i = 1; i <= MAXW; i++) {
					if (w <= i) {
						curSubTreePathCnt[w] = add(curSubTreePathCnt[w], allPathsCnt[v][i]);
					} else {
						curSubTreePathCnt[i] = add(curSubTreePathCnt[i], allPathsCnt[v][i]);
					}
					totalWeight = add(totalWeight, mul(allPathsCnt[u][i], i));
					totalCnt = add(totalCnt, allPathsCnt[u][i]);
				}
				curSubTreePathCnt[w] = add(curSubTreePathCnt[w], 1); // u->v
				subTreeSum[u] = add(subTreeSum[u], subTreeSum[v]); // all v subtree
				// cross subtrees
				for (int i = 1; i <= MAXW; i++) {
					leftCnt = add(leftCnt, allPathsCnt[u][i]);
					leftWeight = add(leftWeight, mul(allPathsCnt[u][i], i));
					subTreeSum[u] = add(subTreeSum[u], mul(mul(add(totalCnt, -leftCnt), curSubTreePathCnt[i]), i));
					subTreeSum[u] = add(subTreeSum[u], mul(leftWeight, curSubTreePathCnt[i]));

				}
				for (int i = 1; i <= MAXW; i++) {
					allPathsCnt[u][i] = add(allPathsCnt[u][i], curSubTreePathCnt[i]);
					// v subtree to u
					subTreeSum[u] = add(subTreeSum[u], mul(curSubTreePathCnt[i], i));
				}
			}
		}
	}

	private static void dfs2(int u, int p, int parentW) {
		if (parentW != -1) {
			long[] curSubTreePathCnt = new long[MAXW + 1];
			long leftCnt = 0;
			long totalCnt = 0;
			long leftWeight = 0;
			long totalWeight = 0;
			// all p - u subtree = all others to u or u subtree
			for (int i = 1; i <= MAXW; i++) {
				if (parentW <= i) {
					curSubTreePathCnt[parentW] = add(curSubTreePathCnt[parentW],
							add(allPathsCnt[p][i], -allPathsCnt[u][i]));
				} else {
					curSubTreePathCnt[i] = add(curSubTreePathCnt[i], add(allPathsCnt[p][i], -allPathsCnt[u][i]));
				}
				totalWeight = add(totalWeight, mul(allPathsCnt[u][i], i));
				totalCnt = add(totalCnt, allPathsCnt[u][i]);
			}
//			curSubTreePathCnt[parentW] = add(curSubTreePathCnt[parentW], 1); // p->u
			long localAns = 0;
			// p subtree to u subtree (exclude u)
			for (int i = 1; i <= MAXW; i++) {
				leftCnt = add(leftCnt, allPathsCnt[u][i]);
				leftWeight = add(leftWeight, mul(allPathsCnt[u][i], i));
				localAns = add(localAns, mul(mul(add(totalCnt, -leftCnt), curSubTreePathCnt[i]), i));
				localAns = add(localAns, mul(leftWeight, curSubTreePathCnt[i]));
			}
			for (int i = 1; i <= MAXW; i++) {
				// p subtree -> u
				localAns = add(localAns, mul(curSubTreePathCnt[i], i));
			}
			ans = mul(ans, add(subTreeSum[0], -localAns));
			for (int i = 1; i <= MAXW; i++) {
				allPathsCnt[u][i] = add(allPathsCnt[u][i], curSubTreePathCnt[i]);
			}
		}

		for (int e : adj[u]) {
			int v = weights[e][0] - 1 + weights[e][1] - 1 - u;
			int w = weights[e][2];
			if (v != p) {
				dfs2(v, u, w);
			}
		}
	}

	static List<Integer>[] buildAdj() {
		List<Integer>[] adj = new List[n];
		for (int i = 0; i < n; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < weights.length; i++) {
			adj[weights[i][0] - 1].add(i);
			adj[weights[i][1] - 1].add(i);
		}
		return adj;
	}

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return (a * b) % mod;
	}

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
		}
		return sb.toString();
	}

	static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
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

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
		}
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Integer>> edges = new HashMap<>();
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<Integer>());
			}
			edges.get(from).add(to);
			if (!directed) {
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Integer>());
				}
				edges.get(to).add(from);
			}

		}
		return edges;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
