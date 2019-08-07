package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class APlusB2 {
	public static void main(String[] args) throws IOException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			long a = in.nextLong();
			long b = in.nextLong();
			out.println(a + b * b);
		}
	}
}
