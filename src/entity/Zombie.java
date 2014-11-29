package entity;

import game.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import state.StateManager;
import map.Map;
import map.Tile;

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
		double xPos = 100 + random.nextInt((GamePanel.WIDTH*GamePanel.SCALE*2)-200);
		double yPos = 100 + random.nextInt((GamePanel.HEIGHT*GamePanel.SCALE*2)-200);
		double xVel = 1;
		double yVel = 1;
		double vec = random.nextDouble();
		
		
		//if it intersected try again
		while(intersectsWithMap(xPos, yPos)){
			xPos = 100 + random.nextInt((GamePanel.WIDTH*GamePanel.SCALE*2)-200);
			yPos = 100 + random.nextInt((GamePanel.HEIGHT*GamePanel.SCALE*2)-200);
		}
		
		this.setWidth(40);
		this.setHeight(36);
		
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
		
		//for each row
		for(int row = 0; row < tiles.length; row++){
			//for each column in row
			for(int col = 0; col < tiles[row].length; col++){
				//if the tile is non-walkable for zombies
				if(tiles[row][col].getTileType() == Tile.BLOCKED || tiles[row][col].getTileType() == Tile.ZOMBIE_BLOCKED){
					//if they're within this tile on the x axis
					if(col*tileWidth < xPos+ this.getWidth()/2 && col*tileWidth + tileWidth  > xPos -this.getWidth()/2){
						//if they're within this tile on y axis
						if(row*tileWidth < yPos+ this.getHeight()/2 && row*tileWidth + tileWidth > yPos - this.getHeight()/2){
							//tell them no!
							return true;
						}
					}
				}
			}
		}
		
		
		
		return false;
	}
	
	public void update(){
		
		/* FAR TOO MUCH BOGO SORT GOING ON IN THIS FUNCTION */
		/* SHOULD SUFFICE FOR NOW */
		
		for(Sock s : Player.getInstance().getSocks()){
			if(s.getX() + 16 > this.getX() && s.getX() -16 < this.getX()){
				if(s.getY() + 16 > this.getY() && s.getY() -16 < this.getY()){
					this.setStunned(true);
				}
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
				StateManager.setState(StateManager.DEATH_STATE);
			}
			
			//While the time delta is too high or the point intersects with the map
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
			
	
			
			//set the x
			//System.out.println(this.getX() + "+" + xTemp);
			
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
			
			this.setX(xTemp);
			this.setY(yTemp);
		}
		
		
		
	}
	
	public boolean intersectsWithPlayer(double x, double y){
		if(Player.getInstance().getX() < x
				&& Player.getInstance().getX() + 32 > x
				&& Player.getInstance().getY() < y
				&& Player.getInstance().getY() + 32 > y){
			return true;
		}else
			return false;
	}
	
	@Override
	public void draw(Graphics g){
		
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		

		//g.setColor(Color.RED);
		///System.out.println("Y:"+(int)(this.getY() - playerY));
		//System.out.println("X:"+(int)(this.getX() - playerX));
		//g.fillOval((int)(this.getX() - playerX)+GamePanel.WIDTH/2,(int)(this.getY() - playerY)+GamePanel.HEIGHT/2, 10, 10);

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
