package new_sd3;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum SoundEffects implements Runnable {
	EXPLOSION("explosion.wav"), SPAWN("spawn.wav");

	private Clip clip;
	private Thread t;
	private URL url;
	private static boolean volumeOn = true;

	// constructor
	SoundEffects(String sndFile) {
		this.url = this.getClass().getClassLoader().getResource(sndFile);
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void run() {
		// if sound is playing, stop, rewind and play from the beginning
		if (volumeOn) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
	}

	// static method used for pre-loading sounds
	public static void init() {
		values(); // calls the constructor
	}

	public void start() {

		if (t == null) {
			t = new Thread(this);
			t.start();
		}

	}
	
	// Mutes or unmutes volume
	// Default is on
	public static void toggleVolume() {
		if (volumeOn) {
			volumeOn = false;
		} else {
			volumeOn = true;
		}
	}

}
