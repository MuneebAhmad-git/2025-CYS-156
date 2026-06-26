import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

class Pacman {
    int x,y;
    static int score = 0;
    String[][] end = {{"    "},{"     "}};

    public int getScore() {
        return score;
    }

    public void setInfo(int x, int y){
        this.y = y;
        this.x = x;
    }
    public String[][] PlayerMovement(String[][] string_array,int num) {

        if (num == 1) {
            string_array[x][y] = " ";
            if (Objects.equals(string_array[x][y - 1], "#")){
                System.out.println("Hit an obstacle!");
                string_array[x][y] = "P";
            } else if (Objects.equals(string_array[x][y - 1], "E")){
                System.out.println("\t\t\t\tEaten by the Enemy(E)");
                GameOver();
                return end;
            }else {
                string_array[x][y = y - 1] = "P";
                if (string_array[x][y-1] != " "){
                    score = score + 1;
                }
            }
        } else if (num == 2) {
            string_array[x][y] = " ";
            if (Objects.equals(string_array[x][y + 1], "#")){
                System.out.println("Hit an obstacle!");
                string_array[x][y] = "P";
            }else if (Objects.equals(string_array[x][y + 1], "E")){
                System.out.println("\t\t\t\tEaten by the Enemy(E)");
                GameOver();
                return end;
            }else {
                string_array[x][y = y + 1] = "P";
                if (string_array[x][y+1] != " "){
                    score = score + 1;
                }
            }

        } else if (num == 3) {
            string_array[x][y] = " ";
            if (Objects.equals(string_array[x - 1][y], "#")){
                System.out.println("Hit an obstacle!");
                string_array[x][y] = "P";
                
            }else if (Objects.equals(string_array[x - 1][y], "E")){
                System.out.println("\t\t\t\tEaten by the Enemy(E)");
                GameOver();
                return end;
            }else {
                string_array[x = x - 1][y] = "P";
                if (string_array[x-1][y] != " "){
                    score = score + 1;
                }
            }
        } else if (num == 4) {
            string_array[x][y] = " ";
            if (Objects.equals(string_array[x + 1][y], "#")){
                System.out.println("Hit an obstacle!");
                string_array[x][y] = "P";
            }else if (Objects.equals(string_array[x + 1][y], "E")){
                System.out.println("\t\t\t\tEaten by the Enemy(E)");
                GameOver();
                return end;
            }else {
                string_array[x = x + 1][y] = "P";
                if (string_array[x+1][y] != " "){
                    score = score + 1;
                }
            }
        }
        return string_array;
    }
    public void GameOver(){
        String[] gameOver = {
            "\t\t\t\t\t #####   ##   #   # #####        ###   #   # ##### #####",
            "\t\t\t\t\t#       #  #  ## ## #           #   #  #   # #     #   #",
            "\t\t\t\t\t#  ###  ####  # # # #####       #   #  #   # ##### #####",
            "\t\t\t\t\t#   #   #  #  #   # #           #   #   # #  #     #  #",
            "\t\t\t\t\t #####   #  #  #   # #####       ###     #   ##### #   #"
        };

        for (int i = 0; i < gameOver.length; i++) {
            for (int j = 0; j < gameOver[i].length(); j++) {
                System.out.print(gameOver[i].charAt(j));
            }
            System.out.println();
        }
    }
}

class Ghost{
    int x,y;

    Ghost(int x, int y){
        this.y = y;
        this.x = x;
    }
    public String[][] EnemyMovement(String[][] string_array,int num) {

        if (num == 0) {
            if (string_array[x][y-1] == " "){
                    string_array[x][y] = " ";
                }else{
                     string_array[x][y] = "'";
                }
            if (Objects.equals(string_array[x][y - 1], "#")){
                string_array[x][y] = "E";
            }else {
                string_array[x][y = y - 1] = "E";
            }
        } else if (num == 1) {
                if (string_array[x][y-1] == " "){
                    string_array[x][y] = " ";
                }else{
                     string_array[x][y] = "'";
                 }
            if (Objects.equals(string_array[x][y + 1], "#")){
                string_array[x][y] = "E";
                
            }else {
                string_array[x][y = y + 1] = "E";
                 
            }

        } else if (num == 2) {
             if (string_array[x][y-1] == " "){
                    string_array[x][y] = " ";
                }else{
                     string_array[x][y] = "'";
                 }
            if (Objects.equals(string_array[x - 1][y], "#")){
                string_array[x][y] = "E";
                
            }else {
                string_array[x = x - 1][y] = "E";
            }
        } else if (num == 3) {
             if (string_array[x][y-1] == " "){
                    string_array[x][y] = " ";
                }else{
                     string_array[x][y] = "'";
                 }
            if (Objects.equals(string_array[x + 1][y], "#")){
                string_array[x][y] = "E";
            }else {
                string_array[x = x + 1][y] = "E";
                
            }
        }
        return string_array;
    }

}

class Gameboard {
    int x = 6;
    int y = 10;
    int ex_1 = 15;
    int ey_1 = 40;
    int ex_2 = 15;
    int ey_2 = 38;

