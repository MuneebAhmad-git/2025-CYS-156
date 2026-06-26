import java.util.Scanner;

public class divisions {

    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter radius: ");

        float radius = sc.nextFloat();

        double a  = Math.PI*Math.pow(radius,2);

        int area = (int) a;

        System.out.print("The area of the Circle is: ");

        System.out.println(area);

    }

}
