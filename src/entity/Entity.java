package entity;
/************************************************************
 * Entity														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Abstract base class for all entities to be drawn
 * 			on the map.																	
 ************************************************************/

import java.awt.image.BufferedImage;

public abstract class Entity {
	private int x;
	private int y;
	
	private int xVel;
	private int yVel;
	
	private BufferedImage img;
	
	private int width;
	private int height;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getXVel() {
		return xVel;
	}
	public void setXVel(int xVel) {
		this.xVel = xVel;
	}
	public int getYVel() {
		return yVel;
	}
	public void setYVel(int yVel) {
		this.yVel = yVel;
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}