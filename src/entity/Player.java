package entity;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Audio;
import map.Map;
import map.Tile;

/************************************************************
 * Player														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Class that manages the singleton player.																	
 ************************************************************/

public class Player extends Entity {
	private double speedMultiplier;
	private ArrayList<BufferedImage> images;
	
	private boolean WKeyDown;
	private boolean AKeyDown;
	private boolean SKeyDown;
	private boolean DKeyDown;
	
	private static Player instance = new Player();
	
	private ArrayList<Sock> socks;
	private int numSocks;
	
	
	private Player(){
		this.images = new ArrayList<BufferedImage>();
		
		this.socks = new ArrayList<Sock>();
		this.setNumSocks(10);
		
		try {		
			BufferedImage tileset;
			tileset = ImageIO.read(Player.class.getResourceAsStream("/player.png"));
			
			for(int x = 0; x < (tileset.getWidth()/32); x++){
				for(int y = 0; y < (tileset.getHeight()/32); y++){
					this.images.add(tileset.getSubimage(x*32, y*32, 32, 32));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setImg(images.get(0));
		
		this.setX(Map.getTileSize()*10);
		this.setY(Map.getTileSize()*10);
		this.setXVel(0);
		this.setYVel(0);
		this.setVector(1);
		this.setSpeedMultiplier(1.25);
		
		setWKeyDown(false);
		setAKeyDown(false);
		setSKeyDown(false);
		setDKeyDown(false);
	}
	
	public static Player getInstance(){
		return instance;
	}
	
	public static void reset(){
		instance = new Player();
	}
	
	public void update(){
		ArrayList<Sock> removeList = new ArrayList<Sock>();
		for(Sock s : socks){
			s.update();
			if(s.isDone() == true) removeList.add(s);
		}
		
		for(Sock s : removeList){
			socks.remove(s);
		}
		
		//System.out.println("SKey:" + SKeyDown + " WKey:" + WKeyDown + " AKey:" + AKeyDown + " DKey:" + DKeyDown);
		
		//If W or S and A or D
		//Vector is not being used on player
		//because even though its normalized
		//it makes the movement choppier
		if((WKeyDown || SKeyDown) && (AKeyDown || DKeyDown)){

			this.setVector(.5);
		}else if(SKeyDown || WKeyDown && !(AKeyDown || DKeyDown)){
			this.setVector(0);
		}else{
			this.setVector(1);
		}
		
		if(AKeyDown && !DKeyDown){
			this.setXVel(-1);
			this.setImg(this.images.get(1));
		}else if(DKeyDown && !AKeyDown){
			this.setXVel(1);
			this.setImg(this.images.get(2));
		}else{
			this.setXVel(0);
		}
		
		
		if(WKeyDown && !SKeyDown){
			this.setYVel(-1);
			this.setImg(this.images.get(3));
		}else if(SKeyDown && !WKeyDown){
			this.setYVel(1);
			this.setImg(this.images.get(0));
		}else{
			this.setYVel(0);
		}
		
		if(WKeyDown){
			if(AKeyDown)
				this.setImg(this.images.get(13));
			if(DKeyDown)
				this.setImg(this.images.get(15));				
		}else if(SKeyDown){
			if(AKeyDown)
				this.setImg(this.images.get(12));
			if(DKeyDown)
				this.setImg(this.images.get(14));	
		}
		
		
		
		//Calculate default velocity
		double velTempX = (this.getXVel());/**this.getVector());*/
		double velTempY = (this.getYVel());/**(1-this.getVector()));*/
		
		//System.out.println("x:"+velTempX + " y:" + velTempY);
		
		//work in the speed multiplier
		double multTempX = velTempX * speedMultiplier;
		double multTempY = velTempY * speedMultiplier;
		
		//calc final pos
		double xTemp = multTempX + this.getX();
		double yTemp = multTempY + this.getY();
		
		if(!this.intersectsWithMap(this.getX(), yTemp)){
			super.setY(yTemp);
		}
		if(!this.intersectsWithMap(xTemp, this.getY())){
			super.setX(xTemp);
		}
		
		
		//set
	}
	
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();

		int xTile = (int)((xPos+getWidth()/2)/tileWidth);
		int yTileTop = (int)((yPos-16)/tileWidth);
		int yTileBott = (int)((yPos+16)/tileWidth);
		
		if(tiles[yTileTop][xTile].getTileType() == Tile.BLOCKED) return true;
		if(tiles[yTileBott][xTile].getTileType() == Tile.BLOCKED) return true;
		return false;
	}
	
	@Override
	public void draw(Graphics g){
		for(Sock s : socks){
			s.draw(g);
		}
		
		//Temporary draw to denote the player
		if(this.getImg() == null){
			g.drawOval(GamePanel.WIDTH/2-4, GamePanel.HEIGHT/2-4, 8, 8);
		}else{
			g.drawImage(this.getImg(), GamePanel.WIDTH/2-16, GamePanel.HEIGHT/2-16, this.getImg().getWidth(), this.getImg().getHeight(), null);
		}
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
	
	public void mouseClicked(MouseEvent e){
		if(this.numSocks > 0){
			double mousePosX = (e.getPoint().x/GamePanel.SCALE + (getX() - GamePanel.WIDTH/2));
			double mousePosY = (e.getPoint().y/GamePanel.SCALE + (getY() - GamePanel.HEIGHT/2));
			
			//System.out.println(mousePosX + "," + mousePosY + " " + this.getX() + "," + this.getY());
			this.socks.add(new Sock((int)mousePosX, (int)mousePosY, (int)this.getX(), (int)this.getY()));
			numSocks--;
			Audio.getInstance().playSockThrow();
		}
	}

	
	public void keyPressed(KeyEvent e){
		//System.out.println(""+e.getKeyChar() + " Pressed!");
		switch(Character.toUpperCase(e.getKeyChar())){
		case 'W':
			setWKeyDown(true);
			break;
		case 'A':
			setAKeyDown(true);
			break;
		case 'S':
			setSKeyDown(true);
			break;
		case 'D':
			setDKeyDown(true);
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		//System.out.println(""+e.getKeyChar() + " Released!");
		switch(Character.toUpperCase(e.getKeyChar())){
		case 'W':
			setWKeyDown(false);
			break;
		case 'A':
			setAKeyDown(false);
			break;
		case 'S':
			setSKeyDown(false);
			break;
		case 'D':
			setDKeyDown(false);
			break;
		}
	}

	public boolean isWKeyDown() {
		return WKeyDown;
	}

	public void setWKeyDown(boolean wKeyDown) {
		WKeyDown = wKeyDown;
	}

	public boolean isAKeyDown() {
		return AKeyDown;
	}

	public void setAKeyDown(boolean aKeyDown) {
		AKeyDown = aKeyDown;
	}

	public boolean isSKeyDown() {
		return SKeyDown;
	}

	public void setSKeyDown(boolean sKeyDown) {
		SKeyDown = sKeyDown;
	}

	public boolean isDKeyDown() {
		return DKeyDown;
	}

	public void setDKeyDown(boolean dKeyDown) {
		DKeyDown = dKeyDown;
	}

	public int getNumSocks() {
		return numSocks;
	}

	public void setNumSocks(int numSocks) {
		this.numSocks = numSocks;
	}

	public ArrayList<Sock> getSocks() {
		return socks;
	}
}
