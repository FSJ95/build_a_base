import java.util.*;
public class Statement{

	public String fullStr;
	public String columnsString;
	public ArrayList<String> columns;
	public int indexOfTable;
	public String table;
	public String whereString;
	public ArrayList<String> conditionsStrings;
	public boolean checkcond;
	public ArrayList<Condition> conditions = new ArrayList<Condition>();
	public int limit;

	public Statement(String fullStr)
	{
		this.fullStr = fullStr;
		this.limit = 5;
		this.table = "";
		this.whereString = "";
		this.columnsString = "";
		checkcond = false;
	}
	public void parseColumnNames()
	{
		if(checkKeywordSyntax("COLUMNNAMES", 2)){
			this.table = this.fullStr.substring("COLUMNNAMES".length()+1);
		}
	}
	public void parseFrom()
	{
		if(checkKeywordSyntax("FROM", 2)){
			//Saves the index of TALBE name
			this.indexOfTable = this.fullStr.indexOf("FROM") + 5;

			this.table = this.fullStr.substring(indexOfTable);
			if((this.table).contains(" "))
				this.table = this.table.substring(0, this.table.indexOf(" "));	
		}
		
	}
	public void parseLimit()
	{
		//Checks for LIMIT keyword
		if(checkKeywordSyntax("LIMIT", 1))
			//First substring the full statement into whatever
			if(this.fullStr.substring(this.fullStr.indexOf("LIMIT")+6).matches("\\d{1,9}"))
			{
				this.limit = Integer.parseInt(this.fullStr.substring(this.fullStr.indexOf("LIMIT")+6));
			}

	}
	public void parseWhere()
	{
		if(checkKeywordSyntax("WHERE", 4)){
			this.whereString = this.fullStr.substring(this.fullStr.indexOf("WHERE")+6);
			if(this.whereString.contains("LIMIT")){
				this.whereString = this.whereString.substring(0, this.whereString.indexOf("LIMIT"));
			}
			this.conditionsStrings = new ArrayList<String>(Arrays.asList(whereString.split("AND ")));
			for(String conditionsStr : this.conditionsStrings)
			{
				Condition condition = new Condition(conditionsStr);
				this.conditions.add(condition);
				this.checkcond = true;
			}
		}
	}
	public void parseSelect()
	{
		if(checkKeywordSyntax("FROM", 2))
		{
			this.parseFrom();
			//Creates a substring of the statement of the columns, expecting a whitespace after SELLECT
			this.columnsString = this.fullStr.substring(6, this.fullStr.indexOf("FROM"));
			//Splits the columns with , and a whitespace
			this.columns = new ArrayList<String>(Arrays.asList(columnsString.split("\\, ")));
			this.parseWhere();
			this.parseLimit();
		}
	}
	public void parseDelete()
	{
		this.parseFrom();
		this.parseWhere();
		this.parseLimit();
	}
	public boolean checkKeywordSyntax(String keyword, int charsLonger)
	{
		if(this.fullStr.contains(keyword))
			if(this.fullStr.length() > (this.fullStr.indexOf(keyword)+keyword.length()+charsLonger))
				return true;
		return false;
	}
	public void printWhereCond()
	{
		for(String s : this.conditionsStrings)
			System.out.println(s);
	}
}