package r2019E;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CherrisMesh {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[][] edges = getIntArr(in, m, 2);
			int r = solve(n, m, edges);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int n, int m, int[][] edges) {
		UnionFindSet<Integer> ufs = new UnionFindSet<>();
		for (int i = 1; i <= n; i++) {
			ufs.makeSet(i);
		}
		for (int[] edge : edges) {
			ufs.union(edge[0], edge[1]);
		}
		int c = ufs.countGroups();
		return n + c - 2;
	}

	static class UnionFindSet<T> {
		Map<T, T> parents = new HashMap<T, T>();
		Map<T, Integer> ranks = new HashMap<T, Integer>();

		public void makeSet(T x) {
			parents.put(x, x);
			ranks.put(x, 1);
		}

		/**
		 * Find representation of a set which contains x while path compression
		 */
		public T findSet(T x) {
			T parent = parents.get(x);
			if (parent == x) {
				return x;
			} else {
				T root = findSet(parent);
				parents.put(x, root);
				return root;
			}
		}

		/**
		 * Union 2 sets with respect to their ranks
		 */
		public void union(T x, T y) {
			T xRoot = findSet(x);
			T yRoot = findSet(y);
			if (xRoot != yRoot) {
				int xRank = ranks.get(x);
				int yRank = ranks.get(y);
				if (xRank > yRank) {
					parents.put(yRoot, xRoot);
				} else {
					parents.put(xRoot, yRoot);
					if (xRank == yRank) {
						ranks.put(yRoot, ranks.get(yRoot) + 1);
					}
				}
			}
		}

		public int countGroups() {
			HashSet<T> set = new HashSet<>();
			for (T k : parents.keySet()) {
				set.add(findSet(k));
			}
			return set.size();
		}
	}

	static long mod = 1000000007;

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

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
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

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
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
}
