package basic_problems;

import java.io.*;
public class WriteEmpData {
    public static void main(String args[]){
        String filePath="D://IdeaProjects//week5//CSV File Handling//src//basic_problems//empDataPgm2.csv";
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(filePath))){
            writer.write("ID, Name, Department, Salary\n");
            writer.write("1,Sita,IT,345.O0\n");
            writer.write("2,Gita,Manager,34445.O0\n");
            writer.write("3,Rita,Dev,3445.O0\n");
            writer.write("4,Mithu,HR,2445.O0\n");
            writer.write("5,Chintu,Sales,45666.O0\n");
            System.out.println("CSV file written successfully!");

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
