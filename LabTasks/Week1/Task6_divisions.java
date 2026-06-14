import java.util.Scanner;
public class Task6_divisions {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter any number: ");
        float num = sc.nextFloat();
        int n = 0;
        while (num >= 1){
            num = num/2;
            n++;
        }
        System.out.print("Total no. of divisions are: ");
        System.out.println(n);

    }
}
