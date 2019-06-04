/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Spelling;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is a DAO class responsible for DB Controller for spelling table. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class SpellingDAO {
    
    private static Connection mysqlConnection;
    
    /**
     * This is a function for retrieving all the question from Spelling table.
     *
     * @return ArrayList of Spelling question that contain all the Spelling question.
     */
    public static ArrayList<Spelling> getAllQuestion() {
        
        ArrayList<Spelling> spellingQuestionList = new ArrayList<Spelling>();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM spelling ORDER BY difficultyLevel ASC");
            if(rs != null) {
                while (rs.next()) {
                    Spelling ques = new Spelling(
                            rs.getInt("id"), 
                            rs.getString("question"), 
                            rs.getString("hint"),
                            rs.getString("answer"),
                            rs.getInt("difficultyLevel"),
                            rs.getInt("score")
                        );
                    spellingQuestionList.add(ques);
                }
                return spellingQuestionList;
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
