import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManageRSA {
	private static final int SIZE = 1024;
	private static final int CONSTANT_P = 257;
	private static final int CONSTANT_Q = 263;
	private static final int PUBLIC_KEY = 7;

	private BigInteger n, z, p, q, publicKey, privateKey;

	public ManageRSA() {
		initVariables(BigInteger.valueOf(CONSTANT_P), BigInteger.valueOf(CONSTANT_Q));
		publicKey = autoPublic();
		privateKey = calculatePrivateKey(publicKey);
	}

	private BigInteger generatePrime() {
		return new BigInteger(SIZE, 15, new Random());
	}

	public List<BigInteger> encrypt(BigInteger publicKey, String message) {
		int i;
		List<BigInteger> newMessage = new ArrayList<>();

		for (i = 0; i < message.length(); i++) {
			BigInteger c = BigInteger.valueOf(message.charAt(i));
			newMessage.add(c.modPow(publicKey, n));
		}

		return newMessage;
	}

	public String decrypt(List<BigInteger> message) {
		return decrypt(privateKey, message);
	}

	public String decrypt(BigInteger privateKey, List<BigInteger> message) {
		String newMessage = new String();

		for (int i = 0; i < message.size(); i++) {
			BigInteger newChar = message.get(i).modPow(privateKey, n);
			newMessage += Character.toString((char) newChar.intValue());
		}

		return newMessage;
	}

	private void initVariables(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;
		n = this.p.multiply(this.q);
		z = this.p.subtract(BigInteger.valueOf(1)).multiply(this.q.subtract(BigInteger.valueOf(1)));
	}

	public int setPublicKey(BigInteger pub) {
		if (pub.isProbablePrime(0) && pub.compareTo(z) < 0) {
			this.publicKey = pub;
			privateKey = calculatePrivateKey(pub);
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
		BigInteger key;
		do {
			key = new BigInteger(2 * SIZE, new Random());
		}
		while ((key.compareTo(z) != 1)
				||
				(key.gcd(z).compareTo(BigInteger.valueOf(1)) != 0));

		return key;
	}
}
