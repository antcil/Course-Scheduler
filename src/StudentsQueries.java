
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anthonyciliberto
 */
public class StudentsQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static PreparedStatement getID;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.students (studentid,firstname,lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<StudentEntry> getAllStudents() {
            connection = DBConnection.getConnection();  
            ArrayList<StudentEntry> studentList = new ArrayList<StudentEntry>();
            try {
                getStudentList = connection.prepareStatement("select studentid,firstname,lastname from app.students order by lastname");
                resultSet = getStudentList.executeQuery();

                while(resultSet.next()) {
                    String studentID = resultSet.getString(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    StudentEntry student = new StudentEntry(studentID, firstName, lastName);
                    studentList.add(student);
                }
            }
            catch(SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return studentList;
        }
    
    public static String getStudentID(String firstName, String lastName) {
        ArrayList<StudentEntry> studentList = StudentsQueries.getAllStudents();
        for (StudentEntry student : studentList) {
            if (student.getFirstName().equals(firstName) & student.getLastName().equals(lastName)) {
                return student.getStudentID();
            }
                    }
        return null;
    }
    
    public static StudentEntry getStudent(String studentID) {
        ArrayList<StudentEntry> studentList = StudentsQueries.getAllStudents();
        // Traverses student list and returns student if ID matches
        for (StudentEntry student : studentList) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }
    
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        try {
            dropStudent = connection.prepareStatement("delete from app.students where studentid='" + studentID + "'");
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
