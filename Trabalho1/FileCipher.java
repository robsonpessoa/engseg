import java.math.BigInteger;
import java.util.List;

/**
 * Created by rpessoa on 21/05/16.
 */
public class FileCipher {

	public void encrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		String content = fio.readTextFile();

		ManageRSA rsa = new ManageRSA();
		List<BigInteger> encrypted = rsa.encrypt(content);

		if (fileOut != null)
			fio.write(encrypted, fileOut);
		else fio.write(encrypted);
	}

	public void decrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		List<BigInteger> content = fio.read();

		ManageRSA rsa = new ManageRSA();
		String decrypted = rsa.decrypt(content);

		if (fileOut != null)
			fio.write(decrypted, fileOut);
		else fio.write(decrypted);
	}

	public static void main(String args[]) {
		String command = args[0];
		String sourceFile = args[1];
		String destinyFile = null;
		if (args[2] != null)
			destinyFile = args[2];

		FileCipher fileCipher = new FileCipher();

		switch (command) {
			case "encrypt":
				try {
					fileCipher.encrypt(sourceFile, destinyFile);
					System.out.println("File encrypted successfully.");
				} catch (Exception ex) {
					System.err.println("Occurred an error when trying to cipher the file:\n\t"
							+ ex.getMessage());
				}
				break;
			case "decrypt":
				try {
					fileCipher.decrypt(sourceFile, destinyFile);
					System.out.println("File decrypted successfully.");
				} catch (Exception ex) {
					System.err.println("Occurred an error when trying to cipher the file:\n\t"
							+ ex.getMessage());
				}
				break;
		}

	}
}
