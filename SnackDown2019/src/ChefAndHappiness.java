
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ChefAndHappiness {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();
			int[] nums = new int[n];
			for (int j = 0; j < n; j++) {
				nums[j] = in.nextInt();
			}
			boolean r = solve(n, nums);
			System.out.println(r ? "Truly Happy" : "Poor Chef");
		}
		in.close();
	}

	private static boolean solve(int n, int[] nums) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < nums.length; i++) {
			List<Integer> l = map.get(nums[i]);
			if (l == null) {
				l = new ArrayList<Integer>();
				map.put(nums[i], l);
			}
			l.add(i + 1);
		}
		for (Entry<Integer, List<Integer>> e : map.entrySet()) {
			List<Integer> l = e.getValue();
			if (l.size() > 1) {
				int cnt = 2;
				for (int i : l) {
					if (map.containsKey(i)) {
						cnt--;
					}
					if (cnt == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
