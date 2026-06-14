interface Student {
    void name();
    void Roll();
}
abstract class Teacher{
    abstract void name();
    abstract void Roll();
    abstract void cnic();
}

class AB extends Teacher implements Student{
    @Override
    public void name(){
        System.out.println("Muneeb");
    }
    @Override
    public void Roll(){
        System.out.println("19"); 
    }
    @Override
    public void cnic(){
        System.out.println("35201-000000-0");
    }

}
public class Task3{
    public static void main(String[]arg){
        AB obj = new AB();
        obj.name();
        obj.Roll();
    }
}