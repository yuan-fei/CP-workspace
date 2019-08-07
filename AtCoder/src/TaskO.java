
/**
 * https://atcoder.jp/contests/dp/tasks/dp_o
 * 
 * Problem Statement
*
* There are N men and N women, both numbered 1,2,…,N.
*
* For each i,j (1≤i,j≤N), the compatibility of Man i and Woman j is given as an integer a[i,j]. If a[i,j]=1, Man i and Woman j are compatible; if ai,j=0, they are not.
*
* Taro is trying to make N pairs, each consisting of a man and a woman who are compatible. Here, each man and each woman must belong to exactly one pair.Find the number of ways in which Taro can make N pairs, modulo 109+7.
*
* Constraints
*
* All values in input are integers.
*
* 1≤N≤21
*
* ai,j is 0 or 1.
* 
* bitDP
 * */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskO {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int[][] a = getIntArr(in, n, n);
		long r = solve(n, a);
		System.out.println(r);
		in.close();
	}

	static long mod = 1000000007;

	private static long solve(int n, int[][] a) {
		long[] dp = new long[1 << n];
		dp[0] = 1;
		for (int mask = 0; mask < 1 << n; mask++) {
			int ones = Integer.bitCount(mask);
			for (int j = 0; j < n; j++) {
				if ((mask & (1 << j)) != 0 && a[ones - 1][j] == 1) {
					int subMask = mask ^ (1 << j);
					dp[mask] = add(dp[mask], dp[subMask]);
				}
			}
		}
		return dp[(1 << n) - 1];
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
