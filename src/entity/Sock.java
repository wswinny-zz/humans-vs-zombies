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
	private double endPosX;
	private double endPosY;
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
		
		//Determine the vector value
		int xDistance = Math.abs(goXPos - playerXPos);
		int yDistance = Math.abs(goYPos - playerYPos);
		int totalDistance = xDistance + yDistance;
		
		//Set the vector value
		this.setVector((xDistance/totalDistance));
		
		//Set end x/y values
		this.setEndPosX(goXPos);
		this.setEndPosY(goYPos);
		
		
		//Determine the xVelocity for the sock
		if(goXPos > playerXPos){
			this.setXVel(1);
		}else if(playerXPos > goXPos){
			this.setXVel(-1);
		}else if(playerXPos == goXPos){
			this.setXVel(0);
		}
		
		//Determine the yVelocity for the sock
		if(goYPos > playerYPos){
			this.setYVel(1);
		}else if(playerYPos > goYPos){
			this.setYVel(-1);
		}else if(playerYPos == goYPos){
			this.setXVel(0);
		}
	}
	
	//Draw the sock on the panel
	@Override
	public void draw(Graphics g){
		double playerX = Player.getInstance().getX();
		double playerY = Player.getInstance().getY();

		if(sockImage != null){
			g.drawImage(sockImage,
					(int)(this.getX() - playerX)+GamePanel.WIDTH/2,
					(int)(this.getY() - playerY)+GamePanel.HEIGHT/2,
					this.getImg().getWidth(),
					this.getImg().getHeight(), null);
		}
	}
	
	//Update the location of the sock
	public void update(){
		double xPos;
		double yPos;
		
		//Change the position according to the velocity and vector
		xPos = (this.getXVel() * this.getVector()) + this.getX();
		yPos = (this.getYVel() * (1 - this.getVector()) + this.getY());
		
		//Determine whether or not the sock has reached its final destination
		if(xPos == this.endPosX && yPos == this.endPosY){
			this.isDone = true;
		}
		
		//Update the values of x and y for the sock's position
		this.setX(xPos);
		this.setY(yPos);
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public double getEndPosY() {
		return endPosY;
	}

	public void setEndPosY(double endPosY) {
		this.endPosY = endPosY;
	}

	public double getEndPosX() {
		return endPosX;
	}

	public void setEndPosX(double endPosX) {
		this.endPosX = endPosX;
	}
}
