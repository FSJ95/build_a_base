import java.util.*;
public class Row{
	public ArrayList<Column> columns = new ArrayList<Column>();
	public int index;

	public void printRow()
	{
		for(Column column : this.columns){
			System.out.print("Column: "+column.name+" Value: "+column.value+"\n");
		}
	}
}
