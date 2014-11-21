package entity;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/************************************************************
 * Player														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Class that manages the singleton player.																	
 ************************************************************/

public class Player extends Entity {
	private double speedMultiplier;
	
	private boolean WKeyDown;
	private boolean AKeyDown;
	private boolean SKeyDown;
	private boolean DKeyDown;
	
	private static final Player instance = new Player();
	
	private Player(){
		this.setXVel(1);
		this.setYVel(1);
		this.setVector(1);
		
		setWKeyDown(false);
		setAKeyDown(false);
		setSKeyDown(false);
		setDKeyDown(false);
	}
	
	public static Player getInstance(){
		return instance;
	}
	
	public void update(){
		//Calculate vector based on the keys pressed
		//
		
		double velTempX = (this.getXVel()*super.getVector());
		double velTempY = (this.getYVel()*(1-super.getVector()));
		
		double multTempX = velTempX * speedMultiplier;
		double multTempY = velTempY * speedMultiplier;
		
		double xTemp = multTempX + this.getX();
		double yTemp = multTempY + this.getY();
		
		super.setX((int)xTemp);
		super.setY((int)yTemp);
	}
	
	@Override
	public void draw(Graphics g){
		//Temporary draw to denote the player
		g.drawOval(GamePanel.WIDTH/2-4, GamePanel.HEIGHT/2-4, 8, 8);
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
	
	public void mouseClicked(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		
	}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()){
		case 'W':
			setWKeyDown(true);
			break;
		case 'A':
			setWKeyDown(true);
			break;
		case 'S':
			setWKeyDown(true);
			break;
		case 'D':
			setWKeyDown(true);
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyChar()){
		case 'W':
			setWKeyDown(false);
			break;
		case 'A':
			setWKeyDown(false);
			break;
		case 'S':
			setWKeyDown(false);
			break;
		case 'D':
			setWKeyDown(false);
			break;
		}
	}

	public boolean isWKeyDown() {
		return WKeyDown;
	}

	public void setWKeyDown(boolean wKeyDown) {
		WKeyDown = wKeyDown;
	}

	public boolean isAKeyDown() {
		return AKeyDown;
	}

	public void setAKeyDown(boolean aKeyDown) {
		AKeyDown = aKeyDown;
	}

	public boolean isSKeyDown() {
		return SKeyDown;
	}

	public void setSKeyDown(boolean sKeyDown) {
		SKeyDown = sKeyDown;
	}

	public boolean isDKeyDown() {
		return DKeyDown;
	}

	public void setDKeyDown(boolean dKeyDown) {
		DKeyDown = dKeyDown;
	}
}
