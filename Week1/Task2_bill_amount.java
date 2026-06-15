import java.util.Scanner;

public class Task2_bill_amount {
    public static void main(String[] arg) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Units Consumed: ");
        int units = sc.nextInt();
        int total_units = 0;
        int n = 0;
        
        if (units <= 100){

            n = units*5;
            total_units = total_units+n;

        }

        if (units > 100 && units <= 200) {
            n = 100;
            n = n*5;
            total_units = total_units+n;
            n = units-100;
            n = n*7;
            total_units = total_units+n;
        }
        if (units > 200){

            n = 100;
            n = n*5;
            total_units = total_units+n;
            n = 100;
            n = n*7;
            total_units = total_units+n;
            n = units-200;
            n = n*15;
            total_units = total_units+n;
        }
        System.out.print("The total bill is: ");
        System.out.println(total_units);
   }
}
