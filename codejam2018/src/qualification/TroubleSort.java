package qualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TroubleSort {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] arr = new int[n];
			for (int j = 0; j < n; j++) {
				arr[j] = in.nextInt();
			}
			int result = solveLarge(n, arr);
			String output = result < 0 ? "OK" : "" + result;
			System.out.println("Case #" + i + ": " + output);
		}
		in.close();

	}

	private static int solveSmall(int n, int[] a) {
		troubleSort(a);
		for (int i = 0; i < n - 1; i++) {
			if (a[i] > a[i + 1]) {
				return i;
			}
		}
		return -1;
	}

	private static int solveLarge(int n, int[] a) {
		PriorityQueue<Integer> heapOdd = new PriorityQueue<Integer>();
		PriorityQueue<Integer> heapEven = new PriorityQueue<Integer>();
		for (int i = 0; i < a.length; i++) {
			if (i % 2 == 0) {
				heapEven.offer(a[i]);
			} else {
				heapOdd.offer(a[i]);
			}
		}

		int last = Integer.MIN_VALUE;
		int index = 0;
		while (true) {
			int e;
			if (index % 2 == 0) {
				if (!heapEven.isEmpty()) {
					e = heapEven.poll();
				} else {
					return -1;
				}
			} else {
				if (!heapOdd.isEmpty()) {
					e = heapOdd.poll();
				} else {
					return -1;
				}
			}
			if (e < last) {
				return index - 1;
			}
			last = e;
			index++;
		}
	}

	private static int troubleSort(int[] arr) {
		boolean done = false;
		while (!done) {
			done = true;
			for (int i = 0; i < arr.length - 2; i++) {
				if (arr[i] > arr[i + 2]) {
					done = false;
					swap(arr, i, i + 2);
				}
			}
		}
		return 0;
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

}
