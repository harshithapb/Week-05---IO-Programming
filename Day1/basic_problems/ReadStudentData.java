package basic_problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//"D://IdeaProjects//week5//CSV File Handling//src//basic_problems//stud_data.csv"
public class ReadStudentData {
    public static void main(String[] args){
        String csvFile="D://IdeaProjects//week5//CSV File Handling//src//basic_problems//stud_data.csv";
        String line=" ";
        String csvSplitBy=",";

        try(BufferedReader br= new BufferedReader(new FileReader(csvFile))){
                System.out.println("--- Student Details ---");
                System.out.println("-----------------------");
            int row=1;
            while((line= br.readLine())!=null){
                String[] stud=line.split(csvSplitBy);
                if(stud.length==4){

                    String id=stud[0].trim();
                    String name=stud[1].trim();
                    String age=stud[2].trim();
                    String marks=stud[3].trim();
                    System.out.println(" row : "+row +"=>"+" id: "+ id +" name :"+name +" age :"+age + " MArks "+marks );
                    row++;
                }
                else{
                    System.err.println("Skipping invalid row: " + line);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
            System.err.println("Error reading csv file "+csvFile);

        }
    }
}
