package week3;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;

import mooc.EdxIO;

public class KthOrderStatisitcs {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		solve();
		// int[] arr = new int[] { 4, 2, 3 };
		// System.out.println(findKthLargest(arr, 0, 2, 2));
		// System.out.println(Arrays.toString(arr));
		// System.out.println(dualPivotPartition(arr, 0, 2));
		// System.out.println(Arrays.toString(arr));
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			solve(io, io);
		}
	}

	private static void solve(EdxIO in, EdxIO out) {
		int n = in.nextInt();
		int k1 = in.nextInt();
		int k2 = in.nextInt();
		int A = in.nextInt();
		int B = in.nextInt();
		int C = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int[] arr = new int[n];
		arr[0] = a;
		arr[1] = b;
		for (int i = 2; i < arr.length; i++) {
			arr[i] = A * a + B * b + C;
			a = b;
			b = arr[i];
		}
		int leftIdx = findKthLargest(arr, 0, arr.length - 1, k1 - 1);
		int rightIdx = findKthLargest(arr, leftIdx, arr.length - 1, k2 - k1);
		List<Integer> res = new ArrayList<>();
		for (int i = leftIdx; i <= rightIdx; i++) {
			res.add(arr[i]);
		}
		Collections.sort(res);

		StringBuilder sb = new StringBuilder();
		for (int i : res) {
			sb.append(i + " ");
		}

		out.print(sb.toString());
	}

	private static void solveBak(FastScanner in, PrintWriter out) {
		int n = in.nextInt();
		int k1 = in.nextInt();
		int k2 = in.nextInt();
		int A = in.nextInt();
		int B = in.nextInt();
		int C = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int[] arr = new int[n];
		arr[0] = a;
		arr[1] = b;
		for (int i = 2; i < arr.length; i++) {
			arr[i] = A * a + B * b + C;
			a = b;
			b = arr[i];
		}
		int leftIdx = findKthLargestHelper(arr, 0, arr.length - 1, k1 - 1);
		int left = arr[leftIdx];
		int rightIdx = findKthLargestHelper(arr, leftIdx, arr.length - 1, k2 - k1);
		int right = arr[rightIdx];
		List<Integer> res = new ArrayList<>();
		if (left == right) {
			for (int i = 0; i < k2 - k1 + 1; i++) {
				res.add(left);
			}
		} else {
			int lessThanLeftCnt = 0;
			int lessThanRightCnt = 0;

			for (int i = 0; i < arr.length; i++) {
				if (arr[i] < left) {
					lessThanLeftCnt++;
				}
				if (arr[i] < right) {
					lessThanRightCnt++;
				}
			}
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == left) {
					if (lessThanLeftCnt < k1 - 1) {
						lessThanLeftCnt++;
					} else {
						res.add(arr[i]);
					}
				}
				if (arr[i] == right) {
					if (lessThanRightCnt < k2) {
						res.add(arr[i]);
						lessThanRightCnt++;
					}
				}
				if (arr[i] > left && arr[i] < right) {
					res.add(arr[i]);
				}
			}
			Collections.sort(res);
		}

		StringBuilder sb = new StringBuilder();
		for (int i : res) {
			sb.append(i + " ");
		}

		out.write(sb.toString());
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
			return index;
		} else if (index < start + k) {
			return findKthLargestHelper(arr, index + 1, end, k - (index - start + 1));
		} else {
			return findKthLargestHelper(arr, start, index - 1, k);
		}
	}

	public static int findKthLargest(int[] arr, int start, int end, int k) {
		while (start < end) {
			int index = dualPivotPartition(arr, start, end);
			// System.out.println(index + "," + k + "," + start + "," + end);
			// System.out.println(Arrays.toString(arr));
			if (index < start + k) {
				k -= (index - start + 1);
				start = index + 1;
			} else {
				end = index;
			}
		}
		return start;
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

	private static int dualPivotPartition(int[] arr, int start, int end) {
		int mid = (end + start) / 2;
		int pivot = arr[mid];

		while (true) {
			while (arr[start] < pivot) {
				start++;
			}
			while (arr[end] > pivot) {
				end--;
			}
			if (start >= end) {
				return end;
			} else {
				swap(arr, start, end);
				start++;
				end--;
			}
		}
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
