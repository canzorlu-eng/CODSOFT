package CODSOFT;

import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter your number of subjects: ");
        int numOfSubjects= scanner.nextInt();
        int[] grades=new int[numOfSubjects];
        scanner.nextLine();
        for (int i = 0; i < grades.length; i++) {
            System.out.print("Enter your "+(i+1)+". subject's grade: ");
            grades[i]=scanner.nextInt();scanner.nextLine();
        }
        int sum=0;
        for (int grade: grades)
            sum+=grade;
        System.out.println("Your total marks: "+sum+"\nAverage:"+(double)sum/numOfSubjects+"\nYour grade: "+getGrade(sum/numOfSubjects));
        scanner.close();
    }
    public static String getGrade(int average){
        String grade;
        switch (average / 10) {
            case 10: // 100
                grade="A+";
                break;
            case 9:  // 90-99
                grade = "A";
                break;
            case 8:  // 80-89
                if (average >= 85) {
                    grade = "A-";
                } else {
                    grade = "B+";
                }
                break;
            case 7:  // 70-79
                if (average >= 75) {
                    grade = "B";
                } else {
                    grade = "B-";
                }
                break;
            case 6:  // 60-69
                if (average >= 65) {
                    grade = "C+";
                } else {
                    grade = "C";
                }
                break;
            case 5:  // 50-59
                if (average >= 55) {
                    grade = "C-";
                } else {
                    grade = "D+";
                }
                break;
            case 4:  // 40-49
                grade = "D";
                break;
            default: // 0-39
                grade = "F";
                break;
        }
        return grade;
    }
}
