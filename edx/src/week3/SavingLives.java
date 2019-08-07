package week3;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class SavingLives {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		try (FastScanner in = new FastScanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			solve(in, out);
		}
	}

	private static void solve(FastScanner in, PrintWriter out) {
		int n = in.nextInt();
		long[][] arr = new long[n][];
		for (int i = 0; i < n; i++) {
			arr[i] = new long[] { in.nextInt(), in.nextInt() };
		}
		long W = in.nextInt();
		Arrays.sort(arr, Comparator.comparingLong(o -> -o[0]));
		long total = 0;
		for (int i = 0; i < arr.length; i++) {
			total += arr[i][0] * Math.min(arr[i][1], W);
			W -= Math.min(arr[i][1], W);
		}
		out.write("" + total);
	}

	private static long mergeSort(int[] arr, int[] arrBak, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			return mergeSort(arr, arrBak, start, mid) + mergeSort(arr, arrBak, mid + 1, end)
					+ merge(arr, arrBak, start, mid, end);
		}
		return 0;
	}

	private static long merge(int[] arr, int[] arrBak, int start, int mid, int end) {
		long cnt = 0;
		System.arraycopy(arr, start, arrBak, start, end - start + 1);
		int i = start;
		int j = mid + 1;
		int k = start;
		long delta = 0;
		while (i <= mid && j <= end) {
			if (arrBak[i] <= arrBak[j]) {
				arr[k] = arrBak[i];
				cnt += delta;
				i++;
			} else {
				arr[k] = arrBak[j];
				delta += 1;
				j++;
			}
			k++;
		}

		while (i <= mid) {
			arr[k] = arrBak[i];
			cnt += delta;
			i++;
			k++;
		}

		while (j <= end) {
			arr[k] = arrBak[j];
			j++;
			k++;
		}
		// System.out.println(start + ", " + mid + ", " + end + "=" + cnt);
		return cnt;
	}

	static class CircularDoublelinkedList {
		class Node {
			int val;
			Node prv, nxt;

			public Node(int val, Node prv, Node nxt) {
				super();
				this.val = val;
				this.prv = prv;
				this.nxt = nxt;
			}

			@Override
			public String toString() {
				return "" + val;
			}
		}

		Node head, mid;
		int size;

		void append(int v) {
			if (head == null) {
				head = new Node(v, null, null);
				head.nxt = head.prv = head;
			} else {
				Node n = new Node(v, head.prv, head);
				head.prv.nxt = n;
				head.prv = n;
			}
			changeSize(1);
		}

		void removeTail() {
			if (size == 1) {
				head = null;
				mid = null;
			} else {
				head.prv.prv.nxt = head;
				head.prv = head.prv.prv;
			}

			changeSize(-1);
		}

		void rotateHalf() {
			if (size > 1) {
				Node tail = head.prv;
				head = mid.nxt;
				mid = tail;
				if (size % 2 == 1) {
					mid = mid.prv;
				}
			}

		}

		void changeSize(int delta) {
			size += delta;
			if (size < 2) {
				mid = null;
			} else if (size == 2) {
				mid = head;
			} else {
				int stp = size / 2 - (size - delta) / 2;
				if (stp > 0) {
					mid = mid.nxt;
				}
				if (stp < 0) {
					mid = mid.prv;
				}
			}
		}

		@Override
		public String toString() {
			if (head == null) {
				return "";
			}
			StringBuilder sb = new StringBuilder();
			Node n = head;
			sb.append(n.val + " ");
			n = n.nxt;
			while (n != head) {
				sb.append(n.val + " ");
				n = n.nxt;
			}
			return sb.toString();
		}
	}

	static class FastScanner implements Closeable {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		@Override
		public void close() throws IOException {
			br.close();
		}
	}
}
