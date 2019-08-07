package r1b;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RoundingError {
	public static void main(String[] args) {
		solve();
		// test();
		// System.out.println(solve(1394, 1, new Integer[] { 10 }));
		// System.out.println(Benchmark.solve(1394, 1, new int[] { 10 }));
	}

	private static void test() {
		for (int i = 0; i < 100000; i++) {
			testOnce();
		}
	}

	private static void testOnce() {
		Random r = new Random();
		int n = r.nextInt(10000) + 90000;
		int l = r.nextInt(10);
		Integer[] a = new Integer[l];
		int[] c = new int[l];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] = r.nextInt(11);

		}
		if ((1000 / n) % 10 >= 5) {
			System.out.println(n);
		}
		if (solve(n, l, a) != Benchmark.solve(n, l, c)) {
			System.out.println(n + ", " + l + ", " + Arrays.toString(c) + ": " + Benchmark.solve(n, l, c));
		}

	}

	private static void solve() {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int l = in.nextInt();
			// int r = Benchmark.solve(n, l, getIntArr(in, l));
			int r = solve(n, l, getIntegerArr(in, l));
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static int mySolve(int n, int l, Integer[] c) {
		int ret = 0;
		int remaining = n;
		double p = 0;
		for (int i = 0; i < c.length; i++) {
			remaining -= c[i];
		}
		if (isRoundUp(1, n)) {
			for (int i = 0; i < c.length; i++) {
				ret += round(c[i], n);
			}
		} else {
			Arrays.sort(c, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return -Integer.compare((o1 * 1000 / n) % 10, (o2 * 1000 / n) % 10);
				}
			});

			for (int i = 0; i < c.length; i++) {
				while (remaining > 0 && !isRoundUp(c[i], n)) {
					c[i]++;
					remaining--;
				}
				ret += round(c[i], n);
			}
		}

		if (remaining > 0) {
			remaining--;
			int cnt = 1;
			while (remaining > 0 && !isRoundUp(cnt, n)) {
				cnt++;
				remaining--;
			}
			ret += round(cnt, n);
			if (remaining > 0) {
				ret += (remaining / cnt) * round(cnt, n);
				ret += round(remaining % cnt, n);
			}
		}

		return ret;
	}

	private static int solve(int n, int l, Integer[] c) {
		int ret = 0;
		int remaining = n;
		double p = 0;
		for (int i = 0; i < c.length; i++) {
			remaining -= c[i];
		}
		if (isRoundUp(1, n)) {
			for (int i = 0; i < c.length; i++) {
				ret += round(c[i], n);
			}
		} else {
			Arrays.sort(c, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					int r = Integer.compare((o1 * 1000 / n) % 10, (o2 * 1000 / n) % 10);
					if (r == 0) {
						r = Integer.compare(o1, o2);
					}
					return -r;
				}
			});

			for (int i = 0; i < c.length; i++) {
				while (remaining > 0 && !isRoundUp(c[i], n)) {
					c[i]++;
					remaining--;
				}
				ret += round(c[i], n);
			}
		}

		if (remaining > 0) {
			remaining--;
			int cnt = 1;
			while (remaining > 0 && !isRoundUp(cnt, n)) {
				cnt++;
				remaining--;
			}
			ret += round(cnt, n);
			if (remaining > 0) {
				ret += (remaining / cnt) * round(cnt, n);
				ret += round(remaining % cnt, n);
			}
		}

		return ret;
	}

	private static int round(int a, int n) {
		int r = 100 * a / n;
		if (isRoundUp(a, n)) {
			r += 1;
		}
		return r;
		// return (int) Math.round(a * 100 / (double) n);
	}

	static boolean isRoundUp(int a, int n) {
		return (1000 * a / n) % 10 >= 5;
	}

	static class Benchmark {

		public static int solve1(int N, int L, int[] a) {
			Benchmark.Val[] vals = new Benchmark.Val[L];
			int cSum = 0;
			int percent = 0;
			final int goodMod = (N + 1) / 2;
			for (int i = 0; i < L; i++) {
				vals[i] = new Benchmark.Val(a[i], N);
				cSum += vals[i].C;
			}

			int remaining = N - cSum;
			final int oneAdds = 100 % N;
			if (oneAdds == 0) {
				percent = (remaining * 100) / N;
				for (int i = 0; i < L; i++) {
					percent += (vals[i].C * 100) / N;
				}
			} else {
				Arrays.sort(vals);

				for (int i = vals.length - 1; i >= 0; i--) {
					if (vals[i].mod >= goodMod) {
						percent += getPercent(vals[i].C, N);
					} else {
						int needAdd = goodMod - vals[i].mod;
						int needTake = (needAdd + oneAdds - 1) / oneAdds;
						if (needTake <= remaining) {
							Benchmark.Val tmp = vals[i];
							vals[i] = new Benchmark.Val(tmp.C + needTake, N);
							remaining -= needTake;
						}

						percent += getPercent(vals[i].C, N);
					}
				}

				final int groupSize = (goodMod + oneAdds - 1) / oneAdds;
				int numGroups = remaining / groupSize;
				int groupPercent = getPercent(groupSize, N);
				percent += numGroups * groupPercent;
				remaining -= numGroups * groupSize;

				percent += getPercent(remaining, N);
			}
			// Arrays.sort(C);

			// final int toGetOne = (N + 199) / 200;
			//
			// int idx = 0;
			// while (remaining > 0 && idx < L) {
			// int need = Math.max(0, toGetOne - C[idx]);
			// if (remaining >= need) {
			// C[idx] += need;
			// remaining -= need;
			// }
			//
			// percent += (C[idx] * 100) / N;
			// idx++;
			// }
			//
			// percent += remaining / toGetOne;

			return percent;

		}

		public static int solve(int n, int L, int[] a) {
			Benchmark.Val[] vals = new Benchmark.Val[L];
			int cSum = 0;
			int percent = 0;
			final int goodMod = (n + 1) / 2;
			for (int i = 0; i < L; i++) {
				vals[i] = new Benchmark.Val(a[i], n);
				cSum += vals[i].C;
			}

			int remaining = n - cSum;
			final int oneAdds = 100 % n;
			// if (oneAdds == 0) {
			// percent = (remaining * 100) / n;
			// for (int i = 0; i < L; i++) {
			// percent += (vals[i].C * 100) / n;
			// }
			// } else
			if (isRoundUp(1, n)) {
				for (int i = 0; i < L; i++) {
					percent += round(vals[i].C, n);
				}
			} else {
				Arrays.sort(vals, new Comparator<Benchmark.Val>() {
					@Override
					public int compare(Val o1, Val o2) {
						int r = Integer.compare((o1.C * 1000 / n) % 10, (o2.C * 1000 / n) % 10);
						if (r == 0) {
							r = Integer.compare(o1.C, o2.C);
						}
						return r;
					}
				});

				for (int i = vals.length - 1; i >= 0; i--) {
					if (isRoundUp(vals[i].C, n)) {
						percent += round(vals[i].C, n);
					} else {
						while (remaining > 0 && !isRoundUp(vals[i].C, n)) {
							vals[i].C++;
							remaining--;
						}
						percent += round(vals[i].C, n);
					}
				}

			}
			if (remaining > 0) {
				remaining--;
				int cnt = 1;
				while (remaining > 0 && !isRoundUp(cnt, n)) {
					cnt++;
					remaining--;
				}
				percent += round(cnt, n);
				if (remaining > 0) {
					percent += (remaining / cnt) * round(cnt, n);
					percent += round(remaining % cnt, n);
				}
			}
			// Arrays.sort(C);

			// final int toGetOne = (N + 199) / 200;
			//
			// int idx = 0;
			// while (remaining > 0 && idx < L) {
			// int need = Math.max(0, toGetOne - C[idx]);
			// if (remaining >= need) {
			// C[idx] += need;
			// remaining -= need;
			// }
			//
			// percent += (C[idx] * 100) / N;
			// idx++;
			// }
			//
			// percent += remaining / toGetOne;

			return percent;

		}

		private static int getPercent(int C, int N) {
			int per = (C * 100) / N;
			int md = (C * 100) % N;
			int good = (N + 1) / 2;
			if (md >= good) {
				per++;
			}
			return per;
		}

		private static class Val implements Comparable<Benchmark.Val> {
			int C;
			int mod;

			public Val(int c, int N) {
				C = c;
				mod = (100 * C) % N;
			}

			public int compareTo(Benchmark.Val o) {
				if (mod != o.mod) {
					return Integer.compare(mod, o.mod);
				}

				return Integer.compare(C, o.C);
			}

		}

	}

	static long mod = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return (a * b) % mod;
	}

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static String str(int[] a) {
		String[] str = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			str[i] = a[i] + "";
		}
		return String.join(" ", str);
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static Integer[] getIntegerArr(Scanner in, int size) {
		Integer[] arr = new Integer[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Integer>> edges = new HashMap<>();
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<Integer>());
			}
			edges.get(from).add(to);
			if (!directed) {
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Integer>());
				}
				edges.get(to).add(from);
			}

		}
		return edges;
	}
}
