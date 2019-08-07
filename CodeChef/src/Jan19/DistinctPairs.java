package Jan19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DistinctPairs {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		Element[] a = new Element[n];
		Element[] b = new Element[m];
		for (int i = 0; i < n; i++) {
			a[i] = new Element(in.nextInt(), i);
		}
		for (int i = 0; i < m; i++) {
			b[i] = new Element(in.nextInt(), i);
		}
		List<int[]> r = solve(n, m, a, b);
		StringBuilder sb = new StringBuilder();
		for (int[] pair : r) {
			sb.append(pair[0]);
			sb.append(" ");
			sb.append(pair[1]);
			sb.append("\n");
		}
		System.out.println(sb.toString());
		in.close();
	}

	private static List<int[]> solve(int n, int m, Element[] a, Element[] b) {
		Arrays.sort(a);
		Arrays.sort(b);
		int i = 0;
		int j = 0;
		List<int[]> res = new ArrayList<>();
		while (i < a.length && j < b.length) {
			res.add(new int[] { a[i].idx, b[j].idx });
			if (a[i].x < b[j].x) {
				i++;
			} else {
				j++;
			}
		}
		if (i == a.length) {
			for (int k = j + 1; k < b.length; k++) {
				res.add(new int[] { a[a.length - 1].idx, b[k].idx });
			}
		} else if (j == b.length) {
			for (int k = i + 1; k < a.length; k++) {
				res.add(new int[] { a[k].idx, b[b.length - 1].idx });
			}
		}
		return res;
	}

	static class Element implements Comparable<Element> {
		int x;
		int idx;

		public Element(int x, int idx) {
			this.x = x;
			this.idx = idx;
		}

		@Override
		public int compareTo(Element o) {
			return Integer.compare(x, o.x);
		}

	}

}
