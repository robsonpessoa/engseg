import java.math.BigInteger;
import java.util.List;

/**
 * Created by rpessoa on 21/05/16.
 */
public class FileCipher {

	public static void main(String args[]) {
		if (args.length < 2 || args.length > 3) {
			System.err.println("Error: command not accepted.");
			System.err.println("Try the following:");
			System.err.println("\t encrypt <filename_in> [<filename_out>]");
			System.err.println("\t decrypt <filename_in> [<filename_out>]");
			return;
		}

		String command = args[0];
		String sourceFile = args[1];
		String destinyFile = null;
		if (args.length == 3)
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

	public void encrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		String content = fio.readTextFile();

		System.out.println("Encrypting...");
		// Applying the SinCypher
		SinCypher sin = new SinCypher();
		content = sin.encrypt(content);

		// Applying the RSA
		ManageRSA rsa = new ManageRSA();
		List<BigInteger> encrypted = rsa.encrypt(content);

		if (fileOut != null)
			fio.write(encrypted, fileOut);
		else fio.write(encrypted);
	}

	public void decrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		List<BigInteger> content = fio.read();

		System.out.println("Decrypting...");
		// Applying the RSA
		ManageRSA rsa = new ManageRSA();
		String decrypted = rsa.decrypt(content);

		// Applying the SinCypher
		SinCypher sin = new SinCypher();
		decrypted = sin.decrypt(decrypted);

		if (fileOut == null)
			fio.writeTextFile(decrypted, fileIn.replace(".lrc", ""));
		else
			fio.writeTextFile(decrypted, fileOut);
	}
}
