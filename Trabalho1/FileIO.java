import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {
	private static final String WINDOWS_SUFFIX = ".txt";
	private static final String OUT_SUFFIX = ".lrc";

	private final String filename;
	private File file = null;
	private final boolean hasSuffix;
	private final String ls = System.getProperty("line.separator");

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

	public String readTextFile() throws Exception {
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

	    return result;
	}

	public List<BigInteger> read() throws Exception {
		System.out.println("Reading the file...");
		Scanner reader = new Scanner(file);
		List<BigInteger> result = new ArrayList<>();

		try {
			while(reader.hasNextInt()) {
				result.add(BigInteger.valueOf(reader.nextInt()));
			}
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
		FileOutputStream writer = new FileOutputStream(filename);

		try {
			for (BigInteger b : content)
				writer.write(b.intValue());
		} finally {
			writer.close();
		}

		System.out.println("The file was saved with the name: " + filename + ".");
	}
}