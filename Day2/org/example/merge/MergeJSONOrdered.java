package org.example.merge;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
public class MergeJSONOrdered {

    public static void main(String[] args){
        ObjectMapper obj =new ObjectMapper();
        obj.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String ,Object> userDetails=new LinkedHashMap<>();
        userDetails.put("name", "Sita");
        userDetails.put("id", 1);
        userDetails.put("age", 21);

        Map<String,Object> address =new LinkedHashMap<>();
        address.put("city","Mumbai");
        address.put("zip",400062);
        address.put("country","India");
        userDetails.put("address", address);

        System.out.println(" ordered ver of JSON Obj :"+ userDetails.toString());


    }

}
