import java.util.*;
import java.io.*;
public class DeleteTest{

	public static void main(String[] args) {

		try {
		File inputFile = new File("insert.tsv");
		File tempFile = new File("tempinsert.tsv");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = "titleId=";
		String currentLine;

		String columnsString = reader.readLine();
		String[] columns = columnsString.split("\t");
		/*
		for(String column : columns){
			System.out.println(column);
		}
		*/
		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove

		    String trimmedLine = currentLine.trim();

		    if(trimmedLine.equals(lineToRemove)) continue;
				/*
				System.out.println(trimmedLine);
				System.out.println(currentLine);
				*/
		    writer.write(currentLine + System.getProperty("line.separator"));
		}

		writer.close();
		reader.close();
		//boolean successful = tempFile.renameTo(inputFile);
		}
		catch (FileNotFoundException e) {

		}
		catch (IOException e) {

		}

	}

}
