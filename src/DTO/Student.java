/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 * This is a Student class responsible for Data Model for Student. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class Student {
    
    private int id;
    private String fullName;
    private int schoolId;
    private String schoolName;
    private String password;
    private String time;
    private boolean login;
    
    /**
     * This is a parametric constructor. 
     */
    public Student() {
        this.id = 0;
        this.fullName = null;
        this.schoolId = 0;
        this.schoolName = null;
        this.password = null;
        this.time = null;
        this.login = false;
    }
    
    /**
     *
     * This is a parametric constructor. 
     * 
     * @param id is a int that value that will uniquely identify record.
     * @param fullName is a string that will contain student name.
     * @param schoolId is a int that will contain school id.
     * @param schoolName is a string that will contain school name.
     * @param password is a string that will contain password name.
     * @param time is is a string that will contain time the account is create.
     */
    public Student(int id, String fullName, int schoolId, String schoolName, String password, String time, boolean login) {
        this.id = id;
        this.fullName = fullName;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.password = password;
        this.time = time;
        this.login = login;
    }

    /**
     *
     * This is a getter for id. 
     * 
     * @return int id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     *
     * This is a getter for fullName. 
     * 
     * @return String fullName value.
     * 
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * This is a getter for schoolId. 
     * 
     * @return int schoolId value.
     * 
     */
    public int getSchoolId() {
        return schoolId;
    }

    /**
     *
     * This is a getter for schoolId. 
     * 
     * @return int schoolId value.
     * 
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     *
     * This is a getter for password. 
     * 
     * @return string password value.
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * This is a getter for time. 
     * 
     * @return string time value.
     * 
     */
    public String getTime() {
        return time;
    }
    
    /**
     *
     * This is a getter for isLogin. 
     * 
     * @return boolean true if user have already login to the system.
     * 
     */
    public boolean isLogin() {
        return login;
    }
    
    

    /**
     *
     * This is a setter for id. 
     * 
     * @param id is int value.
     * 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * This is a setter for fullName. 
     * 
     * @param fullName is string value.
     * 
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * This is a setter for schoolId. 
     * 
     * @param schoolId is int value.
     * 
     */
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    /**
     *
     * This is a setter for schoolName. 
     * 
     * @param schoolName is string value.
     * 
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     *
     * This is a setter for password. 
     * 
     * @param password is string value.
     * 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * This is a setter for time. 
     * 
     * @param time is string value.
     * 
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * This is a setter for isLogin. 
     * 
     * @param login boolean true if user have login into the system.
     * 
     */
    public void setLogin(boolean login) {
        this.login = login;
    }
    
    
  
}
