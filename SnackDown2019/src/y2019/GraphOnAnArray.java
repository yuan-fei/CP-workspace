package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GraphOnAnArray {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] nums = new int[n];
			for (int j = 0; j < n; j++) {
				nums[j] = in.nextInt();
			}
			solve(n, nums);
		}
		in.close();
	}

	private static void solve(int n, int[] a) {
		if (dfsCheck(a)) {
			System.out.println(0);
		} else {
			if (a[0] == 47) {
				a[0] = 43;
			} else {
				a[0] = 47;
			}
			System.out.println(1);
		}
		System.out.println(getArrayStr(a));
	}

	private static String getArrayStr(int[] a) {
		String s = "" + a[0];
		for (int i = 1; i < a.length; i++) {
			s += " " + a[i];
		}
		return s;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static Set<Integer> visited;

	private static boolean dfsCheck(int[] a) {
		visited = new HashSet<Integer>();
		dfs(a, 0);
		return visited.size() == a.length;
	}

	private static void dfs(int[] a, int i) {
		visited.add(i);
		for (int j = 0; j < a.length; j++) {
			if (!visited.contains(j) && gcd(a[i], a[j]) == 1) {
				dfs(a, j);
			}
		}
	}
}
