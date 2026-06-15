import java.util.Objects;
import java.util.Scanner;

class Person{
    String name;
    int x;
    int y;

    public Person(String name){

        this.name = name;
        this.x = 0;
        this.y = 0;
    }

    public int walk_Y(String Direction){
        if (Objects.equals(Direction, "^")){
            return y + 1;
        } else if (Objects.equals(Direction, "v")) {
            return y - 1;
        }
        return y;
    }
    public int walk_X(String Direction){
        if (Direction.equals(">")){
            return x + 1;
        } else if (Direction.equals("<")) {
            return x - 1;
        }
        return x;
    }

    public void update_Info(int new_x,int new_y){
        this.x = new_x;
        this.y = new_y;
    }

    public void get_Info(){
        System.out.println(this.name+" is at: ("+x+","+y+")");
    }

}

public class Main {
    public static void main(String[] arg){

        String direction;
        int hurdle_x = 2;
        int hurdle_y = 4;
        int hasanNexty = 0;
        int hasanNextx = 0;
        Scanner sc = new Scanner(System.in);
        Person ali = new Person("Ali");
        Person hasan = new Person("Hasan");
        System.out.println("Starting: Ali's Location is=(0:0)");
        System.out.println("Hassan's Location is =(0:0) Hasan has Random Movement, Ali follows order");

        for (int i = 0; i < 1; i--){

            hasanNextx = hasan.x;
            hasanNexty = hasan.y;

            System.out.print("Ali: Where should I go now: ");
            direction = sc.next();

            int aliNexty = ali.walk_Y(direction);
            int aliNextx = ali.walk_X(direction);

            double random_double = Math.random();
            random_double = random_double*4;
            int random_int = (int)random_double;

            if (aliNextx == hurdle_x && aliNexty == hurdle_y) {
                System.out.println("Ali encounters hurdle at: "+"("+hurdle_x+","+hurdle_y+")");
                ali.get_Info();
            }else{
                ali.update_Info(aliNextx,aliNexty);
                ali.get_Info();
            }

            switch(random_int){
                case 0:
                    hasanNexty = hasan.walk_Y("^");
                    break;
                case 1:
                    hasanNexty = hasan.walk_Y("v");
                    break;
                case 2:
                    hasanNextx = hasan.walk_X(">");
                    break;
                case 3:
                    hasanNextx = hasan.walk_X("<");
                    break;
            }
            if (hasanNextx == hurdle_x && hasanNexty == hurdle_y) {
                System.out.println("Hasan encounters hurdle at: "+"("+hurdle_x+","+hurdle_y+")");
            }else{
                hasan.update_Info(hasanNextx,hasanNexty);
                hasan.get_Info();
            }

        }
    }
}
