import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {
	private Clip clip;
	private String fn;
	private AudioInputStream audioIn;
	public AudioClip(String name) {
		fn = name;
		try { 
			audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("sounds/"+fn+".wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public void start() {
		if (!clip.isRunning()) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void stop() {
		clip.stop();
	}
	
}
