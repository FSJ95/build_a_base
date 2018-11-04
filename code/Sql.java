import java.util.*;
public class Sql{
	public static void main(String[] args){
		Scanner conin = new Scanner(System.in);
		System.out.println("Sql V0.1");
		boolean quit = false;
		int i;
		while(!quit){
			String statement = conin.nextLine();
			if(statement.equals("quit")){
				quit = true;
			}
			else{
				if(statement.startsWith("SELECT")
					&& statement.contains("FROM")
					&& statement.length() > (statement.indexOf("FROM") + 5)
					){
					
					String columnsString = statement.substring(6, statement.indexOf("FROM"));
					
					String[] columns = columnsString.split(",");

					String table = statement.substring((statement.indexOf("FROM") + 5), statement.length());

					Select select = new Select();
					select.createExample();
					select.printResult();
				}
			}
		}
	}
}
