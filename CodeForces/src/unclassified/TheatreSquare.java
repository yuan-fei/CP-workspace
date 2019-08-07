package unclassified;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TheatreSquare {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int n = in.nextInt();
		int m = in.nextInt();
		int a = in.nextInt();
		long r = solve(n, m, a);
		System.out.println(r);
		in.close();
	}

	private static long solve(long n, long m, int a) {
		long l = (long) Math.ceil(1.0 * n / a);
		long w = (long) Math.ceil(1.0 * m / a);
		return l * w;
	}

}
