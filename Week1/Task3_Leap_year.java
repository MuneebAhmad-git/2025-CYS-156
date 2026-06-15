import java.util.Scanner;

public class Task3_Leap_year {
	public static void main(String[] arg) {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter any year: ");

		int year = sc.nextInt();
		if (year%4==0){
			System.out.println("Its a Leap year");

		} else {
			System.out.println("Its not a Leap year");

		}
	}
}
