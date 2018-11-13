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
	/*
	public void sortTable(){
		//Creates a new temporary list of rows and sorts it by column names then lastly overwrites the tables rows.
		ArrayList<Row> sortedRows = new ArrayList<Row>();
		for(Row row : this.rows)
		{
			Row rowToAdd = new Row();
			for(String columnName : this.columnNames)
			{
				if(!columnName.isEmpty()){
					//System.out.println("adding to column: |" + columnName + "|");
					for(Column column : row.columns)
					{
						if(columnName.equals(column.name)){
							//System.out.println("value added: "+column.value);
							rowToAdd.columns.add(column);
							break;
						}
					}
				}
			}
			sortedRows.add(rowToAdd);
		}
		this.rows.clear();
		this.rows.addAll(sortedRows);
	}
	*/
	public boolean hasColumn(String column){
		for(String columnName : columnNames){
			if(column.equals(columnName))
				return true;
		}
		return false;
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