import java.util.*;
public class Select{
	public ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	

	public void printResult(){

		this.result.forEach((rows) -> {
			rows.forEach((column) -> {
				System.out.print("|" + column.toString() + "|");
			});
			System.out.println();
		});
	}
	public void createExample(){
		int i, j;
		for(i = 0; i<10;i++){
			ArrayList<String> row = new ArrayList<String>();
			for(j=0;j<3;j++){
				row.add(j, Integer.toString(j*i));
			}
			this.result.add(i, row);
		}
		
	}
}