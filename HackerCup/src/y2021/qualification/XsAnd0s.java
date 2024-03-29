package y2021.qualification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class XsAnd0s {

	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				String[] board = getStringArr(in, n);
				int[] r = solve(board);
				if (r[1] == 0) {
					out.println("Case #" + i + ": " + "Impossible");
				} else {
					out.println("Case #" + i + ": " + r[0] + " " + r[1]);
				}
			}

		}

	}

	private static int[] solve(String[] board) {
		int n = board.length;
		int minSteps = n + 1;
		int cnt = 0;
		Set<Integer> single = new HashSet<>();
		for (int i = 0; i < n; i++) {
			int steps = 0;
			int lastPoint = -1;
			for (int j = 0; j < n; j++) {
				int c = step(board[i].charAt(j));
				if (c < 0) {
					steps = -1;
					break;
				} else {
					if (c == 1) {
						lastPoint = i * 100 + j;
					}
					steps += c;
				}
			}
			if (steps > 0) {
				if (minSteps > steps) {
					minSteps = steps;
					cnt = 0;
				}
				if (minSteps == steps) {
					if (minSteps == 1) {
						if (!single.contains(lastPoint)) {
							single.add(lastPoint);
							cnt++;
						}
					} else {
						cnt++;
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			int steps = 0;
			int lastPoint = -1;
			for (int j = 0; j < n; j++) {
				int c = step(board[j].charAt(i));
				if (c < 0) {
					steps = -1;
					break;
				} else {
					if (c == 1) {
						lastPoint = j * 100 + i;
					}
					steps += c;
				}
			}
			if (steps > 0) {
				if (minSteps > steps) {
					minSteps = steps;
					cnt = 0;
				}
				if (minSteps == steps) {
					if (minSteps == 1) {
						if (!single.contains(lastPoint)) {
							single.add(lastPoint);
							cnt++;
						}
					} else {
						cnt++;
					}
				}
			}
		}
		return new int[] { minSteps, cnt };
	}

	static int step(char c) {
		switch (c) {
		case 'X':
			return 0;
		case '.':
			return 1;
		case 'O':
			return -1;
		}
		return -1;
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
