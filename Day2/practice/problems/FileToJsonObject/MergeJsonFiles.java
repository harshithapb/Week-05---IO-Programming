package practice.problems.FileToJsonObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class MergeJsonFiles {
    public static void main(String [] args){

        String file1Path="D://IdeaProjects//week5//JSON_//src//main//java//practice//problems//FileToJsonObject//file1.json";
        String file2Path="D://IdeaProjects//week5//JSON_//src//main//java//practice//problems//FileToJsonObject//file2.json";

        try{
            ObjectMapper obj =new ObjectMapper();

            File file1=new File(file1Path);
            JsonNode node1=obj.readTree(file1);

            File file2=new File(file2Path);
            JsonNode node2=obj.readTree(file2);

            ObjectNode mergedNode= obj.createObjectNode();

            if(node1.isObject()){
                Iterator<Map.Entry<String, JsonNode>> fields1=node1.fields();
                while(fields1.hasNext()){
                    Map.Entry<String ,JsonNode> field =fields1.next();
                    mergedNode.set(field.getKey(),field.getValue());
                }
            }

            if(node2.isObject()){
                Iterator<Map.Entry<String,JsonNode>> fields2=node2.fields();
                while(fields2.hasNext()){
                    Map.Entry<String, JsonNode> field =fields2.next();
                    mergedNode.set(field.getKey(),field.getValue());
                }
            }

            System.out.println("Merged JSON Obj");
            System.out.println(obj.writerWithDefaultPrettyPrinter().writeValueAsString(mergedNode));
        }catch(IOException  e){
            e.printStackTrace();
        }
    }
}
