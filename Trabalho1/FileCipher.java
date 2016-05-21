import java.math.BigInteger;
import java.util.List;

/**
 * Classe principal responsável por interagir com o usuário.
 */
public class FileCipher {

	private int RSAPublicKey;
	private int scrambleKey;

	public FileCipher(int rsaPublicKey, int scrambleKey) {
		this.RSAPublicKey = rsaPublicKey;
		this.scrambleKey = scrambleKey;
	}

	private static boolean validateArguments(String args[]) {
		if (args.length < 2 || args.length > 3) {
			System.err.println("Error: command not accepted.");
			System.err.println("Try the following:");
			System.err.println("\t encrypt <filename_in> [<filename_out>]");
			System.err.println("\t decrypt <filename_in> [<filename_out>]");
			return false;
		}

		String rsaKeyString = args[1];
		String scrambleKeyString = args[2];

		int rsaKey = Integer.parseInt(rsaKeyString);
		int scrambleKey = Integer.parseInt(scrambleKeyString);

		if (!Util.isPrime(rsaKey) || !Util.isPrime(scrambleKey)) {
			System.err.println("The keys must be a prime number");
			return false;
		}

		if (rsaKey == scrambleKey) {
			System.err.println("The keys must be distinct numbers");
			return false;
		}

		return true;
	}

	/**
	 * O programa principal responsável por direcionar os comandos escolhidos
	 * pelo usuário.
	 * <p>
	 * Os comandos possíveis são 'encrypt' e 'decrypt', que deverãp ser executados
	 * passando como argumento dois números primos distintos, assim como o nome do
	 * arquivo de entrada. Além disso, um nome de arquivo de saída também pode ser
	 * passado como argumento (opicional).
	 *
	 * @param args os argumentos do programa
	 */
	public static void main(String args[]) {
		if (!validateArguments(args))
			return;

		String command = args[0];
		String rsaKeyString = args[1];
		String scrambleKeyString = args[2];
		int rsaKey = Integer.parseInt(rsaKeyString);
		int scrambleKey = Integer.parseInt(scrambleKeyString);
		String sourceFile = args[3];
		String destinyFile = null;
		if (args.length == 5)
			destinyFile = args[4];

		FileCipher fileCipher = new FileCipher(rsaKey, scrambleKey);

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

	/**
	 * Lê um arquivo de texto de entrada e gera um arquivo criptografado.
	 * <p>
	 * A criptografia acontece em dois níveis:
	 * <ol>
	 *     <li>Deslocar a posição dos caracteres. (@link ScrambleCipher)</li>
	 *     <li>Aplicar o algoritmo RSA de criptografia. (@link ManageRSA)</li>
	 * </ol>
	 * Após a criptografia, o arquivo é salvo como tipo binário. Se o usuário
	 * não houver optado por um nome no arquivo de saída, este será igual ao
	 * do arquivo de entrada adicionada a extensão '.lrc'. Caso contrário, o
	 * nome escolhido pelo usuário será utilizado.
	 *
	 * @param  fileIn  o nome do arquivo de entrada a ser criptografado
	 * @param  fileOut o nome do arquivo binário de saída
	 * @see         ScrambleCipher#encrypt(String)
	 * @see         ManageRSA#encrypt(String)
	 */
	public void encrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		String content = fio.readTextFile();

		System.out.println("Encrypting...");
		// Applying the ScrambleCipher
		ScrambleCipher scramble = new ScrambleCipher();
		scramble.setPublicKey(scrambleKey);
		content = scramble.encrypt(content);

		// Applying the RSA
		ManageRSA rsa = new ManageRSA();
		rsa.setPublicKey(RSAPublicKey);
		List<BigInteger> encrypted = rsa.encrypt(content);

		if (fileOut != null)
			fio.write(encrypted, fileOut);
		else fio.write(encrypted);
	}

	/**
	 * Lê um arquivo binário de entrada e gera um arquivo de texto descriptografado.
	 * <p>
	 * A descriptografia acontece em dois níveis:
	 * <ol>
	 *     <li>Aplicar o algoritmo RSA de descriptografia. (@link ManageRSA)</li>
	 *     <li>Deslocar a posição dos caracteres de volta à inicial. (@link ScrambleCipher)</li>
	 * </ol>
	 * Após a descriptografia, o arquivo é salvo como tipo texto novamente. Se o usuário
	 * não houver optado por um nome no arquivo de saída, este será igual ao
	 * do arquivo de entrada adicionada a extensão '.txt'. Caso contrário, o
	 * nome escolhido pelo usuário será utilizado.
	 *
	 * @param  fileIn  o nome do arquivo binário de entrada a ser descriptografado
	 * @param  fileOut o nome do arquivo de texto de saída
	 * @see         ScrambleCipher#decrypt(String)
	 * @see         ManageRSA#decrypt(List)
	 */
	public void decrypt(String fileIn, String fileOut) throws Exception {
		FileIO fio = new FileIO(fileIn);
		List<BigInteger> content = fio.read();

		System.out.println("Decrypting...");
		// Applying the RSA
		ManageRSA rsa = new ManageRSA();
		rsa.setPublicKey(RSAPublicKey);
		String decrypted = rsa.decrypt(content);

		// Applying the ScrambleCipher
		ScrambleCipher scramble = new ScrambleCipher();
		scramble.setPublicKey(scrambleKey);
		decrypted = scramble.decrypt(decrypted);

		if (fileOut == null)
			fio.writeTextFile(decrypted, fileIn.replace(".txt", ""));
		else
			fio.writeTextFile(decrypted, fileOut);
	}
}
