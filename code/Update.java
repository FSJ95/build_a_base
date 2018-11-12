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
	System.out.println(this.statement.table);
    for(Column column: this.statement.updateColumns){
    	System.out.println("Name: "+column.name+" Value: "+column.value);
    }
    System.out.println(this.statement.whereString);
	}
}
