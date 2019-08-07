package qualification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class MrX {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				char[] a = getCharArr(in);
				int r = solve(a);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static int solve(char[] a) {
		Stack<State> s = new Stack<>();
		for (char c : a) {
			switch (c) {
			case 'x':
				s.push(new State(true, false, 1, 1));
				break;
			case 'X':
				s.push(new State(false, true, 1, 1));
				break;
			case '1':
				s.push(new State(true, true, 0, 1));
				break;
			case '0':
				s.push(new State(false, false, 1, 0));
				break;
			case '(':
				break;
			case ')':
				State st1 = s.pop();
				State st2 = s.pop();
				s.push(st2.operate(st1));
				break;
			case '&':
			case '|':
			case '^':
				s.peek().postOperator = c;
				break;
			}
		}
		State st = s.pop();
		int min = Math.min(st.minToTrue, st.minToFalse);
		return min;
	}

	static class State {
		boolean trueValue = false;
		boolean falseValue = false;
		int minToTrue = Integer.MAX_VALUE;
		int minToFalse = Integer.MAX_VALUE;
		char postOperator;

		public State(boolean trueValue, boolean falseValue) {
			super();
			this.trueValue = trueValue;
			this.falseValue = falseValue;
		}

		public State(boolean trueValue, boolean falseValue, int minToTrue, int minToFalse) {
			super();
			this.trueValue = trueValue;
			this.falseValue = falseValue;
			this.minToTrue = minToTrue;
			this.minToFalse = minToFalse;
		}

		public State operate(State s) {
			State rt = null;
			switch (postOperator) {
			case '&':
				rt = and(s);
				break;
			case '|':
				rt = or(s);
				break;
			case '^':
				rt = xor(s);
				break;
			}
			return rt;
		}

		public State and(State s) {
			State rt = new State(this.trueValue && s.trueValue, this.falseValue && s.falseValue);
			if (rt.trueValue == rt.falseValue) {
				if (rt.trueValue) {
					rt.minToTrue = 0;
					// &->^
					rt.minToFalse = 1;
				} else {
					rt.minToFalse = 0;
					// &->|
					rt.minToTrue = Math.min(this.minToTrue + 1, s.minToTrue + 1);
				}
			} else {
				rt.minToFalse = Math.min(this.minToFalse, s.minToFalse);
				// &->|
				rt.minToTrue = Math.min(this.minToTrue + 1, s.minToTrue + 1);
			}
			return rt;
		}

		public State or(State s) {
			State rt = new State(this.trueValue || s.trueValue, this.falseValue || s.falseValue);
			if (rt.trueValue == rt.falseValue) {
				if (rt.trueValue) {
					rt.minToTrue = 0;
					// |->&
					rt.minToFalse = Math.min(this.minToFalse + 1, s.minToFalse + 1);
				} else {
					rt.minToFalse = 0;
					rt.minToTrue = Math.min(this.minToTrue, s.minToTrue);
				}
			} else {
				// |->&
				rt.minToFalse = Math.min(this.minToFalse + 1, s.minToFalse + 1);
				rt.minToTrue = Math.min(this.minToTrue, s.minToTrue);
			}
			return rt;
		}

		public State xor(State s) {
			State rt = new State(this.trueValue ^ s.trueValue, this.falseValue ^ s.falseValue);
			if (rt.trueValue == rt.falseValue) {
				if (rt.trueValue) {
					rt.minToTrue = 0;
					rt.minToFalse = 1;
				} else {
					rt.minToFalse = 0;
					rt.minToTrue = Math.min(s.minToTrue + 1, this.minToTrue + 1);
				}
			} else {
				rt.minToFalse = Math.min(this.minToFalse + 1, s.minToFalse + 1);
				rt.minToTrue = Math.min(this.minToTrue + 1, s.minToTrue + 1);
			}
			return rt;
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

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
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

	static char[] getCharArr(Scanner in) {
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
