import java.util.Scanner;
public class Task8 {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the currency: ");
        String currency = sc.nextLine();
        switch (currency){
            case "USD":
                System.out.println("United States Dollar");
                break;
            case "EUR":
                System.out.println("Euro");
                break;
            case "GBP":
                System.out.println("British Pound");
                break;
            default:
                System.out.println("Invalid Currency!");
        }
    }
}
