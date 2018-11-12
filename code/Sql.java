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
				if(line.equalsIgnoreCase("QUIT") || line.equalsIgnoreCase("EXIT")){
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
					statement.parseSelect();
					Select select = new Select(statement, defaultconf);
					select.readTable();
					select.printResult();
				}
				else if(line.startsWith("DELETE"))
				{
					Statement statement = new Statement(line);
					statement.parseDelete();
					Delete delete = new Delete(statement, defaultconf);
					delete.deleteRow();
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
					System.out.println("Commands: ");
					System.out.println("CHARSET=charset");
					System.out.println("QUIT");
					System.out.println("SELECT [columns[]] FROM [tablename] WHERE [conditions] LIMIT [int]");
					System.out.println("INSERT INTO [tablename] ([Column], [Column]...) VALUES ([Value], [Value]...)");
					System.out.println("DELETE FROM [tablename] WHERE [conditions]");
					System.out.println("COLUMNNAMES [TABLE]");

				}
				else if(line.startsWith("INSERT INTO"))
				{
					Statement statement = new Statement(line);
					statement.parseInsert();
					Table table = new Table();
					table.readColumnNames(statement, defaultconf);
					Insert insert = new Insert(statement);
					insert.insertIntoTable(table);
				}
				else if(line.startsWith("UPDATE"))
				{
					Statement statement = new Statement(line);
					statement.parseUpdate();
					Update update = new Update(statement, defaultconf);
				  update.updateRow();
				}
			}

		}
	}
}
