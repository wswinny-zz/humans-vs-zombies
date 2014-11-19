package entity;

public class Player extends Entity {
	private int speedMultiplier;
	
	private static final Player instance = new Player();
	
	private Player(){}
	
	public static Player getInstance(){
		return instance;
	}

	public int getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(int speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
}
