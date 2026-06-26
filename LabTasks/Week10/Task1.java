interface Camera{
    void take_picture();
}
interface GPS{
    void location();
}
abstract class Message{
    void text(){
        System.out.println("Sending Message...");
    }
}
interface microphone{
    void talk();
}
class Smartphone extends Message implements GPS, Camera, microphone{
    @Override
    public void take_picture(){
        System.out.println("Picture Taken!");
    }
    @Override
    public void location(){
        System.out.println("You are on Earth!");
    } 
    @Override
    public void talk(){
        System.out.println("Talking!");
    }
}

public class Task1 {
    public static void main(String[] arg){
        Smartphone obj = new Smartphone();
        obj.location();
        obj.take_picture();
        obj.talk();
        obj.text();
    }   
}
