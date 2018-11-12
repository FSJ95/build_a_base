import java.util.*;
public class Statement{

	public String fullStr;
	public String columnsString;
	public ArrayList<String> columns = new ArrayList<String>();
	public int indexOfTable;
	public String table;
	public String whereString;
	public ArrayList<String> conditionsStrings;
	public ArrayList<Column> updateColumns;
	public boolean checkcond;
	public ArrayList<Condition> conditions = new ArrayList<Condition>();
	public int limit;
	public ArrayList<String> values;
	public String valuesString;
	public Row rowToInsert;

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
	public void parseInsert(){
		if(checkKeywordSyntax("INTO", 4)
		&& this.fullStr.contains("VALUES (")
		&& this.checkKeywordSyntax("(", 2)
		&& this.fullStr.contains(")")){
			this.indexOfTable = 12;
			this.table = this.fullStr.substring(this.indexOfTable, (this.fullStr.indexOf("("))-1);
			this.columnsString = this.fullStr.substring(this.fullStr.indexOf("(")+1, this.fullStr.indexOf(")"));
			this.valuesString = this.fullStr.substring(this.fullStr.indexOf("VALUES (")+8, this.fullStr.lastIndexOf(")"));
			this.columns = new ArrayList<String>(Arrays.asList(columnsString.split("\\, ")));
			this.values = new ArrayList<String>(Arrays.asList(valuesString.split("\\, ")));
			if(this.columns.size() == this.values.size()){
				this.rowToInsert = new Row();
				for(int i = 0;i<this.columns.size();i++)
				{
					Column column = new Column(this.columns.get(i), this.values.get(i));
					this.rowToInsert.columns.add(column);
				}
			}
		}
	}
	public void printInsert()
	{
		System.out.println("Table: "+this.table);
		System.out.println("columnsString: "+this.columnsString);
		System.out.println("valuesString: "+this.valuesString);
	}
	public void parseWhere()
	{
		if(checkKeywordSyntax("WHERE", 3)){
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
		if(checkKeywordSyntax("DELETE FROM", 2)){
			this.parseFrom();
			this.parseWhere();
			this.parseLimit();
		}
	}
	public void parseUpdate()
	{
		if(checkKeywordSyntax("UPDATE", 2)){
		  if(checkKeywordSyntax("SET", 4))
			{
				this.table = this.fullStr.substring(7, this.fullStr.indexOf("SET")-1);
				if(this.fullStr.contains("WHERE")){
					this.columnsString = this.fullStr.substring(this.fullStr.indexOf("SET")+4, this.fullStr.indexOf("WHERE"));
				}
				else{
					this.columnsString = this.fullStr.substring(this.fullStr.indexOf("SET")+4);
				}
				this.columns = new ArrayList<String>(Arrays.asList(this.columnsString.split("\\, ")));

				for(String columnStr: this.columns){
					String setArr[] = columnStr.split("=");
					Column columnToAdd = new Column(setArr[0], setArr[1]);
					this.updateColumns.add(columnToAdd);
				}
				this.parseWhere();
				this.parseLimit();
			}
		}
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
