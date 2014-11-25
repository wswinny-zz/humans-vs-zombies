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
		

		try {		
			BufferedImage tileset;
			tileset = ImageIO.read(Player.class.getResourceAsStream("/zombie.png"));
			
			if(images.size() == 0){
				for(int x = 0; x < (tileset.getWidth()/32); x++){
					for(int y = 0; y < (tileset.getHeight()/32); y++){
						images.add(tileset.getSubimage(x*32, y*32, 32, 32));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
					if(col*tileWidth < xPos+10 && col*tileWidth + tileWidth  > xPos -10){
						//if they're within this tile on y axis
						if(row*tileWidth < yPos+16 && row*tileWidth + tileWidth > yPos -7){
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
		
		
		//calculate random update delta
		long delta = (new Date().getTime() - lastTime)/1000;
		
		//calculate stun delta 
		long stunDelta = (new Date().getTime() - stunnedTime)/1000;
		
		//Handle stun
		if(stunDelta > 10){
			this.stunned = false;
		}
		
		
		//put in the default values
		double velTempX = (this.getXVel())*this.getVector();
		double velTempY = (this.getYVel())*(1-this.getVector());

		//work in the speed multiplier
		double multTempX = velTempX * speedMultiplier;
		double multTempY = velTempY * speedMultiplier;
		

		
		//calc final pos
		double xTemp = multTempX + this.getX();
		double yTemp = multTempY + this.getY();
		
		if(delta > 5){
			System.out.println("Hi");
			//this.lastTime = new Date().getTime();
		}
		
		//While the time delta is too high or the point intersects with the map
		if(delta > 3 || intersectsWithMap(xTemp, yTemp)){
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
			this.setImg(Zombie.images.get(1));
			
		}
		
		this.setX(xTemp);
		this.setY(yTemp);
		
		
		
	}
	
	@Override
	public void draw(Graphics g){
		
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();
		

		g.setColor(Color.RED);
		///System.out.println("Y:"+(int)(this.getY() - playerY));
		//System.out.println("X:"+(int)(this.getX() - playerX));
		//g.fillOval((int)(this.getX() - playerX)+GamePanel.WIDTH/2,(int)(this.getY() - playerY)+GamePanel.HEIGHT/2, 10, 10);

		if(this.getImg() != null){
			g.drawImage(this.getImg(),
					(int)(this.getX() - playerX)+GamePanel.WIDTH/2,
					(int)(this.getY() - playerY)+GamePanel.HEIGHT/2,
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
