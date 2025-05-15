package org.example.json.read.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;


public class User {
    private int id;
    private String name;
    private int age;
    private String email;

    public User(String name,String email){
       this.name=name;
       this.email=email;
    }

    public User(){
        // req for jackson
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public static void main(String[] args){
        try{
            ObjectMapper obj= new ObjectMapper();
            obj.enable(SerializationFeature.INDENT_OUTPUT);

            String fileName="D://IdeaProjects//week5//JSON_//src//main//java//org//example//json//read//object//user1.json";

            User user3=new User("Ram Chand","ramchand@gmail.com");
            obj.writeValue(new File(fileName),user3);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
