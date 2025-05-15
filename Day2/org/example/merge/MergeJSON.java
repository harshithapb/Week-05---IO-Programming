package org.example.merge;

import org.json.JSONObject;

public class MergeJSON {
    public static void main(String[] args){
        JSONObject  address=new JSONObject();
        address.put("city","Mumbai");
        address.put("zip",400062);
        address.put("country","India");

        JSONObject user=new JSONObject();
        user.put("address",address);
        user.put("age",21);
        user.put("name","Sita");
        user.put("id",1);

        System.out.println("Nested JSON Object is \n "+user.toString());

    }

}
