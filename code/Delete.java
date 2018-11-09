import java.util.*;
import java.io.*;
public class Delete{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	public ArrayList<String> matchedColumns = new ArrayList<String>();
	public Statement statement;
	public Config config;
	public Delete (Statement statement, Config config){
		this.statement = statement;
		this.config = config;
	}

}
