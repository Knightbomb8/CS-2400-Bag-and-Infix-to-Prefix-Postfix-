package bag;

import java.util.Arrays;

public class ArrayBag implements BagInterface
{
    //initializes base values for everything
    private Student[] bag;
    private static final int DEFAULT_CAPACITY = 25;
    private int number_of_entries;

    private static final int MAX_CAPACITY = 10000;
    private boolean integrity_ok = false;

    //creates two arrays, one too store current student IDs and
    //another to keep track of how many students per academic level
    private int[] student_IDs;
    private int[] academic_levels = new int[4];

    //default constructor run by the program
    public ArrayBag()
    {
        this(DEFAULT_CAPACITY);
    }

    //constructor to set bag at specific size
    public ArrayBag(int capacity)
    {
        if(capacity <= MAX_CAPACITY)
        {
            this.bag = new Student[capacity];
            this.student_IDs = new int[capacity];
            this.integrity_ok = true;
        }
        else
        {
            throw new IllegalStateException("Attempt to create a bag whose size exceeds maximum capacity");
        }
    }

    //adds a student to the class
    public boolean addStudent(Student new_student)
    {
        checkIntegrity();

        //makes sure the ID and is valid
        String new_student_academic_level = new_student.getAcademic_year();
        int new_student_ID = new_student.getStudent_ID();
        checkID(new_student_ID);
        if(isArrayFull())
        {
            doubleCapacity();
        }

        //adds the student to the array and updates how many students with that academic level exist
        updateAcademicLevels("add", new_student_academic_level);

        student_IDs[number_of_entries] = new_student_ID;
        bag[number_of_entries] = new_student;
        number_of_entries++;

        return true;
    }

    //removes a student from the class based on student ID
    public boolean removeStudent(int ID)
    {
        boolean result = false;

        for(int index = 0; index < number_of_entries; index++)
        {
            if(bag[index].getStudent_ID() == ID)
            {
                removeEntry(index);
                result = true;
                index = number_of_entries;
            }
        }
        return result;        
    }

    //removes a student from the bag if there is atleast one student in the bag
    public Student removeStudent()
    {
        Student student = null;

        if(number_of_entries > 0)
        {
            student = bag[number_of_entries - 1];
            removeEntry(number_of_entries - 1);
        }

        return student;
    }

    //returns a specific student based on their ID
    public Student searchForStudent(int ID)
    {
        checkIntegrity();
        Student searchedStudent = null;
        for(int index = 0; index < number_of_entries; index++)
        {
            int student_ID = bag[index].getStudent_ID();
            if(student_ID == ID)
            {
                searchedStudent = bag[index];
                index = number_of_entries;
            }
        }
        return searchedStudent;
    }

    //returns the current number of students in the class/roster
    public int getClassSize()
    {
        return number_of_entries;
    }

    //checks if the class is full
    public boolean isClassFull()
    {
        return(number_of_entries == bag.length);
    }

    //checks if the class is empty
    public boolean isClassEmpty()
    {
        return(number_of_entries == 0);
    }

    //returns the quantity of students in eacha academic year
    public String getStudentsAcademicLevelQuantity()
    {
        String result = "";

        result += "Number of Freshman: " + academic_levels[0] + "\n";
        result += "Number of Sophmore: " + academic_levels[1] + "\n";
        result += "Number of Sophmore: " + academic_levels[2] + "\n";
        result += "Number of Sophmore: " + academic_levels[3] + "\n";

        return result;
    }

    //returns a new array of all the students currently in the class
    public Student[] toArray()
    {
        Student[] result = new Student[number_of_entries];

        for(int index = 0; index < number_of_entries; index++)
        {
            result[index] = bag[index];
        }

        return result;
    }

    //deletes all the students currently in the class and all of the currently held student IDs
    public void clearClass()
    {
        bag = new Student[DEFAULT_CAPACITY];
        student_IDs = new int[DEFAULT_CAPACITY];
        number_of_entries = 0;
    }

    //removes a specific entry in the bag based on the index given to it
    private void removeEntry(int index)
    {
        if(index >= 0 && index <= number_of_entries)
        {
            Student student = bag[index];
            String academic_level = student.getAcademic_year();
            int ID = student.getStudent_ID();

            updateAcademicLevels("remove", academic_level);
            removeID(ID);

            bag[index] = bag[number_of_entries - 1];
            bag[number_of_entries] = null;

            number_of_entries--;
        }
    }

    //removes an ID from the ID array
    private void removeID(int ID)
    {
        for(int index = 0; index < number_of_entries; index++)
        {
            int currentID = bag[index].getStudent_ID();
            if(currentID == ID)
            {
                student_IDs[index] = student_IDs[number_of_entries - 1];
                student_IDs[number_of_entries - 1] = 0;
            }
        }
    }

    //updates how many students of each academic type exist
    //the multiplier tells us if we are adding or removing
    private void updateAcademicLevels(String multiplier, String academic_level)
    {
        int direction = 1;
        int target = 0;

        if(multiplier.equals("remove"))
        {
            direction *= -1;
        }

        switch(academic_level)
        {
            case "freshman":
                target = 0;
                break;
            case "sophmore":
                target = 1;
                break;
            case "junior":
                target = 2;
                break;
            case "senior":
                target = 3;
                break;
        }

        academic_levels[target] = academic_levels[target] + direction;
    }

    //makes sure the new ID being added does not already exist
    private void checkID(int ID)
    {
        for(int index = 0; index < number_of_entries; index++)
        {
            if(ID == student_IDs[index])
            {
                throw new IllegalArgumentException("There is already a student that exists with this ID: " + ID);
            }
        }
    }

    //returns if the array is full or not
    private boolean isArrayFull()
    {
        return(bag.length == number_of_entries);
    }

    //doubles the array capacity
    private void doubleCapacity()
    {
        int new_length = 2 * bag.length;
        checkCapacity(new_length);
        bag = Arrays.copyOf(bag, new_length);
        student_IDs = Arrays.copyOf(student_IDs, new_length);
    }
    
    //makes sure the new capacity is not too great
    private void checkCapacity(int capacity)
    {
        if(capacity > MAX_CAPACITY)
        {
            throw new IllegalStateException("Attempt to create a bag whose capacity exceeds maximum limit of: " + MAX_CAPACITY);
        }
    }

    //checks to make sure the array is still working
    private void checkIntegrity()
    {
        if(!integrity_ok)
        {
            throw new SecurityException("ArrayBag of Students is corrupt");
        }
    }
}