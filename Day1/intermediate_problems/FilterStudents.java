package intermediate_problems;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterStudents {
    public static void print(List<String> a){
        for(int i=0;i<a.size();i++){
            System.out.println(a.get(i));
        }
        System.out.println();
    }
    public static void main(String[] args){
        String filePath="D://IdeaProjects//week5//CSV File Handling//src//basic_problems//stud_data.csv";
        List<String> quailified=new ArrayList<>();
        String splitBy=",";
        String line;
        System.out.println("Without using lambda exp");
        try(BufferedReader br= new BufferedReader(new FileReader(filePath))){
            while ((line=br.readLine())!=null){
                String[] cols=line.split(splitBy);
                if(cols.length==4) {
                    try {
                        int marks = Integer.parseInt(cols[3].trim());
                        if (marks > 80) {
                            quailified.add(line);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid marks format " + e.getMessage());
                    }
                }
                else {
                    System.err.println("Warning: Incorrect number of columns in line - " + line);
                }
            }
            print(quailified);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Using lambda exp");
        int qualifyingMarks=80;
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            Stream<String> lines=br.lines().skip(0); // Skip the header row

            List<String> qualifiedStudents=lines
                    .filter(linee -> {
                        String[] cols = linee.split(",");
                        if (cols.length == 4) {
                            try {
                                int m = Integer.parseInt(cols[3].trim());
                                return m > qualifyingMarks;
                            } catch (NumberFormatException e) {
                                return false; // Ignore lines with invalid marks
                            }
                        }
                        return false; // Ignore lines with incorrect number of columns
                    })
                    .collect(Collectors.toList());
            System.out.println("Students  scoring above "+qualifyingMarks);
            qualifiedStudents.forEach(System.out::println);
        }catch(IOException e){
            e.printStackTrace();
        }



    }

}
