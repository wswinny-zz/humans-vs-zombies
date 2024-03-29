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
		//Create the array lists
		this.images = new ArrayList<BufferedImage>();
		this.socks = new ArrayList<Sock>();
		
		//Player starts with 10 socks
		this.setNumSocks(10);
		
		//Load in the player tileset
		try {		
			BufferedImage tileset;
			tileset = ImageIO.read(Player.class.getResourceAsStream("/player.png"));
			
			for(int x = 0; x < (tileset.getWidth()/32); x++){
				for(int y = 0; y < (tileset.getHeight()/32); y++){
					this.images.add(tileset.getSubimage(x*32, y*32, 32, 32));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Set the first image
		this.setImg(images.get(0));
		
		//Set the players starting location
		this.setX(Map.getTileSize()*10);
		this.setY(Map.getTileSize()*10);
		
		//Set the starting velocity
		this.setXVel(0);
		this.setYVel(0);
		
		//Set vector and speed mult
		this.setVector(1);
		this.setSpeedMultiplier(1.25);
		
		//Set default key state
		setWKeyDown(false);
		setAKeyDown(false);
		setSKeyDown(false);
		setDKeyDown(false);
	}
	
	public static Player getInstance(){
		return instance;
	}
	
	public static void reset(){
		//Reset the player
		instance = new Player();
	}
	
	public void update(){
		//Update the socks
		ArrayList<Sock> removeList = new ArrayList<Sock>();
		for(Sock s : socks){
			s.update();
			if(s.getIsDone() == true) removeList.add(s);
		}
		
		//Remove the socks if they're doen
		for(Sock s : removeList){
			socks.remove(s);
		}
		
		//System.out.println("SKey:" + SKeyDown + " WKey:" + WKeyDown + " AKey:" + AKeyDown + " DKey:" + DKeyDown);
		
		//If W or S and A or D
		//Vector is not being used on player
		//because even though its normalized
		//it makes the movement choppier
		//Check if were going at an angle
		if((WKeyDown || SKeyDown) && (AKeyDown || DKeyDown)){

			this.setVector(.5);
		}else if(SKeyDown || WKeyDown && !(AKeyDown || DKeyDown)){
			this.setVector(0);
		}else{
			this.setVector(1);
		}
		
		//Going only left or right
		if(AKeyDown && !DKeyDown){
			this.setXVel(-1);
			this.setImg(this.images.get(1));
		}else if(DKeyDown && !AKeyDown){
			this.setXVel(1);
			this.setImg(this.images.get(2));
		}else{
			this.setXVel(0);
		}
		
		
		//Going only up or down
		if(WKeyDown && !SKeyDown){
			this.setYVel(-1);
			this.setImg(this.images.get(3));
		}else if(SKeyDown && !WKeyDown){
			this.setYVel(1);
			this.setImg(this.images.get(0));
		}else{
			this.setYVel(0);
		}
		
		//Set the images based on state
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
		
		//If the movement wont cause an intersection, move
		if(!this.intersectsWithMap(this.getX(), yTemp)){
			super.setY(yTemp);
		}
		
		if(!this.intersectsWithMap(xTemp, this.getY())){
			super.setX(xTemp);
		}
		
	}
	
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();
		
		//Translate world coordinates to map coordinates
		int xTile = (int)((xPos+getWidth()/2)/tileWidth);
		int yTileTop = (int)((yPos-16)/tileWidth);
		int yTileBott = (int)((yPos+16)/tileWidth);
		
		//If the tiles above or below dont instersect with a blocked tile
		//then feel free to move in
		if(tiles[yTileTop][xTile].getTileType() == Tile.BLOCKED) return true;
		if(tiles[yTileBott][xTile].getTileType() == Tile.BLOCKED) return true;
		return false;
	}
	
	@Override
	public void draw(Graphics g){
		//Draw all of the socks
		for(Sock s : socks){
			s.draw(g);
		}
		
		//Draw an oval if the image is missing
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
			//Translate window coordinates to world coordinates
			double mousePosX = (e.getPoint().x/GamePanel.SCALE + (getX() - GamePanel.WIDTH/2));
			double mousePosY = (e.getPoint().y/GamePanel.SCALE + (getY() - GamePanel.HEIGHT/2));
			
			//System.out.println(mousePosX + "," + mousePosY + " " + this.getX() + "," + this.getY());
			this.socks.add(new Sock((int)mousePosX, (int)mousePosY, (int)this.getX(), (int)this.getY()));
			numSocks--;
			Audio.getInstance().playSockThrow();
		}
	}

	
	public void keyPressed(KeyEvent e){
		//Translates key press
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
		//Translates key release
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
