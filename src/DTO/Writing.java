/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 * This is a Writing class responsible for Data Model for Writing. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class Writing {
    
    private int id;
    private String question;
    private String answer;
    private int difficultyLevel;
    private int score;
    
    /**
     *
     * This is a parametric constructor. 
     *
     */
    public Writing() {
        this.id = 0;
        this.question = null;
        this.answer = null;
        this.difficultyLevel = 0;
        this.score = 0;
    }
    
    /**
     *
     * This is a parametric constructor. 
     * 
     * @param id is a int that value that will uniquely identify record.
     * @param question is a String value that will contain question.
     * @param answer is a String value that will contain answer.
     * @param difficultyLevel is a int value that will contain difiiculty level of the question.
     * @param score is a int value that will contain worth of the question.
     */
    public Writing(int id, String question, String answer, int difficultyLevel, int score) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.difficultyLevel = difficultyLevel;
        this.score = score;
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
     * This is a setter for question. 
     * 
     * @param question is string value.
     * 
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * This is a setter for answer. 
     * 
     * @param answer is string value.
     * 
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     *
     * This is a setter for width. 
     * 
     * @param difficultyLevel is int value.
     * 
     */
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     *
     * This is a setter for width. 
     * 
     * @param score is int value.
     * 
     */
    public void setScore(int score) {
        this.score = score;
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
     * This is a getter for question. 
     * 
     * @return String question value.
     * 
     */
    public String getQuestion() {
        return question;
    }
    
    /**
     *
     * This is a getter for answer. 
     * 
     * @return String answer value.
     * 
     */
    public String getAnswer() {
        return answer;
    }

    /**
     *
     * This is a getter for difficultyLevel. 
     * 
     * @return int difficultyLevel value.
     * 
     */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     *
     * This is a getter for difficultyLevel. 
     * 
     * @return int score value.
     * 
     */
    public int getScore() {
        return score;
    }
    
    
    
    
}
