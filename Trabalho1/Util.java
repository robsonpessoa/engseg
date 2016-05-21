import static java.lang.Math.sqrt;

/**
 * Created by rpessoa on 21/05/16.
 */
public class Util {
	public static boolean isPrime(int p) {
		for (int i = 2; i < sqrt(p) + 1; i++) {
			if (p % i == 0) return false;
		}

		return true;
	}
}
