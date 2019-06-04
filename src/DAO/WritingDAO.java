/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Writing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author zObY
 */
public class WritingDAO {
    
        private static Connection mysqlConnection;
    
    public static ArrayList<Writing> getAllQuestion() {
        
        ArrayList<Writing> questionList = new ArrayList<Writing>();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/aj_assignment_1","root","");
            Statement stmt = mysqlConnection.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM writing ORDER BY difficultyLevel ASC");
            if(rs != null) {
                while (rs.next()) {
                    Writing ques = new Writing(
                            rs.getInt("id"), 
                            rs.getString("question"), 
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
