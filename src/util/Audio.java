package util;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/************************************************************************
 * Audio															
 * Author: Tara Reeves											
 * 																		
 * Purpose: Allows for audio play-back (for background music, etc)																			
 ************************************************************************/
public class Audio {
	private static Audio instance = null;
	private Clip currentMusic;
	
	//Private Constructor
	private Audio(){
		try {
			currentMusic = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			System.out.println("\nError: Problem while creating clip.\n");
		}
	}

	//Since this class is a singleton, this function is needed
	public static Audio getInstance(){
		if(instance == null){
			instance = new Audio();
		}
		return instance;
	}
	
	//Plays a flying sock sound effect
	public void playSockThrow(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/sockThrow.wav"));
			startEffect(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: objFailed sound effect failed to play.\n");
		}
	}
	
	//Play a happy sound effect when the objective is reached
	public void playObjReached(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/objReached.wav"));
			startEffect(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: objReached sound effect failed to play.\n");
		}
	}
	
	//Plays a sad sound effect when the player fails to reach
	//the objective
	public void playObjFailed(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/objFailed.wav"));
			startEffect(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: objFailed sound effect failed to play.\n");
		}
	}
	
	//Plays a "click" effect when the user hovers over an option on the main menu
	public void playMenuEffect(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/menu_selection.wav"));
			startEffect(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: objFailed sound effect failed to play.\n");
		}
	}
	
	//Plays a sound effect when the player dies (loses)
	public void playDeath(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/deathEffect.wav"));
			startEffect(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: objFailed sound effect failed to play.\n");
		}
	}
	
	//Plays the background music
	public void startBackgroundMusic(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/background.wav"));
			startMusic(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: Background music failed to play.\n");
		}
	}
	
	//Starts the song that is provided to the function
	private void startMusic(AudioInputStream in){
		try {
			//If a song is already open, close it
			if(currentMusic.isActive()){
				currentMusic.close();
			}
			
			 currentMusic = AudioSystem.getClip();
			
			//Start the new song and allow it to loop
			currentMusic.open(in);
			currentMusic.start();
			currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (IOException | LineUnavailableException e) {
			System.out.println("\nError: Music failed.\n");
		}
	}
	
	//Stops music
	public void stopMusic(){
		currentMusic.stop();
	}
	
	//Starts a sound effect
	private void startEffect(AudioInputStream in){
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			clip.open(in);
			clip.start();
		} catch (LineUnavailableException | IOException e) {
			System.out.println("\nError: Sound effect failed.\n");
		}
		
	}
	
	//Starts the menu music
	public void startMenuMusic(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/title.wav"));
			startMusic(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: Menu music failed to play.\n");
		}
	}
	
	//Starts the music that plays when the player dies (loses)
	public void startDeathMusic(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("/death.wav"));
			startMusic(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: Death music failed to play.\n");
		}
	}
	
}
