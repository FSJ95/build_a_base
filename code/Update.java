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
    for(String column: this.statement.columns)
      System.out.println(column);
    /*
    System.out.println(this.statement.updateColumns);
    */
    System.out.println(this.statement.whereString);
	}




}
