package util;


import game.Game;

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
	
	public static Audio getInstance(){
		if(instance == null){
			instance = new Audio();
		}
		return instance;
	}
	
	//Plays a footstep sound effect
	public void playFootStep(){
		
	}
	
	//Plays a flying sock sound effect
	public void playSockThrow(){
		
	}
	
	//Play a happy sound effect when the objective is reached
	public void playObjReached(){
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("objReached.wav"));
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
			in = AudioSystem.getAudioInputStream(Audio.class.getResource("objFailed.wav"));
			startEffect(in);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("\nError: objFailed sound effect failed to play.\n");
		}
	}
	
	//Plays a zombie grunt relative to the distance from the
	//player
	public void playZombieGrunt(double distance){
			
	}
	
	//Plays a sound effect when the player dies (loses)
	public void playDeath(){
		
	}
	
	//Plays the background music
	public void startBackgroundMusic(){
		
	}
	
	private void startMusic(AudioInputStream in){
		try {
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
		
	}
	
}
