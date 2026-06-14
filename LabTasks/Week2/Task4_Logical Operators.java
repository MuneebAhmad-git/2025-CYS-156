import java.util.Scanner;
public class divisions {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Do you have an ID (1/0): ");
        int a = sc.nextInt();
        boolean hasID = false;
        boolean isOver18 = false;
        if (a == 1) {
            hasID = true;
        }

        System.out.print("Are you above 18 (1/0): ");
        a = sc.nextInt();
        if (a == 1) {

            isOver18 = true;
        }
        if (hasID && isOver18){
            System.out.println("Access Granted");
        }else if(hasID || isOver18){
            System.out.println("Special Guest");
        }

    }
}
