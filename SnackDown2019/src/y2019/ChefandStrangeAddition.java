package y2019;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class ChefandStrangeAddition {

	public static void main(String[] args) {
		test();
		// solve();
	}

	private static void test() {
		map.put(new State(0, 0, 0), 1L);
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < countOnes(i); j++) {
				for (int k = countOnes(i) - j; k < countOnes(i) + 2; k++) {
					System.out
							.println("" + j + ", " + k + "=" + Integer.toBinaryString(i) + ":" + solve1Helper(j, k, i));
				}
			}
		}

	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int a = in.nextInt();
			int b = in.nextInt();
			int c = in.nextInt();
			long r = solve1(a, b, c);
			System.out.println(r);
		}
		in.close();
	}

	static class State {
		int x, y, z;

		public State(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public boolean equals(Object obj) {
			State that = (State) obj;
			return (that.z == z && ((that.x == x && that.y == y) || (that.y == x && that.x == y)));
		}

		@Override
		public int hashCode() {

			return x + y + z;
		}
	}

	static HashMap<State, Long> map = new HashMap<State, Long>();
	static HashMap<State, Long> cMap = new HashMap<State, Long>();

	private static long solve1(int a, int b, int c) {
		int cA = countOnes(a);
		int cB = countOnes(b);
		// int l = Integer.highestOneBit(c) + 1;
		map.put(new State(0, 0, 0), 1L);
		return solve1Helper(cA, cB, c);
	}

	static long solve1Helper(int cA, int cB, int c) {
		Long i = map.get(new State(cA, cB, c));
		if (c == 0 && cA + cB != 0) {
			return 0;
		}
		int cc = countOnes(c);
		if (cA + cB < cc) {
			return 0;
		} else if (cA + cB == cc) {
			i = C(cc, Math.min(cA, cB));
			map.put(new State(cA, cB, c), i);
			return i;
		}
		if (Math.max(cA, cB) > Integer.highestOneBit(c)) {
			return 0;
		}
		if (i == null) {
			i = 0L;
			if (c % 2 == 0) {
				if (cA > 0 && cB > 0 && c >= 2) {
					i += solve1Helper(cA - 1, cB - 1, (c - 2) >> 1);
				}
				i += solve1Helper(cA, cB, c >> 1);
			} else {
				if (cA > 0) {
					i += solve1Helper(cA - 1, cB, c >> 1);
				}
				if (cB > 0) {
					i += solve1Helper(cA, cB - 1, c >> 1);
				}
			}
			map.put(new State(cA, cB, c), i);
		}
		return i;
	}

	private static long C(int a, int b) {
		Long r = cMap.get(new State(a, b, 0));
		if (r == null) {
			r = 1L;
			for (int i = a - b + 1, j = 1; j <= b; i++, j++) {
				r *= i;
				r /= j;
			}
			cMap.put(new State(a, b, 0), r);
		}
		return r;
	}

	// private static long solve(int a, int b, int c) {
	// int cA = countOnes(a);
	// int cB = countOnes(b);
	// // int l = Integer.highestOneBit(c) + 1;
	// long[][][] state = new long[cA + 1][cB + 1][c + 1];
	// state[0][0][0] = 1;
	// // state[1][0][1] = 1;
	// // state[0][1][1] = 1;
	// // state[1][0][0] = 1;
	// // state[0][1][0] = 1;
	// for (int i = 1; i <= c; i++) {
	// for (int j = 0; j <= cA; j++) {
	// for (int k = 0; k <= cB; k++) {
	// if (i % 2 == 0) {
	// state[j][k][i] = state[j][k][i >> 1];
	// if (i >= 2 && j > 0 && k > 0) {
	// state[j][k][i] += state[j - 1][k - 1][(i - 2) >> 1];
	// }
	// } else {
	// if (j > 0) {
	// state[j][k][i] += state[j - 1][k][i >> 1];
	// }
	// if (k > 0) {
	// state[j][k][i] += state[j][k - 1][i >> 1];
	// }
	// }
	// // System.out.println(i + "," + j + "," + k + "=" + state[j][k][i]);
	// }
	// }
	// }
	// return state[cA][cB][c];
	// }

	private static long solve(int a, int b, int c) {
		int cA = countOnes(a);
		int cB = countOnes(b);
		// int l = Integer.highestOneBit(c) + 1;
		long[][][] state = new long[cA + 1][cB + 1][c + 1];
		state[0][0][0] = 1;
		// state[1][0][1] = 1;
		// state[0][1][1] = 1;
		// state[1][0][0] = 1;
		// state[0][1][0] = 1;
		for (int i = 1; i <= c; i++) {
			for (int j = 0; j <= cA; j++) {
				for (int k = 0; k <= cB; k++) {
					if (i % 2 == 0) {
						state[j][k][i] = state[j][k][i >> 1];
						if (i >= 2 && j > 0 && k > 0) {
							state[j][k][i] += state[j - 1][k - 1][(i - 2) >> 1];
						}
					} else {
						if (j > 0) {
							state[j][k][i] += state[j - 1][k][i >> 1];
						}
						if (k > 0) {
							state[j][k][i] += state[j][k - 1][i >> 1];
						}
					}
					// System.out.println(i + "," + j + "," + k + "=" + state[j][k][i]);
				}
			}
		}
		return state[cA][cB][c];
	}

	private static int countOnes(int x) {
		int cnt = 0;
		while (x != 0) {
			x &= (x - 1);
			cnt++;
		}
		return cnt;
	}

	private static void bruteForce(int bit, int oneBit, int target) {
		HashMap<Integer, String> s = new HashMap<Integer, String>();
		p(bit, oneBit, 0, 0, "", s);
		Integer[] keys = s.keySet().toArray(new Integer[0]);
		int cnt = 0;
		for (Integer i : keys) {
			int t = target - i;
			if (s.containsKey(t)) {
				if (i < t) {
					System.out.println("" + s.get(i) + ", " + s.get(t));
				} else {
					System.out.println("" + s.get(t) + ", " + s.get(i));
				}
				cnt++;
				s.remove(i);
				s.remove(t);
			}
		}
		System.out.println(cnt);
	}

	static String[] z = new String[] { "", "0", "00", "000", "0000", "00000" };

	static void p(int n, int b, int i, int cnt, String c, HashMap<Integer, String> s) {
		if (i == n + 1) {
			return;
		}
		if (cnt == b) {
			String r = c + z[n - c.length()];
			s.put(Integer.valueOf(r, 2), r);
			return;
		}
		p(n, b, i + 1, cnt, c + "0", s);
		p(n, b, i + 1, cnt + 1, c + "1", s);
	}
}
