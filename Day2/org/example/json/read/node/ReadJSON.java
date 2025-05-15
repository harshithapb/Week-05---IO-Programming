package org.example.json.read.node;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class ReadJSON {
   public static void main(String[] args){
       String fileName="D://IdeaProjects//week5//JSON_//src//main//java//org//example//json//read//node//user1.json";
       try{
           ObjectMapper obj = new ObjectMapper();
           JsonNode rootNode=obj.readTree( new File(fileName));

           JsonNode nameNode= rootNode.get("name");
           String name=null;
           if(nameNode!=null){
               name=nameNode.asText();
               System.out.println("User name :"+ name);
           }

           JsonNode mailNode= rootNode.get("email");
           String mail=null;
           if(mailNode!=null){
               mail=mailNode.asText();
               System.out.println("Mail :"+mail);
           }
       }catch(Exception e){
           e.printStackTrace();
       }
   }



}

