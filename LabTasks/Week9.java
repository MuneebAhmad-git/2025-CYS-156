import java.util.Scanner;

class StudentResult {
    private int subjects;
    private float[] marks;

    StudentResult(int subjects) {
        this.subjects = subjects;
        marks = new float[subjects];
    }

    void enterMarks() {
        Scanner inp = new Scanner(System.in);

        for (int i = 0; i < subjects; i++) {
            System.out.print("Enter marks of subject " + (i + 1) + ": ");
            marks[i] = inp.nextFloat();
        }
    }

    float calculatePercentage() {
        float sum = 0;

        for (float mark : marks) {
            sum += mark;
        }

        return (sum / (subjects * 100)) * 100;
    }

    float calculateCGPA() {
        float percentage = calculatePercentage();
        return percentage / 9.5f;
    }

    void displayResult() {
        float percentage = calculatePercentage();
        float cgpa = calculateCGPA();

        System.out.println("\n------ RESULT ------");
        System.out.println("Percentage = " + percentage + "%");
        System.out.println("CGPA = " + cgpa);
    }
}

public class Week9 {
    public static void main(String[] args) {

        Scanner inp = new Scanner(System.in);

        System.out.print("Enter number of subjects: ");
        int subjects = inp.nextInt();

        StudentResult student = new StudentResult(subjects);

        student.enterMarks();
        student.displayResult();
    }
}