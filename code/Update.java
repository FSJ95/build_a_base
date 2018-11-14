import java.util.*;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;
import java.nio.charset.StandardCharsets;

public class Update
{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	int counter = 0;
	public Statement statement;
	public Config config;
	public Update (Statement statement, Config config){
		this.statement = statement;
		this.config = config;
	}

	public void updateRow()
	{
		File fileToRead = new File(this.statement.table);
		File tempWriteFile = new File(this.statement.table+".tmp");

		try
		{
			Scanner sctable = new Scanner(fileToRead, config.getCharset());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempWriteFile), StandardCharsets.UTF_8));
			if(sctable.hasNextLine()){
				String line = sctable.nextLine();
				String[] fileColumns = line.split("\\t");
				writer.write(line + System.getProperty("line.separator"));
				while(sctable.hasNextLine())
				{
					//System.out.println("3");
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
					//System.out.println("2");
					if(matchBitSet.cardinality() == statement.conditions.size())
					{
						for(Column column: this.statement.updateColumns){

							for(int k=0; k<fullRow.length;k++){
								if(fileColumns[k].equals(column.name)){
									counter++;
									fullRow[k]=column.value;
								}
							}
				    	}
						statement.limit--;
						String s = String.join("\t", fullRow);
						writer.write(s + System.getProperty("line.separator"));
						continue;
					}
					// System.out.println("Line to add: "+line);
					statement.limit--;		
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
			else
			{
				sctable.close();
				writer.close();
			}
			if(counter!=0){
				System.out.println("You have made changes to the database. Rows affected: " + counter);
			} else{
				System.out.println("You have made no changes to the database.");
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){

		}
	}
}
