import java.util.Scanner;

class Bank{
    private int balance;

    public void getBalance(){
        System.out.println("Your Balance is: "+balance);
    }
    public void setBalance(int deposit){
        System.out.println("Balance updated successfully!");
        balance = balance + deposit;
    }
    public void withdraw(int amount){
        System.out.println("Money successfully withdrawn!");
        balance = balance - amount;
    }
    public int Getbalance(){
        return balance;
    }
}

public class Banking_System {
    public static void main(String[] arg){

        int enter;
        int amount;

        Scanner sc = new Scanner(System.in);

        Bank acc = new Bank();
        System.out.println("Welcome to your account");
        while (1>0) {
            int choice;
            System.out.print("\nEnter '1' to see Balance\nEnter '2' to Deposit\nEnter '3' to Withdraw\n>>>> ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    acc.getBalance();
                    break;
                case 2:
                    System.out.print("Enter the Amount to Deposit: ");
                    amount = sc.nextInt();
                    acc.setBalance(amount);
                    break;
                case 3:
                    System.out.print("Enter the Amount to Withdraw: ");
                    amount = sc.nextInt();
                    if (amount <= acc.Getbalance()){
                        acc.withdraw(amount);
                    }else {
                        System.out.println("Balance is Insufficient!!");
                    }    
                    break;
                default:
                    System.out.println("Invalid input!");
            }
            System.out.print("\nEnter '1' to Continue! >>>> ");
            enter = sc.nextInt();
            if (enter==1){
                continue;
            }else{
                break;
            }
        }
    }
}
