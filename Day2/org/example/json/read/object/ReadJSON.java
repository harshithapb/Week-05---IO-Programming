package org.example.json.read.object;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ReadJSON {
    public static void main(String [] args){
        try{
           ObjectMapper obj=new ObjectMapper();
           User user=obj.readValue(new File("D://IdeaProjects//week5//JSON_//src//main//java//org//example//json//read//object//user1.json"), User.class);
           System.out.println("User name :"+user.getName());
            System.out.println("User name :"+user.getEmail());


        }catch(Exception  e){
            e.printStackTrace();
        }
    }
}
