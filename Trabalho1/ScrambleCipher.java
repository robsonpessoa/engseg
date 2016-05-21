public class ScrambleCipher {
	private static final int KEY = 7;

	public String encrypt(String message) {
		String newMessage = new String();
		char temp[] = new char[message.length()];
		int i;

		for (i = 0; i < message.length(); i++) {
			temp[(i * KEY) % message.length()] = message.charAt(i);
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
			temp[i] = message.charAt((i * KEY) % message.length());
		}

		for (i = 0; i < message.length(); i++) {
			newMessage += temp[i];
		}

		return newMessage;
	}
}