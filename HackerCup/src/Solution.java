
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution implements Runnable {

	public static void main(String[] args) throws FileNotFoundException {
//		new Thread(null, new Solution(), "Solution", 1L << 32).start();
		new Solution().run();
	}

	public void run() {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int m = in.nextInt();
				int k = in.nextInt();
				int s = in.nextInt();
				if (i == 18) {
					System.out.println(Arrays.asList(n, m, k, s));
				}
				char[][] board = getStringArr(in, n);
				int[][] spells = getIntArr(in, s, 2);
				long r = solve(board, spells, n, m, k, s);
				System.out.println(i);
				out.println("Case #" + i + ": " + r);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static int[][] moves;
	static int[] totalMoves;

	private long solve(char[][] board, int[][] spells, int n, int m, int k, int s) {
		moves = new int[m][n + 3];
		totalMoves = new int[n + 3];
		for (int i = 1; i < n + 2 - k; i++) {
			totalMoves[i] = totalMoves[i - 1] + 1;
		}
		for (int i = n + 3 - k; i < n + 3; i++) {
			totalMoves[i] = totalMoves[i - 1] + 1;
		}
		int ret = 0;
		for (int j = 0; j < m; j++) {
			solve(board, n, m, k, j, moves[j]);
			for (int i = 0; i < n + 3; i++) {
				totalMoves[i] += moves[j][i];
			}
		}
		for (int[] spell : spells) {
			int i = spell[0] - 1;
			int j = spell[1] - 1;
			board[i][j] = (board[i][j] == 'X') ? '.' : 'X';
			int[] newMove = new int[n + 3];
			solve(board, n, m, k, j, newMove);
			int min = n * m;
			for (i = 0; i < n + 3; i++) {
				totalMoves[i] += newMove[i] - moves[j][i];
				moves[j][i] = newMove[i];
				min = Math.min(min, totalMoves[i]);
			}
//			System.out.println(min);
			ret += min;
		}
		return ret;
	}

	private void solve(char[][] board, int n, int m, int k, int j, int[] ret) {
		solveUp(board, n, m, k, j, ret, 0);
		reverse(board);
		solveUp(board, n, m, n + 1 - k, j, ret, n + 2 - k);
		reverse(board);
	}

	private void reverse(char[][] board) {
		int i = 0;
		int j = board.length - 1;
		while (i < j) {
			char[] t = board[i];
			board[i] = board[j];
			board[j] = t;
			i++;
			j--;
		}
	}

	private void solveUp(char[][] board, int n, int m, int k, int j, int[] ret, int start) {
//		ret = new int[n + 2 - k];
		int[] cntBefore = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			cntBefore[i] = cntBefore[i - 1];
			if (board[i - 1][j] == 'X') {
				cntBefore[i]++;
			}
		}
		for (int i = k; i <= n; i++) {
			int move = 0;
			if (cntBefore[i] >= k) {
				move++;
			} else if (board[i - 1][j] == 'X') {
				move++;
			}
			ret[start + i - k] = move;
		}
		int move = 0;
		if (cntBefore[n] >= k) {
			move++;
		}
		ret[start + n + 1 - k] = move;
	}

	final static long mod = 1000000007;

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

	static char[][] getStringArr(Scanner in, int size) {
		char[][] arr = new char[size][];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next().toCharArray();
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
