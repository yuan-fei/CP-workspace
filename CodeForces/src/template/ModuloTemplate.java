package template;

public class ModuloTemplate {

	public static void main(String[] args) {
		System.out.println(C1(100, 6)); // 192052393
		System.out.println(C1(100, 0));
		System.out.println(C2(100, 6)); // 192052393
		System.out.println(C2(100, 0));
	}

	static long mod = 1000000007;

	public static long C1(int n, int m) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = 1;
			for (int i = 2; i <= m; i++) {
				r = mul(r, inverse(i));
			}
			for (int i = 0; i < m; i++) {
				r = mul(r, (n - i));
			}
			return r;
		}
	}

	static long fact1(int n) {
		long r = 1;
		for (int i = 2; i <= n; i++) {
			r = mul(r, i);
		}
		return r;
	}

	/** set the max N for N! */
	static int maxN = 200002;
	static boolean factCacheInitialized = false;
	static long[] factCache;
	static long[] iFactCache;

	static long C2(int n, int m) {
		if (m > n || m < 0) {
			return 0;
		} else {
			if (m > n / 2) {
				m = n - m;
			}
			long r = mul(fact2(n), mul(inverseFact(m), inverseFact(n - m)));
			return r;
		}
	}

	static long fact2(int n) {
		if (!factCacheInitialized) {
			initFact(maxN);
			factCacheInitialized = true;
		}
		return factCache[n];
	}

	static long inverseFact(int n) {
		if (!factCacheInitialized) {
			initFact(maxN);
			factCacheInitialized = true;
		}
		return iFactCache[n];
	}

	static void initFact(int n) {
		factCache = new long[n];
		iFactCache = new long[n];
		factCache[0] = 1;
		for (int i = 1; i < n; i++) {
			factCache[i] = mul(factCache[i - 1], i);
		}
		iFactCache[n - 1] = inverse(factCache[n - 1]);
		for (int i = n - 2; i >= 0; i--) {
			iFactCache[i] = mul(iFactCache[i + 1], i + 1);
		}
	}

	/** Fermat theorem: Note p must be prime. 1/n = n^(p-2) mod p */
	static long inverse(long n) {
		long r = exp(n, mod - 2);
		return r;
	}

	private static long exp(long base, long exp) {
		if (exp == 0) {
			return 1;
		} else if (exp % 2 == 1) {
			return mul(base, exp(base, exp - 1));
		} else {
			long t = exp(base, exp / 2);
			return mul(t, t);
		}
	}

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	static long mul(long a, long b) {
		return (a * b) % mod;
	}

}
