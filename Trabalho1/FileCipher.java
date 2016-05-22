import javafx.util.converter.BigIntegerStringConverter;

import java.math.BigInteger;
import java.util.List;

/**
 * Classe principal responsável por interagir com o usuário.
 */
public class FileCipher {

	private static final String COMMAND_GENERATE = "generate-keys";
	private static final String COMMAND_ENCRYPT = "encrypt";
	private static final String COMMAND_DECRYPT = "decrypt";

	private BigInteger RSAKey;

	public FileCipher(BigInteger rsaPublicKey) {
		this.RSAKey = rsaPublicKey;
	}

	private static boolean validateArguments(String args[]) {
		if (args[0].equals(COMMAND_GENERATE) && args.length == 1)
			return true;

		if (args.length < 3 || args.length > 4) {
			System.err.println("Error: command not accepted.");
			System.err.println("Try the following:");
			System.err.println("\t encrypt prime1 prime2 <filename_in> [<filename_out>]");
			System.err.println("\t decrypt prime1 prime2 <filename_in> [<filename_out>]");
			return false;
		}

		String rsaKeyString = args[1];

		BigIntegerStringConverter converter = new BigIntegerStringConverter();
		BigInteger rsaKey = converter.fromString(rsaKeyString);

		if (!rsaKey.isProbablePrime(0)) {
			System.err.println("The key must be a prime number");
			return false;
		}

		return true;
	}

	/**
	 * O programa principal responsável por direcionar os comandos escolhidos
	 * pelo usuário.
	 * <p>
	 * Os comandos possíveis são 'encrypt' e 'decrypt', que deverãp ser executados
	 * passando como argumento sua chave pública, assim como o nome do arquivo de
	 * entrada. Além disso, um nome de arquivo de saída também pode ser passado como
	 * argumento opicional.
	 *
	 * @param args os argumentos do programa
	 */
	public static void main(String args[]) {
		if (!validateArguments(args))
			return;

		String command = args[0];
		String rsaKeyString;
		BigInteger rsaKey = null;
		String sourceFile = null;
		String destinyFile = null;

		if (!command.equals(COMMAND_GENERATE)) {
			rsaKeyString = args[1];
			BigIntegerStringConverter converter = new BigIntegerStringConverter();
			rsaKey = converter.fromString(rsaKeyString);
			sourceFile = args[2];
			destinyFile = null;
			if (args.length == 4)
				destinyFile = args[3];
		}

		FileCipher fileCipher = new FileCipher(rsaKey);

		switch (command) {
			case COMMAND_GENERATE:
				ManageRSA rsa = new ManageRSA();
				System.out.println("Keys created successfully.");
				System.out.println("Your public key is: " + rsa.getPublicKey().toString());
				System.out.println("Your private key is: " + rsa.getPrivateKey().toString());
				break;
			case COMMAND_ENCRYPT:
				try {
					fileCipher.encrypt(sourceFile, destinyFile);
					System.out.println("File encrypted successfully.");
				} catch (Exception ex) {
					System.err.println("Occurred an error when trying to cipher the file:\n\t"
							+ ex.getMessage());
				}
				break;
			case COMMAND_DECRYPT:
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

	/**
	 * Lê um arquivo de texto de entrada e gera um arquivo criptografado.
	 * <p>
	 * A criptografia acontece em dois níveis:
	 * <ol>
	 *     <li>Deslocar a posição dos caracteres.</li>
	 *     <li>Aplicar o algoritmo RSA de criptografia.</li>
	 * </ol>
	 * Após a criptografia, o arquivo é salvo como tipo binário. Se o usuário
	 * não houver optado por um nome no arquivo de saída, este será igual ao
	 * do arquivo de entrada adicionada a extensão '.lrc'. Caso contrário, o
	 * nome escolhido pelo usuário será utilizado.
	 *
	 * @param  fileIn  o nome do arquivo de entrada a ser criptografado
	 * @param  fileOut o nome do arquivo binário de saída
	 * @see         ScrambleCipher#encrypt(String)
	 * @see         ManageRSA#encrypt(BigInteger, String)
	 */
	public void encrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		String content = fio.readTextFile();

		System.out.println("Encrypting...");
		// Applying the ScrambleCipher
		ScrambleCipher scramble = new ScrambleCipher();
		content = scramble.encrypt(content);

		// Applying the RSA
		ManageRSA rsa = new ManageRSA();
		List<BigInteger> encrypted = rsa.encrypt(RSAKey, content);

		if (fileOut != null)
			fio.write(encrypted, fileOut);
		else fio.write(encrypted);
	}

	/**
	 * Lê um arquivo binário de entrada e gera um arquivo de texto descriptografado.
	 * <p>
	 * A descriptografia acontece em dois níveis:
	 * <ol>
	 *     <li>Aplicar o algoritmo RSA de descriptografia.</li>
	 *     <li>Deslocar a posição dos caracteres de volta à inicial.</li>
	 * </ol>
	 * Após a descriptografia, o arquivo é salvo como tipo texto novamente. Se o usuário
	 * não houver optado por um nome no arquivo de saída, este será igual ao
	 * do arquivo de entrada adicionada a extensão '.txt'. Caso contrário, o
	 * nome escolhido pelo usuário será utilizado.
	 *
	 * @param  fileIn  o nome do arquivo binário de entrada a ser descriptografado
	 * @param  fileOut o nome do arquivo de texto de saída
	 * @see         ScrambleCipher#decrypt(String)
	 * @see         ManageRSA#decrypt(BigInteger, List)
	 */
	public void decrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		List<BigInteger> content = fio.read();

		System.out.println("Decrypting...");
		// Applying the RSA
		ManageRSA rsa = new ManageRSA();
		String decrypted = rsa.decrypt(RSAKey, content);

		// Applying the ScrambleCipher
		ScrambleCipher scramble = new ScrambleCipher();
		decrypted = scramble.decrypt(decrypted);

		if (fileOut == null)
			fio.writeTextFile(decrypted, fileIn.replace(".txt", ""));
		else
			fio.writeTextFile(decrypted, fileOut);
	}
}
