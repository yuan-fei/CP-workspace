package y2021.qualification;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CheatDetection {
	public static void main(String[] args) {
		solve();
		// testOnce();
		// test();
	}

	static int[] target = { 54, 38, 34, 36, 51, 57, 28, 84, 32, 7, 16, 53, 25, 32, 94, 20, 7, 84, 64, 40, 81, 26, 51,
			75, 83, 46, 94, 37, 79, 10, 92, 90, 76, 98, 72, 19, 92, 11, 100, 10, 72, 21, 1, 3, 77, 17, 54, 29, 41, 98 };

	private static void test() {
		int cnt = 0;
		for (int i = 0; i < 50; i++) {
			if (testOnce()[0] < 86) {
				cnt++;
			}
		}
		System.out.println(cnt);
	}

	private static int[] testOnce() {
		int[] cnt = new int[2];
		// int cnt2 = 0;
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			int x = 0;
			String[] testcase = generateCase(x);
			// System.out.println("###");
			int[] res = solve1(10, testcase);
			// System.out.println(res[0]);
			for (int j = 0; j < res.length; j++) {
				if (res[j] == x + 1) {
					cnt[j]++;
				}
			}

			// if (res[0] == 1) {
			// cnt1++;
			// }
			// if (res[1] == 1) {
			// cnt2++;
			// }
			// System.out.println(res);
		}
		System.out.println(Arrays.toString(cnt));
		return cnt;
	}

	static Scanner in;

	static int precision = 0;

	private static void solve() {
		in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		int p = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			String[] scores = getStringArr(in, 100);
			int[] r = solve1(p, scores);
			if (r[0] == target[i - 1]) {
				precision++;
			}
			System.out.println("Case #" + i + ": " + r[0]);
		}
		System.out.println("precision: " + precision);
		in.close();
	}

	private static int[] solve(int p, String[] scores) {
		int[][] problems = new int[10000][2];
		for (int i = 0; i < 10000; i++) {
			problems[i][0] = i + 1;
			problems[i][1] = getProblemLevel(scores, i);
		}
		int maxSuprise = 0;
		int ans = 0;
		for (int i = 0; i < 100; i++) {
			int accuracy = getPlayerAccuracy(scores, i);
			int playerLevelUb = getPlayerLevelUb(scores, problems, i);
			int suprise = getSuprise(i, playerLevelUb, problems, scores);
			if (accuracy > 5000) {
				if (maxSuprise < suprise) {
					maxSuprise = suprise;
					ans = i + 1;
				}
			}
		}
		return new int[] { ans };
	}

	private static int[] solve1(int p, String[] scores) {
		int[][] problems = new int[10000][2];
		for (int i = 0; i < 10000; i++) {
			problems[i][0] = i + 1;
			problems[i][1] = getProblemLevel(scores, i);
		}

		int[][] players = new int[100][4];
		int diff = 0;
		int maxAccuracy = 0;
		int ans = 0;
		int cnt = 0;
		for (int i = 0; i < 100; i++) {
			int accuracy = getPlayerAccuracy(scores, i);
			int levelUb = getPlayerLevelUb(scores, problems, i);
			players[i][0] = i + 1;
			players[i][1] = accuracy;
			players[i][2] = levelUb;
			int suprise = getSuprise(i, levelUb, problems, scores);
			// players[i][3] = suprise;
			if (accuracy > 5000) {
				cnt++;
				if (diff < accuracy - levelUb) {
					diff = accuracy - levelUb;
					ans = i + 1;
				} else if (diff == accuracy - levelUb && accuracy > maxAccuracy) {
					diff = accuracy - levelUb;
					ans = i + 1;
				}
			}
		}
		int[] t = players[0];
		Arrays.sort(problems, (a, b) -> -Integer.compare(a[1], b[1]));
		Arrays.sort(players, (a, b) -> -Integer.compare(a[1], b[1]));
		fillDiff(players, problems, scores);
		// System.out.println(Arrays.toString(t));
		// int[][] highPlayers = new int[cnt][];
		// int cur = 0;
		// for (int i = 0; i < 100; i++) {
		// if (players[i][1] > 5000) {
		// highPlayers[cur++] = players[i];
		// }
		// }
		// Arrays.sort(players, (a, b) -> -Integer.compare(a[1] - a[2], b[1] -
		// b[2]));
		Arrays.sort(players, (a, b) -> -Integer.compare(a[3], b[3]));
		// return playerHighAccuracy[0][0];
		// head(players, 5);
		return new int[] { players[0][0], ans };
	}

	private static int getPlayerLevelUb(String[] scores, int[][] problems, int k) {
		int min = 10000;
		for (int i = 0; i < scores[k].length(); i++) {
			if (scores[k].charAt(i) == '0') {
				min = Math.min(problems[i][1], min);
			}
		}
		return min;
	}

	private static void fillSuprise(int[][] playerAccuracy, int[][] problemAccuracy, String[] scores) {
		for (int j = 0; j < playerAccuracy.length; j++) {
			playerAccuracy[j][3] = getSuprise(playerAccuracy[j][0] - 1, playerAccuracy[j][2], problemAccuracy, scores);
		}
	}

	private static int getSuprise(int player, int playerLevel, int[][] problemLevels, String[] scores) {
		int cnt = 0;
		for (int i = 0; i < problemLevels.length; i++) {
			if (playerLevel < problemLevels[i][1] && scores[player].charAt(problemLevels[i][0] - 1) == '1') {
				cnt++;
				// cnt += problemLevels[i][1] - player[1];
			}
		}
		return cnt;
	}

	private static void fillDiff(int[][] players, int[][] problems, String[] score) {
		int n = players.length;
		int c = 500;
		for (int i = 0; i < n; i++) {
			int df = 0;
			int cnt = 0;
			for (int d = -1; d <= 1; d++) {
				if (i + d >= 0 && i + d < n) {
					cnt++;
					for (int j = 0; j < c; j++) {
						if (score[players[i][0] - 1].charAt(problems[j][0] - 1) == '1' && score[players[i][0] - 1]
								.charAt(problems[j][0] - 1) != score[players[i + d][0] - 1].charAt(j)) {
							df++;
						}
					}
				}
			}

			// for (int j = 9999; j >= 9999 - c; j--) {
			// if (score[players[i][0] - 1].charAt(problems[j][0] - 1) == '0' &&
			// score[players[i][0] - 1]
			// .charAt(problems[j][0] - 1) != score[players[i + 1][0] -
			// 1].charAt(j)) {
			// df++;
			// }
			// if (score[players[i][0] - 1].charAt(problems[j][0] - 1) == '0' &&
			// score[players[i][0] - 1]
			// .charAt(problems[j][0] - 1) != score[players[i - 1][0] -
			// 1].charAt(j)) {
			// df++;
			// }
			// }
			players[i][3] = df / (cnt - 1);
		}
	}

	static void head(int[][] a, int k) {
		for (int i = 0; i < k; i++) {
			System.out.println(Arrays.toString(a[i]));
		}
	}

	static void tail(int[][] a, int k) {
		for (int i = a.length - k - 1; i < a.length; i++) {
			System.out.println(Arrays.toString(a[i]));
		}
	}

	static int getPlayerAccuracy(String[] scores, int k) {
		int cnt = 0;
		for (char c : scores[k].toCharArray()) {
			if (c == '1') {
				cnt++;
			}
		}
		return cnt;
	}

	static int getProblemLevel(String[] scores, int k) {
		int cnt = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i].charAt(k) == '0') {
				cnt++;
			}
		}
		return cnt * 100;
	}

	static String[] generateCase(int cheater) {
		double[] playerLevels = random(100);
		double[] problemLevels = random(10000);
		String[] ret = new String[100];
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < 10000; j++) {
				if (i == cheater && r.nextBoolean()) {
					sb.append('1');
				} else {
					if (pass(playerLevels[i], problemLevels[j])) {
						sb.append('1');
					} else {
						sb.append('0');
					}
				}
			}
			ret[i] = sb.toString();
		}
		// Arrays.sort(problemLevels);
		// double[][] checkProblem = new double[10000][];
		// for (int i = 0; i < 10000; i++) {
		// checkProblem[i] = new double[] { problemLevels[i],
		// getProblemLevel(ret, i) };
		// }
		// Arrays.sort(checkProblem, (a, b) -> Double.compare(a[0], b[0]));
		// for (int i = 0; i < 10000; i++) {
		// System.out.println(Arrays.toString(checkProblem[i]));
		// }
		// double[][] checkPlayer = new double[100][];
		// for (int i = 0; i < 100; i++) {
		// checkPlayer[i] = new double[] { playerLevels[i],
		// getPlayerAccuracy(ret, i) };
		// }
		// Arrays.sort(checkPlayer, (a, b) -> Double.compare(a[0], b[0]));
		// for (int i = 0; i < 100; i++) {
		// System.out.println(Arrays.toString(checkPlayer[i]));
		// }
		return ret;
	}

	static Random rnd = new Random();

	static boolean pass(double playerLevel, double problemLevel) {
		return rnd.nextDouble() < 1 / (1 + Math.exp(problemLevel - playerLevel));
	}

	static double[] random(int n) {
		Random r = new Random();
		double[] ret = new double[n];
		for (int i = 0; i < n; i++) {
			ret[i] = r.nextDouble() * 6 - 3;
		}
		return ret;
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
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
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
