package entity;
/************************************************************
 * Zombie														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Class that manages the Zombies.																	
 ************************************************************/

public class Zombie extends Entity {
	private double speedMultipler;
	
	public Zombie(){
		
	}
	
	public void update(){
		
	}

	public double getSpeedMultipler() {
		return speedMultipler;
	}

	public void setSpeedMultipler(double speedMultipler) {
		this.speedMultipler = speedMultipler;
	}
}
