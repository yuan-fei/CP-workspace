package r2019C;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class WiggleWalk {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int r = in.nextInt();
			int c = in.nextInt();
			int[] start = getIntArr(in, 2);
			String d = in.next();
			int[] res = solve(n, r, c, start, d);
			System.out.println("Case #" + i + ": " + res[0] + " " + res[1]);
		}
		in.close();
	}

	static int[] dr = new int[] { 1, -1, 0, 0 };
	static int[] dc = new int[] { 0, 0, 1, -1 };

	private static int[] solve(int n, int r, int c, int[] start, String directions) {

		Map<Character, int[]> map = new HashMap<>();
		map.put('E', new int[] { 0, 1 });
		map.put('W', new int[] { 0, -1 });
		map.put('S', new int[] { 1, 0 });
		map.put('N', new int[] { -1, 0 });
		TreeSet<int[]>[] rt = new TreeSet[r + 1];
		TreeSet<int[]>[] ct = new TreeSet[c + 1];
		for (int i = 0; i <= r; i++) {
			rt[i] = new TreeSet<int[]>(Comparator.comparingInt(e -> e[0]));
		}
		for (int i = 0; i <= c; i++) {
			ct[i] = new TreeSet<int[]>(Comparator.comparingInt(e -> e[0]));
		}
		rt[start[0]].add(new int[] { start[1], start[1] });
		ct[start[1]].add(new int[] { start[0], start[0] });
		int[] cur = start;
		for (int i = 0; i < n; i++) {
			int[] d = map.get(directions.charAt(i));
			cur[0] += d[0];
			cur[1] += d[1];
			if (d[0] == 0) {
				int[] sec = rt[cur[0]].floor(new int[] { cur[1], cur[1] });
				if (sec != null && sec[1] >= cur[1]) {
					if (d[1] < 0) {
						cur[1] = sec[0] - 1;
					} else {
						cur[1] = sec[1] + 1;
					}
				}
			} else {
				int[] sec = ct[cur[1]].floor(new int[] { cur[0], cur[0] });
				if (sec != null && sec[1] >= cur[0]) {
					if (d[0] < 0) {
						cur[0] = sec[0] - 1;
					} else {
						cur[0] = sec[1] + 1;
					}
				}
			}
			int[] south = ct[cur[1]].floor(new int[] { cur[0] - 1, cur[0] - 1 });
			if (south != null && south[1] < cur[0] - 1) {
				south = null;
			}
			int[] north = ct[cur[1]].floor(new int[] { cur[0] + 1, cur[0] + 1 });
			if (north != null && north[1] < cur[0] + 1) {
				north = null;
			}
			if (south != null && north != null) {
				ct[cur[1]].remove(north);
				south[1] = north[1];
			} else if (south != null) {
				south[1] = cur[0];
			} else if (north != null) {
				ct[cur[1]].remove(north);
				ct[cur[1]].add(new int[] { north[0] - 1, north[1] });
			} else {
				ct[cur[1]].add(new int[] { cur[0], cur[0] });
			}
			int[] west = rt[cur[0]].floor(new int[] { cur[1] - 1, cur[1] - 1 });
			if (west != null && west[1] < cur[1] - 1) {
				west = null;
			}
			int[] east = rt[cur[0]].floor(new int[] { cur[1] + 1, cur[1] + 1 });
			if (east != null && east[1] < cur[1] + 1) {
				east = null;
			}
			if (west != null && east != null) {
				rt[cur[0]].remove(east);
				west[1] = east[1];
			} else if (west != null) {
				west[1] = cur[1];
			} else if (east != null) {
				rt[cur[0]].remove(east);
				rt[cur[0]].add(new int[] { east[0] - 1, east[1] });
			} else {
				rt[cur[0]].add(new int[] { cur[1], cur[1] });
			}
		}
		return cur;
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
