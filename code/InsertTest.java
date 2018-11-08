import java.io.*;

public class InsertTest
{

public static void main(String[] args)
  {
    Writer output;
    String file = "MyTextFile.txt";
    try{
      output = new BufferedWriter(new FileWriter(file, true));
      output.append("\nNew Line!");
      output.close();
    }
    catch(FileNotFoundException e)
    {
      //e.printStackTrace(e);
    }
    catch(IOException e){

    }
  }
}
