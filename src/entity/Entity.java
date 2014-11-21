package entity;

/************************************************************
 * Entity														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Abstract base class for all entities to be drawn
 * 			on the map.																	
 ************************************************************/

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Entity {
	private int x;
	private int y;
	
	private double vector;
	private double xVel;
	private double yVel;
	
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
	public double getXVel() {
		return xVel;
	}
	public void setXVel(double xVel) {
		this.xVel = xVel;
	}
	public double getYVel() {
		return yVel;
	}
	public void setYVel(double yVel) {
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
	
	public Point getPosition(){
		return new Point(x,y);
	}
	
	public void setPosition(Point p){
		this.setX(p.x);
		this.setY(p.y);
	}
	
	public void draw(Graphics g){
		
	}
	public double getVector() {
		return vector;
	}
	public void setVector(double vector) {
		this.vector = vector;
	}
}
