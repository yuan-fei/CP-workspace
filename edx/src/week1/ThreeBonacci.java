package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ThreeBonacci {
	public static void main(String[] args) throws IOException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			long a = in.nextLong();
			long b = in.nextLong();
			long c = in.nextLong();
			int n = in.nextInt();
			long[] t = new long[Math.max(n + 1, 3)];
			t[0] = a;
			t[1] = b;
			t[2] = c;
			for (int i = 3; i <= n; i++) {
				t[i] = t[i - 1] + t[i - 2] - t[i - 3];
			}
			out.println(t[n]);
		}
	}
}
