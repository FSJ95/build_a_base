import java.util.*;
public class Sql{
	public static void main(String[] args){
		Scanner conin = new Scanner(System.in);
		System.out.println("Sql V0.1");
		boolean quit = false;
		String[] code;
		//Configuration object, for example encoding, (To be added, delimiters on rows, columns, possibly reserved words list)
		Config defaultconf = new Config();
		while(!quit){
			//if there's a ; in the input then it will split them & iterate over them in order to run multiple statements in 1 line
			code = conin.nextLine().split("\\;");
			for(String line : code)
			{
				if(line.equals("QUIT") || line.equals("exit")){
					quit = true;
				}
				else if(line.startsWith("CHARSET="))
				{
					defaultconf.setCharset(line.substring(8));
					System.out.println("Charset changed to: " + defaultconf.getCharset());
				}
				else if(line.startsWith("SELECT"))
				{
					Statement statement = new Statement(line);
					statement.parseLimit();
					statement.parseWhere();
					statement.parseSelect();
					/*
					System.out.println(statement.limit);
					for(String condition : statement.conditionsStrings)
					{
						System.out.println(condition);
					}
					for(Condition condition : statement.conditions)
					{
						System.out.println(condition.operator);
						System.out.println(condition.typeOfCondition);
						System.out.println(condition.conditionArr[0]);
						System.out.println(condition.conditionArr[1]);
					}
					*/
					Select select = new Select();
					select.readTable(statement, defaultconf);
					select.printResult();	
				}
				else if(line.startsWith("COLUMNNAMES"))
				{
					Statement statement = new Statement(line);
					statement.parseColumnNames();
					Table table = new Table();
					table.readColumnNames(statement, defaultconf);
					table.printColumnNames();
				}
				else if(line.startsWith("HELP")){
					System.out.println("Commands: CHARSET=charset, QUIT, SELECT [columns[]] FROM [tablename] WHERE [conditions] LIMIT [int], COLUMNNAMES [TABLE]");
				}
			}
			
		}
	}
}
