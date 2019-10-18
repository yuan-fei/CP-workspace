package r1237;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TreeSet;

public class TaskB {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = 1;
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int[] a = getIntArr(in, n);
			int[] b = getIntArr(in, n);
			int r = solveWrong(n, a, b);
			System.out.println(r);
		}

		in.close();
	}

	private static int solve(int n, int[] a, int[] b) {
		int[] rank = new int[100005];
		for (int i = 0; i < n; i++) {
			rank[b[i]] = i;
		}
		int overtaken = 0;
		TreeSet<Integer> ts = new TreeSet<>();
		for (int i = 0; i < n; i++) {
			if (ts.higher(rank[a[i]]) != null) {
				overtaken++;
			}
			ts.add(rank[a[i]]);
		}
		return overtaken;
	}

	private static int solveWrong(int n, int[] a, int[] b) {
		int[] rank = new int[100005];
		for (int i = 0; i < a.length; i++) {
			rank[a[i]] = i;
		}
		int overtaken = 0;
		int offset = 0;
		int[] delta = new int[n];
		for (int i = 0; i < n; i++) {
			offset += delta[i];
			if (i < offset + rank[b[i]]) {
				overtaken++;
				if (rank[b[i]] + offset + 1 < n) {
					delta[rank[b[i]] + offset + 1] -= 1;
				}
				offset++;
			}
		}
		return overtaken;
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

}
