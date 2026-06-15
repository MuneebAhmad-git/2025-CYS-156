import java.util.Scanner;
public class Task5_number_inverse {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter any number: ");
        long number = sc.nextLong();
        long inverse = 0;
        while (number != 0){
            long digit = number%10;
            inverse = inverse*10 + digit;
            number /= 10;
        }
        System.out.print("The inverse of the number is: ");
        System.out.print(inverse);

    }
}
