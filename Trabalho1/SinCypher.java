
import java.math.*;

public class SinCypher {
	int key = 10;

	public static void main(String args[]){
		SinCypher sc = new SinCypher();
		String message = "Goodbye World...";
		String encrypted, decrypted;

		encrypted = sc.encrypt(message);
		decrypted = sc.decrypt(encrypted);

		System.out.println("Original: " + message + "\nEncriptada: " + encrypted + "\nDecriptada: " + decrypted);

	}

	public String encrypt(String message) {
		String newMessage = new String();
		char temp[] = new char[message.length()];
		int i;

		for(i=0; i<message.length(); i++){
			temp[(i*key)%message.length()] = message.charAt(i);
		}

		for(i=0; i<message.length(); i++){
			newMessage += temp[i];
		}

		return newMessage;
	}

	public String decrypt(String message){
		String newMessage = new String();
		char temp[] = new char[message.length()];
		int i;

		for(i=0; i<message.length(); i++){
			temp[i] = message.charAt((i*key)%message.length());
		}

		for(i=0; i<message.length(); i++){
			newMessage += temp[i];
		}

		return newMessage;
	}
}