    public void getGameboard(String[][] string_array) {

        for (int b = 0; b <= 8; b++) {
            for (int a = 0; a <= 29; a++) {
                System.out.print(string_array[b][a]);
            }
            System.out.println();
        }
    }
    public void displayScore(int score){
        System.out.println("Score = " + score);
    }
    public void StartGame(){
        String[] pacman = {
            "\t\t\t\t\t#####   ##    #####  #   #   ##   #   #",
            "\t\t\t\t\t#   #  #  #   #      ## ##  #  #  ##  #",
            "\t\t\t\t\t#####  ####   #      # # #  ####  # # #",
            "\t\t\t\t\t#      #  #   #      #   #  #  #  #  ##",
            "\t\t\t\t\t#      #  #   #####  #   #  #  #  #   #"
        };

        for (int i = 0; i < pacman.length; i++) {
            for (int j = 0; j < pacman[i].length(); j++) {
                System.out.print(pacman[i].charAt(j));
            }
            System.out.println();
        }

        System.out.println("\n\n");
    }
}
public class Main {
    public static void main(String[] arg) throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        int x = 6;
        int y = 10;
        int ex_1 = 5;
        int ey_1 = 20;
        int ex_2 = 5;
        int ey_2 = 28;
        String[][] string_array = new String[9][30];

        for (int a = 0; a <= 29; a++) {
            string_array[0][a] = "#";
        }
        for (int a = 0; a <= 29; a++) {
            string_array[8][a] = "#";
        }
        for (int b = 0; b <= 8; b++) {
            string_array[b][29] = "#";
        }
        for (int b = 0; b <= 8; b++) {
            string_array[b][0] = "\t\t\t\t\t\t#";
        }
        for (int b = 1; b <= 7; b++) {
            for (int a = 1; a <= 28; a++) {
                string_array[b][a] = "'";
            }
        }
        for (int b = 3; b <= 6; b++) {
            for (int a = 14; a <= 16; a++) {
                string_array[b][a] = "#";
            }
        }
        string_array[x][y] = "P";
        string_array[ex_1][ey_1] = "E";
        string_array[ex_2][ey_2] = "E";

        Gameboard gameboard = new Gameboard();
        gameboard.StartGame();
        
        String message = "\t\t\t\t\t>>> PRESS ENTER TO START <<<";
        for (int i = 0; i < 6; i++) {
            System.out.print("\r" + message);
            Thread.sleep(500);
            System.out.print("\r" + "                                 ");
            Thread.sleep(500);
        }System.out.println();
        
        gameboard.getGameboard(string_array);
        Pacman player = new Pacman();
        player.setInfo(x, y);
        Random rand = new Random();
        Ghost enemy_1 = new Ghost(ex_1,ey_1);
        Ghost enemy_2 = new Ghost(ex_2,ey_2);

        while (true) {
            int movement_1 = rand.nextInt(3);
            int movement_2 = rand.nextInt(3);
            System.out.print("\n");
            System.out.print("Where do you want to go!(w/a/s/d) : ");
            String input = sc.next();

            switch (input) {
                case "a":
                    string_array = player.PlayerMovement(string_array, 1);
                    string_array = enemy_1.EnemyMovement(string_array,movement_1);
                    string_array = enemy_2.EnemyMovement(string_array,movement_2);
                    break;
                case "d":
                    string_array = player.PlayerMovement(string_array, 2);
                    string_array = enemy_1.EnemyMovement(string_array,movement_1);
                    string_array = enemy_2.EnemyMovement(string_array,movement_2);

                    break;
                case "w":
                    string_array = player.PlayerMovement(string_array, 3);
                    string_array = enemy_1.EnemyMovement(string_array,movement_1);
                    string_array = enemy_2.EnemyMovement(string_array,movement_2);
                    break;
                case "s":
                    string_array = player.PlayerMovement(string_array, 4);
                    string_array = enemy_1.EnemyMovement(string_array,movement_1);
                    string_array = enemy_2.EnemyMovement(string_array,movement_2);
                    break;
                case "e":
                    return;
                default:
                    System.out.println("Invalid answer!");
            }
            int score = player.getScore();
            gameboard.getGameboard(string_array);
            gameboard.displayScore(score);
            int m = 0;
            for(int i = 0; i <= 8;i++){
                for (int j = 0; j <= 29; j++){
                    if (string_array[i][j] == " "){
                        m++;
                    }
                }
            }
            if (m == 156){
                String[] youWin = {
                    "\t\t\t\t\t#   #   ###   #   #       #   #  #####  #   #",
                    "\t\t\t\t\t#   #  #   #  #   #       #   #    #    ##  #",
                    "\t\t\t\t\t # #   #   #  #   #       # # #    #    # # #",
                    "\t\t\t\t\t  #    #   #  #   #       # # #    #    #  ##",
                    "\t\t\t\t\t  #     ###    ###         # #   #####  #   #"
                };

                for (int i = 0; i < youWin.length; i++) {
                    for (int j = 0; j < youWin[i].length(); j++) {
                        System.out.print(youWin[i].charAt(j));
                    }
                    System.out.println();
                }
                break;
            }
        }
    }
}
