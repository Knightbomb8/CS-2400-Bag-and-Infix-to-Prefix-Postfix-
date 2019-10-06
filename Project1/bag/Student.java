package bag;

//creates a Student class that will be a data point in our bag
public class Student
{
    //creates four data points for the student the initializes getters and setters for all of the data points
    private int student_ID;
    private String first_name;
    private String last_name;
    private String academic_year;

    public int getStudent_ID() {
    	return this.student_ID;
    }
    public void setStudent_ID(int student_ID) {
    	this.student_ID = student_ID;
    }


    public String getFirst_name() {
    	return this.first_name;
    }
    public void setFirst_name(String first_name) {
    	this.first_name = first_name;
    }

    
    public String getLast_name() {
    	return this.last_name;
    }
    public void setLast_name(String last_name) {
    	this.last_name = last_name;
    }


    public String getAcademic_year() {
    	return this.academic_year;
    }
    public void setAcademic_year(String academic_year) {
    	this.academic_year = academic_year;
    }

    //returns the string equivalent of what data the student has
    public String toString()
    {
        String result = "";

        result += "ID: " + this.getStudent_ID() + "\n";
        result += "First Name: " + this.getFirst_name() + "\n";
        result += "Last Name: " + this.getLast_name() + "\n";
        result += "Academic Level: " + this.getAcademic_year() + "\n";

        return result;
    }

    //constructor to initially give the student data points
    public Student(int ID, String first_name, String last_name, String academic_year)
    {
        this.student_ID = ID;
        this. first_name = first_name;
        this.last_name = last_name;
        this.academic_year = academic_year;
    }
}