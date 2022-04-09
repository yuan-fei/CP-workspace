package y2022.ioForWomen;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class IngredientOptimization {
	public static void main(String[] args) {
		solve();
	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int d = in.nextInt();
			int n = in.nextInt();
			int u = in.nextInt();
			int[][] delivers = getIntArr(in, d, 3);
			int[] orders = getIntArr(in, n);
			int r = solve(d, n, u, delivers, orders);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int solve(int d, int n, int u, int[][] delivers, int[] orders) {
		int[][] events = new int[d * 2 + n][];
		int i = 0;
		TreeMap<int[], Integer> tm = new TreeMap<>(
				(a, b) -> Integer.compare(a[0], b[0]) == 0 ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));
		for (int[] deliver : delivers) {
			events[i] = new int[] { deliver[0], deliver[1], 1, deliver[0] + deliver[2], i };
			i++;
			events[i] = new int[] { deliver[0] + deliver[2], -deliver[1], 2, deliver[0], i - 1 };
			i++;
		}
		for (int j = 0; j < orders.length; j++) {
			int o = orders[j];
			events[i] = new int[] { o, -u, 3, j };
			i++;
		}
		Arrays.sort(events,
				(a, b) -> Integer.compare(a[0], b[0]) != 0 ? Integer.compare(a[0], b[0]) : Integer.compare(a[2], b[2]));

		for (int[] e : events) {
			switch (e[2]) {
			case 1:
				tm.put(new int[] { e[3], e[4] }, e[1]);
				break;
			case 2:
				tm.remove(new int[] { e[0], e[4] });
				break;
			case 3:
				int demand = u;
				while (demand > 0 && !tm.isEmpty()) {
					Entry<int[], Integer> entry = tm.pollFirstEntry();
					int diff = Math.min(demand, entry.getValue());
					demand -= diff;
					if (entry.getValue() > diff) {
						tm.put(entry.getKey(), entry.getValue() - diff);
					}
				}
				if (demand > 0) {
					return e[3];
				}
				break;
			}
		}
		return orders.length;
	}

	private static void test() {
		for (int i = 0; i < 10000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt();
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

	static char[][] getCharArr(Scanner in, int row, int col) {
		char[][] arr = new char[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getCharArr(in, col);
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

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
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
