import java.util.Scanner;

public class Time {
    private int hours;
    private int minutes;
    private int seconds;

    public Time() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void whatTime(int no_of_seconds_till_noon) {

        int totalSeconds = 12 * 3600 + no_of_seconds_till_noon;

        totalSeconds = ((totalSeconds % 86400) + 86400) % 86400;

        this.hours   = totalSeconds / 3600;
        this.minutes = (totalSeconds % 3600) / 60;
        this.seconds = totalSeconds % 60;
    }

    public void display() {
        System.out.println("Current Time is : "+hours+":"+minutes+":"+seconds);
    }

    public static void main(String[] args) {

        Time t = new Time();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Time in Seconds : ");
        int user =  sc.nextInt();

        t.whatTime(user);
        t.display();
    }
}