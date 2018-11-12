import java.io.*;

public class Insert
{
  Statement statement;
  Config config;
  public Insert(Statement statement){
    this.statement = statement;
    //this.config = config;
  }
  public void insertIntoTable(Table table){
    /*try{
      output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
      */
      String lineToWrite = "";
      for(int i = 0; i<table.columnNames.size();i++){
        String columnValue = "\\N";
        if(i!=0){
          lineToWrite += "\t";
        }
        //System.out.println("Table column: "+table.columns.get(i));
        for(Column column : statement.rowToInsert.columns){
          //System.out.println("Insert Column: "+column.name);
          if(table.columnNames.get(i).equals(column.name)){

            columnValue = column.value;
          }
        }
        lineToWrite += columnValue;
      }
        try {
          BufferedWriter out = new BufferedWriter(new FileWriter(statement.table, true));
            out.append("\n"+lineToWrite);
            out.close();
        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {

        }
  }
  /*
public static void main(String[] args)
  {
    Writer output;
    String file = "title.akas.tv_kort.tsv";
    try{
      output = new BufferedWriter(new FileWriter(file, true));
      output.append("\n !");
      output.close();
    }
    catch(FileNotFoundException e)
    {
      //e.printStackTrace(e);
    }
    catch(IOException e){

    }
  }
  */
}
