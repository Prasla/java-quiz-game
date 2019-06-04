/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Question;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is a DAO class responsible for DB Controller for listening table. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class ListeningDAO {
    
    private static Connection mysqlConnection;
    
    /**
     * This is a function for retrieving all the question from Listening table.
     *
     * @return ArrayList of Question that contain all the Listening question.
     */
    public static ArrayList<Question> getAllQuestion() {
        
        ArrayList<Question> questionList = new ArrayList<Question>();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM listening ORDER BY difficultyLevel ASC");
            if(rs != null) {
                while (rs.next()) {
                    Question ques = new Question(
                            rs.getInt("id"), 
                            rs.getString("question"), 
                            rs.getString("option1"),
                            rs.getString("option2"),
                            rs.getString("option3"),
                            rs.getString("option4"),
                            rs.getString("answer"),
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
