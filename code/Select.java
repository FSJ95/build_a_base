import java.util.*;
import java.io.*;
public class Select{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	public Statement statement;
	public Config config;
	public Table selectResult = new Table();
	public Select (Statement statement, Config config)
	{
		this.statement = statement;
		this.config = config;
	}
	public void where(Statement statement){

	}
	public void readColumns(Statement statement, Config config){

	}
	public void readTable(){
		File fileToRead = new File(statement.table);
		try
		{
			Scanner sctable = new Scanner(fileToRead, config.getCharset());
			//Checks if the file has a line to later get the first line
			if(sctable.hasNextLine())
			{
				int i = 0;
				//Gets the first line of table file & splits it by tabs into an array
				String[] fileColumns = sctable.nextLine().split("\\t");
				//Create a list contain the index of the column names selected
				ArrayList<Integer> columnsToInsertIndex = new ArrayList<Integer>();
				//Loops through the columns of the first line with the array
				for(i = 0; i<fileColumns.length; i++)
				{
					//Loops through the statement columns & checks if they match with the columns of the file
					for(String column : statement.columns)
					{
						//trims whitespace in statement columns
						column = column.trim();
						//Checks if the column in the file matches with the one in the statement
						if(fileColumns[i].equals(column)){
							//Adds index of column and name of it to seperate arrays/lists for later use
							columnsToInsertIndex.add(i);
							//matchedColumns.add(column);
							this.selectResult.columnNames.add(column);
						}
					}
				}
				while(sctable.hasNextLine() && statement.limit>0)
				{
					//boolean whereMatch = false;
					BitSet matchBitSet = new BitSet(statement.conditions.size());

					matchBitSet.clear();

					//Creates an array of the data in the current line creating an array of strings seperated by tabs on the line (removing the tabs)
					String[] fullRow = sctable.nextLine().split("\\t");
					//Creates an empty list to add the data that matches the criteria of the statement
					//ArrayList<String> trimmedRow = new ArrayList<String>();
					Row rowToAdd = new Row();
					//String trimmedStr = "";
					for(i=0;i<fullRow.length;i++)
					{
						Column column = new Column(fileColumns[i], fullRow[i], i);
						//Checks if the index of the loop is in the columns selected, if it is it'll add it to the trimmed row
						if(columnsToInsertIndex.contains(i))
						{
							rowToAdd.columns.add(column);
							/*
							trimmedStr+=column.value;
							trimmedRow.add(column.value);
							*/
						}
						//Conditioning logic which looks through each condition in the statement & if it matches it'll set whereMatch to true
						int j;
						for(j=0;j<statement.conditions.size();j++)
						{
							if(statement.conditions.get(j).Test(column, true))
							{

								matchBitSet.set(j);
							}
						}
					}
					if(matchBitSet.cardinality() == statement.conditions.size())
					{
						this.selectResult.rows.add(rowToAdd);
						//result.add(trimmedRow);
						statement.limit--;
					}
				}
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void printResult(){
		this.selectResult.printColumnNames();
		/*
		for(ArrayList<String> row : this.result){
			boolean emptyRow = true;
			for(String column : row){
				if(!column.isEmpty()){
					System.out.print("|" + column.toString() + "|");
					emptyRow = false;
				}
			}
			if(!emptyRow)
				System.out.println();
		}
		*/
		for(Row row : this.selectResult.rows)
		{
			row.printRowValues();
		}
	}

}
