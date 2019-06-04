/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 * This is a Image class responsible for Data Model for Image. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class Image {
    
    private int id;
    private String question;
    private String source;
    private String xy;
    private int width;
    private int height;
    private int difficultyLevel;
    private int score;
    
    /**
     *
     * This is a parametric constructor. 
     * 
     * @param id is a int that value that will uniquely identify record.
     * @param question is a String value that will contain question.
     * @param source is a String value that will contain image source.
     * @param xy is a String value that will contain coordinates.
     * @param width is a int value that will contain width of the object.
     * @param height is a int value that will contain height of the object.
     * @param difficultyLevel is a int value that will contain difiiculty level of the question.
     * @param score is a int value that will contain worth of the question.
     */
    public Image(int id, String question, String source, String xy, int width, int height, int difficultyLevel, int score) {
        this.id = id;
        this.question = question;
        this.source = source;
        this.xy = xy;
        this.width = width;
        this.height = height;
        this.difficultyLevel = difficultyLevel;
        this.score = score;
    }
    
    /**
     *
     * This is a parametric constructor. 
     *
     */
    public Image() {
        this.id = 0;
        this.question = null;
        this.source = null;
        this.xy = null;
        this.width = 0;
        this.height = 0;
        this.difficultyLevel = 0;
        this.score = 0;
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
     * This is a getter for source. 
     * 
     * @return String source value.
     * 
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * This is a getter for xy. 
     * 
     * @return String xy value.
     * 
     */
    public String getXy() {
        return xy;
    }

    /**
     *
     * This is a getter for width. 
     * 
     * @return int width value.
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * This is a getter for height. 
     * 
     * @return int height value.
     * 
     */
    public int getHeight() {
        return height;
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
     * This is a setter for source. 
     * 
     * @param source is string value.
     * 
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * This is a setter for xy. 
     * 
     * @param xy is string value.
     * 
     */
    public void setXy(String xy) {
        this.xy = xy;
    }

    /**
     *
     * This is a setter for width. 
     * 
     * @param width is string value.
     * 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * This is a setter for width. 
     * 
     * @param height is int value.
     * 
     */
    public void setHeight(int height) {
        this.height = height;
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
