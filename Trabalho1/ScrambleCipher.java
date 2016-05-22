public class ScrambleCipher {
	private int key = 13;

	public void setKey(int key) {
		this.key = key;
	}

	public static void main(String[] args){
		ScrambleCipher sc = new ScrambleCipher();
		String message = "Goodbye World...";
		String encrypted, decrypted;

		encrypted = sc.encrypt(message);
		decrypted = sc.decrypt(encrypted);

		System.out.println(message.length());
		System.out.println("Original: " + message + "\nEncriptada: " + encrypted + "\nDecriptada: " + decrypted);

	}

	public String encrypt(String message) {
		String newMessage = new String();
		int temp[] = new int[message.length()];
		int i;

		for(i=0; i<message.length(); i++){
			temp[(i*key)%message.length()] = (int) (((int) message.charAt(i)) + Math.floor(7*Math.sin(i)));
		}

		for(i=0; i<message.length(); i++){
			newMessage += (char) temp[i];
		}

		return newMessage;
	}

	public String decrypt(String message){
		String newMessage = new String();
		int temp[] = new int[message.length()];
		int i;

		for(i=0; i<message.length(); i++){
			temp[i] = (int) (message.charAt((i*key)%message.length()) - Math.floor(7*Math.sin(i)));
		}

		for(i=0; i<message.length(); i++){
			newMessage += (char) temp[i];
		}

		return newMessage;
	}
}