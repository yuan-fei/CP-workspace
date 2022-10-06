package y2022.qualification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SecondMeaning implements Runnable {

	public static void main(String[] args) throws FileNotFoundException {
//		new Thread(null, new Solution(), "Solution", 1L << 32).start();
		new SecondMeaning().run();
	}

	public void run() {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				String s = in.next();
				List<String> res = solve(n, s);
				out.println("Case #" + i + ":");
				out.print(str(res));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> solve(int n, String s) {
		List<String> ret = new ArrayList<>();
		int l = Math.min(10, s.length());
		String prefix = s.substring(0, l);
		int prefixValue = Integer.parseInt(prefix.replace('.', '1').replace('-', '0'), 2);
		int base = 1 << (10 - l);
		n--;
		for (int i = 0; i < 1024; i++) {
			if (i / base != prefixValue) {
				ret.add(encode(i));
				n--;
				if (n == 0) {
					break;
				}
			}
		}
		return ret;
	}

	static String encode(int v) {
		String ret = "";
		for (int i = 9; i >= 0; i--) {
			if (((v >> i) & 1) == 0) {
				ret += '-';
			} else {
				ret += '.';
			}
		}
		return ret;
	}

	final static long mod = 1000000007;

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

	static String str(List<String> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + "\n");
		}
		return sb.toString();
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

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
		}
		return arr;
	}

	static char[][] getStringArr(Scanner in, int size) {
		char[][] arr = new char[size][];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next().toCharArray();
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

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
