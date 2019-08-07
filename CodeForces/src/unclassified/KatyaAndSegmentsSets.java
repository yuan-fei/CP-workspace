package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class KatyaAndSegmentsSets {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int[] l = new int[k];
		int[] r = new int[k];
		int[] s = new int[k];
		for (int i = 0; i < k; ++i) {
			l[i] = in.nextInt();
			r[i] = in.nextInt();
			s[i] = in.nextInt();
		}
		add(n, l, r, s);
		for (int i = 1; i <= m; ++i) {
			int a = in.nextInt();
			int b = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			boolean res = solve(n, a, b, x, y);
			System.out.println(res ? "yes" : "no");
		}
		in.close();
	}

	private static boolean solve(int n, int a, int b, int x, int y) {
		for (int i = a - 1; i <= b - 1; i++) {
			if (!isInclude(sets[i], new int[] { x, y })) {
				return false;
			}
		}
		return true;
	}

	private static boolean isInclude(List<int[]> list, int[] it) {
		int i = Collections.binarySearch(list, it, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}
		});
		if (i < 0) {
			i = -i - 1;
		}
		for (; i < list.size(); i++) {
			if (list.get(i)[1] <= it[1]) {
				return true;
			}
		}
		return false;
	}

	static List<int[]>[] sets;

	private static void add(int n, int[] l, int[] r, int[] s) {
		sets = new List[n];
		for (int i = 0; i < l.length; i++) {
			if (sets[s[i] - 1] == null) {
				sets[s[i] - 1] = new ArrayList<>();
			}
			sets[s[i] - 1].add(new int[] { l[i], r[i] });
		}
		for (List<int[]> set : sets) {
			if (set != null) {
				Collections.sort(set, new Comparator<int[]>() {
					@Override
					public int compare(int[] o1, int[] o2) {
						if (o1[0] == o2[0]) {
							return Integer.compare(o1[1], o2[1]);
						}
						return Integer.compare(o1[0], o2[0]);
					}
				});
			}
		}
	}
}
