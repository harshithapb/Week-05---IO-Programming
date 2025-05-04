package practice.problems.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import practice.problems.ObjectToArray.Person;
import java.io.File;
import java.io.IOException;

public class JsonToXmlConverter {
    public static void main(String[] args){
        try{
            String jsonData = "{\"name\":\"Alice\",\"age\":28,\"email\":\"alice@example.com\"}";

            ObjectMapper jsonObjectMapper=new ObjectMapper();
            Person person=jsonObjectMapper.readValue(jsonData, Person.class);

            XmlMapper xml=new XmlMapper();
            xml.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
            xml.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);

            // Convert the Java object to XML string
            String xmlOutput = xml.writeValueAsString(person);

            System.out.println("XML Output (from Java Object):");
            System.out.println(xmlOutput);

            System.out.println("\n--- Converting JSON String Directly ---");

            // Convert JSON string directly to XML string
            String xmlOutputDirect = xml.writeValueAsString(jsonObjectMapper.readTree(jsonData));
            System.out.println("XML Output (direct from JSON String):");
            System.out.println(xmlOutputDirect);


        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
