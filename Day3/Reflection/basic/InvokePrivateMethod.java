package level.basic;

import java.lang.reflect.Method;
class Calculator {
    private int multiply(int a, int b) {
        return a * b;
    }
}

public class InvokePrivateMethod {
    public static void main(String[] args) throws Exception{
        Calculator calc= new Calculator();
        Class<?> calcClass =calc.getClass();

        Method mult= calcClass.getDeclaredMethod("multiply",int.class,int.class);

        mult.setAccessible(true);

        int result=(int)mult.invoke(calc,5,10);

        System.out.println("Result of private multiply method: "+ result);
    }
}
