public class Join{
	public String joinTable;
	public String mainTable;
	public String mainColumn;
	public String joinColumn;
	public Join(){

	}
	public Join(String joinTable, String mainColumn, String joinColumn){
		this.joinTable = joinTable;
		this.mainColumn = mainColumn;
		this.joinColumn = joinColumn;
	}
}