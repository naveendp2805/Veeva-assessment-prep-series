/*
 * Problem: Student Management System
 *
 * Design an OOP-based student management system that stores details
 * of multiple students and performs different operations branch-wise.
 *
 * Student Details:
 * - Name
 * - Roll Number
 * - Branch
 * - Section
 * - Marks
 * - CGPA
 * - Grade
 *
 * Grade Calculation:
 * S -> 90 and above
 * A -> 80 - 89
 * B -> 70 - 79
 * C -> 60 - 69
 * D -> 50 - 59
 * F -> Below 50
 *
 * Requirements:
 * 1. Display students belonging to a given branch.
 * 2. Display students branch-wise sorted by grade in descending
 *    academic order (S > A > B > C > D > F).
 * 3. Display students branch-wise sorted by marks in ascending order.
 * 4. Display students with S grade in a given branch.
 * 5. Find the branch having the highest number of S grade students.
 *
 * Approach:
 * - Student class encapsulates student information and calculates
 *   the grade based on marks.
 * - StudentManager performs operations on the collection of students.
 * - HashMap is used to group students branch-wise and count S grades.
 * - Comparator is used for sorting students by grade and marks.
 * - ArrayList is used to store the student objects.
 *
 * OOP Concepts:
 * - Encapsulation
 * - Classes and Objects
 * - Constructors
 * - Abstraction through methods
 * - Has-a relationship
 * - Method references
 * - Lambda expressions
 *
 * Time Complexity:
 * - Branch search: O(n)
 * - Branch-wise grouping: O(n)
 * - S grade counting: O(n)
 * - Sorting: O(n log n) per branch
 *
 * Space Complexity: O(n)
 *
 * n = number of students
 */

import java.util.*;

class Student
{
    private String name;
    private String rollno;
    private String branch;
    private char section;
    private int marks;
    private float cgpa;
    private char grade;

    public Student(String name, String rollno, String branch, char section, int marks, float cgpa)
    {
        this.name = name;
        this.rollno = rollno;
        this.branch = branch;
        this.section = section;
        this.marks = marks;
        this.cgpa = cgpa;
        this.grade = calculateGrade(marks);
    }

    private char calculateGrade(int marks2) {
        if(marks2 >= 90) return 'S';
        else if(marks2 >= 80) return 'A';
        else if(marks2 >= 70) return 'B';
        else if(marks2 >= 60) return 'C';
        else if(marks2 >= 50) return 'D';

        return 'F';
    }

    public String getName() {
        return name;
    }

    public String getRollno() {
        return rollno;
    }

    public String getBranch() {
        return branch;
    }

    public char getSection() {
        return section;
    }

    public int getMarks() {
        return marks;
    }

    public float getCgpa() {
        return cgpa;
    }

    public char getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name +
               ", Roll No: " + rollno +
               ", Branch: " + branch +
               ", Section: " + section +
               ", Marks: " + marks +
               ", CGPA: " + cgpa +
               ", Grade: " + grade;
    }
}

class StudentManager
{
    List<Student> students;

    public StudentManager(List<Student> students) {
        this.students = students;
    }

    public void displayStudentsByBranch(String branch) {
        int c=0;

        for(Student student : students)
        {
            if(student.getBranch().equalsIgnoreCase(branch))
            {
                System.out.print(student.getName() + " ");
                c++;
            }
        }

        System.out.println("\nNumber of students: " + c);
    }

    public void displayBranchWiseByGradeDesc() {
        HashMap<String, List<Student>> branchMap = groupByBranch();

        System.out.println("\nStudents Branch-wise by Grade Desc Order:");

        for(Map.Entry<String, List<Student>> entry : branchMap.entrySet())
        {
            List<Student> list = entry.getValue();

            list.sort(Comparator.comparingInt((student -> getGradeRank(student.getGrade()))));

            System.out.println("Branch: " + entry.getKey());

            for(Student student : list)
                System.out.println(student);
        }
    }

