/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.*;

/**
 * This is a Sound class responsible for sound management in application. 
 * 
 * @author Zohaib Ali
 * @version 1.0
 */
public class Sound {
    
    /**
     * This is a function for retrieving and play alert sound.
     *
     * @return Clip object that contain alert sound.
     * @throws javax.sound.sampled.LineUnavailableException 
     * @throws java.io.IOException 
     * @throws javax.sound.sampled.UnsupportedAudioFileException 
     */
    public static Clip alert() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        String soundName = "sound/alert2.wav";    
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }
    
    /**
     * This is a function for retrieving and play listening question.
     *
     * @param url is a string that will containing location of audio file.
     * @return MediaPlayer object that contain media to play.
     * @throws javax.sound.sampled.LineUnavailableException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     */
    public static MediaPlayer playTest(String url) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        String soundName = "listening/" + url;    
        Media hit = new Media(new File(soundName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        return mediaPlayer;
    }
    
}
