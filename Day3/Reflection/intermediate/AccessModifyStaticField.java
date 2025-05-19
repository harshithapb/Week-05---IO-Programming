package level.intermediate;

import java.lang.reflect.Field;

class Configuration {
    private static String API_KEY = "initial_api_key";

    public static String getApiKey() {
        return API_KEY;
    }

    @Override
    public String toString() {
        return "Configuration{API_KEY='" + API_KEY + "'}";
    }
}

public class AccessModifyStaticField {

    public static void main(String[] args) throws Exception {
        System.out.println("Initial API Key: " + Configuration.getApiKey());

        // Get the Class object for Configuration
        Class<?> configClass = Configuration.class;

        // Get the 'API_KEY' field (declared in Configuration)
        Field apiKeyField = configClass.getDeclaredField("API_KEY");

        // Make the private static field accessible
        apiKeyField.setAccessible(true);

        // Modify the value of the private static 'API_KEY' field
        apiKeyField.set(null, "new_secret_key"); // Pass null for static fields

        System.out.println("Modified API Key via Reflection: " + Configuration.getApiKey());

        // Let's verify the change directly
        System.out.println("API Key accessed directly: " + Configuration.getApiKey());
    }
}