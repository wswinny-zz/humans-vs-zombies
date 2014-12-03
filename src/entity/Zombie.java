package entity;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import map.Map;
import map.Tile;
import state.StateManager;
import util.Audio;

/************************************************************
 * Zombie														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Class that manages the Zombies.																	
 ************************************************************/

public class Zombie extends Entity {
	private double speedMultiplier;
	private long lastTime;
	private boolean stunned;
	private long stunnedTime;
	private static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	public Zombie(){
		
		Random random = new Random();
		lastTime = new Date().getTime();
		
		//Generate position 200 from the left and right
		
		
		//Default random values
		double xPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
		double yPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));		
		double xVel = 1;
		double yVel = 1;
		double vec = random.nextDouble();
		
		
		//if it intersected try again
		while(intersectsWithMap(xPos, yPos)){
			xPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
			yPos = random.nextInt((Map.getVisibleMap()[0].length*Map.getTileSize()));
		}
		
		//Set image width and height
		this.setWidth(40);
		this.setHeight(36);
		
		//Load in the zombie tiles
		try {		
			BufferedImage tileset;
			tileset = ImageIO.read(Player.class.getResourceAsStream("/zombie.png"));
			
			if(images.size() == 0){
				for(int x = 0; x < (tileset.getWidth()/this.getWidth()); x++){
					for(int y = 0; y < (tileset.getHeight()/this.getHeight()); y++){
						images.add(tileset.getSubimage(x*this.getWidth(), y*this.getHeight(), this.getWidth(), this.getHeight()));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Set intial attributes
		this.setImg(Zombie.images.get(0));
		this.setSpeedMultiplier(1);
		this.setX(xPos);
		this.setY(yPos);
		this.setXVel(xVel);
		this.setYVel(yVel);
		this.setVector(vec);
	}
	
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();
		
		//Translate coordinates
		int xTile = (int)(xPos/tileWidth);
		int yTile = (int)(yPos/tileWidth);
		
		//If the zombie intersects with blocked or zombie blocked
		if(xTile < 0 || yTile < 0) return true;
		if(xTile >= tiles[0].length || yTile >= tiles.length) return true;
		if(tiles[yTile][xTile].getTileType() == Tile.BLOCKED 
				|| tiles[yTile][xTile].getTileType() == Tile.ZOMBIE_BLOCKED) return true;
		
	
		
		return false;
	}
	
	public int distance(double x1, double y1, double x2, double y2){
		//dist
		return (int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public void trackPlayer(){
		double xPLoc = Player.getInstance().getX();
		double yPLoc = Player.getInstance().getY();

		//If we already intersect, get out
		if(intersectsWithMap(xPLoc, yPLoc)) return;
		
		//Get initial values
		double xRay = this.getX();
		double yRay = this.getY();
		double xDiff = xPLoc - xRay;
		double yDiff = yPLoc - yRay;
		double xVec = xDiff / (Math.abs(xDiff)+Math.abs(yDiff));
		double yVec = yDiff / (Math.abs(xDiff)+Math.abs(yDiff));

		//If were within the distance of 700
		//trace a ray to the player
		if(distance(xRay, yRay, xPLoc, yPLoc) < 700){
			//Move 32 at a time and check within 16 distance
			while(distance(xRay, yRay, xPLoc, yPLoc) > 16){
				//If we have a map intersection return
				if(intersectsWithMap(xRay, yRay)) return;
				xRay += xVec*32;
				yRay += yVec*32;
			}
			//Set the vectors to the players location
			this.setVector(Math.abs(xVec));
			this.setXVel(xDiff/Math.abs(xDiff));
			this.setYVel(yDiff/Math.abs(yDiff));
			return;
		}else return;
	}
	
	public void update(){
		//Begin by deciding whether to track player
		trackPlayer();
		
		//Check if the zombie should get stunned
		for(Sock s : Player.getInstance().getSocks()){
			if(distance(s.getX()+16, s.getY()+16, this.getX(), this.getY()) < 16){
				this.setStunned(true);
			}		

		}
		
		
		//calculate stun delta 
		long stunDelta = (new Date().getTime() - stunnedTime)/1000;
		
		//Handle stun
		if(stunDelta > 10 && this.stunned){
			this.stunned = false;
		}else if(!this.stunned){
			//put in the default values
			double velTempX = (this.getXVel())*this.getVector();
			double velTempY = (this.getYVel())*(1-this.getVector());
	
			//work in the speed multiplier
			double multTempX = velTempX * speedMultiplier;
			double multTempY = velTempY * speedMultiplier;
						
			//calc final pos
			double xTemp = multTempX + this.getX();
			double yTemp = multTempY + this.getY();
			
			if(intersectsWithPlayer(xTemp, yTemp)){
				Audio.getInstance().playDeath();
				StateManager.setState(StateManager.DEATH_STATE);
			}
			
			//If the point intersects with the map 
			//bounce zombie off
			if(intersectsWithMap(xTemp, yTemp)){
				if(intersectsWithMap(xTemp, this.getY())){
					this.setXVel(this.getXVel()*-1);
				}else if(intersectsWithMap(this.getX(), yTemp)){
					this.setYVel(this.getYVel()*-1);
				}else{
					this.setVector(1-this.getVector());
				}
				
				this.lastTime = new Date().getTime();
				
				//recalculate
				velTempX = (this.getXVel())*this.getVector();
				velTempY = (this.getYVel())*(1-this.getVector());
	
	
				multTempX = velTempX * speedMultiplier;
				multTempY = velTempY * speedMultiplier;
				
				xTemp = multTempX + this.getX();
				yTemp = multTempY + this.getY();
			}
			
	
			
			//Set the images
			if(this.getVector() > .5){
				if(this.getXVel() > 0)
					this.setImg(Zombie.images.get(1));
				if(this.getXVel() < 0)
					this.setImg(Zombie.images.get(3));	
			}else{
				if(this.getYVel() > 0)
					this.setImg(Zombie.images.get(0));
				if(this.getYVel() < 0)
					this.setImg(Zombie.images.get(2));
			}
			
			//Set the zombie location
			this.setX(xTemp);
			this.setY(yTemp);
		}
		
		
		
	}
	
	public boolean intersectsWithPlayer(double x, double y){
		//If the zombie intersects with the player
		if(Player.getInstance().getX() < x + 16
				&& Player.getInstance().getX() + 16 > x
				&& Player.getInstance().getY() < y + 16
				&& Player.getInstance().getY() + 16 > y){
			return true;
		}else return false;
	}
	
	@Override
	public void draw(Graphics g){
		
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		

		//g.setColor(Color.RED);
		///System.out.println("Y:"+(int)(this.getY() - playerY));
		//System.out.println("X:"+(int)(this.getX() - playerX));
		//g.fillOval((int)(this.getX() - playerX)+GamePanel.WIDTH/2,(int)(this.getY() - playerY)+GamePanel.HEIGHT/2, 10, 10);

		//Only draw if on screen
		if(distance(playerX, playerY, this.getX(),this.getY()) > 300) return;
		if(this.getImg() != null){
			g.drawImage(this.getImg(),
					(int)(this.getX() - playerX - this.getWidth()/2)+GamePanel.WIDTH/2,
					(int)(this.getY() - playerY - this.getHeight()/2)+GamePanel.HEIGHT/2,
					this.getImg().getWidth(),
					this.getImg().getHeight(), null);
		}
		
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(double speedMultipler) {
		this.speedMultiplier = speedMultipler;
	}

	public boolean isStunned() {
		return stunned;
	}

	public void setStunned(boolean stunned) {
		//record stun time
		this.stunnedTime = new Date().getTime();
		this.stunned = stunned;
	}
}
