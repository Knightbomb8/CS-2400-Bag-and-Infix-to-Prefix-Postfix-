package bag;

import bag.Student;

public interface BagInterface
{
    public boolean addStudent(Student new_student);

    public boolean removeStudent(int ID);

    public Student removeStudent();

    public Student searchForStudent(int ID);

    public int getClassSize();

    public boolean isClassFull();

    public boolean isClassEmpty();

    public String getStudentsAcademicLevelQuantity();

    public Student[] toArray();

    public void clearClass();

}