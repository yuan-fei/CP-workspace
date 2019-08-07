package y2019.r1b;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ManhattanCrepeCart {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int p = in.nextInt();
			int q = in.nextInt();
			int[][] h = new int[p][];
			Map<String, Integer> map = new HashMap<>();
			map.put("N", 2);
			map.put("S", -2);
			map.put("E", 1);
			map.put("W", -1);
			for (int j = 0; j < h.length; j++) {
				h[j] = new int[] { in.nextInt(), in.nextInt(), map.get(in.next()) };
			}
			int[] r = solveLarge(p, q, h);
			System.out.println("Case #" + i + ": " + r[0] + " " + r[1]);
		}
		in.close();
	}

	private static int[] solveLarge(int p, int q, int[][] h) {
		return new int[] { getMin(0, p, q, h), getMin(1, p, q, h) };
	}

	private static int getMin(int direction, int p, int q, int[][] h) {
		int minPoint = 0;
		int maxCnt = getMaxCnt(0, h, direction == 1);

		for (int i = 0; i < p; i++) {
			if (Math.abs(h[i][2]) == direction + 1) {
				int cnt = getMaxCnt(h[i][direction] + 1, h, direction == 1);
				if (cnt >= maxCnt) {
					if (cnt > maxCnt || h[i][direction] + 1 < minPoint) {
						minPoint = h[i][direction] + 1;
					}
					maxCnt = cnt;
				}
			}
		}
		return minPoint;
	}

	private static int getMaxCnt(int v, int[][] h, boolean vertical) {
		int cnt = 0;
		for (int i = 0; i < h.length; i++) {
			switch (h[i][2]) {
			case -1:
				if (!vertical && v < h[i][0]) {
					cnt++;
				}
				break;
			case 1:
				if (!vertical && v > h[i][0]) {
					cnt++;
				}
				break;
			case -2:
				if (vertical && v < h[i][1]) {
					cnt++;
				}
				break;
			case 2:
				if (vertical && v > h[i][1]) {
					cnt++;
				}
				break;
			}
		}
		return cnt;
	}

	private static int[] solve(int p, int q, int[][] h) {
		int maxCnt = 0;
		int[] maxPoint = new int[2];
		for (int x = 0; x <= q; x++) {
			for (int y = 0; y <= q; y++) {
				int cnt = getMax(x, y, h);
				if (cnt > maxCnt) {
					maxCnt = cnt;
					maxPoint = new int[] { x, y };
				}
				if (cnt == p) {
					return maxPoint;
				}
			}
		}
		return maxPoint;
	}

	private static int getMax(int x, int y, int[][] h) {
		int cnt = 0;
		for (int i = 0; i < h.length; i++) {
			switch (h[i][2]) {
			case -1:
				if (x < h[i][0]) {
					cnt++;
				}
				break;
			case 1:
				if (x > h[i][0]) {
					cnt++;
				}
				break;
			case -2:
				if (y < h[i][1]) {
					cnt++;
				}
				break;
			case 2:
				if (y > h[i][1]) {
					cnt++;
				}
				break;
			}
		}
		return cnt;
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

	static class FastScanner implements Closeable {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		@Override
		public void close() throws IOException {
			br.close();
		}
	}

}
