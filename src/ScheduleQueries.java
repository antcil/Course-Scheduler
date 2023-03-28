/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author anthonyciliberto
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addSchedule;
    private static PreparedStatement findCourse;
    private static PreparedStatement getSeatsTaken;
    private static PreparedStatement getStudentSchedule;
    private static PreparedStatement getStudentsScheduledCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry schedule) {
        connection = DBConnection.getConnection();
        try {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester,studentid,coursecode,status,timestamp) values(?,?,?,?,?)");
            addSchedule.setString(1, schedule.getSemester());
            addSchedule.setString(2, schedule.getStudentID());
            addSchedule.setString(3, schedule.getCourseCode());
            addSchedule.setString(4, schedule.getStatus());
            addSchedule.setTimestamp(5, schedule.getTimestamp());
            addSchedule.executeUpdate();
            
            // Finds added course and updates seats
            findCourse = connection.prepareStatement("select semester,coursecode,description,seats from app.courses where coursecode='" + schedule.getCourseCode() + "'");
            int seatsTaken = 0;
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seatsTaken = 0;
        try {
            getSeatsTaken = connection.prepareStatement("select studentid from app.schedule where semester='" + semester + "' and coursecode='" + courseCode + "' and status='S'");
            resultSet = getSeatsTaken.executeQuery();
            
            while(resultSet.next()) {
                seatsTaken += 1;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seatsTaken;
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleList = new ArrayList<ScheduleEntry>();
        try {
            getStudentSchedule = connection.prepareStatement("select semester,studentid,coursecode,status,timestamp from app.schedule where semester='" + semester + "' and studentid='" + studentID + "'");
            resultSet = getStudentSchedule.executeQuery();
            
            while(resultSet.next()) {
                String scheduleSemester = resultSet.getString(1);
                String scheduleStudentID = resultSet.getString(2);
                String scheduleCourseCode = resultSet.getString(3);
                String scheduleStatus = resultSet.getString(4);
                Timestamp scheduleTimestamp = resultSet.getTimestamp(5);
                ScheduleEntry schedule = new ScheduleEntry(scheduleSemester, scheduleCourseCode, scheduleStudentID, scheduleStatus, scheduleTimestamp);
                scheduleList.add(schedule);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleList;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduledStudentsList = new ArrayList<ScheduleEntry>();
        try {
            getStudentsScheduledCourse = connection.prepareStatement("select semester,studentid,coursecode,status,timestamp from app.schedule where semester='" + semester + "' and coursecode='" + courseCode + "' and status='S'");
            resultSet = getStudentsScheduledCourse.executeQuery();
            
            while(resultSet.next()) {
                String scheduleSemester = resultSet.getString(1);
                String scheduleStudentID = resultSet.getString(2);
                String scheduleCourseCode = resultSet.getString(3);
                String scheduleStatus = resultSet.getString(4);
                Timestamp scheduleTimestamp = resultSet.getTimestamp(5);
                ScheduleEntry schedule = new ScheduleEntry(scheduleSemester, scheduleCourseCode, scheduleStudentID, scheduleStatus, scheduleTimestamp);
                scheduledStudentsList.add(schedule);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudentsList;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudentsList = new ArrayList<ScheduleEntry>();
        try {
            // order by timestamp maintains waitlist priority in resulting array list
            getStudentsScheduledCourse = connection.prepareStatement("select semester,studentid,coursecode,status,timestamp from app.schedule where semester='" + semester + "' and coursecode='" + courseCode + "' and status='W' order by timestamp");
            resultSet = getStudentsScheduledCourse.executeQuery();
            
            while(resultSet.next()) {
                String scheduleSemester = resultSet.getString(1);
                String scheduleStudentID = resultSet.getString(2);
                String scheduleCourseCode = resultSet.getString(3);
                String scheduleStatus = resultSet.getString(4);
                Timestamp scheduleTimestamp = resultSet.getTimestamp(5);
                ScheduleEntry schedule = new ScheduleEntry(scheduleSemester, scheduleCourseCode, scheduleStudentID, scheduleStatus, scheduleTimestamp);
                waitlistedStudentsList.add(schedule);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudentsList;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester='" + semester + "' and studentid='" + studentID + "' and coursecode='" + courseCode + "'");
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    } 
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester='" + semester + "' and coursecode='" + courseCode + "'");
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            updateScheduleEntry = connection.prepareStatement("update app.schedule set status='S' where semester='" + semester + "' and status='W' and studentid='" + entry.getStudentID() + "' and coursecode='" + entry.getCourseCode() + "'");
            updateScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
} 
