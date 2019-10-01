package week3;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import mooc.EdxIO;

public class JohnsonJohnson {
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
		// int[] arr = makeBadArray(n);
		int[][] arr = new int[n][3];
		for (int i = 0; i < n; i++) {
			arr[i][0] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			arr[i][1] = in.nextInt();
			arr[i][2] = i;
		}
		Arrays.sort(arr, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(Math.min(o1[0], o1[1]), Math.min(o2[0], o2[1]));
			}
		});

		int[][] optArr = new int[n][];
		int left = 0;
		int right = optArr.length - 1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i][0] < arr[i][1]) {
				optArr[left++] = arr[i];
			} else {
				optArr[right--] = arr[i];
			}
		}
		arr = optArr;
		long r = 0;
		r += arr[0][0];
		r += arr[0][1];
		long slack = arr[0][1];
		long[] tA = new long[n];
		long[] tB = new long[n];
		tA[arr[0][2]] = 0;
		tB[arr[0][2]] = arr[0][0];
		for (int i = 1; i < n; i++) {
			tA[arr[i][2]] = r - slack;
			long save = Math.min(slack, arr[i][0]);
			slack -= save;
			r += (arr[i][0] - save);
			tB[arr[i][2]] = r;
			r += arr[i][1];
			slack += arr[i][1];
		}

		StringBuilder sb = new StringBuilder();
		sb.append(r + "\n");
		for (int i = 0; i < n; i++) {
			sb.append(tA[i] + " ");
		}
		sb.append("\n");
		for (int i = 0; i < n; i++) {
			sb.append(tB[i] + " ");
		}
		out.println(sb.toString());
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
