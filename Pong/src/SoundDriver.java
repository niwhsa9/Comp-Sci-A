import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;


import com.sun.tools.javac.Main;

public class SoundDriver {
	static AudioInputStream[] audioInputStream;
	static Clip clip;
	public SoundDriver (File sounds[]) {
		audioInputStream = new AudioInputStream[sounds.length];
		// = AudioSystem.getClip();
		int c = 0; 
		for(File sound : sounds) {
			try {
				audioInputStream[c] = AudioSystem.getAudioInputStream(sound.getAbsoluteFile());
			} catch(Exception e) {
				e.printStackTrace();
			}
			c++;
		}
		try {
			clip = AudioSystem.getClip();
		} catch(Exception e) {
			
		}
	}

	public static void playHit() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/hit.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playReset() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/beep2.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playGameOver() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/GameOver.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
}
