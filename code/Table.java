import java.util.*;
import java.io.*;
public class Table{
	public ArrayList<String> columnNames = new ArrayList<String>();
	public ArrayList<Row> rows = new ArrayList<Row>();

	public void readColumnNames(Statement statement, Config config){
		//System.out.println("test");
		File fileToRead = new File(statement.table);
		try{
			Scanner sctable = new Scanner(fileToRead, config.getCharset());
			if(sctable.hasNextLine())
			{
				String row = sctable.nextLine();

				this.columnNames = new ArrayList<String>(Arrays.asList(row.split("\t")));
			}
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

	public void printColumnNames()
	{
		for(String column : columnNames)
		{
			if(!column.isEmpty()){	
				System.out.print("|" + column + "|");
			}
		}
		System.out.println();
	}
}