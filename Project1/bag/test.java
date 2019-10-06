package bag;

import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

//where all the testing for the bag will be done
public class test
{
    private static int number_of_students = 0;
    private static Random ran = new Random();
    private static BagInterface roster = new ArrayBag();

    private static Scanner scanner = new Scanner(System.in);

    private static String path = "";
    public static void main(String[] args) throws FileNotFoundException
    {
        determineBagType();
        findPath();
        editingRoster();
        Student bob = new Student(2147000000, "Bob", "Turner", "senior");
        roster.addStudent(bob);
    }

    private static void editingRoster()
    {
        System.out.println("\nThere are quite a few commands you can do in this program. I will quickly run through all of them and what they do:");
        System.out.println("addStudent - will add a student that has a first and last name, academic year, and a Student ID");
        System.out.println("removeAnyStudent - removes random student from the class");
        System.out.println("removeStudentByID - removes student with given ID");
        System.out.println("searchForStudentByID - will return a student after searching by the Student's ID");
        System.out.println("classSize - will return how many students are in the current class");
        System.out.println("isClassFull - will return whether or not the class is full");
        System.out.println("isClassEmpty - will return whether the class is empty or not");
        System.out.println("AcademicQuantities - will return how many students there are in each academic year");
        System.out.println("getStudents - will return every student in the class with their given data");
        System.out.println("clearClass - will remove every student from the class");
        System.out.println("stop - will exit the program\n");

        String[] command = {"addstudent", "removeanystudent", "removestudentbyid", "searchforstudentbyid", "classsize", "isclassempty", "isclassfull", "academicquantities", "getstudents", "clearclass", "stop"};
        List<String> commands = Arrays.asList(command);

        boolean keep_going = true;
        String current_command = "";
        boolean valid_current_command = false;
        boolean empty_command = false;

        while(keep_going)
        {
            do
            {
                if(!empty_command)
                {
                    System.out.print("Please enter one of the commands explained at the beginning: ");
                }
                empty_command = false;
                valid_current_command = false;
                current_command = scanner.nextLine().strip().toLowerCase();
                if(commands.contains(current_command))
                {
                    valid_current_command = true;
                }
                else if(current_command.equals(""))
                {
                    empty_command = true;
                }
                else
                {
                    System.out.println("Invalid command");
                }
            }
            while(!valid_current_command);
            
            if(current_command.equals("stop"))
            {
                keep_going = false;
            }
            else
            {
                executeCommand(current_command);
            }
        }
    }

    public static void executeCommand(String command)
    {
        switch(command)
        {
            case("addstudent"):
                addStudent();
                break;
            case("removeanystudent"):
                removeRandomStudent();
                break;
            case("removestudentbyid"):
                removeStudentByID();
                break;
            case("searchforstudentbyid"):
                searchForStudentByID();
                break;
            case("classsize"):
                classSize();
                break;
            case("isclassempty"):
                isClassEmpty();
                break;
            case("isclassfull"):
                isClassFull();
                break;
            case("academicquantities"):
                academicQuantities();
                break;
            case("getstudents"):
                getStudents();
                break;
            case("clearclass"):
                clearClass();
                break;
        }
    }

    private static void addStudent()
    {
        String first = " ", last = " ", academic_level = " ";
        Integer ID = 0;

        System.out.print("What is the students first name: ");
        first = scanner.nextLine();

        System.out.print("What is the students last name: ");
        last = scanner.nextLine();

        boolean valid_level = false;
        do
        {
            System.out.print("What is the students academic level(freshman, sophmore, junior, senior): ");
            academic_level = scanner.nextLine();
            if(academic_level.equals("freshman") || academic_level.equals("sophmore") || academic_level.equals("junior") || academic_level.equals("senior"))
            {
                valid_level = true;
            }
            else
            {
                System.out.println("Invalid class level");
            }
        }
        while(!valid_level);

        System.out.print("Enter the student ID(-2147483648 to 2147483647): ");
        ID = scanner.nextInt();
        
        Student student = new Student(ID, first, last, academic_level);
        roster.addStudent(student);
    }

    private static void removeRandomStudent()
    {
        if(roster.isClassEmpty())
        {
            System.out.println("The class is empty thus no student was removed");
        }
        else
        {
            Student student = roster.removeStudent();
            System.out.println("The data of the removed student is as follows:");
            System.out.println(student.toString());
        }
    }
    
    private static void removeStudentByID()
    {
        System.out.print("Please enter the ID for the student you want to remove: ");
        int ID = scanner.nextInt();
        boolean removed = false;

        try
        {
            removed = roster.removeStudent(ID);
        }
        catch(NullPointerException e)
        {
            
        }

        if(removed)
        {
            System.out.println("The student with ID " + ID + " has been removed");
        }
        else
        {
            System.out.println("The student with ID " + ID + " did not exist");
        }
    }

    private static void searchForStudentByID()
    {
        System.out.print("Please enter the ID for the student you want to get information about: ");
        int ID = scanner.nextInt();
        Student student = roster.searchForStudent(ID);
        if(student.equals(null))
        {
            System.out.println("The student ID " + ID + " did not exist");
        }
        else
        {
            System.out.println(student.toString());
        }
    }

    private static void classSize()
    {
        System.out.println("The class size is: " + roster.getClassSize());
    }

    private static void isClassEmpty()
    {
        boolean empty_class = roster.isClassEmpty();
        String empty = "is not";
        if(empty_class)
        {
            empty = "is";
        }
        System.out.println("The class " + empty + " empty");
    }

    private static void isClassFull()
    {
        boolean full_class = roster.isClassFull();
        String full = "is not";
        if(full_class)
        {
            full = "is";
        }
        System.out.println("The class " + full + " full");
    }

    private static void academicQuantities()
    {
        System.out.println(roster.getStudentsAcademicLevelQuantity());
    }

    private static void getStudents()
    {
        Student[] students = roster.toArray();
        if(students.length == 0)
        {
            System.out.println("The class is empty thus no students to report");
        }
        else
        {
            printList(students);
        }
    }

    private static void clearClass()
    {
        roster.clearClass();
        System.out.println("The class has been cleared");
    }

    private static void determineBagType()
    {
        boolean correct = false;
        String answer = "";
        do
        {
            System.out.print("What type of bag do you want to use? array or linkedList?: ");
            answer = scanner.nextLine();
            answer = answer.toLowerCase();
            answer = answer.strip();
            if(answer.equals("array") || answer.equals("linkedlist"))
            {
                if(answer.equals("linkedlist"))
                {
                    roster = new LinkedListBag();
                }
                correct = true;
            }
            else
            {
                System.out.println("Invalid bag type");
            }
        }
        while(!correct);
    }

    //gives me the data inside each student
    private static void printList(Student[] students)
    {
        for(int index = 0; index < students.length; index++)
        {
            System.out.println(students[index].toString());
        }
    }

    //creates a random year for the new students
    private static String randomYear()
    {
        String year = "";
        int number = ran.nextInt(4);
        switch(number)
        {
            case 0:
                year = "freshman";
                break;
            case 1:
                year = "sophmore";
                break;
            case 2:
                year = "junior";
                break; 
            case 3:
                year = "senior";
                break;
        }

        return year;
    }

    //determins where the names text file is located
    private static void findPath()
    {
        String temp_path = System.getProperty("user.dir");
        char last = temp_path.charAt(temp_path.length() - 1);
        if(last == 'g')
        {
            path = ".";
        }
        else
        {
            path = "./bag";
        }
        
    }
}