import java.util.*;
import java.io.*;
public class Delete{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	public Statement statement;
	public Config config;
	public Delete (Statement statement, Config config){
		this.statement = statement;
		this.config = config;
	}

	public void deleteRow(){
		File fileToRead = new File(statement.table);

		BufferedReader reader = new BufferedReader(new FileReader(fileToRead));

		String lineToRemove = this.whereString;
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.equals(lineToRemove)) continue;
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		boolean successful = tempFile.renameTo(inputFile);



	}

}
