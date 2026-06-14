import java.util.Scanner;

class JTime{
    int hours;
    int minutes;
    int seconds;
        
    public int task_a(JTime J2){
        int total_seconds_1 = (this.seconds) + (this.hours*3600) + (this.minutes*60);
        int total_seconds_2 = (J2.seconds) + (J2.hours*3600) + (J2.minutes*60);
        return Math.abs(total_seconds_1 - total_seconds_2);
    }

    public JTime task_b(int interval){
        JTime time_stamp_3 = new JTime(interval);
        return time_stamp_3;
    }
    JTime(){

    }

    JTime(int hours,int minutes,int seconds){
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

    }
    JTime(JTime J1){
        this.hours = J1.hours;
        this.minutes = J1.minutes;
        this.seconds = J1.seconds;
    }
    public JTime(int total_seconds){
        if (total_seconds>86400){
            total_seconds = total_seconds - 86400;
        }
        int remaining_seconds;
        this.hours = total_seconds / 3600;
        remaining_seconds = total_seconds % 3600;
        this.minutes = remaining_seconds / 60;
        remaining_seconds = remaining_seconds % 60;
        this.seconds = remaining_seconds;
        System.out.println("Time is: "+hours+":"+minutes+":"+seconds);
    }
}

public class Jtime_time_stamps {
    public static void main(String[] arg){

        int hours;
        int minutes;
        int seconds;
        int time_elapsed;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the details of first time stamp:- \nHours: ");
        hours = sc.nextInt();
        System.out.print("Minutes: ");
        minutes = sc.nextInt();
        System.out.print("Seconds: ");
        seconds = sc.nextInt();
        JTime time_stamp_1 = new JTime(hours,minutes,seconds);
        System.out.print("Enter the details of second time stamp:- \nHours: ");
        hours = sc.nextInt();
        System.out.print("Minutes: ");
        minutes = sc.nextInt();
        System.out.print("Seconds: ");
        seconds = sc.nextInt();
        JTime time_stamp_2 = new JTime(hours,minutes,seconds);
        time_elapsed = time_stamp_1.task_a(time_stamp_2);
        System.out.println("The difference between these time stamps is: "+time_elapsed+" Seconds");
        JTime time_stamp_3 = time_stamp_1.task_b(time_elapsed);
        
    }
}
