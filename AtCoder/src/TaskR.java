
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskR {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		long k = in.nextLong();
		long[][] matrix = getIntArr(in, n, n);
		long r = solve(n, k, matrix);
		System.out.println(r);
		in.close();
	}

	static long mod = 1000000007;

	private static long solve(int n, long k, long[][] matrix) {
		long[][] r = fastMatrixExp(matrix, k);
		long sum = 0;
		for (int i = 0; i < r.length; i++) {
			for (int j = 0; j < r[i].length; j++) {
				sum = (sum + r[i][j]) % mod;
			}
		}
		return sum;
	}

	public static long[][] fastMatrixExp(long[][] a, long exp) {
		if (exp == 0) {
			long[][] b = new long[a.length][a.length];
			for (int i = 0; i < a.length; i++) {
				b[i][i] = 1;
			}
			return b;
		} else if (exp % 2 == 1) {
			return multiplyMatrice(a, fastMatrixExp(a, exp - 1));
		} else {
			long[][] half = fastMatrixExp(a, exp / 2);
			return multiplyMatrice(half, half);
		}
	}

	private static long[][] multiplyMatrice(long[][] a, long[][] b) {
		long[][] mul = new long[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < a[i].length; k++) {
					mul[i][j] = (mul[i][j] + (a[i][k] * b[k][j]) % mod) % mod;
				}
			}
		}
		return mul;
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static long[] getIntArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static long[][] getIntArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
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