    public void displayBranchWiseByMarksAsc() {
        HashMap<String, List<Student>> branchMap = groupByBranch();

        System.out.println("\nStudents Branch-wise by Marks Asc Order:");

        for(Map.Entry<String, List<Student>> entry : branchMap.entrySet())
        {
            List<Student> list = entry.getValue();

            list.sort(Comparator.comparingInt(Student::getMarks));

            System.out.println("Branch: " + entry.getKey());

            for(Student student : list)
                System.out.println(student);
        }
    }

    public void displaySGradeStudentsInAGivenBranch(String branch) {
        int c=0;

        for(Student student : students)
        {
            if(student.getBranch().equalsIgnoreCase(branch) && student.getGrade() == 'S')
            {
                System.out.print(student.getName() + " ");
                c++;
            }
        }

        System.out.println("Total S Grade students in " + branch + " branch: " + c);
    }

    public void displayBranchWithHighestSGradeStudents() {
        HashMap<String, Integer> sGradeCount = new HashMap<>();

        for(Student student : students)
        {
            if(student.getGrade() == 'S')
            {
                String branch = student.getBranch();
                sGradeCount.put(branch, sGradeCount.getOrDefault(branch, 0) + 1);
            }
        }

        String highestBranch = null;
        int max = 0;

        for(Map.Entry<String, Integer> entry : sGradeCount.entrySet())
        {
            if(entry.getValue() > max)
            {
                max = entry.getValue();
                highestBranch = entry.getKey();
            }
        }

        if (highestBranch == null)
            System.out.println("No student has an S grade.");
        else
            System.out.println("Branch with highest S grade students is " + highestBranch + " with count " + max);
    }

    private HashMap<String, List<Student>> groupByBranch() {
        HashMap<String, List<Student>> branchMap = new HashMap<>();

        for(Student student : students)
        {
            branchMap.computeIfAbsent(student.getBranch(), key -> new ArrayList<>())
                     .add(student);
        }

        return branchMap;
    }

    private int getGradeRank(char grade) {

        switch(grade)
        {
            case 'S': return 1;
            case 'A': return 2;
            case 'B': return 3;
            case 'C': return 4;
            case 'D': return 5;
            default: return 7;
        }
    }

}

public class StudentManagement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details of Student " + (i + 1));

            System.out.print("Name: ");
            String name = sc.next();

            System.out.print("Roll No: ");
            String rollno = sc.next();

            System.out.print("Branch: ");
            String branch = sc.next();

            System.out.print("Section: ");
            char section = sc.next().charAt(0);

            System.out.print("Marks: ");
            int marks = sc.nextInt();

            System.out.print("CGPA: ");
            float cgpa = sc.nextFloat();

            students.add(new Student(name, rollno, branch, section, marks, cgpa));
        }

        StudentManager manager = new StudentManager(students);

        while(true)
        {
            System.out.println("\n===== MENU =====");
            System.out.println("1: Display students by branch");
            System.out.println("2: Display branch-wise students by grade");
            System.out.println("3: Display branch-wise students by marks");
            System.out.println("4: Display S grade students of a branch");
            System.out.println("5: Branch with highest S grades");
            System.out.println("6: exit");

            System.out.print("\nEnter choice: ");
            int choice = sc.nextInt();

            switch(choice)
            {
                case 1: System.out.print("Enter branch: ");
                        String branch1 = sc.next();
                        manager.displayStudentsByBranch(branch1);
                        break;

                case 2: manager.displayBranchWiseByGradeDesc();
                        break;

                case 3: manager.displayBranchWiseByMarksAsc();
                        break;

                case 4: System.out.print("Enter branch: ");
                        String branch2 = sc.next();
                        manager.displaySGradeStudentsInAGivenBranch(branch2);
                        break;

                case 5: manager.displayBranchWithHighestSGradeStudents();
                        break;

                case 6: sc.close();
                        return;

                default: System.out.println("invalid option");
            }
        }
    }
}
