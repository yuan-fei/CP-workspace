package unclassified;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EhabAndAnotherAnotherXorProblem {
	static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		boolean aRemainingIsLarger = false;
		int curA = 0;
		int curB = 0;
		int ans = query(0, 0);
		if (ans == 1) {
			aRemainingIsLarger = true;
		} else {
			aRemainingIsLarger = false;
		}
		for (int i = 29; i >= 0; --i) {
			int bitMask = 1 << i;
			int ans1 = query(curA | bitMask, curB);
			int ans2 = query(curA, curB | bitMask);
			if (ans1 == 1 && ans2 == -1) {
				// both 0 at bit i
			} else if (ans1 == -1 && ans2 == 1) {
				// both 1 at bit i
				curA |= bitMask;
				curB |= bitMask;
			} else {
				if (aRemainingIsLarger) {
					curA |= bitMask;
				} else {
					curB |= bitMask;
				}
				if (ans1 == -1) {
					aRemainingIsLarger = false;
				} else {
					aRemainingIsLarger = true;
				}
			}
			// System.out.println(curA + ", " + curB);
		}

		System.out.println("! " + curA + " " + curB);
		in.close();
	}

	static int query(int c, int d) {
		System.out.println("? " + c + " " + d);
		// int a = 10;
		// int b = 5;
		// return (int) Math.signum((a ^ c) - (b ^ d));
		return in.nextInt();
	}

}
