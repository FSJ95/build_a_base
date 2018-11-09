import java.util.*;
import java.io.*;
public class DeleteTest{

	public static void main(String[] args) {

		try {
		File inputFile = new File("insert.tsv");
		File tempFile = new File("tempinsert.tsv");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));

		String lineToRemove = "ordering=1";
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.equals(lineToRemove)) continue;
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		boolean successful = inputFile.renameTo(inputFile);
		}
		catch (FileNotFoundException e) {

		}
		catch (IOException e) {

		}

	}

}
