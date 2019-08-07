package week2;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

public class Stacks {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		try (FastScanner in = new FastScanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			solve(in, out);
		}
	}

	private static void solve(FastScanner in, PrintWriter out) throws Exception {
		int n = in.nextInt();
		int[] stk = new int[n + 1];
		stk[0] = 1;
		int min = 0;
		for (int i = 1; i <= n; i++) {
			int d = in.nextInt();
			if (d == 0) {
				stk[min]--;
				stk[min + 1]++;
				if (stk[min] == 0) {
					min++;
				}
			} else {
				stk[1]++;
				min = 1;
			}
		}
		StringBuilder sb = new StringBuilder();
		int tot = 0;
		for (int i = min; i < stk.length && n > 0; i++) {
			if (stk[i] > 0) {
				tot++;
				sb.append(i + " " + stk[i] + "\n");
				n -= stk[i];
			}
		}
		sb.insert(0, tot + "\n");
		out.write(sb.toString());
	}

	static class MyQueue {
		static class Element {
			int e;
			int min;

			public Element(int e, int min) {
				super();
				this.e = e;
				this.min = min;
			}

			@Override
			public String toString() {

				return "e: " + e + ", min: " + min;
			}
		}

		Stack<Element> s1 = new Stack<>();
		Stack<Element> s2 = new Stack<>();

		void enqueue(int e) {
			int min = Integer.MAX_VALUE;
			if (!s1.isEmpty()) {
				min = s1.peek().min;
			}
			s1.add(new Element(e, Math.min(min, e)));
		}

		public int min() {
			int min = Integer.MAX_VALUE;
			if (!s1.isEmpty()) {
				min = s1.peek().min;
			}
			if (!s2.isEmpty()) {
				min = Math.min(min, s2.peek().min);
			}
			return min;
		}

		boolean isEmpty() {
			return s1.isEmpty() && s2.isEmpty();
		}

		int dequeue() throws Exception {

			if (s2.isEmpty()) {
				int min = s1.peek().e;
				while (!s1.isEmpty()) {
					Element e = s1.pop();
					e.min = Math.min(min, e.e);
					s2.push(e);
					min = e.min;
				}
			}
			if (!s2.isEmpty()) {
				return s2.pop().e;
			} else {
				throw new Exception("Empty Queue");
			}
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
