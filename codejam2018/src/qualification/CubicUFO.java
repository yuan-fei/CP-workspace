package qualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CubicUFO {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			double a = in.nextDouble();
			double[][] result = solveSmall(a);
			System.out.println("Case #" + i + ": ");
			for (int j = 0; j < result.length; j++) {
				System.out.println(result[j][0] + " " + result[j][1] + " " + result[j][2]);
			}
		}
		in.close();

	}

	private static double[][] solveSmall(double a) {
		double[][] result = new double[3][3];
		double theta = Math.asin(a / Math.sqrt(2)) - Math.PI / 4;
		result[0] = new double[] { -0.5 * Math.sin(theta), 0.5 * Math.cos(theta), 0 };
		result[1] = new double[] { 0.5 * Math.cos(theta), 0.5 * Math.sin(theta), 0 };
		result[2] = new double[] { 0, 0, 0.5 };
		return result;
	}

}
