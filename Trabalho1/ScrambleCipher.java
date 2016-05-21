public class ScrambleCipher {
	private int publicKey = 7;

	public void setPublicKey(int key) {
		this.publicKey = 7;
	}

	public String encrypt(String message) {
		String newMessage = new String();
		char temp[] = new char[message.length()];
		int i;

		for (i = 0; i < message.length(); i++) {
			temp[(i * publicKey) % message.length()] = message.charAt(i);
		}

		for (i = 0; i < message.length(); i++) {
			newMessage += temp[i];
		}

		return newMessage;
	}

	public String decrypt(String message) {
		String newMessage = new String();
		char temp[] = new char[message.length()];
		int i;

		for (i = 0; i < message.length(); i++) {
			temp[i] = message.charAt((i * publicKey) % message.length());
		}

		for (i = 0; i < message.length(); i++) {
			newMessage += temp[i];
		}

		return newMessage;
	}
}