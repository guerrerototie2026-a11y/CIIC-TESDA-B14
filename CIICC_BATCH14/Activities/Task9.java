import static java.lang.Math.*;

public class Task9 {

    public static int add(int a, int b) {
        return addExact(a, b);
    }

    public static int subtract(int a, int b) {
        return subtractExact(a, b);
    }

    public static int multiply(int a, int b) {
        return multiplyExact(a, b);
    }

    public static float divide(int a, int b) {
        return (float) floorDiv(a, b);
    }

    public static void main(String[] args) {
        int x = 20;
        int y = 6;

        System.out.println("Math Operations with Static Import:");
        System.out.println("Operand 1: " + x + ", Operand 2: " + y);
        
        System.out.println("Addition (addExact): " + add(x, y));
        System.out.println("Subtraction (subtractExact): " + subtract(x, y));
        System.out.println("Multiplication (multiplyExact): " + multiply(x, y));
        System.out.println("Division (floorDiv): " + divide(x, y));

        int a = -15;
        int b = 4;
        System.out.println("\nOperand 1: " + a + ", Operand 2: " + b);
        System.out.println("Addition: " + add(a, b));
        System.out.println("Subtraction: " + subtract(a, b));
        System.out.println("Multiplication: " + multiply(a, b));
        System.out.println("Division (Floor Div): " + divide(a, b));
    }
}
