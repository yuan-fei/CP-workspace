package week6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import mooc.EdxIO;

public class HamiltonianPaths {
	private static int N;
	private static char[][] adj;
	private static int[] from;
	private static int[] to;
	private static int[] fromZero;
	private static int[] toZero;
	private static int[] found;

	public static void main(String[] args) throws Exception {
		// generate();
		solve();
	}

	private static void generate() throws IOException, FileNotFoundException {
		Random r = new Random();
		int N = 25;
		try (EdxIO io = EdxIO.create()) {
			io.println(N);
			for (int i = 0; i < N; i++) {
				String s = "";
				for (int j = 0; j < N; j++) {
					s += r.nextInt(2);
				}
				io.println(s);
			}
		}
	}

	private static void solve() throws IOException, FileNotFoundException {
		try (EdxIO io = EdxIO.create()) {
			N = io.nextInt();
			adj = new char[N][];
			from = new int[N];
			to = new int[N];
			found = new int[N];
			for (int i = 0; i < N; i++) {
				String s = io.next();
				adj[i] = s.toCharArray();
			}
			for (int i = 0; i < N; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(adj[i]);
				from[i] = Integer.parseInt(sb.reverse().toString(), 2);
			}
			for (int i = 0; i < N; i++) {
				for (int j = i; j < N; j++) {
					char c = adj[i][j];
					adj[i][j] = adj[j][i];
					adj[j][i] = c;
				}
			}
			for (int i = 0; i < N; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(adj[i]);
				to[i] = Integer.parseInt(sb.reverse().toString(), 2);
			}
			hamiltonDP();
			findPath(io);
			// StringBuilder sb = new StringBuilder();
			// io.println("Impossible");
		}
	}

	private static void findPath(EdxIO io) {
		int[] path = new int[N];
		StringBuilder sb = new StringBuilder();
		for (int m = 0; m < (1 << (N - 1)); m++) {
			int mComp = ((1 << (N - 1)) - 1) ^ m;
			if (toZero[m] != 0 && fromZero[mComp] != 0) {
				for (int i = 0; i < N; i++) {
					if ((toZero[m] & (1 << i)) != 0 && ((~found[i]) & fromZero[mComp]) != 0) {
						int l = restoreToZeroPath(m, i, path);
						for (int j = 0; j < N; j++) {
							if ((fromZero[mComp] & (1 << j)) != 0) {
								if ((found[i] & (1 << j)) == 0) {
									found[i] |= (1 << j);
									path[l] = 1;
									restoreFromZeroPath(mComp, j, path);
									output(sb, path);
								}
							}
						}
					}
				}
			}
		}
		if (sb.length() == 0) {
			io.println("No paths");
		} else {
			io.println(sb.toString());
		}
	}

	private static void output(StringBuilder sb, int[] path) {
		for (int i = 0; i < path.length; i++) {
			if (i == 0) {
				sb.append(path[i]);
			} else {
				sb.append(" -> " + path[i]);
			}
		}
		sb.append("\n");
	}

	private static void restoreFromZeroPath(int mask, int last, int[] path) {
		int end = path.length - 1;
		while (mask != 0) {
			path[end--] = last + 1;
			mask = mask ^ (1 << (last - 1));
			int con = fromZero[mask] & to[last];
			last = -1;
			for (int i = 1; i < N; i++) {
				if ((con & (1 << i)) != 0) {
					last = i;
					break;
				}
			}
		}
	}

	private static int restoreToZeroPath(int mask, int first, int[] path) {
		int end = 0;
		while (mask != 0) {
			path[end++] = first + 1;
			mask = mask ^ (1 << (first - 1));
			int con = toZero[mask] & from[first];
			first = -1;
			for (int i = 1; i < N; i++) {
				if ((con & (1 << i)) != 0) {
					first = i;
				}
			}
		}
		return end;
	}

	private static void hamiltonDP() {
		fromZero = new int[1 << (N - 1)];
		toZero = new int[1 << (N - 1)];
		fromZero[0] = 1;
		toZero[0] = 1;
		for (int m = 0; m < (1 << (N - 1)); m++) {
			for (int i = 1; i < N; i++) {
				if ((m & (1 << (i - 1))) != 0) {
					int mPrev = m ^ (1 << (i - 1));
					if ((fromZero[mPrev] & to[i]) != 0) {
						fromZero[m] |= 1 << i;
					}
				}
			}
		}
		for (int m = 0; m < (1 << (N - 1)); m++) {
			for (int i = 1; i < N; i++) {
				if ((m & (1 << (i - 1))) != 0) {
					int mPrev = m ^ (1 << (i - 1));
					if ((toZero[mPrev] & from[i]) != 0) {
						toZero[m] |= 1 << i;
					}
				}
			}
		}
	}

}