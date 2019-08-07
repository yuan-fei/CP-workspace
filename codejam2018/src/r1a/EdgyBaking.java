package r1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class EdgyBaking {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			double p = in.nextDouble();
			int[] ws = new int[n];
			int[] hs = new int[n];
			for (int j = 0; j < n; j++) {
				ws[j] = in.nextInt();
				hs[j] = in.nextInt();
			}
			double r = solveLarge(p, ws, hs);
			System.out.println("Case #" + i + ": " + r);
		}
		in.close();

	}

	private static double solveSmall(double p, int[] ws, int[] hs) {
		int cnt = ws.length;
		int w = ws[0];
		int h = hs[0];
		int totalMin = 2 * (w + h) * cnt;
		double cutMax = Math.sqrt(w * w + h * h);
		double cutMin = Math.min(w, h);
		double pCutMax = 2 * cutMax;
		double pCutMin = 2 * cutMin;
		double delta = p - totalMin;
		double ratio = delta / pCutMax;
		if (ratio > cnt) {
			return totalMin + cnt * pCutMax;
		} else {
			double rMax = Math.ceil(ratio);
			double rMin = Math.floor(ratio);
			if (rMax * pCutMin <= delta || rMin * pCutMax >= delta) {
				return p;
			} else {
				return totalMin + rMin * pCutMax;
			}
		}
	}

	private static double solveLarge(double p, int[] ws, int[] hs) {
		int cnt = ws.length;
		double total = 0;
		List<Interval> intervals = new ArrayList<>();
		List<Interval> mergedIntervals = new ArrayList<>();
		for (int i = 0; i < cnt; i++) {
			int w = ws[0];
			int h = hs[0];
			intervals.add(new Interval(2 * Math.min(w, h), 2 * Math.sqrt(w * w + h * h)));
			total += 2 * (w + h);
		}
		mergedIntervals.add(new Interval(0, 0));
		for (int i = 0; i < intervals.size(); i++) {
			List<Interval> newIntervals = new ArrayList<>();
			for (int j = 0; j < mergedIntervals.size(); j++) {
				newIntervals.add(mergedIntervals.get(j).add(intervals.get(i)));
			}
			mergedIntervals = merge(mergedIntervals, newIntervals);
		}
		int i = Collections.binarySearch(mergedIntervals, new Interval(p - total, p - total));
		if (i >= 0) {
			return p;
		} else {
			i = -i - 1;
			return Math.min(total + mergedIntervals.get(i - 1).right, p);
		}
	}

	private static List<Interval> merge(List<Interval> mergedIntervals, List<Interval> newIntervals) {
		List<Interval> ret = new ArrayList<>();
		int i, j;
		i = j = 0;
		Interval cur = null;
		while (i < mergedIntervals.size() || j < newIntervals.size()) {
			if (cur == null) {
				if (i == mergedIntervals.size()) {
					cur = newIntervals.get(j);
				} else if (j == newIntervals.size()) {
					cur = mergedIntervals.get(i);
				} else if (mergedIntervals.get(i).compareTo(newIntervals.get(j)) <= 0) {
					cur = mergedIntervals.get(i);
				} else {
					cur = newIntervals.get(j);
				}
			}
			if ((i == mergedIntervals.size() || !cur.isIntersect(mergedIntervals.get(i)))
					&& (j == newIntervals.size() || !cur.isIntersect(newIntervals.get(j)))) {
				ret.add(cur);
				cur = null;
			} else {
				if (i < mergedIntervals.size() && cur.isIntersect(mergedIntervals.get(i))) {
					cur.merge(mergedIntervals.get(i));
					i++;
				}
				if (j < newIntervals.size() && cur.isIntersect(newIntervals.get(j))) {
					cur.merge(newIntervals.get(j));
					j++;
				}
			}

		}
		if (cur != null) {
			ret.add(cur);
		}
		return ret;
	}

	static class Interval implements Comparable<Interval> {
		double left;
		double right;

		public Interval(double left, double right) {
			super();
			this.left = left;
			this.right = right;
		}

		public Interval add(Interval intv) {
			return new Interval(this.left + intv.left, this.right + intv.right);
		}

		public boolean isIntersect(Interval intv) {
			return (this.left <= intv.right && this.right >= intv.left)
					|| (intv.left <= this.right && intv.right >= this.left);
		}

		public void merge(Interval intv) {
			this.left = Math.min(left, intv.left);
			this.right = Math.max(right, intv.right);
		}

		@Override
		public int compareTo(Interval o) {
			return Double.compare(this.left, o.left);
		}

		@Override
		public String toString() {
			return "[" + left + ", " + right + "]";
		}
	}
}
