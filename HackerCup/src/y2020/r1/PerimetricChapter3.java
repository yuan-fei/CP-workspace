package y2020.r1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class PerimetricChapter3 {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int N = in.nextInt();
				int K = in.nextInt();
				int[] L = new int[N];
				int[] W = new int[N];
				int[] H = new int[N];
				for (int j = 0; j < K; j++) {
					L[j] = in.nextInt();
				}
				int[] Labcd = getIntArr(in, 4);
				for (int j = 0; j < K; j++) {
					W[j] = in.nextInt();
				}
				int[] Wabcd = getIntArr(in, 4);
				for (int j = 0; j < K; j++) {
					H[j] = in.nextInt();
				}
				int[] Habcd = getIntArr(in, 4);
				int r = solve(N, K, L, W, H, Labcd, Wabcd, Habcd);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static int solve(int n, int k, int[] l, int[] w, int[] h, int[] labcd, int[] wabcd, int[] habcd) {
		fill(l, labcd, k, n);
		fill(w, wabcd, k, n);
		fill(h, habcd, k, n);
		long ans = 1;
		long perimeter = 0;
		TreeMap<Integer, int[]> tm = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			int iStart = l[i];
			int iEnd = l[i] + w[i];
			int start = iStart;
			int end = iEnd;
			Entry<Integer, int[]> first = tm.floorEntry(iStart);
			if (first != null && first.getValue()[0] >= iStart) {
				start = first.getKey();
			}
			long mergedHeight = 0;
			long mergedWidth = 0;
			int firstH = 0;
			int lastEnd = 0;
			int lastH = 0;
			Entry<Integer, int[]> cur = tm.ceilingEntry(start);
			while (cur != null) {
				if (cur.getKey() <= iEnd) {
					if (firstH == 0) {
						firstH = cur.getValue()[1];
					}
					end = Math.max(end, cur.getValue()[0]);
					if (lastEnd == cur.getKey()) {
						mergedHeight = add(mergedHeight, Math.abs(cur.getValue()[1] - lastH));
					} else {
						mergedHeight = add(mergedHeight, (cur.getValue()[1] + lastH));
					}
					mergedWidth = add(mergedWidth, (cur.getValue()[0] - cur.getKey()));
					tm.remove(cur.getKey());
					lastEnd = cur.getValue()[0];
					lastH = cur.getValue()[1];
					cur = tm.ceilingEntry(start);
				} else {
					break;
				}
			}
			if (lastEnd <= iEnd) {
				mergedHeight = add(mergedHeight, lastH);
				// if (cur != null && lastEnd == cur.getKey()) {
				// mergedHeight = add(mergedHeight, Math.abs(cur.getValue()[1] -
				// lastH));
				// } else {
				// mergedHeight = add(mergedHeight, lastH);
				// }
			}

			if (start < iStart) {
				tm.put(start, new int[] { iStart, firstH });
			}

			perimeter = add(perimeter, h[i]);
			// if (iStart > start) {
			// perimeter = add(perimeter, h[i] - firstH);
			// } else {
			// perimeter = add(perimeter, h[i]);
			// }

			tm.put(iStart, new int[] { iEnd, h[i] });
			if (end > iEnd) {
				tm.put(iEnd, new int[] { end, lastH });
			}
			if (iEnd > lastEnd) {
				perimeter = add(perimeter, h[i]);
			} else {
				perimeter = add(perimeter, h[i] - lastH);
			}

			perimeter = add(perimeter, mul(2, add(end - start, -mergedWidth)));
			perimeter = add(perimeter, -mergedHeight);
			// System.out.println(perimeter);
			ans = mul(ans, perimeter);
		}
		return (int) ans;
	}

	private static void fill(int[] a, int[] abcd, int k, int n) {
		for (int i = k; i < n; i++) {
			a[i] = compute(a[i - 2], a[i - 1], abcd);
		}
	}

	private static int compute(long x1, long x2, int[] abcd) {
		long ret = 0;
		ret = (abcd[0] * x1) % abcd[3];
		ret = (ret + (abcd[1] * x2) % abcd[3]) % abcd[3];
		ret = (ret + abcd[2]) % abcd[3] + 1;
		return (int) ret;
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
