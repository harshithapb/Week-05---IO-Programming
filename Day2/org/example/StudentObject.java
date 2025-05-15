package org.example;
import org.json.JSONObject;
import org.json.JSONArray;


public class StudentObject {
    public static void main(String[] args){
        JSONObject stud=new JSONObject();
        stud.put("name","Ram");
        stud.put("age",21);

        JSONArray subjects=new JSONArray();
        subjects.put("Maths" );
        subjects.put("Chem");
        subjects.put("Phy");

        stud.put("age",21);
        stud.put("subjects",subjects);

        System.out.println(stud.toString());

    }
}
