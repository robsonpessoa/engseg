import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
	private static final String WINDOWS_SUFIX = ".txt";
	private static final String OUT_SUFIX = "_cyphered";

	private final String filename;
	private File file = null;
	private final boolean hasSufix;
	private final String ls = System.getProperty("line.separator");

	public FileIO(String filename) {
		if (filename.endsWith(WINDOWS_SUFIX)) {
			int size = filename.length();
			this.filename = filename.substring(0, size - WINDOWS_SUFIX.length());
			this.hasSufix = true;
		} else {
			this.filename = filename;
			this.hasSufix = false;
		}

		try {
			this.file = new File(filename);
		} catch(Exception e) {
	    	e.printStackTrace();
	    } 
	}

	public String read() throws IOException {
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
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	        reader.close();
	    }

	    return result;
	}

	public void write(String content) throws IOException {
		String filename = this.filename + OUT_SUFIX;
		if (hasSufix)
			filename += WINDOWS_SUFIX;

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	    
	    try {
	        writer.write(content);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	        writer.close();
	    }
	}
}