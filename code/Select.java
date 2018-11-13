import java.util.*;
import java.io.*;
public class Select{
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	public Statement statement;
	public Config config;
	public Table selectResult = new Table();
	public Table selectFullResult = new Table();
	public Select (Statement statement, Config config)
	{
		this.statement = statement;
		this.config = config;
	}
	public void leftJoinTables()
	{
		for(Join join: this.statement.joinBlocks){
			//Check if the table has the column
			if(this.selectFullResult.hasColumn(join.mainColumn))
			{
				//opening a file with the table provided in the join
				File fileToRead = new File(join.joinTable);
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
							for(String column : this.statement.columns)
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
							//Add to selectFullResult
							this.selectFullResult.columnNames.add(fileColumns[i]);
						}
						while(sctable.hasNextLine()){
							String fullRowStr = sctable.nextLine(); 
							String[] fullRow = fullRowStr.split("\\t");
							String mainValue = "\\N", joinValue = "\\N";
							int rowPositionToAdd = 0;
							for(i=0;i<fullRow.length;i++)
							{
								if(join.joinColumn.equals(fileColumns[i]))
									joinValue = fullRow[i];
							}
							
							for(Row row : selectFullResult.rows)
							{
								for(Column column : row.columns)
								{
									if(column.name.equals(join.mainColumn))
									{
										mainValue = column.value;
									}
								}
								if(mainValue.equals(joinValue)){
									rowPositionToAdd = selectFullResult.rows.indexOf(row);
									for(i=0;i<fullRow.length;i++)
									{
										this.selectFullResult.rows.get(rowPositionToAdd).columns.add(new Column(fileColumns[i], fullRow[i], i));
										if(columnsToInsertIndex.contains(i))
										{
											this.selectResult.rows.get(rowPositionToAdd).columns.add(new Column(fileColumns[i], fullRow[i], i));
										}
									}
								}
							}
						}
					}
				}
				catch(FileNotFoundException e)
				{
					e.printStackTrace();
				}	
			}
		}
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
					//Add to selectFullResult
					this.selectFullResult.columnNames.add(fileColumns[i]);
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
					Row fullRowToAdd = new Row();
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
						fullRowToAdd.columns.add(column);
					}
					if(matchBitSet.cardinality() == statement.conditions.size())
					{
						this.selectResult.rows.add(rowToAdd);
						this.selectFullResult.rows.add(fullRowToAdd);
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
	public void printAllResults(){
		this.selectFullResult.printColumnNames();
		for (Row row : this.selectFullResult.rows ) {
			row.printRowValues();
		}
	}
	public void printResult(){
		this.selectResult.printColumnNames();
		for(Row row : this.selectResult.rows)
		{
			row.printRowValues();
		}
	}

}
