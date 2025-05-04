package practice.problems.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvToJsonConverter {
    public static void main(String [] args){
        File csvFile = new File("D://IdeaProjects//week5//JSON_//src//main//java//practice//problems//csv//data.csv");

        try{
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema= CsvSchema.emptySchema().withHeader();

            MappingIterator<Map<String, String>> iterator = csvMapper.readerFor(Map.class)
                    .with(csvSchema)
                    .readValues(csvFile);

            List<Map<String, String>> data = iterator.readAll();

            ObjectMapper jsonMapper = new ObjectMapper();
            String jsonOutput = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);

            System.out.println("JSON Output:");
            System.out.println(jsonOutput);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
