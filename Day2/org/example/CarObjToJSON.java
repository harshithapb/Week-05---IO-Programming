package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

class Car{
    public String name;
    public String color;
    public float cost;

    Car(String name,String color,float cost){
        this.name=name;
        this.color=color;
        this.cost=cost;
    }
}
public class CarObjToJSON {
    public static void main(String[] args){
        try{
            ObjectMapper obj= new ObjectMapper();
            Car car=new Car("Honda","Black",40000.800f);
            String jsonString =obj.writeValueAsString(car);
            System.out.println(jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


