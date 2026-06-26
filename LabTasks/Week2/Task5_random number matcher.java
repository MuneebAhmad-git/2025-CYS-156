import java.util.Scanner;
public class divisions {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Guess any Number between 1-10: ");
        int userGuess = sc.nextInt();
        int realGuess = (int)(Math.random()*11);
        if (userGuess == realGuess){
            System.out.println("True");
        }else {
            System.out.println("False");
        }
        System.out.println(realGuess);
    }
}
