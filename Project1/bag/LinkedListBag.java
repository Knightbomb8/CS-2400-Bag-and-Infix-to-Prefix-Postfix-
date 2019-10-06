package bag;

import java.util.ArrayList;

public class LinkedListBag implements BagInterface
{
    private Node first_node;
    private int number_of_entries;

    private ArrayList<Integer> student_IDs = new ArrayList<>();
    private int[] academic_levels = new int[4];

    public LinkedListBag()
    {
        first_node = null;
        number_of_entries = 0;
    }

    //returns the student with given ID and null if no student with said ID exists
    public Student searchForStudent(int ID)
    {
        boolean found = false;
        Student student = null;
        Node currentNode = first_node;

        while(!found && currentNode != null)
        {
            if(currentNode.getData().getStudent_ID() == ID)
            {
                found = true;
                student = currentNode.getData();
            }
            else
            {
                currentNode = currentNode.getNextNode();
            }
        }
        return student;
    }

    //removes a student with given ID
    public boolean removeStudent(int ID)
    {
        boolean result = false;
        Node node = getNodeWithID(ID);

        if(node != null)
        {
            updateAcademicLevels("remove", node.getData().getAcademic_year());
            removeID(node.getData().getStudent_ID());

            node.setData(first_node.getData());
            first_node = first_node.getNextNode();

            number_of_entries--;
            result = true;
        }

        return result;
    }

    //removes first student on the list
    public Student removeStudent()
    {
        Student student = null;

        if(first_node != null)
        {
            student = first_node.getData();
            first_node = first_node.getNextNode();

            number_of_entries--;
            removeID(student.getStudent_ID());
            updateAcademicLevels("remove", student.getAcademic_year());
        }
        return student;
    }

    //returns an array with all the current students
    public Student[] toArray()
    {
        Student[] students = new Student[number_of_entries];
        Node currentNode = first_node;
        int index = 0;

        while(currentNode != null && index < number_of_entries)
        {
            students[index] = currentNode.getData();
            index++;
            currentNode = currentNode.getNextNode();
        }

        return students;
    }

    //adds a student to the bag
    public boolean addStudent(Student student)
    {
        int new_student_ID = student.getStudent_ID();
        checkID(new_student_ID);
        student_IDs.add(new_student_ID);
        updateAcademicLevels("add", student.getAcademic_year());

        Node new_node = new Node(student, first_node);
        first_node = new_node;

        number_of_entries++;
        return true; 
    }

    //deletes class
    public void clearClass()
    {
        first_node = null;
        number_of_entries = 0;
    }

    //checks if class is empty
    public boolean isClassEmpty()
    {
        return(number_of_entries == 0);
    }

    public boolean isClassFull()
    {
        return(false);
    }

    //returns current class size
    public int getClassSize()
    {
        return number_of_entries;
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

    //gets the Node with specific studentID attached to it
    private Node getNodeWithID(int ID)
    {
        Node currentNode = first_node;
        boolean found = false;

        while(!found && currentNode != null)
        {
            if(ID == currentNode.getData().getStudent_ID())
            {
                found = true;
            }
            else
            {
                currentNode = currentNode.getNextNode();
            }
        }
        return currentNode;
    }

    //removes a certain ID from the ID array
    private void removeID(int ID)
    {
        if(student_IDs.contains(ID))
        {
            student_IDs.remove((Integer)ID);
        }
    }

    //checks if one of the new ID's already exists
    private void checkID(int ID)
    {
        if(student_IDs.contains(ID))
        {
            throw new IllegalArgumentException("There is already a student that exists with this ID: " + ID);
        }
    }

    //adds or removes number of students in each academic level
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

    //node class for storing data and next info
    private class Node
    {
        private Node nextNode;
        private Student data;

        private Node(Student student)
        {
            this(student, null);
        }

        private Node(Student student, Node next)
        {
            data = student;
            this.nextNode = next;
        }

        public Node getNextNode() {
            return this.nextNode;
        }
        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

        public Student getData() {
        	return this.data;
        }
        public void setData(Student data) {
        	this.data = data;
        }
    }
}