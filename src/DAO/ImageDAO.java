/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Image;
import java.sql.*;
import java.util.ArrayList;

/**
 * This is a DAO class responsible for DB Controller for image table. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class ImageDAO {
    
    private static Connection mysqlConnection;
    
    /**
     * This is a function for retrieving all the question from image table.
     *
     * @return ArrayList of Image that contain all the image question.
     */
    public static ArrayList<Image> getAllQuestion() {
        
        ArrayList<Image> questionList = new ArrayList<Image>();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM image ORDER BY difficultyLevel ASC");
            if(rs != null) {
                while (rs.next()) {
                    Image ques = new Image(
                            rs.getInt("id"), 
                            rs.getString("question"), 
                            rs.getString("source"),
                            rs.getString("xy"),
                            rs.getInt("width"),
                            rs.getInt("height"),
                            rs.getInt("difficultyLevel"),
                            rs.getInt("score")
                        );
                    questionList.add(ques);
                }
                return questionList;
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
    
}
