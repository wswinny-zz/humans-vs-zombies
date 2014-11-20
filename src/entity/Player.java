package entity;
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

	public int getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(int speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
}
