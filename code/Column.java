public class Column
{
	public String name;
	public String value;
	public int index;
	public Column(String name, String value, int index)
	{
		this.name = name;
		this.value = value;
		this.index = index; 
	}
	public Column(String name, String value)
	{
		this.name = name;
		this.value = value;
	}
}