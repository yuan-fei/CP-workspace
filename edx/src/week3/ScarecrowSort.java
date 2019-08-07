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
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;

import mooc.EdxIO;

public class ScarecrowSort {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		solve();
	}

	private static void solve() {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int k = io.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; ++i) {
				a[i] = io.nextInt();
			}
			int[] b = new int[(n + k - 1) / k];
			for (int i = 0; i < k; ++i) {
				int last = 0;
				for (int j = i; j < n; j += k) {
					b[last++] = a[j];
				}
				Arrays.sort(b, 0, last);
				for (int j = i, t = 0; j < n; j += k, ++t) {
					a[j] = b[t];
				}
			}
			boolean ok = true;
			for (int i = 1; i < n; ++i) {
				if (a[i - 1] > a[i]) {
					ok = false;
					break;
				}
			}
			io.println(ok ? "YES" : "NO");
		}
	}

	private static void solve0(FastScanner in, PrintWriter out) {
		int n = in.nextInt();
		int k1 = in.nextInt();
		int k2 = in.nextInt();
		int A = in.nextInt();
		int B = in.nextInt();
		int C = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();

		PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.comparingInt(o -> -o));
		PriorityQueue<Integer> right = new PriorityQueue<>(Comparator.comparingInt(o -> -o));

		for (int i = 0; i < n; i++) {
			int cur = 0;
			if (i == 0) {
				cur = a;
			} else if (i == 1) {
				cur = b;
			} else {
				cur = A * a + B * b + C;
				a = b;
				b = cur;
			}
			if (left.size() < k1 - 1) {
				left.offer(cur);
			} else {
				int cddt = cur;
				if (!left.isEmpty() && left.peek() > cur) {
					left.offer(cur);
					cddt = left.poll();
				}

				if (right.size() < k2 - k1 + 1) {
					right.offer(cddt);
				} else {
					if (!right.isEmpty() && right.peek() > cddt) {
						right.offer(cddt);
						right.poll();
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		while (!right.isEmpty()) {
			sb.insert(0, right.poll() + " ");
		}
		out.write(sb.toString());
	}

	public static int findKthLargestHelper(int[] arr, int start, int end, int k) {
		int index = partition(arr, start, end);
		if (index == start + k) {
			return arr[index];
		} else if (index < start + k) {
			return findKthLargestHelper(arr, index + 1, end, k - (index - start + 1));
		} else {
			return findKthLargestHelper(arr, start, index - 1, k);
		}
	}

	public static int findKthLargest(int[] arr, int start, int end, int k) {
		int index = -1;
		while (index == start + k) {
			index = partition(arr, start, end);
			if (index < start + k) {
				return findKthLargestHelper(arr, index + 1, end, k - (index - start + 1));
			} else {
				return findKthLargestHelper(arr, start, index - 1, k);
			}
		}
		return arr[index];
	}

	private static int partition(int[] arr, int start, int end) {
		int j = start;
		int mid = new Random().nextInt(end - start + 1) + start;
		swap(arr, mid, end);
		int pivot = arr[end];

		for (int i = start; i < end; i++) {
			if (arr[i] < pivot) {
				swap(arr, i, j);
				j++;
			}
		}
		swap(arr, end, j);
		return j;
	}

	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
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
