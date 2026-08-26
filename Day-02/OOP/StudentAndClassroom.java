/*
 * QUESTION:
 *
 * Design an OOP-based system to manage students in different
 * classrooms/courses.
 *
 * Each student has:
 *   - ID
 *   - Name
 *   - Branch
 *
 * A student should be considered unique based on their ID.
 * If the same student is added more than once to a course,
 * count that student only once.
 *
 * The program should:
 *   1. Find the number of unique students in each course.
 *   2. Find the course having the highest number of students.
 *
 * EXAMPLE INPUT:
 *
 * Java:
 *   101, Ravi, CSE
 *   102, Arun, ECE
 *   101, Ravi, CSE
 *
 * SQL:
 *   103, Kiran, CSE
 *   104, Naveen, ECE
 *
 * EXAMPLE OUTPUT:
 *
 * Java course contains 2 unique students.
 * SQL course contains 2 unique students.
 *
 * Course with most students: Java
 */

import java.util.*;

class Student
{
    private int id;
    private String name;
    private String branch;

    public Student(int id, String name, String branch)
    {
        this.id = id;
        this.name = name;
        this.branch = branch;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        Student student = (Student) obj;

        return this.id == student.getId();
    }
}

class Classroom
{
    private HashMap<String, Set<Student>> courses;

    public Classroom(HashMap<String, Set<Student>> courses) {
        this.courses = courses;
    }

    public void getNoOfUniqueStudentsInEachClassroom() {
        for(Map.Entry<String, Set<Student>> entry : courses.entrySet())
            System.out.println(entry.getKey() + " course contains " + entry.getValue().size() + " unique students.");

    }

    public String getClassroomWithMostStudents() {
        String res = null;
        int maxStudents = -1;

        for(Map.Entry<String, Set<Student>> entry : courses.entrySet())
        {
            if(entry.getValue().size() > maxStudents)
            {
                maxStudents = entry.getValue().size();
                res = entry.getKey();
            }
        }

        return res;
    }
}


public class StudentAndClassroom
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        HashMap<String, Set<Student>> courses = new HashMap<>();

        System.out.print("Enter number of courses: ");
        int numberOfCourses = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numberOfCourses; i++)
        {
            System.out.print("\nEnter course name: ");
            String courseName = sc.nextLine();

            System.out.print("Enter number of students: ");
            int numberOfStudents = sc.nextInt();
            sc.nextLine();

            Set<Student> students = new HashSet<>();

            for (int j = 0; j < numberOfStudents; j++)
            {
                System.out.println("\nStudent " + (j + 1));

                System.out.print("Enter student ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter student name: ");
                String name = sc.nextLine();

                System.out.print("Enter branch: ");
                String branch = sc.nextLine();

                students.add(new Student(id, name, branch));
            }

            courses.put(courseName, students);
        }

        Classroom classroom = new Classroom(courses);

        System.out.println("\n===== RESULT =====");

        classroom.getNoOfUniqueStudentsInEachClassroom();

        System.out.println(
            "\nCourse with most students: " +
            classroom.getClassroomWithMostStudents()
        );

        sc.close();
    }
}
