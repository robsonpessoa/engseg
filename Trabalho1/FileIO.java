import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	private static final String WINDOWS_SUFFIX = ".txt";
	private static final String OUT_SUFFIX = ".lrc";

	private final String filename;
	private final boolean hasSuffix;
	private final String ls = System.getProperty("line.separator");
	private File file = null;

	public FileIO(String filename) throws Exception {
		if (filename.endsWith(WINDOWS_SUFFIX)) {
			int size = filename.length();
			this.filename = filename.substring(0, size - WINDOWS_SUFFIX.length());
			this.hasSuffix = true;
		} else {
			this.filename = filename;
			this.hasSuffix = false;
		}

		this.file = new File(filename);
	}

	public static void main(String args[]) {
		try {
			FileIO fio = new FileIO("file_in");
			String message = fio.readTextFile();
			fio.write(message, "file_out.bin");

			fio = new FileIO("file_out.bin");
			List<BigInteger> content = fio.read();
			System.out.println(content);

			String newMessage = new String();

			for (int i = 0; i < content.size(); i++) {
				BigInteger newChar = content.get(i);
				newMessage += Character.toString((char) newChar.intValue());
			}

			fio.writeTextFile(newMessage, "file_out");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readTextFile() throws Exception {
		System.out.println("Reading the file...");
		BufferedReader reader = new BufferedReader(new FileReader(file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();

	    String result = null;

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(this.ls);
	        }

	        result = stringBuilder.toString();
	    } finally {
	        reader.close();
	    }

		System.out.println("The file was read successfully.");

	    return result;
	}

	public void writeTextFile(String content, String filename) throws IOException {
		System.out.println("Saving the text file...");
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		try {
			writer.write(content);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

		System.out.println("The file was saved with the name: " + filename);
	}

	public void writeTextFile(String content) throws IOException {
		String filename = this.filename + OUT_SUFFIX;
		if (hasSuffix)
			filename += WINDOWS_SUFFIX;

		writeTextFile(content, filename);
	}

	public List<BigInteger> read() throws Exception {
		System.out.println("Reading the file...");
		DataInputStream reader = new DataInputStream(new FileInputStream(file));
		List<BigInteger> result = new ArrayList<>();

		try {
			while(true) {
				result.add(BigInteger.valueOf(reader.readInt()));
			}
		} catch(EOFException ex){

		} finally {
			reader.close();
		}

		System.out.println("The file was read successfully.");

		return result;
	}

	public void write(String content) throws Exception {
		String filename = this.filename + OUT_SUFFIX;
		if (hasSuffix)
			filename += WINDOWS_SUFFIX;

		write(content, filename);
	}

	public void write(List<BigInteger> content) throws Exception {
		String filename = this.filename + OUT_SUFFIX;
		if (hasSuffix)
			filename += WINDOWS_SUFFIX;

		write(content, filename);
	}

	public void write(String content, String filename) throws Exception {
		char values[] = content.toCharArray();

		List<BigInteger> newContent = new ArrayList<>();
		for (char c : values)
			newContent.add(BigInteger.valueOf(c));

		write(newContent, filename);
	}

	public void write(List<BigInteger> content, String filename) throws Exception {
		System.out.println("Saving the file...");
		DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename));

		try {
			for (BigInteger b : content)
				writer.writeInt(b.intValue());
		} finally {
			writer.close();
		}

		System.out.println("The file was saved with the name: " + filename);
	}
}