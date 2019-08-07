
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

public class AppyLovesOne {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int n = in.nextInt();
		int q = in.nextInt();
		int k = in.nextInt();
		int[] ones = new int[n];
		for (int j = 0; j < n; j++) {
			ones[j] = in.nextInt();
		}
		String query = in.next();
		Integer[] r = solve(n, q, k, ones, query);
		for (int i : r) {
			System.out.println(i);
		}

		in.close();
	}

	private static Integer[] solve(int n, int q, int k, int[] ones, String query) {
		Queue<Interval> l = new LinkedList<Interval>();
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(Comparator.comparing(i -> -i));
		int right = ones.length;
		Interval last = null;
		for (int i = ones.length - 1; i >= 0; i--) {
			if (i == 0 || ones[i] != ones[i - 1]) {
				last = new Interval(right - i, ones[i]);
				l.offer(last);
				Integer cnt = map.get(right - i);
				if (cnt == null) {
					cnt = 0;
				}
				if (ones[i] == 1) {
					map.put(right - i, cnt + 1);
				}
				right = i;
			}
		}
		List<Integer> res = new ArrayList<Integer>();
		char[] qs = query.toCharArray();
		for (int i = 0; i < qs.length; i++) {
			if (qs[i] == '!') {
				Interval itv = l.peek();
				if (itv.isOne == 1) {
					int cnt = map.get(itv.length);
					cnt--;
					if (cnt == 0) {
						map.remove(itv.length);
					} else {
						map.put(itv.length, cnt);
					}
					if (map.containsKey(itv.length - 1)) {
						cnt = map.get(itv.length - 1);
					} else {
						cnt = 0;
					}
					cnt++;
					map.put(itv.length - 1, cnt);
				}
				itv.length--;
				if (itv.length == 0) {
					l.poll();
				}
				if (itv.isOne == 1) {
					if (last.isOne == 1) {
						int cnt = map.get(last.length);
						cnt--;
						if (cnt == 0) {
							map.remove(last.length);
						} else {
							map.put(last.length, cnt);
						}
					} else {
						last = new Interval(0, 1);
						l.offer(last);
					}
					int cnt;
					if (map.containsKey(last.length + 1)) {
						cnt = map.get(last.length + 1);
					} else {
						cnt = 0;
					}
					cnt++;
					map.put(last.length + 1, cnt);
				} else {
					if (last.isOne == 1) {
						last = new Interval(0, 0);
						l.offer(last);
					}
				}
				last.length++;
			} else {
				int maxLen = 0;
				if (!map.isEmpty()) {
					maxLen = map.firstKey();
				}
				res.add(Math.min(k, maxLen));
			}
		}
		return res.toArray(new Integer[0]);
	}

	static class Interval {
		int length;
		int isOne;

		public Interval(int length, int isOne) {
			this.length = length;
			this.isOne = isOne;
		}

	}
}
