package basic_problems;
import java.io.*;
public class CountRows {
    public static void main(String[] args){
        String filePath="D://IdeaProjects//week5//CSV File Handling//src//basic_problems//empDataPgm2.csv";
        int row=0;
        String csvSplitBy=",";
        String line=" ";
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            while((line=br.readLine())!=null){
                row++;
            }
            System.out.println(" No of rows= "+(row-1));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
