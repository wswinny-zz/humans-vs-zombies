package entity;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import map.Map;
import map.Tile;
import state.ObjectiveMode;
import util.Audio;

/************************************************************************
 * Objective														
 * Author: Tara Reeves											
 * 																		
 * Purpose: Acts as an objective the player must reach																		
 ************************************************************************/
public class Objective extends Entity{
	private static double objectiveDuration;
	private long startTime;
	private boolean playerReached = false;
	
	//Constructor
	public Objective(){
		//Generate objective and its random position/duration
		generateNewObjective();
		
		//Set the image of the objective if it hasn't been set already
		if(this.getImg() == null){
			BufferedImage imgTemp;
			try {
				imgTemp = ImageIO.read(Player.class.getResourceAsStream("/objective.png"));
				this.setImg(imgTemp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void generateNewDuration(int distance){
		//Generate a new duration
		objectiveDuration = (double)(distance / 60);
		
		//Store start time so objective can keep track of 
		//time limit
		this.startTime = new Date().getTime();	
	}
	
	//Generates a random, new location for the objective
	private void generateNewObjective(){
		//Generate random position
		Random random = new Random();
		double xPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
		double yPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));

		//if it intersected with a non-reachable block, try again
		while(intersectsWithMap(xPos, yPos)){
			xPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
			yPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
		}	
				
		//Set the x and y position of the objective
		this.setX(xPos);
		this.setY(yPos);
		
		//Get the player's current location
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		
		//Find the distance between the player and the new objective
		int distance = (int)Math.sqrt((playerX-this.getX())*(playerX-this.getX()) 
				+ (playerY-this.getY())*(playerY-this.getY()));

		//Generate a new duration time accordingly
		generateNewDuration(distance);
	}
	
	//Determines whether or not the position given intersects with the map
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();
		
		
		int xTile = (int)(xPos/tileWidth);
		int yTile = (int)(yPos/tileWidth);
		
		if(xTile < 0 || yTile < 0) return true;
		if(xTile >= tiles[0].length || yTile >= tiles.length) return true;
		if(tiles[yTile][xTile].getTileType() == Tile.BLOCKED 
				|| tiles[yTile][xTile].getTileType() == Tile.ZOMBIE_BLOCKED) return true;
		
		return false;
	}

	//Returns whether or not the objective has been reached
	public boolean objectiveReached(){
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		
		double xDistance = Math.abs(playerX - this.getX());
		double yDistance = Math.abs(playerY - this.getY());
		
		if(xDistance <= 30 && yDistance <= 30){
			Audio.getInstance().playObjReached();
			return true;
		}
		else
			return false;
	}
	
	//Updates the objective. If it has already been reached, then create a new
	//location
	public void update(){
		//If the player has reached the objective,
		//generate a new objective in a different location
		if(playerReached){
			generateNewObjective();
			this.playerReached = false;
		}
		
		//Obtain the value of the time elapsed
		long timeElapsed = (new Date().getTime() - this.startTime)/1000;
		
		System.out.println("Time Remaining: " + (objectiveDuration - timeElapsed));
		//System.out.println("Duration: " + objectiveDuration);
		
		//If the duration for the objective is up, apply a speed boost properly
		//and generate a new objective in a different location
		if(timeElapsed >= objectiveDuration){
			if(objectiveReached()){
				//Give the player a speed boost
				Player.getInstance().setSpeedMultiplier(
						Player.getInstance().getSpeedMultiplier() + 0.5);
			}
			else{
				Audio.getInstance().playObjFailed();
				
				//Give the zombies a speed boost
				ObjectiveMode.increaseZombieSpeedMultiplier();
			}
			
			//Generate the new objective
			generateNewObjective();
			this.playerReached = false;
		}
	}
	
	//Draw the objective onto the panel
	@Override
	public void draw(Graphics g){
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		
		if(!this.playerReached){
			if(objectiveReached())
				this.playerReached = true;
		}

		//Paint the objective
		if(this.getImg() != null){
			g.drawImage(this.getImg(),
					(int)(this.getX() - playerX)+GamePanel.WIDTH/2,
					(int)(this.getY() - playerY)+GamePanel.HEIGHT/2,
					this.getImg().getWidth(),
					this.getImg().getHeight(), null);
		}
	}

	public static double getObjectiveDuration() {
		return objectiveDuration;
	}

	public static void setObjectiveDuration(double objectiveDuration) {
		Objective.objectiveDuration = objectiveDuration;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = new Date().getTime();
	}
}
