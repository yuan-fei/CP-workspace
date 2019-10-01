package week3;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

import mooc.EdxIO;

public class AntiQuicksort {
	public static void main(String[] args) throws Exception {
		// generateLookup();
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {
		// try (FastScanner in = new FastScanner(new File("input.txt"));
		// PrintWriter out
		// = new PrintWriter("output.txt")) {
		// solve(in, out);
		// }
		try (EdxIO io = EdxIO.create()) {
			solve(io, io);
		}
	}

	private static void solve(EdxIO in, EdxIO out) {
		int n = in.nextInt();
		int[] arr = IntStream.range(0, n).toArray();
		for (int i = 1; i < arr.length; i++) {
			swap(arr, i - 1, (i + 1) / 2 - 1);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append((arr[i] + 1) + " ");
		}
		out.println(sb.toString());
	}

	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
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
