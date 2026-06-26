import java.util.Scanner;
public class divisions {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String firstname = sc.nextLine();
        System.out.print("Enter your last name: ");
        String lastname = sc.nextLine();
        String greetings = "Welcome!";
        System.out.println(greetings+firstname+lastname);
        System.out.println(greetings.concat(firstname.concat(lastname)));
    }
}
