package practice.problems.KeyValues;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
public class KeyValue {
    public static void printKeysAndValues(JsonNode node, String prefix){
        if(node.isObject()){
            Iterator<Map.Entry<String, JsonNode>> fileds = node.fields();
            while(fileds.hasNext()){
                Map.Entry<String,JsonNode> field=fileds.next();
                String key=field.getKey();
                JsonNode value=field.getValue();
                System.out.println(prefix + key + ": " + value.asText()); // Print as text initially
                printKeysAndValues(value, prefix + "  "); // Recursively process nested objects
            }
        }
        else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                JsonNode arrayElement = node.get(i);
                System.out.println(prefix + "[" + i + "]:");
                printKeysAndValues(arrayElement, prefix + "  "); // Recursively process array elements
            }
        }
        else if (node.isValueNode()) {
            // For simple values (text, number, boolean) we've already printed them
            // in the object iteration using asText(). We can skip here to avoid repetition
        }
    }


    public static void main(String [] args){
        try{
            ObjectMapper obj =new ObjectMapper();
            File jsonFile= new File("D://IdeaProjects//week5//JSON_//src//main//java//org//practice//problems//data.json");
            JsonNode rootNode =obj.readTree(jsonFile);

            printKeysAndValues(rootNode," ");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
