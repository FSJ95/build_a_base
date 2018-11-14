import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Delete
{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	public Statement statement;
	public Config config;
	public Delete (Statement statement, Config config){
		this.statement = statement;
		this.config = config;
	}

	public void deleteRow()
	{
		File fileToRead = new File(this.statement.table);
		File tempWriteFile = new File(this.statement.table+".tmp");
		try{
			Scanner sctable = new Scanner(fileToRead, config.getCharset());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempWriteFile), StandardCharsets.UTF_8));
			if(sctable.hasNextLine()){
				String line = sctable.nextLine();
				String[] fileColumns = line.split("\\t");
				writer.write(line + System.getProperty("line.separator"));
				while(sctable.hasNextLine())
				{
						BitSet matchBitSet = new BitSet(statement.conditions.size());
						matchBitSet.clear();
						line = sctable.nextLine();
						String[] fullRow = line.split("\\t");
						for(int i=0; i<fullRow.length;i++){
							Column column = new Column(fileColumns[i], fullRow[i], i);
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
							continue;
						}
						writer.write(line + System.getProperty("line.separator"));
					}
					sctable.close();
					writer.close();
					//creates a backup before saving changes (overwriting the original table)
					fileToRead.renameTo(new File(this.statement.table+".bkup"));
					
					//Deletes old table in order to save changes
					new File(this.statement.table).delete();

					if(!tempWriteFile.renameTo(new File(this.statement.table)))
					{
						System.out.println("Overwriting failed, please restore table from file: "+this.statement.table+".bkup");
					}
					else
					{
						//Deletes bkup file if rename was successful
						new File(this.statement.table+".bkup").delete();
					}
			}
			else{
				sctable.close();
				writer.close();
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){

		}
	}
}
