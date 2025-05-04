//package practice.problems.emailvalidate;
//
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.networknt.schema.JsonSchema;
//import com.networknt.schema.JsonSchemaFactory;
//import com.networknt.schema.SpecVersion;
//import com.networknt.schema.ValidationMessage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Set;
//
//public class EmailValidation {
//    public static void validateJson(String schemaPath, String dataPath){
//        try{
//            ObjectMapper obj= new ObjectMapper();
//            //loading json schema
//            File schemaFile= new File(schemaPath);
//            JsonNode schemaNode=obj.readTree(schemaFile);
//
//
//            //loading json data
//            File dataFile=new File(dataPath);
//            JsonNode jsonData= obj.readTree(dataFile);
//
//            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.DRAFTV7);
//            JsonSchema schema = factory.getSchema(schemaNode);
//
//            // Validate JSON Data against the Schema
//            Set<ValidationMessage> validationErrors = schema.validate(jsonData);
//
//            System.out.println("Validating " + dataPath + " against " + schemaPath + ":");
//            if (validationErrors.isEmpty()) {
//                System.out.println("  JSON is valid!");
//            } else {
//                System.out.println("  JSON is invalid. Errors:");
//                validationErrors.forEach(error -> System.out.println("    " + error.getMessage()));
//            }
//            System.out.println("---");
//
//
//        }catch (IOException e) {
//            System.err.println("Error reading JSON files: " + e.getMessage());
//        }
//    }
//    public static void main(String[] args){
//        String schemaFile = "D:\\IdeaProjects\\week5\\JSON_\\src\\main\\java\\practice\\problems\\emailvalidate\\email_schema.json";
//        String validDataFile = "D:\\IdeaProjects\\week5\\JSON_\\src\\main\\java\\practice\\problems\\emailvalidate\\email_schema.json";
//        String invalidMissingFile = "D:\\IdeaProjects\\week5\\JSON_\\src\\main\\java\\practice\\problems\\emailvalidate\\email_schema.json";
//        String invalidTypeFile = "D:\\IdeaProjects\\week5\\JSON_\\src\\main\\java\\practice\\problems\\emailvalidate\\email_schema.json";
//        String invalidFormatFile = "D:\\IdeaProjects\\week5\\JSON_\\src\\main\\java\\practice\\problems\\emailvalidate\\email_schema.json";
//
//        validateJson(schemaFile, validDataFile);
//        validateJson(schemaFile, invalidMissingFile);
//        validateJson(schemaFile, invalidTypeFile);
//        validateJson(schemaFile, invalidFormatFile);
//
//    }
//}
