public class Task8 {

    public static void processIntegers(int... numbers) {
        int SumOfParameters = 0;

        for (int num : numbers) {
            SumOfParameters += num;
        }

        System.out.println("Cumulative Sums for Each Parameter:");
        for (int num : numbers) {
            int cumulativeSum = 0;
            StringBuilder calculationSteps = new StringBuilder();
            calculationSteps.append(num).append(" = (");
            
            for (int i = 1; i <= num; i++) {
                cumulativeSum += i;
                calculationSteps.append(i);
                if (i < num) {
                    calculationSteps.append("+");
                }
            }
            calculationSteps.append(") [Total: ").append(cumulativeSum).append("]");
            System.out.println(calculationSteps.toString());
        }

        System.out.println("\nSum of all parameters: " + SumOfParameters);
    }

    public static void main(String[] args) {
        processIntegers(4, 5, 10);
    }
}
