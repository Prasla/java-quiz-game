/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import DAO.ImageDAO;
import DAO.ListeningDAO;
import DAO.WritingDAO;
import DAO.MathDAO;
import DAO.SpellingDAO;
import DTO.Question;
import DTO.Spelling;
import DTO.Image;
import DTO.Result;
import DTO.Writing;
import Sound.Sound;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.sound.sampled.Clip;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javafx.scene.media.MediaPlayer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * This is a game menu class responsible for quiz section layout, flow and logic. Alone with result options. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class gameMenu extends javax.swing.JFrame {
    
    private Timer timer;
    private Timer subTimer;
    private Timer tenSecTimer;
    private Timer fiveSecTimer;
    private Timer undoClueTimer;
    private Timer doNextTimer;
    
    private JOptionPane hintMessage;
    private JDialog hintDialog;
    private Clip alertClip;
    private MediaPlayer listeningMedia;
    
    private Question activeQuestion;
    private Spelling activeSpellingQuestion;
    private Writing activeWritingQuestion;
    private Image activeImageQuestion;
    
    private String questionType;
    private int questionId;
    private int qdl;
    private int questionCount;
    private int hintPopup;
    private int extraTimeTaken;
    private int x;
    private int y;
    private int checkSection;
    private int nextForWriting;
    
    //Final result
    int mathScore;
    int totalMathScore;
    int imageScore;
    int totalImageScore;
    int listeningScore;
    int totalListeningScore;
    int spellingScore;
    int totalSpellingScore;
    int writingScore;
    int totalWritingScore;
    //End
    
    ButtonGroup mathOptions;
    
    ArrayList<Result> resultList;
    ArrayList<Question> mathQuestions;
    ArrayList<Spelling> spellingQuestions;
    ArrayList<Question> listeningQuestions;
    ArrayList<Writing> writingQuestions;
    ArrayList<Image> imageQuestions;

    /**
     * Constructor for objects of new form gameMenu.
     */
    public gameMenu() {
        initComponents();
        conditionReset();
        
        mathOptions = new ButtonGroup();
        
        resultList = new ArrayList<Result>();
        
        mathQuestions = new ArrayList<Question>();

        spellingQuestions = new ArrayList<Spelling>();
        
        listeningQuestions = new ArrayList<Question>();
        
        writingQuestions = new ArrayList<Writing>();
        
        imageQuestions = new ArrayList<Image>();
        
        timer = new Timer(1000 , taskPerformer);
        subTimer =  new Timer(1000 , subTimerPerformer);
        tenSecTimer = new Timer(10000 , tenSecTimerPerformer);
        fiveSecTimer = new Timer(5000 , fiveSecTimerPerformer);
        undoClueTimer = new Timer(4000, undoClueTimerPerformer);
        doNextTimer = new Timer(10000, doNextTimerPerformer);

        hintMessage = new JOptionPane();   
        
        x = 0;
        y = 0;
        checkSection = 0;
        nextForWriting = 0;
        
        try {
            alertClip = Sound.alert();
        } catch (Exception e){
            JOptionPane.showMessageDialog(jPanel2,
                e,
                "Error - Alert Sound",
                JOptionPane.ERROR_MESSAGE);
        }
        
        
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        jPanel5.setVisible(false);
        jPanel6.setVisible(false);
        jPanel7.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        
        timer.start();
        com.sun.javafx.application.PlatformImpl.startup(()->{});
    }
    
    /**
     * Thread function from over all quiz timer.
     */
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            
            String[] timerArr = jLabel1.getText().split(":");
            int min = Integer.parseInt(timerArr[0]);
            int sec = Integer.parseInt(timerArr[1]);
            
            
            if(sec == 0) {
                min--; sec = 59;
            } else {
                sec--;
            }
            
            if(min == 0 && sec == 0) {
                jLabel1.setText("0"+Integer.toString(min)+ ":0" +Integer.toString(sec));
                
                stopTimers();
                
                JOptionPane.showMessageDialog(jPanel1, "You 15 minutes is up." );
                
                switchToResult();   
                
            } else {
                if((sec > 9)) {
                    if(min > 9) {
                        jLabel1.setText(Integer.toString(min)+ ":" +Integer.toString(sec));
                    } else {
                        jLabel1.setText("0"+Integer.toString(min)+ ":" +Integer.toString(sec));
                    }
                } else {
                    if(min > 9) {
                        jLabel1.setText(Integer.toString(min)+ ":0" +Integer.toString(sec));
                    } else {
                        jLabel1.setText("0"+Integer.toString(min)+ ":0" +Integer.toString(sec));
                    }
                }
            }
            
            if(min == 0 && sec > 0) {
                if(sec%2 == 0) {
                    jLabel1.setForeground(Color.black);
                } else {
                    jLabel1.setForeground(Color.red);
                }
                alertClip.start();
                alertClip.loop(alertClip.LOOP_CONTINUOUSLY);
            }
            
        }
    };
    
    /**
     * Thread function from each section timer.
     */
    ActionListener subTimerPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            
            String[] timerArr = jLabel8.getText().split(":");
            int min = Integer.parseInt(timerArr[0]);
            int sec = Integer.parseInt(timerArr[1]);
            
            if(sec == 0) { min--; sec = 59; } else { sec--; }
            
            if(min == 0 && sec == 0) {
                alertClip.stop();
                jLabel8.setText("0"+Integer.toString(min)+ ":0" +Integer.toString(sec));
                if(questionType.equals("Math")) {
                    exitMathQuiz();
                } else if(questionType.equals("Spelling")) {
                    exitSpellingQuiz();
                } else if(questionType.equals("Listening")) {
                    exitListeningQuiz();
                } else if(questionType.equals("Writing")) {
                    exitWritingQuiz();
                } else if(questionType.equals("Image")) {
                    exitImageQuiz();
                }
            } else {
                if((sec > 9)) {
                    jLabel8.setText("0"+Integer.toString(min)+ ":" +Integer.toString(sec));
                } else {
                    jLabel8.setText("0"+Integer.toString(min)+ ":0" +Integer.toString(sec));
                }
            }
            
            if(min == 0 && sec > 0) {
                if(sec%2 == 0) {
                    jLabel8.setForeground(Color.black);
                } else {
                    jLabel8.setForeground(Color.red);
                }
                alertClip.start();
                alertClip.loop(alertClip.LOOP_CONTINUOUSLY);
            }
            
        }
    };

    /**
     * Thread function from giving hint after 10 Seconds in spelling section.
     */
    ActionListener tenSecTimerPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            tenSecTimer.stop();
            hintPopup = 1;
            String message = "Frist 3 letters are " + activeSpellingQuestion.getHint() +".";
            hintMessage.setMessage(message);
            hintDialog = hintMessage.createDialog(jPanel4, "Hint");
            fiveSecTimer.start();
            hintDialog.setModal(false);
            hintDialog.setVisible(true);
        }
    };
    
    /**
     * Thread function from remove hint pop-up after 5 Seconds in spelling section.
     */
    ActionListener fiveSecTimerPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            hintDialog.setVisible(false);
            fiveSecTimer.stop();
        }
    };
    
    /**
     * Thread function from remove clue highlighter after 4 Seconds in writing section.
     */
    ActionListener undoClueTimerPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            jTextPane1.getHighlighter().removeAllHighlights();
            undoClueTimer.stop();
            doNextTimer.start();
        }
    };
    
    /**
     * Thread function from do move to next question after 10 Seconds of clue in writing section.
     */
    ActionListener doNextTimerPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            checkWritingAnswerAfterClue();
            if(!loadWritingQuestion(writingQuestions)) {
                exitWritingQuiz();
            }
            doNextTimer.stop();           
        }
    };
    //End
    
    /**
     * This function will stop all the timers.
     */
    private void stopTimers() {
        alertClip.stop();
        alertClip.stop();
        if(listeningMedia != null) {
            listeningMedia.stop();
        }
        undoClueTimer.stop();
        doNextTimer.stop();
        fiveSecTimer.stop();
        tenSecTimer.stop();
        subTimer.stop();
        timer.stop();
    }
    
    /**
     * This function will help in switching from game menu and result menu.
     */
    private void switchToResult() {
        //Close all quiz panel and avtive Result
        jLabel8.setVisible(false);
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel8.setVisible(false);

        jPanel6.setVisible(true);   
    }
    
    /**
     * The function will manage score counter.
     *
     * @param score is the int that contain score to be added in score count.
     */
    private void score(int score) {
        int cs = Integer.parseInt(jLabel6.getText());
        cs += score;
        jLabel6.setText(Integer.toString(cs));
    }
    
    /**
     * This function will help in displaying time taken by each section took to finish.
     */
    private void displayTimeTaken() {
        String message = null;
        
        String[] timerArr = jLabel8.getText().split(":");
            int min = Integer.parseInt(timerArr[0]);
            int sec = Integer.parseInt(timerArr[1]);
            int minTaken = 0;
            int secTaken = 0;
        
        if(sec == 0) {
            if(extraTimeTaken == 1 && questionType.equals("Listening")) {
                minTaken = 5 - min;
            } else {
                minTaken = 3 - min;
            }
            
        } else {
            if(extraTimeTaken == 1 && questionType.equals("Listening")) {
                minTaken = 4 - min;
                secTaken = 60 - sec;
            } else {
                minTaken = 2 - min;
                secTaken = 60 - sec;
            }
            
        }
        
        if(secTaken > 9) {
            message = "You have taken 0" + minTaken + ":" + secTaken + " time to finish math section.";
        } else {
            message = "You have taken 0" + minTaken + ":0" + secTaken + " time to finish math section.";
        }
        
        JOptionPane.showMessageDialog(this, message);  
    }
    
    /**
     * This function will reset the layout element to default to game flow.
     */
    private void conditionReset() {
        activeQuestion = null;
        questionId = 0;
        qdl = 0;
        questionCount = 0;
        questionType = "none";
        jLabel8.setText("03:00");
        jLabel8.setForeground(Color.black);
        jLabel8.setVisible(false);
        jButton13.setVisible(false);
        jButton17.setVisible(false);
        extraTimeTaken = 0;   
        hintPopup = 0;
    }
    
    /**
     * This function will reset the layout element to default for result flow.
     */
    private void resultReset() {
        jLabel13.setText("");
        jButton27.setVisible(false);
        jButton28.setVisible(false);
        jPanel6.setVisible(false);
    }
    
    /**
     * This function will set logic element used by adaptive question algo.
     */
    private void setQuestionStatus(int id, int diffLevel, String type) {
        questionCount++;
        questionId = id;
        qdl = diffLevel == 3 ? 2 : diffLevel;
        questionType = type;
    }

    /**
     * This function will add extra time and enable play again button in listening section.
     */
    private void addExtraTime() {
        extraTimeTaken = 1;
        jButton13.setVisible(true);
        timer.stop();
        subTimer.stop();
        alertClip.stop();
        
        String[] timerArr = jLabel1.getText().split(":");
        int min = Integer.parseInt(timerArr[0]);
        int sec = Integer.parseInt(timerArr[1]);
        
        min += 2;
        
        String[] subTimerArr = jLabel8.getText().split(":");
        int subMin = Integer.parseInt(subTimerArr[0]);
        int subSec = Integer.parseInt(subTimerArr[1]);
        
        subMin += 2;
        
        int cs = Integer.parseInt(jLabel6.getText());
        cs -= 5;
        
        
        jLabel6.setText(Integer.toString(cs));
        if(min < 9) {
            if(sec < 9) {
                jLabel1.setText("0"+Integer.toString(min)+ ":0" +Integer.toString(sec));
            } else {
                jLabel1.setText("0"+Integer.toString(min)+ ":" +Integer.toString(sec));
            }
        } else {
            if(sec < 9) {
                jLabel1.setText(Integer.toString(min)+ ":0" +Integer.toString(sec));
            } else {
                jLabel1.setText(Integer.toString(min)+ ":" +Integer.toString(sec));
            }
        }
        if(subSec < 9) {
            jLabel8.setText("0"+Integer.toString(subMin)+ ":0" +Integer.toString(subSec));
        } else {
            jLabel8.setText("0"+Integer.toString(subMin)+ ":" +Integer.toString(subSec));
        }
        
        timer.start();
        subTimer.start();
    }
 
    /**
     * This function will check for eligibility of extra time button.
     */
    private void checkForExtraTime() {
        int cs = Integer.parseInt(jLabel6.getText());
        if(cs > 9) {
            jButton17.setVisible(true);
        }
    }

    /**
     * This function will return text value of selected radio option for math sections.
     * 
     * @param buttonGroup is a ButtonGroup with grouped radio option for math section.
     * @return String that will have the user selected answer for math section questions.
     */
    public String getSelectedAnswer(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
    //End
    
    /**
     * This function will check for writing mistake made by user during writing test.
     */
    private void checkForWirtingMistake() {
        
        if(nextForWriting == 0) {
            DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
            String[] words = jTextPane1.getText().split(" ");
            String[] answerWords = activeWritingQuestion.getAnswer().split(" ");
            int lenght = (answerWords.length > words.length) ? words.length :  answerWords.length;
            int mistake = 0;

            for(int i=0; i<lenght; i++) {
                if(!words[i].equals(answerWords[i])) {
                    mistake++;
                    int startPos = jTextPane1.getText().indexOf(words[i]);
                    int endPos = startPos + words[i].length();

                    try {
                        jTextPane1.getHighlighter().addHighlight(startPos, endPos, highlightPainter);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                            e,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            if(mistake == 0) {
                Result r = new Result(
                    activeWritingQuestion.getId(),
                    activeWritingQuestion.getQuestion(),
                    activeWritingQuestion.getAnswer(),
                    jTextPane1.getText(),
                    "Writing",
                    activeWritingQuestion.getScore(),
                    activeWritingQuestion.getScore(),
                    true

                );
                resultList.add(r);
                score(activeWritingQuestion.getScore());
                if(!loadWritingQuestion(writingQuestions)) {
                    exitWritingQuiz();
                }
            } else if(lenght == 1 && words[0].equals("")) {
                Result r = new Result(
                    activeWritingQuestion.getId(),
                    activeWritingQuestion.getQuestion(),
                    activeWritingQuestion.getAnswer(),
                    jTextPane1.getText(),
                    "Writing",
                    activeWritingQuestion.getScore(),
                    0,
                    false
                );
                resultList.add(r);
                if(!loadWritingQuestion(writingQuestions)) {
                    exitWritingQuiz();
                }
            } else if(lenght < answerWords.length) {
                JOptionPane.showMessageDialog(jPanel7,
                    "Sentence is incomplete.", "Error", JOptionPane.ERROR_MESSAGE);
                    //jButton19.setEnabled(false);
                    nextForWriting = 1;
                    undoClueTimer.start();
            } else {
                //jButton19.setEnabled(false);
                nextForWriting = 1;
                undoClueTimer.start();
            }
        } else {
            undoClueTimer.stop();
            doNextTimer.stop();
            checkWritingAnswerAfterClue();
            if(!loadWritingQuestion(writingQuestions)) {
                exitWritingQuiz();
            }
        }
    }
    
    /**
     * This function will check for writing answer after clue in writing test.
     */
    private void checkWritingAnswerAfterClue() {
        Result r = new Result(
                activeWritingQuestion.getId(),
                activeWritingQuestion.getQuestion(),
                activeWritingQuestion.getAnswer(),
                jTextPane1.getText(),
                "Writing",
                activeWritingQuestion.getScore()            
            );
        if(jTextPane1.getText().equals(activeWritingQuestion.getAnswer())) {
            r.setMark(activeWritingQuestion.getScore()/2);
            r.setStatus(true);
            score(activeWritingQuestion.getScore()/2);
        } else {
            r.setMark(0);
            r.setStatus(false);
            qdl = activeWritingQuestion.getDifficultyLevel() == 1 ? 0 : activeWritingQuestion.getDifficultyLevel() - 2;
        }
        resultList.add(r);
    }
    
    /**
     * This function will check for listening answer after clue in listening test.
     */
    private void checkListingAnswer() {
        String[] answerArray = activeQuestion.getAnswer().split(",");
        int right = 0;
        int checked = 0;
        
        String rightAnswer = "";
        String response = "";
        
        for (String answerArray1 : answerArray) {
            if (answerArray1.equals("1") && jCheckBox1.isSelected()) {
                right++;
                rightAnswer += jCheckBox1.getText();
            }
            if (answerArray1.equals("2") && jCheckBox2.isSelected()) {
                right++;
                rightAnswer += ("/" + jCheckBox2.getText());
            }
            if (answerArray1.equals("3") && jCheckBox3.isSelected()) {
                right++;
                rightAnswer += ("/" + jCheckBox3.getText());
            }
            if (answerArray1.equals("4") && jCheckBox4.isSelected()) {
                right++;
                rightAnswer += ("/" + jCheckBox4.getText());
            }
        }
        
        if(jCheckBox1.isSelected()){
            checked++;
            response += jCheckBox1.getText();
        } 
        
        if(jCheckBox2.isSelected()){
            checked++;
            response += ("/" + jCheckBox2.getText());
        } 
        
        if(jCheckBox3.isSelected()){
            checked++;
            response += ("/" + jCheckBox3.getText());
        } 
        
        if(jCheckBox4.isSelected()){
            checked++;
            response += ("/" + jCheckBox4.getText());
        } 
        
        Result r = new Result(
            activeQuestion.getId(),
            activeQuestion.getQuestion(),
            rightAnswer,
            response,
            "Listening",
            activeQuestion.getScore()
        );
        if(right == answerArray.length && checked == right) {
            score(activeQuestion.getScore());
            r.setMark(activeQuestion.getScore());
            r.setStatus(true);
        } else {
            qdl = activeQuestion.getDifficultyLevel() == 1 ? 0 : activeQuestion.getDifficultyLevel() - 2;
            r.setMark(0);
            r.setStatus(false);
        }
        resultList.add(r);
    }

    /**
     * This function will check for spelling answer after clue in spelling test.
     */
    private void checkSpellingAnswer() {
        String answer = jTextField1.getText().trim().toLowerCase();
        Result r = new Result(
            activeSpellingQuestion.getId(),
            activeSpellingQuestion.getQuestion(),
            activeSpellingQuestion.getAnswer(),
            answer,
            "Spelling",
            activeSpellingQuestion.getScore()
        );
        if(answer.length() > 0) {
            if(answer.equals(activeSpellingQuestion.getAnswer())) {
                if(hintPopup == 1) {
                    score(activeSpellingQuestion.getScore()/2);
                    r.setMark(activeSpellingQuestion.getScore()/2);
                } else {
                    score(activeSpellingQuestion.getScore());
                    r.setMark(activeSpellingQuestion.getScore());
                }
                
                r.setStatus(true);
            } else {
                qdl = activeSpellingQuestion.getDifficultyLevel() == 1 ? 0 : activeSpellingQuestion.getDifficultyLevel() - 2;
                r.setMark(0);
                r.setStatus(false);
            }
        }
        resultList.add(r);
    }
    
    /**
     * This function will check for math answer in math test.
     * 
     * @return selection is string containing user selection option. 
     * @return answer is string containing correct answer for maths question.
     */
    private void checkMathAnwser(String selection, String answer) {
        Result r = new Result(
            activeQuestion.getId(),
            activeQuestion.getQuestion(),
            answer,
            selection,
            "Math",
            activeQuestion.getScore()
        );
        if(selection.equals(answer)) {
            score(activeQuestion.getScore());
            r.setMark(activeQuestion.getScore());
            r.setStatus(true);
        } else {
            qdl = activeQuestion.getDifficultyLevel() == 1 ? 0 : activeQuestion.getDifficultyLevel() - 2;
            r.setMark(0);
            r.setStatus(false);
        }
        resultList.add(r);
    }
    
    /**
     * This function will check answer for image object selection.
     * 
     */
    private void checkImageAnswer() {
        
        Result r = new Result(
            activeImageQuestion.getId(),
            activeImageQuestion.getQuestion(),
            "Image",
            activeImageQuestion.getScore()
        );
        
        String[] xy = activeImageQuestion.getXy().split(",");
        int objectX = Integer.parseInt(xy[0]);
        int objectY = Integer.parseInt(xy[1]);
        
        if((x >= objectX && x <= objectX+activeImageQuestion.getWidth()) && (y >= objectY && y <= objectY+activeImageQuestion.getHeight())) {
            score(activeImageQuestion.getScore());
            r.setMark(activeImageQuestion.getScore());
            r.setStatus(true);
        } else {
            qdl = activeImageQuestion.getDifficultyLevel() == 1 ? 0 : activeImageQuestion.getDifficultyLevel() - 2;
            r.setMark(0);
            r.setStatus(false);
        }
        resultList.add(r);
    }

    
    /**
     * This function will implement adaptive question algo logic for math questions.
     * 
     * @param list is a ArrayList<Question> that contain all the math questions.
     * 
     */
    private boolean loadMathQuestion(ArrayList<Question> list) {
        mathOptions.clearSelection();
        String type = "Math";
        int index = 0;
        while(list.size() > index) {
            if(questionCount == 5) { 
                return false; 
            } else {
                activeQuestion = list.get(index);
                if(activeQuestion.getDifficultyLevel() > qdl && (questionId != activeQuestion.getId() || !type.equals(questionType))) {
                    jLabel3.setText(activeQuestion.getQuestion());
                    jRadioButton1.setText(activeQuestion.getOption1());
                    jRadioButton2.setText(activeQuestion.getOption2());
                    jRadioButton3.setText(activeQuestion.getOption3());
                    jRadioButton4.setText(activeQuestion.getOption4());
                    setQuestionStatus(activeQuestion.getId(), activeQuestion.getDifficultyLevel(), type);
                    list.remove(activeQuestion);
                    return true;
                } else {
                    index++;
                }
            }
        }
        return false;
    }

    /**
     * This function will implement adaptive question algo logic for math questions.
     * 
     * @param list is a ArrayList<Image> that contain all the math questions.
     * 
     */
    private boolean loadImageQuestion(ArrayList<Image> list) {
        jButton10.setEnabled(false);
        String type = "Image";
        int index = 0;
        while(list.size() > index) {
            if(questionCount == 5) { 
                return false; 
            } else {
                activeImageQuestion = list.get(index);
                if(activeImageQuestion.getDifficultyLevel() > qdl && (questionId != activeImageQuestion.getId() || !type.equals(questionType))) {
                    jLabel4.setText(activeImageQuestion.getQuestion());
                    try {
                        BufferedImage image = ImageIO.read(new File("images/"+activeImageQuestion.getSource()));
                        jLabel9.setIcon(new ImageIcon(image));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                            e,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    setQuestionStatus(activeImageQuestion.getId(), activeImageQuestion.getDifficultyLevel(), type);
                    list.remove(activeImageQuestion);
                    return true;
                } else {
                    index++;
                }
            }
        }
        return false;
    }
    
    /**
     * This function will implement adaptive question algo logic for Image questions.
     * 
     * @param list is a ArrayList<Spelling> that contain all the Image questions.
     * 
     */
    private boolean loadSpellingQuestion(ArrayList<Spelling> list) {
        String type = "Spelling";
        int index = 0;
        hintPopup = 0;
        jTextField1.setText("");
        while(list.size() > index) {
            if(questionCount == 5) { 
                return false; 
            } else {
                activeSpellingQuestion = list.get(index);
                if(activeSpellingQuestion.getDifficultyLevel() > qdl && (questionId != activeSpellingQuestion.getId() || !type.equals(questionType))) {
                    tenSecTimer.start();
                    jLabel7.setText(activeSpellingQuestion.getQuestion());
                    setQuestionStatus(activeSpellingQuestion.getId(), activeSpellingQuestion.getDifficultyLevel(), type);
                    list.remove(activeSpellingQuestion);
                    return true;
                } else {
                    index++;
                }
            }
        }
        return false;
    }
    
    /**
     * This function will implement adaptive question algo logic for Writing questions.
     * 
     * @param list is a ArrayList<Writing> that contain all the Writing questions.
     * 
     */
    private boolean loadWritingQuestion(ArrayList<Writing> list) {
        String type = "Writing";
        int index = 0;
        //jButton19.setEnabled(true);
        nextForWriting = 0;
        jTextPane1.setText("");
        while(list.size() > index) {
            if(questionCount == 3) { 
                return false; 
            } else {
                activeWritingQuestion = list.get(index);
                if(activeWritingQuestion.getDifficultyLevel() > qdl && (questionId != activeWritingQuestion.getId() || !type.equals(questionType))) {
                    jLabel11.setText(activeWritingQuestion.getQuestion());
                    setQuestionStatus(activeWritingQuestion.getId(), activeWritingQuestion.getDifficultyLevel(), type);
                    list.remove(activeWritingQuestion);
                    return true; 
                } else {
                    index++;
                }
            }
        }
        return false; 
    }
    
    /**
     * This function will implement adaptive question algo logic for listening questions.
     * 
     * @param list is a ArrayList<Question> that contain all the listening questions.
     * 
     */
    private boolean loadListeningQuestion(ArrayList<Question> list) {
        String type = "Listening";
        int index = 0;
        
        jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);
        jCheckBox3.setSelected(false);
        jCheckBox4.setSelected(false);
        
        while(list.size() > index) {
            if(questionCount == 3) { 
                return false; 
            } else {
                activeQuestion = listeningQuestions.get(index);
                if(activeQuestion.getDifficultyLevel() > qdl && (questionId != activeQuestion.getId() || !type.equals(questionType))) {
                    setQuestionStatus(activeQuestion.getId(), activeQuestion.getDifficultyLevel(), type);
                    jCheckBox1.setText(activeQuestion.getOption1());
                    jCheckBox2.setText(activeQuestion.getOption2());
                    jCheckBox3.setText(activeQuestion.getOption3());
                    jCheckBox4.setText(activeQuestion.getOption4());
                    try {
                        listeningMedia = Sound.playTest(activeQuestion.getQuestion());
                        listeningMedia.play();
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(this,
                            e,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    list.remove(activeQuestion);
                    return true; 
                } else {
                    index++;
                }
            }
        }
        return false;
    }
    
    /**
     * This function will help is checking switch condition for Game menu and Result menu.
     * 
     */
    private void checkForSwitch() {
        if(checkSection == 5) {
            stopTimers();
            switchToResult();
        }
    }
    
    /**
     * This function will help in exit for quiz by stopping timers.
     * 
     */
    private void exitQuiz() {
        alertClip.stop();
        subTimer.stop();
        timer.stop();
        displayTimeTaken();
        timer.start();
    }
    
    /**
     * This function will help in exit for spelling quiz by switching panels.
     * 
     */
    private void exitSpellingQuiz() {
        tenSecTimer.stop();
        exitQuiz();
        conditionReset();
        jButton3.setEnabled(false);
        jPanel4.setVisible(false);
        jPanel1.setVisible(true);
        checkForSwitch();
    }
    
    /**
     * This function will help in exit for math quiz by switching panels.
     * 
     */
    private void exitMathQuiz() {
        exitQuiz();
        conditionReset();
        jButton1.setEnabled(false);
        jPanel2.setVisible(false);
        jPanel1.setVisible(true);
        checkForSwitch();
    }
    
    /**
     * This function will help in exit for listening quiz by switching panels.
     * 
     */
    private void exitListeningQuiz() {
        listeningMedia.stop();
        exitQuiz();
        conditionReset();
        jButton4.setEnabled(false);
        jPanel5.setVisible(false);
        jPanel1.setVisible(true);
        checkForSwitch();
    }
    
    /**
     * This function will help in exit for writing quiz by switching panels.
     * 
     */
    private void exitWritingQuiz() {
        exitQuiz();
        undoClueTimer.stop();
        doNextTimer.stop();
        conditionReset();
        jButton5.setEnabled(false);
        jPanel7.setVisible(false);
        jPanel1.setVisible(true);
        checkForSwitch();
    }
    
    /**
     * This function will help in exit for image quiz by switching panels.
     * 
     */
    private void exitImageQuiz() {
        exitQuiz();
        conditionReset();
        jButton2.setEnabled(false);
        jPanel3.setVisible(false);
        jPanel1.setVisible(true);
        checkForSwitch();
    }
    
    /**
     * This function will create pie chart using jFreechart api for overall score.
     * 
     * @return ChartPanel is a object panel which contain pie chart.
     */
    private ChartPanel pieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        dataset.setValue( "Math" , new Double( mathScore ) );  
        dataset.setValue( "Image" , new Double( imageScore ) );   
        dataset.setValue( "Spelling" , new Double( spellingScore ) );    
        dataset.setValue( "Writing" , new Double( writingScore ) ); 
        dataset.setValue( "Listening" , new Double( listeningScore ) );

        // based on the dataset we create the chart
        JFreeChart chart = ChartFactory.createPieChart(
            "Overall Result",                  
            dataset,                
            false,                  
            true,
            false
        );
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setMaximumSize(new Dimension(390, 250));
        chartPanel.setPreferredSize(new Dimension(390, 250));
        chartPanel.setLocation(0, 0);
        
        return chartPanel;
    }
    
    /**
     * This function will create pie chart using jFreechart api for overall score.
     * 
     * @return ChartPanel is a object panel which contain pie chart.
     */
    private ChartPanel barChart() {
        
        final String series1 = "Obtain";
        final String series2 = "Total";
        
        final String section1 = "Math";
        final String section2 = "Image";
        final String section3 = "Spelling";
        final String section4 = "Listening";
        final String section5 = "Writing";

        
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset();
        
        dataset.addValue(mathScore, series1, section1);
        dataset.addValue(imageScore, series1, section2);
        dataset.addValue(spellingScore, series1, section3);
        dataset.addValue(listeningScore, series1, section4);
        dataset.addValue(writingScore, series1, section5);
        
        dataset.addValue(totalMathScore, series2, section1);
        dataset.addValue(totalImageScore, series2, section2);
        dataset.addValue(totalSpellingScore, series2, section3);
        dataset.addValue(totalListeningScore, series2, section4);
        dataset.addValue(totalWritingScore, series2, section5);

        final JFreeChart chart = ChartFactory.createBarChart("Obtain/Total per section",
                "",
            "Score", // range axis label
            dataset, // data
            PlotOrientation.VERTICAL, // orientation
            false, // include legend
            true, // tooltips?
            false // URLs?
        );
        
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setMaximumSize(new Dimension(390, 250));
        chartPanel.setPreferredSize(new Dimension(390, 250));
        chartPanel.setLocation(0, 0);
        
        return chartPanel;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel6 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jButton26 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(350, 150));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 450, 400));
        setMaximumSize(new java.awt.Dimension(32767, 32767));
        setPreferredSize(new java.awt.Dimension(450, 400));
        setResizable(false);
        setSize(new java.awt.Dimension(450, 400));

        jLabel1.setText("10:00");

        jLabel2.setText("Time Left :");

        jLabel5.setText("Score:");

        jLabel6.setText("00");

        jLabel8.setText("03:00");

        jButton6.setText("Quit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setLocation(0,0);
        jPanel1.setPreferredSize(new java.awt.Dimension(390, 285));
        this.pack();

        jButton5.setText("Writing");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton1.setText("Math");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Image recognizing ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("listening");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Spelling");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(125, 125, 125))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addGap(25, 25, 25)
                .addComponent(jButton2)
                .addGap(25, 25, 25)
                .addComponent(jButton3)
                .addGap(25, 25, 25)
                .addComponent(jButton4)
                .addGap(25, 25, 25)
                .addComponent(jButton5)
                .addGap(35, 35, 35))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel2.setLocation(0,0);
        this.pack();

        jLabel3.setText("Maths");

        jRadioButton1.setText("Option A");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Option B");

        jRadioButton3.setText("Option C");

        jRadioButton4.setText("Option D");

        jButton7.setText("Next");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Done");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton8)
                        .addGap(0, 248, Short.MAX_VALUE)
                        .addComponent(jButton7))
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addGap(15, 15, 15))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel3.setLocation(0,0);
        this.pack();

        jLabel4.setText("Select all the triangle?");
        jLabel4.setMaximumSize(new java.awt.Dimension(360, 50));
        jLabel4.setMinimumSize(new java.awt.Dimension(90, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(360, 35));

        jButton9.setText("Done");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Next");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel9.setAlignmentY(0.0F);
        jLabel9.setMaximumSize(new java.awt.Dimension(360, 175));
        jLabel9.setPreferredSize(new java.awt.Dimension(360, 175));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton10))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addGap(15, 15, 15))
        );

        jPanel4.setFocusCycleRoot(true);
        jPanel4.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel4.setLocation(0,0);
        this.pack();

        jLabel7.setText("Spelling");
        jLabel7.setMaximumSize(new java.awt.Dimension(360, 70));
        jLabel7.setMinimumSize(new java.awt.Dimension(360, 70));
        jLabel7.setPreferredSize(new java.awt.Dimension(360, 70));

        jButton11.setText("Done");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Next");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jTextField1.setFocusCycleRoot(true);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton12)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton12))
                .addGap(15, 15, 15))
        );

        jPanel5.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel5.setLocation(0, 0);
        this.pack();

        jLabel10.setText("<html><p>Select all the keyword(s) from the given clip?</p></html>");
        jLabel10.setPreferredSize(new java.awt.Dimension(253, 40));

        jButton15.setText("Done");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Next");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("Extra Time");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton13.setText("Play again");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("jCheckBox1");
        jCheckBox1.setMaximumSize(new java.awt.Dimension(360, 23));
        jCheckBox1.setPreferredSize(new java.awt.Dimension(360, 23));

        jCheckBox2.setText("jCheckBox2");
        jCheckBox2.setMaximumSize(new java.awt.Dimension(360, 23));
        jCheckBox2.setPreferredSize(new java.awt.Dimension(360, 23));

        jCheckBox3.setText("jCheckBox3");
        jCheckBox3.setMaximumSize(new java.awt.Dimension(360, 23));
        jCheckBox3.setPreferredSize(new java.awt.Dimension(360, 23));

        jCheckBox4.setText("jCheckBox4");
        jCheckBox4.setMaximumSize(new java.awt.Dimension(360, 23));
        jCheckBox4.setPreferredSize(new java.awt.Dimension(360, 23));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16))
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton16)
                    .addComponent(jButton17))
                .addGap(15, 15, 15))
        );

        jPanel7.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel7.setLocation(0, 0);
        this.pack();

        jLabel11.setText("Writing");
        jLabel11.setMaximumSize(new java.awt.Dimension(360, 70));
        jLabel11.setPreferredSize(new java.awt.Dimension(360, 70));

        jButton18.setText("Done");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText("Next");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jTextPane1.setPreferredSize(new java.awt.Dimension(360, 100));
        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jButton18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton19))
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18)
                    .addComponent(jButton19))
                .addGap(15, 15, 15))
        );

        jPanel6.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel6.setLocation(0,0);
        this.pack();

        jButton14.setText("Math Result");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton20.setText("Image Result");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setText("Spelling Result");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setText("Over All Result");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("Listening Result");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setText("Writing Result");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Result");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(135, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(15, 15, 15)
                .addComponent(jButton14)
                .addGap(15, 15, 15)
                .addComponent(jButton20)
                .addGap(15, 15, 15)
                .addComponent(jButton21)
                .addGap(15, 15, 15)
                .addComponent(jButton23)
                .addGap(15, 15, 15)
                .addComponent(jButton24)
                .addGap(15, 15, 15)
                .addComponent(jButton22)
                .addGap(15, 15, 15))
        );

        jPanel8.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel8.setLocation(0, 0);
        this.pack();

        jButton25.setText("Back");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("<html>\n<table>\n<tr>\n<th>Q. No</th>\n</tr>\n</table>\n</html>");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel13.setPreferredSize(new java.awt.Dimension(360, 220));

        jButton27.setText("Pie Chart");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setText("Bar Chart");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton28))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25)
                    .addComponent(jButton27)
                    .addComponent(jButton28))
                .addGap(15, 15, 15))
        );

        jPanel9.setPreferredSize(new java.awt.Dimension(390, 285));
        jPanel9.setLocation(0, 0);
        this.pack();

        jButton26.setText("Back");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jButton26)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(jButton26)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel1))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This is a event handler for math quiz button.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here: Math
        conditionReset();
        checkSection++;
        jPanel1.setVisible(false);
        jButton8.setVisible(false);

        mathOptions.add(jRadioButton1);
        mathOptions.add(jRadioButton2);
        mathOptions.add(jRadioButton3);
        mathOptions.add(jRadioButton4);
        
        mathQuestions = MathDAO.getAllQuestion();
        loadMathQuestion(mathQuestions);
        
        subTimer.start();
        jPanel2.setVisible(true);
        jLabel8.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * This is a event handler for writing quiz button.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here: Writing
        conditionReset();
        checkSection++;
        jPanel1.setVisible(false);
        jButton18.setVisible(false);
        
        writingQuestions = WritingDAO.getAllQuestion();
        loadWritingQuestion(writingQuestions);
        
        subTimer.start();
        jPanel7.setVisible(true);
        jLabel8.setVisible(true);
             
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * This is a event handler for image quiz button.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here: Image recognizing 
        
        conditionReset();
        checkSection++;
        jPanel1.setVisible(false);
        jButton9.setVisible(false);
        jButton10.setEnabled(false);
        
        jLabel9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!jButton10.isEnabled()) {
                    jButton10.setEnabled(true);
                }
                x = e.getX();
                y = e.getY();
            }
        });

        imageQuestions = ImageDAO.getAllQuestion();
        loadImageQuestion(imageQuestions);

        subTimer.start();
        jPanel3.setVisible(true);
        jLabel8.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * This is a event handler for Spelling quiz button.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here: Spelling
        conditionReset();
        checkSection++;
        jPanel1.setVisible(false);
        jButton11.setVisible(false);
        
        jTextField1.setFocusable(true);
        spellingQuestions = SpellingDAO.getAllQuestion();        
        loadSpellingQuestion(spellingQuestions);

        subTimer.start();
        jPanel4.setVisible(true);
        jLabel8.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * This is a event handler for Listening quiz button.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here: listening
        conditionReset();
        checkSection++;
        checkForExtraTime();
        
        jPanel1.setVisible(false);
        listeningQuestions = ListeningDAO.getAllQuestion(); 
        loadListeningQuestion(listeningQuestions);
        
        subTimer.start();
        jButton15.setVisible(false);
        jPanel5.setVisible(true);
        jLabel8.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * This is a event handler for Quit button for Math Section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here: Finish Button For Math Quiz
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure to finish math section?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            exitMathQuiz();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * This is a event handler for Next button for Math Section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here: Next Button For Math Quiz
        if (!jButton8.isVisible()) {
            jButton8.setVisible(true);
        }
        if(getSelectedAnswer(mathOptions) != null) {
            checkMathAnwser(getSelectedAnswer(mathOptions), activeQuestion.getAnswer());                
        } else {
            checkMathAnwser("", activeQuestion.getAnswer()); 
        }
        
        if(!loadMathQuestion(mathQuestions)) {
            exitMathQuiz();
        }
    }//GEN-LAST:event_jButton7ActionPerformed


    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    /**
     * This is a event handler for Quit button for Image Section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here: Image recognizing finish
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure to finish image recognizing section?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            exitImageQuiz();
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    /**
     * This is a event handler for Next button for Image Section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here: Image recognizing next 
        if(!jButton9.isVisible()) {
            jButton9.setVisible(true);
        }
        
        checkImageAnswer();
        
        if(!loadImageQuestion(imageQuestions)) {
            exitImageQuiz();
        }
         
    }//GEN-LAST:event_jButton10ActionPerformed
    
    /**
     * This is a event handler for Quit button for Spelling Section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here: Spelling finish
        tenSecTimer.stop();
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure to finish spelling section?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            exitSpellingQuiz();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    /**
     * This is a event handler for Next button for Spelling Section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here: Spelling next
        tenSecTimer.stop();
        
        if (!jButton11.isVisible()) {
            jButton11.setVisible(true);
        }

        //Check answer and update score if corrent else update algo logic
        checkSpellingAnswer();
        
        //Load Next Question
        if(!loadSpellingQuestion(spellingQuestions)) {
            exitSpellingQuiz();
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    /**
     * This is a event handler for Quit button for Game Menu.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here: Quit
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure to quit?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            stopTimers();
            
            setVisible(false);
            
            Menu m = new Menu();
            m.setVisible(true);
            
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * This is a event handler for Next button for Listening section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here: Listening Next        
        listeningMedia.stop();
        
        if(!jButton15.isVisible()) {
            jButton15.setVisible(true);
        }
        
        if(jButton13.isVisible()) {
            jButton13.setEnabled(true);
        }

        checkListingAnswer();
        
        //Load Next Question
        if(!loadListeningQuestion(listeningQuestions)) {
            exitListeningQuiz();
        }      
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * This is a event handler for Quit button for Listening section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here: Listening Finish
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure to finish listening section?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            exitListeningQuiz();
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    /**
     * This is a event handler for Extra time button for Listening section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here: Listening Extra time
        jButton17.setEnabled(false);
        addExtraTime();
    }//GEN-LAST:event_jButton17ActionPerformed

    /**
     * This is a event handler for Next button for Writing section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here: Writing Next
        if(!jButton18.isVisible()) {
            jButton18.setVisible(true);
        }

        checkForWirtingMistake();        
    }//GEN-LAST:event_jButton19ActionPerformed

    /**
     * This is a event handler for Quit button for Writing section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here: Writing Finish
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure to finish listening section?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            exitWritingQuiz();
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * This is a event handler for Play Again button for Listening section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here: Play Again
        try {
            jButton13.setEnabled(false);
            listeningMedia.stop();
            listeningMedia.play();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this,
                e,
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton13ActionPerformed


    /**
     * This is a event handler for Overall Result button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here: Overall
        resultReset();
        mathScore = 0;
        totalMathScore = 0;
        
        imageScore = 0;
        totalImageScore = 0;
        
        listeningScore = 0;
        totalListeningScore = 0;
        
        spellingScore = 0;
        totalSpellingScore = 0;
        
        writingScore = 0;
        totalWritingScore = 0;
        
        for(Result r: resultList) {
            switch (r.getType()) {
                case "Math":
                    mathScore += r.getMark();
                    totalMathScore += r.getScore();
                    break;
                case "Image":
                    imageScore += r.getMark();
                    totalImageScore += r.getScore();
                    break;
                case "Spelling":
                    spellingScore += r.getMark();
                    totalSpellingScore += r.getScore();
                    break;
                case "Writing":
                    writingScore += r.getMark();
                    totalWritingScore += r.getScore();
                    break;
                case "Listening":
                    listeningScore += r.getMark();
                    totalListeningScore += r.getScore();
                    break; 
                default:
                    break;
            }
        }
        
        int  total = Integer.parseInt(jLabel6.getText());

        String htmlTable = "<html><table border='1' width='100%' ><tr>"+
                "<th>Section</th>"+
                "<th>Total Mark</th>"+
                "<th>Obtain Mark</th>"+
            "</tr>"+
            "<tr>"+
                "<td>Math</td>"+
                "<td>"+totalMathScore+"</td>"+
                "<td>"+mathScore+"</td>"+
            "</tr>"+
            "<tr>"+
                "<td>Image</td>"+
                "<td>"+totalImageScore+"</td>"+
                "<td>"+imageScore+"</td>"+
            "</tr>"+
                "<tr>"+
                "<td>Listening</td>"+
                "<td>"+totalListeningScore+"</td>"+
                "<td>"+listeningScore+"</td>"+
            "</tr>"+
                "<tr>"+
                "<td>Spelling</td>"+
                "<td>"+totalSpellingScore+"</td>"+
                "<td>"+spellingScore+"</td>"+
            "</tr>"+
                "<tr>"+
                "<td>Writing</td>"+
                "<td>"+totalWritingScore+"</td>"+
                "<td>"+writingScore+"</td>"+
            "</tr>"+
            "</tr>"+
                "<tr>"+
                "<td>Total</td>"+
                "<td>"+(totalWritingScore+totalSpellingScore+totalListeningScore+totalImageScore+totalMathScore)+"</td>"+
                "<td>"+total+"</td>"+
            "</tr>"+
        "</table></html>";

        jButton27.setVisible(true);
        jButton28.setVisible(true);
        jLabel13.setText(htmlTable);
        jPanel8.setVisible(true);
           
        
    }//GEN-LAST:event_jButton22ActionPerformed

    /**
     * This is a event handler for Math Result button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here: Math
        resultReset();
        String htmlTable = "<html><table border='1' width='100%' ><tr>"+
                "<th>Q.No</th>"+
                "<th>Answer</th>"+
                "<th>Response</th>"+
                "<th>Total</th>"+
                "<th>Score</th>"+
                "</tr>";
        List<Result> answers = resultList.stream().filter((r) -> (r.getType().equals("Math"))).collect(Collectors.<Result>toList());
        int index = 0;
        for(Result r: answers) {
            index++;
            htmlTable += "<tr>"+
                    "<td>"+index+"</td>"+
                    "<td>"+r.getAnswer()+"</td>"+
                    "<td>"+r.getResponse()+"</td>"+
                    "<td>"+r.getScore()+"</td>"+
                    "<td>"+r.getMark()+"</td>"+
                "</tr>";
            
        }
        jLabel13.setText(htmlTable);
        jPanel8.setVisible(true);       
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     * This is a event handler for Image Result button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here: Image
        resultReset();
        String htmlTable = "<html><table border='1' width=100% ><tr>"+
                "<th>No.</th>"+
                "<th>Question</th>"+
                "<th>Total</th>"+
                "<th>Score</th>"+
                "</tr>";
        List<Result> answers = resultList.stream().filter((r) -> (r.getType().equals("Image"))).collect(Collectors.<Result>toList());
        int index = 0;
        for(Result r: answers) {
            index++;
            htmlTable += "<tr>"+
                    "<td>"+index+"</td>"+
                    "<td>"+r.getQuestion()+"</td>"+
                    "<td>"+r.getScore()+"</td>"+
                    "<td>"+r.getMark()+"</td>"+
                "</tr>";
            
        }
        jLabel13.setText(htmlTable);
        jPanel8.setVisible(true);
    }//GEN-LAST:event_jButton20ActionPerformed
    
    /**
     * This is a event handler for Spelling Result button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here: Spelling
        resultReset();
        String htmlTable = "<html><table border='1' width='100%' ><tr>"+
                "<th>Q.No</th>"+
                "<th>Answer</th>"+
                "<th>Response</th>"+
                "<th>Total</th>"+
                "<th>Score</th>"+
                "</tr>";
        List<Result> answers = resultList.stream().filter((r) -> (r.getType().equals("Spelling"))).collect(Collectors.<Result>toList());
        int index = 0;
        for(Result r: answers) {
            index++;
            htmlTable += "<tr>"+
                    "<td>"+index+"</td>"+
                    "<td>"+r.getAnswer()+"</td>"+
                    "<td>"+r.getResponse()+"</td>"+
                    "<td>"+r.getScore()+"</td>"+
                    "<td>"+r.getMark()+"</td>"+
                "</tr>";
            
        }
        jLabel13.setText(htmlTable);
        jPanel8.setVisible(true);
    }//GEN-LAST:event_jButton21ActionPerformed

    /**
     * This is a event handler for Listening Result button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here: Listening
        resultReset();
        String htmlTable = "<html><table border='1' width='100%' ><tr>"+
                "<th>Q.No</th>"+
                "<th>Answer</th>"+
                "<th>Response</th>"+
                "<th>Total</th>"+
                "<th>Score</th>"+
                "</tr>";
        List<Result> answers = resultList.stream().filter((r) -> (r.getType().equals("Listening"))).collect(Collectors.<Result>toList());
        int index = 0;
        for(Result r: answers) {
            index++;
            htmlTable += "<tr>"+
                    "<td>"+index+"</td>"+
                    "<td>"+r.getAnswer()+"</td>"+
                    "<td>"+r.getResponse()+"</td>"+
                    "<td>"+r.getScore()+"</td>"+
                    "<td>"+r.getMark()+"</td>"+
                "</tr>";
            
        }
        jLabel13.setText(htmlTable);
        jPanel8.setVisible(true);
    }//GEN-LAST:event_jButton23ActionPerformed

    /**
     * This is a event handler for Writing Result button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here: Writing
        resultReset();
        String htmlTable = "<html><table border='1' width='100%' ><tr>"+
                "<th>Q.No</th>"+
                "<th>Answer</th>"+
                "<th>Response</th>"+
                "<th>Total</th>"+
                "<th>Score</th>"+
                "</tr>";
        List<Result> answers = resultList.stream().filter((r) -> (r.getType().equals("Writing"))).collect(Collectors.<Result>toList());
        int index = 0;
        for(Result r: answers) {
            index++;
            htmlTable += "<tr>"+
                    "<td>"+index+"</td>"+
                    "<td>"+r.getAnswer()+"</td>"+
                    "<td>"+r.getResponse()+"</td>"+
                    "<td>"+r.getScore()+"</td>"+
                    "<td>"+r.getMark()+"</td>"+
                "</tr>";
            
        }
        jLabel13.setText(htmlTable);
        jPanel8.setVisible(true);
    }//GEN-LAST:event_jButton24ActionPerformed

    /**
     * This is a event handler for Back button for Result section.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        jPanel8.setVisible(false);
        jPanel6.setVisible(true);
    }//GEN-LAST:event_jButton25ActionPerformed
    
     /**
     * This is a event handler for Back button for Graph panel.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here: Graph Panel Back Button
        jPanel9.setVisible(false);
        jPanel8.setVisible(true);
    }//GEN-LAST:event_jButton26ActionPerformed
    
    /**
     * This is a event handler for Pie button for pie Graph panel.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here: Pie chart button for overall result
        jPanel8.setVisible(false);
        jPanel9.setLayout(new BorderLayout());
        jPanel9.validate();
        jPanel9.add(pieChart());
        jPanel9.setVisible(true);
    }//GEN-LAST:event_jButton27ActionPerformed

    /**
     * This is a event handler for Bar button for bar Graph panel.
     * 
     * @param evt is a ActionEvent handler.
     */
    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        jPanel8.setVisible(false);
        jPanel9.setLayout(new BorderLayout());
        jPanel9.validate();
        jPanel9.add(barChart());
        jPanel9.setVisible(true);
    }//GEN-LAST:event_jButton28ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
