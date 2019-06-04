/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 * This is a Question class responsible for Data Model for Question. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class Question {
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private int difficultyLevel;
    private int score;

    /**
     *
     * This is a parametric constructor. 
     *
     */
    public Question() {
        this.id = 0;
        this.question = null;
        this.option1 = null;
        this.option2 = null;
        this.option3 = null;
        this.option4 = null;
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
     * @param option1 is a String value that will contain option1.
     * @param option2 is a String value that will contain option2.
     * @param option3 is a String value that will contain option3.
     * @param option4 is a String value that will contain option4.
     * @param answer is a String value that will contain answer.
     * @param difficultyLevel is a int value that will contain difiiculty level of the question.
     * @param score is a int value that will contain worth of the question.
     */
    public Question(int id, String question, String option1, String option2, String option3, String option4, String answer, int difficultyLevel, int score) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.difficultyLevel = difficultyLevel;
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
     * This is a getter for option1. 
     * 
     * @return String option1 value.
     * 
     */
    public String getOption1() {
        return option1;
    }

    /**
     *
     * This is a getter for option2. 
     * 
     * @return String option2 value.
     * 
     */
    public String getOption2() {
        return option2;
    }

    /**
     *
     * This is a getter for option3. 
     * 
     * @return String option3 value.
     * 
     */
    public String getOption3() {
        return option3;
    }

    /**
     *
     * This is a getter for option4. 
     * 
     * @return String option4 value.
     * 
     */
    public String getOption4() {
        return option4;
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
     * This is a setter for option1. 
     * 
     * @param option1 is string value.
     * 
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     *
     * This is a setter for option2. 
     * 
     * @param option2 is string value.
     * 
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }
    
    /**
     *
     * This is a setter for option3. 
     * 
     * @param option3 is string value.
     * 
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     *
     * This is a setter for option4. 
     * 
     * @param option4 is string value.
     * 
     */
    public void setOption4(String option4) {
        this.option4 = option4;
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

}
