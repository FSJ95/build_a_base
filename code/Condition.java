import java.util.*;
public class Condition{
	public String operator;
	public String typeOfCondition;
	public String[] conditionArr;
	public Condition(String conStr)
	{
		if(conStr.contains("=")){
			this.operator = "equals";
			this.typeOfCondition = "comparison";
			this.conditionArr = conStr.split("=");
		}
	}
	public boolean Test(Column column, boolean trim)
	{
		String columncond = this.conditionArr[0];
		String valuecond = this.conditionArr[1];
		if(trim)
		{
			column.name = column.name.trim();
			column.value = column.value.trim();
			columncond = columncond.trim();
			valuecond = valuecond.trim();
		}
		switch(operator) {
			case "equals":
				if(column.value.isEmpty())
					return false;
				if(column.name.equals(columncond) && column.value.equals(valuecond))
				{
					return true;
				}
			default:
				return false;
		}
	}
}