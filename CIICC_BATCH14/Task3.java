
public class Task3 {
    public static void main(String[] args) {
        String a = "Wow";
        String b = a;
        String c = "Hello";
        String d = "Wow!";

        boolean bl = a == b;
        boolean b2 = d.equals(b + "!");
        boolean b3 = !c.equals(a);

        if (bl && b2 && b3) {
            System.out.println("Success!");
        }
    }
}
