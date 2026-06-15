import java.util.Scanner;
public class Task9_somparisons {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        int x = 10;
        int y = 20;
        if (x==y){
            System.out.println("X is equal to Y");
        }else {
            System.out.println("X is not equal to Y");
        }
        x++;
        System.out.println("Updated value of X is: "+x);
        if (x>10){
            System.out.println("X is greater than 10");
        }
    }
}
