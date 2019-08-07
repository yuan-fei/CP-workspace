package week2;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Queue {
	public static void main(String[] args) throws IOException {
		// generateLookup();
		try (FastScanner in = new FastScanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			solve(in, out);
		}
	}

	private static void generate(Scanner in, PrintWriter out) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		sb.append("1000000\n");
		for (int i = 0; i < 500000; i++) {
			sb.append("+ " + r.nextInt(1000000000) + "\n");
		}
		for (int i = 0; i < 500000; i++) {
			sb.append("-\n");
		}
		out.write(sb.toString());
	}

	private static void solve(FastScanner in, PrintWriter out) {
		int n = in.nextInt();
		MyQueue stk = new MyQueue();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			String s = in.next();
			if (s.equals("+")) {
				stk.enqueue(in.next());
			} else {
				sb.append(stk.dequeue() + "\n");
			}
		}
		String r = sb.toString();
		// System.out.println(r);
		out.write(r);
	}

	static class MyQueue {
		String[] arr = new String[1000001];
		int head = 0;
		int tail = -1;
		int size = 0;

		void enqueue(String e) {
			tail = (tail + 1) % arr.length;
			arr[tail] = e;
			size++;
		}

		boolean isEmpty() {
			return size == 0;
		}

		String dequeue() {
			String ret = arr[head];
			head = (head + 1) % arr.length;
			size--;
			return ret;
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
