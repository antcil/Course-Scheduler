
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
public class CoursesQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static ResultSet resultSet;
    private static PreparedStatement getCourseCodeList;
    private static PreparedStatement getSeats;
    private static PreparedStatement dropCourse;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            // Same as addSemester method but with respectove columns and variables
            addCourse = connection.prepareStatement("insert into app.courses (semester,coursecode,description,seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<CourseEntry> getAllCourses (String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> semesterCourses = new ArrayList<CourseEntry>();
        try {
            // SQL statment that searches for coursecode of coursees only in respective semester
            getCourseList = connection.prepareStatement("select semester,coursecode,description,seats from app.courses where semester='" + semester + "'");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next()) {
                String CourseSemester = resultSet.getString(1);
                String courseCode = resultSet.getString(2);
                String description = resultSet.getString(3);
                int seats = resultSet.getInt(4);
                CourseEntry course = new CourseEntry(CourseSemester, courseCode, description, seats);
                semesterCourses.add(course);
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return semesterCourses;
    }
    
    public static ArrayList<String> getAllCourseCodes (String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodeList = new ArrayList<String>();
        try {
            getCourseCodeList = connection.prepareStatement("select coursecode from app.courses where semester='" + semester + "'");
            resultSet = getCourseCodeList.executeQuery();
            
            while(resultSet.next()) {
                String courseCode = resultSet.getString(1);
                courseCodeList.add(courseCode);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodeList;
    }
    
    public static int getCourseSeats (String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;
        // Gets total number of seats
        try {
            getSeats = connection.prepareStatement("select seats from app.courses where semester='" + semester + "' and coursecode='" + courseCode + "'");
            resultSet = getSeats.executeQuery();
            
            while(resultSet.next()) {
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropCourse = connection.prepareStatement("delete from app.courses where semester='" + semester + "' and coursecode='" + courseCode + "'");
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
