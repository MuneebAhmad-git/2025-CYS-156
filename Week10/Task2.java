abstract class Student {
    abstract void name();
    abstract void Roll();

    void display(){
        System.out.print("Student Information");
    }
}

abstract class Teacher{
    abstract void name();
    abstract void cnic();
}

class AB extends Student{
    @Override
    public void name(){
        System.out.println("Muneeb");
    }
    @Override
    public void Roll(){
        System.out.println("19"); 
    }

}
public class Task2{
    public static void main(String[]arg){
        AB obj = new AB();
        obj.name();
        obj.Roll();
    }
}