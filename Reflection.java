import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {

    public static void inspectClass(Object obj) {
        Class<?> clazz = obj.getClass();

        // Print class name
        System.out.println("Class Name: " + clazz.getName());

        // Print fields
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("Fields:");
        for (Field field : fields) {
            System.out.println("\t" + field.getName() + " : " + field.getType());
        }

        // Print methods
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println("\t" + method.getName() + " : " + method.getReturnType());
        }
    }

    public static void main(String[] args) {
        // Create an instance of the Airport class
        Airport airport = Airport.getInstance("Sample Airport");

        // Inspect the Airport class using reflection
        inspectClass(airport);

        // Create an instance of the Flight class
        Flight flight = new Flight("Sample Company", "FL123", new Boeing737(), "2024-03-20 08:00", "New York", null, 5);

        // Inspect the Flight class using reflection
        inspectClass(flight);
    }
}