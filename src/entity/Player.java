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
		//System.out.println("SKey:" + SKeyDown + " WKey:" + WKeyDown + " AKey:" + AKeyDown + " DKey:" + DKeyDown);
		
		//If W or S and A or D
		//Vector is not being used on player
		//because even though its normalized
		//it makes the movement choppier
		if((WKeyDown || SKeyDown) && (AKeyDown || DKeyDown)){
			this.setVector(.5);
		}else if(SKeyDown || WKeyDown && !(AKeyDown || DKeyDown)){
			this.setVector(0);
		}else{
			this.setVector(1);
		}
		
		
		if(AKeyDown && !DKeyDown){
			this.setXVel(-1);
		}else if(DKeyDown && !AKeyDown){
			this.setXVel(1);
		}else{
			this.setXVel(0);
		}
		
		
		if(WKeyDown && !SKeyDown){
			this.setYVel(-1);
		}else if(SKeyDown && !WKeyDown){
			this.setYVel(1);
		}else{
			this.setYVel(0);
		}
		
		
		
		//Calculate default velocity
		double velTempX = (this.getXVel());/**this.getVector());*/
		double velTempY = (this.getYVel());/**(1-this.getVector()));*/
		
		System.out.println("x:"+velTempX + " y:" + velTempY);
		
		//work in the speed multiplier
		double multTempX = velTempX * speedMultiplier;
		double multTempY = velTempY * speedMultiplier;
		
		//calc final pos
		double xTemp = multTempX + this.getX();
		double yTemp = multTempY + this.getY();
		
		//set
		super.setX(xTemp);
		super.setY(yTemp);
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
		//System.out.println(""+e.getKeyChar() + " Pressed!");
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
		//System.out.println(""+e.getKeyChar() + " Released!");
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
