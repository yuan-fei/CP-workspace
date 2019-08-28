package r2019E;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CodeEatSwitcher {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int D = in.nextInt();
			int S = in.nextInt();
			TimeSlot[] timeSlots = new TimeSlot[S];
			for (int j = 0; j < S; j++) {
				timeSlots[j] = new TimeSlot(in.nextInt(), in.nextInt());
			}
			int[][] days = getIntArr(in, D, 2);
			String r = solve(S, D, timeSlots, days);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();
	}

	private static String solve(int S, int D, TimeSlot[] timeSlots, int[][] days) {
		Arrays.sort(timeSlots);
		long[] prefSumC = new long[S + 1];
		long[] suffixSumE = new long[S + 2];

		for (int i = 1; i <= S; i++) {
			prefSumC[i] = prefSumC[i - 1] + timeSlots[i - 1].c;
		}
		for (int i = S; i > 0; i--) {
			suffixSumE[i] = suffixSumE[i + 1] + timeSlots[i - 1].e;
		}
		StringBuilder ans = new StringBuilder();
		for (int[] day : days) {
			int ub = Arrays.binarySearch(prefSumC, day[0]);
			if (ub < 0) {
				ub = -ub - 1;
			}
			if (ub > S) {
				ans.append("N");
			} else {
				long fullE = 0;
				if (ub + 1 <= S) {
					fullE += suffixSumE[ub + 1] - suffixSumE[S + 1];
				}
				long extraC = prefSumC[ub] - day[0];
				long requiredE = day[1] - fullE;
				if (ub == 0) {
					ans.append((requiredE <= 0) ? "Y" : "N");
				} else {
					if (requiredE * timeSlots[ub - 1].c <= timeSlots[ub - 1].e * extraC) {
						ans.append("Y");
					} else {
						ans.append("N");
					}
				}
			}
		}
		return ans.toString();
	}

	static class TimeSlot implements Comparable<TimeSlot> {
		int c;
		int e;

		public TimeSlot(int c, int e) {
			super();
			this.c = c;
			this.e = e;
		}

		@Override
		public int compareTo(TimeSlot that) {
			return Integer.compare(that.c * e, c * that.e);
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

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
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
