import java.util.Scanner;

public class saving_calculator {    
    static void main(String[] arg){       
        Scanner sc = new Scanner(System. in );

        float monthly_salary;        
        float portion_saved;        
        int total_cost;        
        double portion_down_payment;        
        double current_saving = 0;        
        int months_required = 0;       
        double r = 0.04; 
        System.out.print("Enter your Monthly Salary: ");        
        monthly_salary = sc.nextFloat();        
        System.out.print("Enter the total cost of your dream asset: ");        
        total_cost = sc.nextInt();        
        System.out.print("Enter portion of Salary to save (e.g. 0.10 for 10%): ");        
        portion_saved = sc.nextFloat();        
        portion_down_payment = total_cost*0.25;

        while (current_saving <= portion_down_payment){            
            current_saving = current_saving + monthly_salary*portion_saved;

            current_saving = current_saving + current_saving*r/12;            
            months_required += 1;        
        }        
        System.out.println("Total number of months required are: "+months_required);  
        System.out.println("Years to Save : "+months_required / 12.0);
    }
}