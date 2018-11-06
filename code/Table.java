import java.util.*;
import java.io.*;
public class Table{
	public ArrayList<String> columns = new ArrayList<String>();


	public void readColumnNames(Statement statement, Config config){
		//System.out.println("test");
		File fileToRead = new File(statement.table);
		try{
			Scanner sctable = new Scanner(fileToRead, config.getCharset());
			if(sctable.hasNextLine())
			{
				String row = sctable.nextLine();

				this.columns = new ArrayList<String>(Arrays.asList(row.split("\t")));
			}
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

	public void printColumnNames()
	{
		for(String column : columns)
		{
			if(!column.isEmpty()){	
				System.out.println(" " + column + " ");
			}
		}
	}
}