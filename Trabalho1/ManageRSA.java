import static java.lang.Math.sqrt;

import java.util.List;
import java.util.ArrayList;
import java.math.*;

public class ManageRSA {
	private static final int CONSTANT_P = 257;
	private static final int CONSTANT_Q = 263;
	private static final int PRIVATE_KEY = 7;

	private BigInteger n, z, p, q, publicKey, privateKey;

	public ManageRSA() {
		initVariables(CONSTANT_P, CONSTANT_Q);
		publicKey = autoPublic();
		privateKey = calculatePrivateKey(publicKey);
	}

	public List<BigInteger> encrypt(String message) {
		int i;
		List<BigInteger> newMessage = new ArrayList<BigInteger>();

		for (i = 0; i < message.length(); i++) {
			BigInteger c = BigInteger.valueOf(message.charAt(i));
			newMessage.add(c.modPow(publicKey, n));
		}

		return newMessage;
	}

	public String decrypt(List<BigInteger> message) {
		String newMessage = new String();

		for (int i = 0; i < message.size(); i++) {
			BigInteger newChar = message.get(i).modPow(privateKey, n);
			newMessage += Character.toString((char) newChar.intValue());
		}

		return newMessage;
	}

	public void initVariables(int p, int q) {
		if (checkPrime(p)) this.p = BigInteger.valueOf(p);
		if (checkPrime(q)) this.q = BigInteger.valueOf(q);
		n = this.p.multiply(this.q);
		z = this.p.subtract(BigInteger.valueOf(1)).multiply(this.q.subtract(BigInteger.valueOf(1)));
	}

	public int setPublicKey(int pub) {
		if (checkPrime(pub) && pub < z.intValue()) {
			this.publicKey = BigInteger.valueOf(pub);
			privateKey = calculatePrivateKey(BigInteger.valueOf(pub));
			return 1;
		}

		return -1;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	private BigInteger calculatePrivateKey(BigInteger pb) {
		BigInteger pv = publicKey.modInverse(z);
		return pv;
	}

	private BigInteger autoPublic() {
		return BigInteger.valueOf(PRIVATE_KEY);
	}

	private boolean checkPrime(int p) {
		for (int i = 2; i < sqrt(p) + 1; i++) {
			if (p % i == 0) return false;
		}

		return true;
	}

	public static void main(String args[]) {
		ManageRSA a = new ManageRSA();

		String message = "olar";
		List<BigInteger> cyphered = a.encrypt(message);

		System.out.println(message
				+ " - " + cyphered
				+ " - " + a.decrypt(cyphered));
	}
}
