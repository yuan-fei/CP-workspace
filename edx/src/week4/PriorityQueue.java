package week4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import mooc.EdxIO;

public class PriorityQueue {
	public static void main(String[] args) throws Exception {
		solve();
	}

	private static void solve() throws IOException, FileNotFoundException {

		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			boolean[] curValue = new boolean[n + 1];
			Arrays.fill(curValue, false);
			java.util.PriorityQueue<IndexedValue> q = new java.util.PriorityQueue<>(
					(a, b) -> Long.compare(a.value, b.value));
			for (int i = 0; i < n; i++) {
				switch (io.nextChar()) {
				case 'A':
					curValue[i + 1] = true;
					q.offer(new IndexedValue(io.nextInt(), i + 1));
					break;
				case 'X':
					while (!q.isEmpty() && !curValue[q.peek().i]) {
						q.poll();
					}
					if (!q.isEmpty()) {
						IndexedValue iv = q.poll();
						io.println(iv.value);
						curValue[iv.i] = false;
					} else {
						io.println("*");
					}

					break;
				case 'D':
					int idx = io.nextInt();
					int newValue = io.nextInt();
					if (curValue[idx]) {
						q.offer(new IndexedValue(newValue, idx));
					}
					break;
				}
			}
		}
	}

	static class IndexedValue {
		int value;
		int i;

		public IndexedValue(int value, int i) {
			super();
			this.value = value;
			this.i = i;
		}

	}
}
