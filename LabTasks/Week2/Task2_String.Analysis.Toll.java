import java.util.Scanner;
public class divisions {
    public static void main(String[] arg){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Full name: ");
        String name = sc.nextLine();
        System.out.println("Total length of the name is: "+name.length());
        name = name.toUpperCase();
        System.out.print("Index of ' ' is: ");
        System.out.println(name.indexOf(" "));

    }
}
