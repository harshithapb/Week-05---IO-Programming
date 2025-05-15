package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectToJsonArray {
    public static void main(String[] args){
        List<Person> peopleList= new ArrayList<>();
        peopleList.add(new Person("Ram",21,"ram@g,ail.com"));
        peopleList.add(new Person("Shyam",24,"shyam@gmail.com"));
        peopleList.add(new Person("Mohan",31,"mohan@gmail.com"));
        peopleList.add(new Person("Lata",21,"lata@gmail.com"));

        try{
           ObjectMapper obj=new ObjectMapper();

           obj.enable(SerializationFeature.INDENT_OUTPUT);
           String jsonArray=obj.writeValueAsString(peopleList);
           System.out.println(jsonArray);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
