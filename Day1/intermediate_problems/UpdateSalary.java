package intermediate_problems;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateSalary {
    private static final String INPUT_FILE="D://IdeaProjects//week5//CSV File Handling//src//intermediate_problems//employees.csv";
    private static final String OUTPUT_FILE="D://IdeaProjects//week5//CSV File Handling//src//intermediate_problems//buffer.csv";
    private static final String OUTPUT_FILE1="D://IdeaProjects//week5//CSV File Handling//src//intermediate_problems//stream.csv";
    private static final String IT_DEPT="IT";
    private static final double SALARY_INCREASE=0.10;

    private static void writeFile(List<String> lines ,String op){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(op))){
            for(String line: lines){
                bw.write(line);
                bw.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public  static void updateBufferedReader(){
        List<String> updated=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(INPUT_FILE))){
            String header=br.readLine();
            if(header!=null){
                updated.add(header);
            }
            String line;
            while((line=br.readLine())!=null){
                String[] cols=line.split(",");
                if(cols.length>=4 && cols[2].trim().equalsIgnoreCase(IT_DEPT)){
                    try{
                        double currSal=Double.parseDouble(cols[3].trim());
                        double newSal=currSal*(1+SALARY_INCREASE);
                        cols[3]=String.format(".2f",newSal);
                        updated.add(line);
                    }catch(NumberFormatException e){
                        System.err.println("Warning: Invalid salary format in line - " + line);
                        updated.add(line);
                    }
                }
                else{
                    updated.add(line);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
            return;
        }
        writeFile(updated,OUTPUT_FILE);
        System.out.println("Salary updated (BufferedReader). Updated data saved to " + OUTPUT_FILE);
    }

    public static void updateSalaryStreams(){
        List<String> updated;
        try(BufferedReader br=new BufferedReader(new FileReader(INPUT_FILE))){
            Stream<String> lines=br.lines();
            updated=lines
                    .map(line -> {
                        String[] cols = line.split(",");
                        if (cols.length >= 4 && cols[2].trim().equalsIgnoreCase(IT_DEPT)) {
                            try {
                                double currSal = Double.parseDouble(cols[3].trim());
                                double newSal = currSal * (1 + SALARY_INCREASE);
                                cols[3] = String.format("%.2f", newSal);
                                return String.join(",", cols);
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Invalid salary format in line - " + line);
                                return line;

                            }
                        }
                        return line;
                    } ).collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        writeFile(updated ,OUTPUT_FILE1);
        System.out.println("Salary updated (Streams). Updated data saved to " + OUTPUT_FILE1);
    }





    public static void main(String [] args){
        updateBufferedReader();
        updateSalaryStreams();
    }
}
