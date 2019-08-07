package week3;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Sorting {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		try (FastScanner in = new FastScanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			solve(in, out);
		}
	}

	private static void solve(FastScanner in, PrintWriter out) {
		int n = in.nextInt();
		int[] arr = new int[n];
		int[] arrBak = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}
		mergeSort(arr, arrBak, 0, arr.length - 1, out);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i] + " ");
		}
		out.write(sb.toString());
	}

	private static void mergeSort(int[] arr, int[] arrBak, int start, int end, PrintWriter out) {
		if (start < end) {
			int mid = (start + end) / 2;
			mergeSort(arr, arrBak, start, mid, out);
			mergeSort(arr, arrBak, mid + 1, end, out);
			merge(arr, arrBak, start, mid, end, out);
		}
	}

	private static void merge(int[] arr, int[] arrBak, int start, int mid, int end, PrintWriter out) {
		System.arraycopy(arr, start, arrBak, start, end - start + 1);
		int i = start;
		int j = mid + 1;
		int k = start;
		while (i <= mid && j <= end) {
			if (arrBak[i] <= arrBak[j]) {
				arr[k] = arrBak[i];
				i++;
			} else {
				arr[k] = arrBak[j];
				j++;
			}
			k++;
		}
		while (i <= mid) {
			arr[k] = arrBak[i];
			i++;
			k++;
		}
		while (j <= end) {
			arr[k] = arrBak[j];
			j++;
			k++;
		}
		out.println((start + 1) + " " + (end + 1) + " " + arr[start] + " " + arr[end]);

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
