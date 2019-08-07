package week2;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

import mooc.EdxIO;

/** circular doubly linked list */
public class KenobisLightsabers {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		try (FastScanner in = new FastScanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			solve(in, out);
		}
	}

	private static void editorialSolve() {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			Deque<Integer> l = new ArrayDeque<>();
			Deque<Integer> r = new ArrayDeque<>();
			for (int i = 0; i < n; ++i) {
				String cmd = io.next();
				switch (cmd.charAt(0)) {
				case 'a':
					r.addLast(io.nextInt());
					break;
				case 't':
					r.removeLast();
					break;
				case 'm': {
					Deque<Integer> tmp = l;
					l = r;
					r = tmp;
					break;
				}
				}
				if (l.size() > r.size()) {
					r.addFirst(l.removeLast());
				} else if (l.size() < r.size() - 1) {
					l.addLast(r.removeFirst());
				}
			}
			l.addAll(r);
			io.println(l.size());
			for (int i : l) {
				io.print(i).print(' ');
			}
			io.println();
		}
	}

	private static void solve(FastScanner in, PrintWriter out) {
		int n = in.nextInt();
		CircularDoublelinkedList cl = new CircularDoublelinkedList();
		for (int i = 1; i <= n; i++) {
			switch (in.next()) {
			case "add":
				cl.append(in.nextInt());
				break;
			case "take":
				cl.removeTail();
				break;
			case "mum!":
				cl.rotateHalf();
				break;
			}
		}
		out.write(cl.size + "\n");
		out.write(cl.toString());
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
