
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;

import mooc.EdxIO;

public class Main {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		solve();
	}

	private static void solve() {
		try (EdxIO io = EdxIO.create()) {
			int B = io.nextInt();
			int n = io.nextInt();

			long[][] a = new long[n][];
			for (int i = 0; i < n; ++i) {
				a[i] = new long[] { io.nextInt(), io.nextInt(), io.nextInt() };
			}

			int m = io.nextInt();
			long[][] b = new long[m][];
			for (int i = 0; i < m; ++i) {
				b[i] = new long[] { io.nextInt(), io.nextInt() };
			}

			TreeMap<Long, Long> offset = new TreeMap<>();
			for (int i = 0; i < a.length; i++) {
				long v = offset.getOrDefault(a[i][1], 0L);
				offset.put(a[i][1], v + a[i][0]);
				v = offset.getOrDefault(a[i][2] + 1, 0L);
				offset.put(a[i][2] + 1, v - a[i][0]);
			}
			TreeMap<Long, Long> query = new TreeMap<>();
			for (long[] q : b) {
				query.put(q[0] - 1, 0L);
				query.put(q[1], 0L);
			}
			long prefSum = 0;
			long cur = 0;
			long lastOffsetChange = 0;
			Entry<Long, Long> e = offset.pollFirstEntry();
			for (Long k : query.keySet()) {
				while (e != null && e.getKey() <= k) {
					prefSum += (e.getKey() - lastOffsetChange) * cur;
					cur += e.getValue();
					lastOffsetChange = e.getKey();
					e = offset.pollFirstEntry();
				}
				query.put(k, prefSum + (k - lastOffsetChange + 1) * cur);
			}
			for (long[] q : b) {
				long r = query.get(q[1]) - query.get(q[0] - 1);
				io.println(r);
			}
			//
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
