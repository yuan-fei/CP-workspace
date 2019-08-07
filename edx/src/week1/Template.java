package week1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Template {
	public static void main(String[] args) throws IOException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			out.println(in.nextInt() + in.nextInt());
		}
	}
}
