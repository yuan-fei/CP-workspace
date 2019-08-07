package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CircularDance {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		int[][] a = new int[t][2];
		for (int i = 0; i < t; ++i) {
			a[i][0] = in.nextInt();
			a[i][1] = in.nextInt();
		}
		List<Integer> r = solve(t, a);
		System.out.println(getStr(r));
		in.close();
	}

	private static List<Integer> solve(int t, int[][] a) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			if (!map.containsKey(a[i][0])) {
				map.put(a[i][0], new ArrayList<Integer>());
			}
			if (!map.containsKey(a[i][1])) {
				map.put(a[i][1], new ArrayList<Integer>());
			}
			map.get(a[i][0]).add(a[i][1]);
			map.get(a[i][1]).add(a[i][0]);
		}
		List<Integer> res = new ArrayList<>();
		dfs(1, map, res, new HashSet<Integer>());

		if (res.get(1) != a[res.get(0) - 1][0] && res.get(1) != a[res.get(0) - 1][1]) {
			Collections.reverse(res);
		}
		return res;
	}

	private static void dfs(int i, Map<Integer, List<Integer>> map, List<Integer> res, HashSet<Integer> set) {
		res.add(i);
		set.add(i);
		for (int j : map.get(i)) {
			if (!set.contains(j)) {
				dfs(j, map, res, set);
			}
		}
	}

	static String getStr(List<Integer> a) {
		String[] str = new String[a.size()];
		for (int i = 0; i < a.size(); i++) {
			str[i] = a.get(i) + "";
		}
		return String.join(" ", str);
	}
}
