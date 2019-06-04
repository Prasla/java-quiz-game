/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Student;
import java.sql.*;
import java.util.ArrayList;

/**
 * This is a DAO class responsible for DB Controller for spelling table. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class StudentDAO {
    
    private static Connection mysqlConnection;
    
    /**
     * This is a function insert Student from Spelling table.
     *
     * @param std is Student object to be added into Student table.
     * @return boolean true if record is added else false.
     */
    public static boolean addStudentRecord(Student std) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            stmt.executeUpdate("INSERT INTO student (`fullName`, `schoolId`, `schoolName`, `password`, `creationTime`) Values ('"+std.getFullName()+"','"+std.getSchoolId()+"','"+std.getSchoolName()+"','"+std.getPassword()+"','"+std.getTime()+"')");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        } finally {
            if(mysqlConnection != null) {
                try {
                    mysqlConnection.close();
                } catch(SQLException ex) {
                        return false;
                }
            }
        }
    }
    
    /**
     * This is a function for retrieving all the student record from student table.
     *
     * @return ArrayList of Student object.
     */
    public static ArrayList<Student> getStudent() {
        
        ArrayList<Student> studentList = new ArrayList<Student>();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            if(rs != null) {
                while (rs.next()) {
                    Student std = new Student(rs.getInt("id"), rs.getString("fullName"), rs.getInt("schoolId"),
                        rs.getString("schoolName"),rs.getString("password"),rs.getString("creationTime"),rs.getBoolean("login"));
                    studentList.add(std);
                }
                return studentList;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            return null;
        } finally {
            if(mysqlConnection != null) {
                try {
                    mysqlConnection.close();
                } catch(SQLException ex) {
                    return null;
                }
            }
        }
    } 
    
    /**
     * This is a function for retrieving all the question from Spelling table.
     *
     * @param schoolId is a int that contain student school id.
     * @return Student object if record is fount else null.
     */
    public static Student getStudentById(int schoolId) {      
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM student WHERE schoolId = " + schoolId);
            if(rs.next()) {
                Student std = new Student(rs.getInt("id"), rs.getString("fullName"), rs.getInt("schoolId"),
                    rs.getString("schoolName"),rs.getString("password"),rs.getString("creationTime"),rs.getBoolean("login"));
     
                return std;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            return null;
        } finally {
            if(mysqlConnection != null) {
                try {
                    mysqlConnection.close();
                } catch(SQLException ex) {
                    return null;
                }
            }
        }
    } 
    
    public static boolean setStudentLoginId(int schoolId) {      
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            stmt.executeUpdate("UPDATE student SET login = 1 WHERE schoolId = " + schoolId);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        } finally {
            if(mysqlConnection != null) {
                try {
                    mysqlConnection.close();
                } catch(SQLException ex) {
                    return false;
                }
            }
        }
    } 
    
}
