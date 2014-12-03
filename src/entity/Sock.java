package entity;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/************************************************************************
 * Sock														
 * Author: Tara Reeves											
 * 																		
 * Purpose: Acts as a sock the player can throw																	
 ************************************************************************/
public class Sock extends Entity{
	private boolean isDone;
	private double startPosX;
	private double startPosY;
	private static BufferedImage sockImage;
	
	//Constructor
	public Sock(int goXPos, int goYPos, int playerXPos, int playerYPos){
		//Set the sock image if it hasn't been done already
		if(sockImage == null){
			try {
				sockImage = ImageIO.read(Player.class.getResourceAsStream("/sock.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//set the sock's image
		this.setImg(sockImage);
		
		//Determine the vector value
		double xDistance = Math.abs(goXPos - playerXPos);
		double yDistance = Math.abs(goYPos - playerYPos);
		double totalDistance = xDistance + yDistance;
		
		//Set the vector value
		this.setVector((xDistance/totalDistance));
		
		//Set start x/y values
		this.setStartPosX(playerXPos);
		this.setStartPosY(playerYPos);
		
		
		//Determine the xVelocity for the sock
		if(goXPos > playerXPos){
			this.setXVel(5);
		}else if(playerXPos > goXPos){
			this.setXVel(-5);
		}else if(playerXPos == goXPos){
			this.setXVel(0);
		}
		
		//Determine the yVelocity for the sock
		if(goYPos > playerYPos){
			this.setYVel(5);
		}else if(playerYPos > goYPos){
			this.setYVel(-5);
		}else if(playerYPos == goYPos){
			this.setXVel(0);
		}
		
		//Set starting position
		this.setX(playerXPos);
		this.setY(playerYPos);
	}
	
	//Draw the sock on the panel
	@Override
	public void draw(Graphics g){
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();

		if(this.getImg() != null && !this.isDone){
			g.drawImage(this.getImg(),
					(int)(this.getX() - playerX)+GamePanel.WIDTH/2,
					(int)(this.getY() - playerY)+GamePanel.HEIGHT/2,
					this.getImg().getWidth(),
					this.getImg().getHeight(), null);
		}
	}
	
	//Update the location of the sock
	public void update(){
		double xPos = this.getStartPosX();
		double yPos = this.getStartPosY();
		 
		//Obtain the difference between the sock's current position and the 
		//destination location
		int distance = (int)Math.sqrt((xPos-this.getX())*(xPos-this.getX()) 
				+ (yPos-this.getY())*(yPos-this.getY()));
		
		//Determine whether or not the sock has reached its final destination
		if(distance > 300){
			this.isDone = true;
		}
		
		//Change the position according to the velocity and vector
		xPos = (this.getXVel() * this.getVector()) + this.getX();
		yPos = (this.getYVel() * (1 - this.getVector()) + this.getY());
		
		//Update the values of x and y for the sock's position
		this.setX(xPos);
		this.setY(yPos);
	}

	public boolean getIsDone() {
		return this.isDone;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	public double getStartPosY() {
		return startPosY;
	}

	public void setStartPosY(double startPosY) {
		this.startPosY = startPosY;
	}

	public double getStartPosX() {
		return startPosX;
	}

	public void setStartPosX(double startPosX) {
		this.startPosX = startPosX;
	}
}
