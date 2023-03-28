/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anthonyciliberto
 */
public class StudentEntry {
    private String studentID;
    private String firstName;
    private String lastName;
    
    public StudentEntry(String studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    // Easier way to get the string to properly display student name
    public String getNameLayout() {
        return lastName + "," + firstName;
    }
}
