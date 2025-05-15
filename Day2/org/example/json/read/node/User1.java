package org.example.json.read.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
public class User1 {
    public static void main(String[] args) {
        try {
            String fileName = "D://IdeaProjects//week5//JSON_//src//main//java//org//example//json//read//node//user1.json";


            ObjectMapper obj = new ObjectMapper();
            obj.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, String> userData = new HashMap<>();
            userData.put("name", "Ram Laal");
            userData.put("email", "ram1@gmail.com");

            obj.writeValue(new File(fileName), userData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
