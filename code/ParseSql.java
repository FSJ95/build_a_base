import java.util.*;
public class ParseSql{
	public static void select(Statement statement)
	{
		/*
		if(statement.fullStr.contains("FROM")
		&& statement.fullStr.length() > (statement.fullStr.indexOf("FROM") + 5))
		{
			statement.columnsString = statement.fullStr.substring(6, statement.fullStr.indexOf("FROM"));
			statement.columns = new ArrayList<String>(Arrays.asList(columnsString.split(",")));
			statement.indexOfTable = statement.fullStr.indexOf("FROM") + 5;

			if(statement.fullStr.contains("WHERE")){
				statement.table = statement.fullStr.substring(statement.indexOfTable, statement.fullStr.indexOf("WHERE"));
			}
			else
			{
				statement.table = statement.fullStr.substring(statement.indexOfTable, statement.fullStr.length());
			}
		}*/
	}
}