package practice;

public class FightTime {

	public static void main(String[] args) {
		System.out.println(new FightTime().fightTime(500, 100, 0, 5));
	}

	public long fightTime(long hp, long attack, int level, long duration) {
		long delta = attack * level / 100;
		long t1 = bSearch(hp, attack, delta, 0);
		long t2 = bSearch(hp, attack, delta, duration);
		return Math.min(t1, t2);
	}

	long calc(long t, long delta, long hp, long attack, long duration) {

		long tMagic = Math.min(t - 1, duration);
		long tBasic = Math.max(t - 1 - tMagic, 0);
		long v = attack + tBasic * delta;
		long total = tBasic * attack + tBasic * (tBasic - 1) * delta / 2 + 5 * tMagic * v
				+ 5 * tMagic * (tMagic - 1) * delta / 2;
		return hp - total;
	}

	long bSearch(long hp, long attack, long delta, long duration) {
		long start = 0;
		long end = hp / attack;
		while (start + 1 < end) {
			long mid = start + (end - start) / 2;
			if (calc(mid, delta, hp, attack, duration) < 0) {
				end = mid;
			} else {
				start = mid;
			}
		}
		if (calc(start, delta, hp, attack, duration) <= 0) {
			return start;
		} else {
			return end;
		}
	}
}
