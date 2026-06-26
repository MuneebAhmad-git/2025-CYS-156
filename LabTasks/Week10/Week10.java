interface Student {
    void name();
    void Roll();
}
interface Teacher{
    void name();
    void cnic();
}

class AB implements Student, Teacher{
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
public class Week10{
    public static void main(String[]arg){
        AB obj = new AB();
        obj.name();
        obj.Roll();
    }
}