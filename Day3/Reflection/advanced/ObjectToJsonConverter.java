//package level.advanced;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ObjectToJsonConverter {
//
//    public static String toJson(Object obj) throws IllegalAccessException {
//        if (obj == null) {
//            return "null";
//        }
//
//        Class<?> clazz = obj.getClass();
//        StringBuilder jsonBuilder = new StringBuilder("{");
//        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
//                .filter(field -> !Modifier.isStatic(field.getModifiers())) // Ignore static fields
//                .collect(Collectors.toList());
//
//        for (int i = 0; i < fields.size(); i++) {
//            Field field = fields.get(i);
//            field.setAccessible(true); // Allow access to private fields
//            String fieldName = field.getName();
//            Object fieldValue = field.get(obj);
//
//            jsonBuilder.append(escapeString(fieldName)).append(":");
//            jsonBuilder.append(toJsonValue(fieldValue));
//
//            if (i < fields.size() - 1) {
//                jsonBuilder.append(",");
//            }
//        }
//
//        jsonBuilder.append("}");
//        return jsonBuilder.toString();
//    }
//
//    private static String toJsonValue(Object value) throws IllegalAccessException {
//        if (value == null) {
//            return "null";
//        }
//
//        if (value instanceof String) {
//            return escapeString((String) value);
//        } else if (value instanceof Number || value instanceof Boolean) {
//            return value.toString();
//        } else if (value.getClass().isArray()) {
//            return toJsonArray(value);
//        } else if (value instanceof List) {
//            return toJsonList((List<?>) value);
//        } else {
//            // For other objects, recursively convert them to JSON
//            return toJson(value);
//        }
//    }
//
//    private static String toJsonArray(Object array) throws IllegalAccessException {
//        StringBuilder sb = new StringBuilder("[");
//        int length = java.lang.reflect.Array.getLength(array);
//        for (int i = 0; i < length; i++) {
//            Object element = java.lang.reflect.Array.get(array, i);
//            sb.append(toJsonValue(element));
//            if (i < length - 1) {
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        return sb.toString();
//    }
//
//    private static String toJsonList(List<?> list) throws IllegalAccessException {
//        StringBuilder sb = new StringBuilder("[");
//        for (int i = 0; i < list.size(); i++) {
//            sb.append(toJsonValue(list.get(i)));
//            if (i < list.size() - 1) {
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        return sb.toString();
//    }
//
//    private static String escapeString(String str) {
//        return "\"" + str.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
//    }
//
//    public static void main(String[] args) throws IllegalAccessException {
//        Person person = new Person("Alice", 30, new String[]{"Reading", "Hiking"},
//                List.of("New York", "London"), new Address("123 Main St", "Some City"));
//        String jsonOutput = toJson(person);
//        System.out.println(jsonOutput);
//
//        Configuration config = new Configuration("super_secret_key", 5, true, null);
//        String configJson = toJson(config);
//        System.out.println(configJson);
//    }
//}
//
//class Person {
//    private String name;
//    private int age;
//    private String[] hobbies;
//    private List<String> citiesVisited;
//    private Address address;
//
//    public Person(String name, int age, String[] hobbies, List<String> citiesVisited, Address address) {
//        this.name = name;
//        this.age = age;
//        this.hobbies = hobbies;
//        this.citiesVisited = citiesVisited;
//        this.address = address;
//    }
//
//    // Getters (not strictly needed for reflection in this example, but good practice)
//    public String getName() {
//        return name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public String[] getHobbies() {
//        return hobbies;
//    }
//
//    public List<String> getCitiesVisited() {
//        return citiesVisited;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//}
//
//class Address {
//    private String street;
//    private String city;
//
//    public Address(String street, String city) {
//        this.street = street;
//        this.city = city;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public String getCity() {
//        return city;
//    }
//}
//
//class Configuration {
//    private String apiKey;
//    private int maxRetries;
//    private boolean isEnabled;
//    private Object optionalSetting;
//
//    public Configuration(String apiKey, int maxRetries, boolean isEnabled, Object optionalSetting) {
//        this.apiKey = apiKey;
//        this.maxRetries = maxRetries;
//        this.isEnabled = isEnabled;
//        this.optionalSetting = optionalSetting;
//    }
//
//    public String getApiKey() {
//        return apiKey;
//    }
//
//    public int getMaxRetries() {
//        return maxRetries;
//    }
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public Object getOptionalSetting() {
//        return optionalSetting;
//    }
//}