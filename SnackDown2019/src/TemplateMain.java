
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TemplateMain {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int m = in.nextInt();
			System.out.println();
		}
		in.close();
	}
}
