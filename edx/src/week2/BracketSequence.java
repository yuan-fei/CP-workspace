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

public class BracketSequence {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		try (FastScanner in = new FastScanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			solve(in, out);
		}
	}

	private static void solve(FastScanner in, PrintWriter out) throws Exception {
		int n = in.nextInt();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			String s = in.next();
			boolean r = solve(s);
			sb.append(r ? "YES\n" : "NO\n");
		}
		String r = sb.toString();
		// System.out.println(r);
		out.write(r);
	}

	private static boolean solve(String s) {
		Stack<Character> stk = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '(':
			case '[':
				stk.push(c);
				break;
			case ')':
				if (!stk.isEmpty() && stk.peek() == '(') {
					stk.pop();
				} else {
					return false;
				}
				break;
			case ']':
				if (!stk.isEmpty() && stk.peek() == '[') {
					stk.pop();
				} else {
					return false;
				}
				break;
			}
		}

		return stk.isEmpty();
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
