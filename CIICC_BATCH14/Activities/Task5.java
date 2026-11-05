import java.util.Scanner;
public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter the second number: ");
        double num2 = scanner.nextDouble();

        System.out.print("Enter the third number: ");
        double num3 = scanner.nextDouble();

        if (num1 == num2 && num2 == num3) {
            System.out.println("All numbers are equal");
        } else if (num1 >= num2 && num1 >= num3) {
            System.out.println("The largest number is: " + num1);
        } else if (num2 >= num1 && num2 >= num3) {
            System.out.println("The largest number is: " + num2);
        } else {
            System.out.println("The largest number is: " + num3);
        }
        scanner.close();
    }
}
