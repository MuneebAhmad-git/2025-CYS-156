import java.util.Scanner;
public class divisions {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the current hour (0 to 23): ");
        int hour = sc.nextInt();
        if (hour>=5 && hour<=11){
            System.out.println("Good Morning!");
        } else if (hour>=12 && hour<=17 ) {
            System.out.println("Good Afternoon!");
        } else if (hour>=18 && hour<=23) {
            System.out.println("Good Evening!");
        }else{
            System.out.println("Invalid hour!");
        }
    }
}
