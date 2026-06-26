import java.util.Scanner;
public class Task4_ascending_descending {
    public static void main(String[] arg){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Starting point: ");
        int start = sc.nextInt();
        System.out.print("Enter Ending point:");
        int end = sc.nextInt();
        if (start<end){
            for (int n = start; n<=end;n++){
                System.out.print(n);
            }
        }else if (start > end){
            for (int n = start;n>=end;n--){
                System.out.print(n);
            }
        }

    }
}
