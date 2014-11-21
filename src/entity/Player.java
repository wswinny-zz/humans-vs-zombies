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
		this.setXVel(0);
		this.setYVel(0);
		this.setVector(1);
		this.setSpeedMultiplier(1.0);
		
		setWKeyDown(false);
		setAKeyDown(false);
		setSKeyDown(false);
		setDKeyDown(false);
	}
	
	public static Player getInstance(){
		return instance;
	}
	
	public void update(){
		//If w key and a or d key is down
		//we are moving diagonally
		if(WKeyDown && (AKeyDown || DKeyDown)){
			this.setVector(.5);
			this.setYVel(-1);
			//if the a key is down the velocity is pos
			if(AKeyDown){
				this.setXVel(-1);
			}else{
				this.setXVel(1);
			}
		//If s key and a or d key is down
		//we are moving diagonally
		}else if (SKeyDown && (AKeyDown || DKeyDown)){
			this.setVector(.5);
			this.setYVel(1);
			if(AKeyDown){
				this.setXVel(-1);
			}else{
				this.setXVel(1);
			}
		//If just s or w is down
		}else if(SKeyDown && !WKeyDown){
			this.setYVel(1);
			this.setVector(0);
		}else if(WKeyDown && !SKeyDown){
			this.setYVel(-1);
			this.setVector(0);
		}
		
		//Calculate default velcity
		double velTempX = (this.getXVel()*super.getVector());
		double velTempY = (this.getYVel()*(1-super.getVector()));
		
		//work in the speed multiplier
		double multTempX = velTempX * speedMultiplier;
		double multTempY = velTempY * speedMultiplier;
		
		//calc final pos
		double xTemp = multTempX + this.getX();
		double yTemp = multTempY + this.getY();
		
		//set
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
		switch(Character.toUpperCase(e.getKeyChar())){
		case 'W':
			setWKeyDown(true);
			break;
		case 'A':
			setAKeyDown(true);
			break;
		case 'S':
			setSKeyDown(true);
			break;
		case 'D':
			setDKeyDown(true);
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		switch(Character.toUpperCase(e.getKeyChar())){
		case 'W':
			setWKeyDown(false);
			break;
		case 'A':
			setAKeyDown(false);
			break;
		case 'S':
			setSKeyDown(false);
			break;
		case 'D':
			setDKeyDown(false);
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
