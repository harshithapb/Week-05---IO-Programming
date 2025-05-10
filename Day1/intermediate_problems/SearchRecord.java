package intermediate_problems;

import java.util.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

public class SearchRecord {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String filePath="D://IdeaProjects//week5//CSV File Handling//src//intermediate_problems//employees.csv";

        System.out.println("Enter name of emp:");
        String name=sc.nextLine();
        String line;
        boolean found=false;

        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            String header=br.readLine();
            while((line=br.readLine())!=null){
                String [] cols =(line.split(","));
                if( cols.length==4 && cols[1].trim().equalsIgnoreCase(name.trim())){
                    System.out.println(name +" is found ");
                    System.out.println("Dept : "+ cols[2]+" Salary : "+cols[3]);
                    found=true;
                    break;
                }

            }
            if(!found){
                System.out.println(name +" not found ");
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Using lambda  going to search");
        System.out.println("Enter name of  other emp:");
        String searchName=sc.nextLine();

        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            Stream<String> lines=br.lines().skip(1);

            Optional<String[]> foundEmp=lines
                    .map(line1->line1.split(","))
                    .filter(cols->cols.length>=4 && cols[1].trim().equalsIgnoreCase(searchName))
                    .findFirst();
             if(foundEmp.isPresent()){
                 String[] empData=foundEmp.get();
                 System.out.println(searchName +" is found ");
                 System.out.println("Dept :"+empData[2].trim()+"  Salary :"+ empData[3]);
             }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            sc.close();
        }

    }

}
