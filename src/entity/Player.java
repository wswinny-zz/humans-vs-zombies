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
	private int speedMultiplier;
	
	private static final Player instance = new Player();
	
	private Player(){
		this.setXVel(1);
		this.setYVel(1);
	}
	
	public static Player getInstance(){
		return instance;
	}
	
	public void update(){
		double xTemp = this.getXVel() + this.getX();
		
		double yTemp = this.getYVel() + this.getY();
		super.setX((int)xTemp);
		super.setY((int)yTemp);
	}
	
	@Override
	public void draw(Graphics g){
		g.drawOval(GamePanel.WIDTH/2-4, GamePanel.HEIGHT/2-4, 8, 8);
	}

	public int getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(int speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
	
	public void mouseClicked(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		
	}
	
	public void keyPressed(KeyEvent e){
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}
}
