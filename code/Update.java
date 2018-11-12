import java.util.*;
import java.io.*;

public class Update
{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	public Statement statement;
	public Config config;
	public Update (Statement statement, Config config){
		this.statement = statement;
		this.config = config;
	}

	public void updateRow()
	{
	// System.out.println(this.statement.table);
  //   for(Column column: this.statement.updateColumns){
  //   	System.out.println("Name: "+column.name+" Value: "+column.value);
  //   }
  //   System.out.println(this.statement.whereString);
	// }


	
	// File fileToRead = new File(this.statement.table);
	// File tempWriteFile = new File(this.statement.table+".tmp");
	// //System.out.println(this.statement.table);
	// //System.out.println(this.statement.limit);
	// /*
	// for(Condition condition : this.statement.conditions){
	// 	System.out.println("column: "+condition.conditionArr[0]);
	// 	System.out.println("value: "+condition.conditionArr[1]);
	// }
	// */
	// try{
	// 	Scanner sctable = new Scanner(fileToRead, config.getCharset());
	// 	BufferedWriter writer = new BufferedWriter(new FileWriter(tempWriteFile));
	// 	if(sctable.hasNextLine()){
	// 		String line = sctable.nextLine();
	// 		String[] fileColumns = line.split("\\t");
	// 		writer.write(line + System.getProperty("line.separator"));
	// 		while(sctable.hasNextLine())
	// 		{
	// 				BitSet matchBitSet = new BitSet(statement.conditions.size());
	// 				matchBitSet.clear();
	// 				line = sctable.nextLine();
	// 				String[] fullRow = line.split("\\t");
	// 				for(int i=0; i<fullRow.length;i++){
	// 					Column column = new Column(fileColumns[i], fullRow[i], i);
	// 					int j;
	// 					for(j=0;j<statement.conditions.size();j++)
	// 					{
	// 						if(statement.conditions.get(j).Test(column, true))
	// 						{
	// 							matchBitSet.set(j);
	// 						}
	// 					}
	// 				}
	// 				if(matchBitSet.cardinality() == statement.conditions.size())
	// 				{
	// 					//System.out.println("Line to remove" +line);
	// 					//statement.limit--;
	// 					continue;
	// 				}
	// 				//System.out.println("Line to add: "+line);
	// 				//statement.limit--;
	// 				writer.write(line + System.getProperty("line.separator"));
	// 			}
	// 			tempWriteFile.renameTo(fileToRead);
	// 	}
	// 	writer.close();
	// }
	// catch(FileNotFoundException e){
	// 	e.printStackTrace();
	// }
	// catch(IOException e){
	//
	// }
	}
}
