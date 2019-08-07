package r1182;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskB {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int h = in.nextInt();
		int w = in.nextInt();
		String[] t = getStringArr(in, h);
		boolean r = solve(h, w, t);
		System.out.println(r ? "YES" : "NO");
		in.close();
	}

	private static boolean solve(int h, int w, String[] t) {
		int[] dx = new int[] { -1, 0, 0, 1 };
		int[] dy = new int[] { 0, -1, 1, 0 };
		int total = count(h, w, t);
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (t[i].charAt(j) == '*') {
					int s = 1;
					boolean isCenter = true;
					for (int k = 0; k < 4; k++) {
						int l = 0;
						int y = i + dx[k];
						int x = j + dy[k];
						while (0 <= x && x < w && 0 <= y && y < h && t[y].charAt(x) == '*') {
							l++;
							y = y + dx[k];
							x = x + dy[k];
						}
						s += l;
						if (l == 0) {
							isCenter = false;
							break;
						}
					}
					if (isCenter == true && s == total) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static int count(int h, int w, String[] t) {
		int cnt = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (t[i].charAt(j) == '*') {
					cnt++;
				}
			}
		}
		return cnt;
	}

	private static int[] getRow(int h, int w, String[] t) {
		int[] ret = new int[3];
		Arrays.fill(ret, -1);
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (t[i].charAt(j) == '*') {
					if (ret[0] == -1) {
						ret[2] = i;
						ret[0] = j;
						ret[1] = j;
					} else if (ret[2] == i && ret[1] == j - 1) {
						ret[1] = j;
					} else {
						return null;
					}
				}
			}
		}
		if (ret[2] == -1) {
			return null;
		}
		return ret;
	}

	private static int[] getCol(int h, int w, String[] t) {
		int[] ret = new int[3];
		Arrays.fill(ret, -1);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (t[j].charAt(i) == '*') {
					if (ret[0] == -1) {
						ret[2] = i;
						ret[0] = j;
						ret[1] = j;
					} else if (ret[2] == i && ret[1] == j - 1) {
						ret[1] = j;
					} else {
						return null;
					}
				}
			}
		}
		if (ret[2] == -1) {
			return null;
		}
		return ret;
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
