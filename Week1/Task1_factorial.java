import java.util.Scanner;

public class Task1_factorial {

	public static void main(String[] arg){

		Scanner sc = new Scanner(System.in);
		int fact = 0;

		System.out.print("Enter Factorial Number: ");
		int num = sc.nextInt();

		if(num <0){
			System.out.println("Factotial of a negative number can not be taken");

		}else if(num == 0){

			System.out.println("Factorial of '0' can not be taken");

		}else {

			fact = 1;
		}
		for (int n = 1; n <= num; n++) {

			fact = fact* n;

		}
		System.out.print("The Factorial of the Number is: ");
		System.out.println(fact);

	}

}
