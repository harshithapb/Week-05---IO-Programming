package practice.problems.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterByAge {
    public static void main(String[] args){
        try{
            ObjectMapper obj =new ObjectMapper();
            File jsonFile=new File("D://IdeaProjects//week5//JSON_//src//main//java//org//example//filter//users.json");
            JsonNode rootNode= obj.readTree(jsonFile);

            if(rootNode.isArray()) {
                List<JsonNode> filteredUsers = new ArrayList<>();

                for (JsonNode userNode : rootNode) {
                    JsonNode ageNode = userNode.get("age");
                    if (ageNode != null && ageNode.isInt() && ageNode.asInt() > 25) {
                        filteredUsers.add(userNode);
                    }
                }


                System.out.println("Users with age >25 :");
                ObjectMapper prettyPrinter = new ObjectMapper();
                prettyPrinter.enable(SerializationFeature.INDENT_OUTPUT);
                System.out.println(prettyPrinter.writeValueAsString(filteredUsers));
            }
            else{
                System.out.println("The JSON file does not contain a root array.");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
