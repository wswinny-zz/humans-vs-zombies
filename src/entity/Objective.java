package entity;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import map.Map;
import map.Tile;
import util.Audio;

/************************************************************************
 * Objective														
 * Author: Tara Reeves											
 * 																		
 * Purpose: Acts as an objective the player must reach																		
 ************************************************************************/
public class Objective extends Entity{
	private static int objectiveDuration;
	private boolean playerReached = false;
	
	//Constructor
	public Objective(){
		//Generate position 200 from the left and right
		Random random = new Random();
		double xPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
		double yPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));

		//if it intersected try again
		while(intersectsWithMap(xPos, yPos)){
			xPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
			yPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
		}		
		
		xPos = Player.getInstance().getX() + 100;
		yPos = Player.getInstance().getY() + 100;
		
		//Set the x and y position of the objective
		this.setX(xPos);
		this.setY(yPos);
		
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
	
	//Determines whether or not the position given intersects with the map
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();
		
		int xTile = (int)(xPos/tileWidth);
		int yTile = (int)(yPos/tileWidth);
		
		if(xTile < 0 || yTile < 0) return true;
		if(xTile >= tiles[0].length || yTile >= tiles.length) return true;
		if(tiles[yTile][xTile].getTileType() == Tile.BLOCKED) return true;
	
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
	
	//Draw the objective onto the panel
	@Override
	public void draw(Graphics g){
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		
		if(!playerReached){
			if(objectiveReached())
				playerReached = true;
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

	public static int getObjectiveDuration() {
		return objectiveDuration;
	}

	public static void setObjectiveDuration(int objectiveDuration) {
		Objective.objectiveDuration = objectiveDuration;
	}
}
