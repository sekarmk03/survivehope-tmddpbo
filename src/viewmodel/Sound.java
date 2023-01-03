/*
 * Filename     : Sound.java
 * Programmer   : Sekar Madu Kusumawardani
 * Date         : 2022-06-15
 * Email        : sekarmadu99@gmail.com
 * Website      : https://sekarmk03.github.io/
 * Deskripsi    : kelas Sound untuk memutar backsound
*/
package viewmodel;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author sekar
 */
public class Sound {
    public Clip playSound(Clip clip, String filename){     
        // konstruktor
        try {
            // mengambil audio input
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("resources/" + filename).getAbsoluteFile());
            // Get a sound clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start(); // mulai audio
            clip.loop(Clip.LOOP_CONTINUOUSLY); // loop audio
            
        } catch (UnsupportedAudioFileException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (LineUnavailableException e) {
           e.printStackTrace();
        }
        return clip;
    }
    
    public void stopSound(Clip clip){
        // untuk menyetop musik
        // System.out.println("Stopping");
        clip.stop();
    }
}
