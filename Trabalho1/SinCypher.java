
import java.math.*;

public class SinCypher {
	int keyA = 10, keyF = 10;

	public static void main(String args[]){
		System.out.println("teste");
	}

	public String encrypt(String message) {
		String newMessage = new String();
		int i;

		for (i=0; i<message.length(); i++){
			newMessage += (char)(((int) message.charAt(i)) + calculateKey(i));
		}

		return newMessage;
	}

	public String decrypt(String message){
		String newMessage = new String();
		int i;

		for (i=0; i<message.length(); i++){
			newMessage += (char)(((int) message.charAt(i)) - calculateKey(i));
		}

		return newMessage;
	}

	private int calculateKey(int position){
		return (int) Math.sin(position*keyF)*keyA;
	}
}