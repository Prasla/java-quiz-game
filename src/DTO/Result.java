/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 * This is a Result class responsible for Data Model for Result. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class Result {
    
    private int id;
    private String question;
    private String answer;
    private String response;
    private String type;
    private int score;
    private int mark;
    private boolean status;
    
    /**
     *
     * This is a parametric constructor. 
     * 
     * @param id is a int that will uniquely identify record.
     * @param question is a String that will contain question text.
     * @param answer is a String that will contain answer text.
     * @param response is a String that will contain response text.
     * @param type is a String that will contain type text.
     * @param score is a int that will contain score value.
     * @param mark is a int that will contain mark value.
     * @param status is a int that will contain status right or wrong.
     */
    public Result(int id, String question, String answer, String response, String type, int score, int mark, boolean status) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.response = response;
        this.type = type;
        this.score = score;
        this.mark = mark;
        this.status = status;
    }
    
    /**
     *
     * This is a parametric constructor. 
     * 
     * @param id is a int that will uniquely identify record.
     * @param question is a String that will contain question text.
     * @param answer is a String that will contain answer text.
     * @param response is a String that will contain response text.
     * @param type is a String that will contain type text.
     * @param score is a int that will contain score value.
     */
    public Result(int id, String question, String answer, String response, String type, int score) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.response = response;
        this.type = type;
        this.score = score;
        this.mark = 0;
        this.status = false;
    }
    
    /**
     *
     * This is a parametric constructor. 
     * 
     * @param id is a int that will uniquely identify record.
     * @param question is a String that will contain question text.
     * @param type is a String that will contain type text.
     * @param score is a int that will contain score value.
     */
    public Result(int id, String question, String type, int score) {
        this.id = id;
        this.question = question;
        this.answer = null;
        this.response = null;
        this.type = type;
        this.score = score;
        this.mark = 0;
        this.status = false;
    }
    
    /**
     *
     * This is a constructor. 
     * 
     */
    public Result() {
        this.id = 0;
        this.question = null;
        this.answer = null;
        this.response = null;
        this.type = null;
        this.score = 0;
        this.mark = 0;
        this.status = false;
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
     * This is a getter for response. 
     * 
     * @return String response value.
     * 
     */
    public String getResponse() {
        return response;
    }

    /**
     *
     * This is a getter for type. 
     * 
     * @return String type value.
     * 
     */
    public String getType() {
        return type;
    }

    /**
     *
     * This is a getter for mark. 
     * 
     * @return String mark value.
     * 
     */
    public int getMark() {
        return mark;
    }

    /**
     *
     * This is a getter for score. 
     * 
     * @return String score value.
     * 
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * This is a getter for status. 
     * 
     * @return boolean status value.
     * 
     */
    public boolean isStatus() {
        return status;
    }

    /**
     *
     * This is a setter for id. 
     *
     * @param id is a int value.
     * 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * This is a setter for question. 
     * 
     * 
     * @param question is a String value.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * This is a setter for answer. 
     * 
     * @param answer is a String value.
     * 
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     *
     * This is a setter for response. 
     * 
     * @param response is a String value.
     * 
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     *
     * This is a setter for type. 
     * 
     * @param type is a String value.
     * 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * This is a setter for mark. 
     * 
     * @param mark is a String value.
     * 
     */
    public void setMark(int mark) {
        this.mark = mark;
    }

    /**
     *
     * This is a setter for score. 
     * 
     * @param score is a int value.
     * 
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * This is a setter for status. 
     * 
     * @param status is a boolean status.
     * 
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

}
