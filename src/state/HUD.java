package state;

import entity.Objective;
import entity.Player;
import game.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.Map;
import map.Tile;

/************************************************************
 * HUD														
 * Author: Aaron Hitchcock											
 * 																		
 * Purpose: Class that handles the Heads up display for the
 * 			users information. Includes the pointer to the
 *			objective																
 ************************************************************/

public class HUD
{
	BufferedImage sock;
	BufferedImage miniMap;
	public HUD(){
		try {
			sock = ImageIO.read(HUD.class.getResourceAsStream("/sock.png"));
			miniMap = ImageIO.read(HUD.class.getResourceAsStream("/map-img.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		


		g2.drawImage(sock, GamePanel.WIDTH-64, GamePanel.HEIGHT-32, sock.getWidth(), sock.getHeight(), null);

		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.setColor(Color.BLACK);
		g2.drawString("x"+Player.getInstance().getNumSocks(), GamePanel.WIDTH-32, GamePanel.HEIGHT - 12);
		
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .75f));
		g2.drawImage(miniMap, GamePanel.WIDTH-96, 0, 96, 96, null);
		g2.setColor(Color.BLACK);
		g2.drawRect(GamePanel.WIDTH-96, 0, 95, 96);
		
		//DRAW PLAYER
		int size = Map.getVisibleMap()[0][0].getImage().getWidth();
		double playerPercentX = 1-(Player.getInstance().getX()/(Map.getVisibleMap().length*size));
		double playerPercentY = (Player.getInstance().getY()/(Map.getVisibleMap()[0].length*size));
		g2.setColor(Color.RED);
		g2.fillRect((int)(GamePanel.WIDTH - (playerPercentX*96)), (int)(96*playerPercentY), 5, 5);
		g2.setColor(Color.BLACK);
		g2.drawRect((int)(GamePanel.WIDTH - (playerPercentX*96)), (int)(96*playerPercentY), 5, 5);

		//DRAW OBJECTIVE
		Objective obj = ObjectiveMode.getObjective();
		double objectivePercentX = 1-(obj.getX()/(Map.getVisibleMap().length*size));
		double objectivePercentY = (obj.getY()/(Map.getVisibleMap()[0].length*size));
		g2.setColor(Color.YELLOW);
		g2.fillRect((int)(GamePanel.WIDTH - (objectivePercentX*96)), (int)(96*objectivePercentY), 5, 5);
		g2.setColor(Color.BLACK);
		g2.drawRect((int)(GamePanel.WIDTH - (objectivePercentX*96)), (int)(96*objectivePercentY), 5, 5);
		
		
		//DRAW PLAYER STATE
		
		boolean playerState = intersectsWithMap(Player.getInstance().getX(), Player.getInstance().getY());
		
		g2.setFont(new Font("Arial", Font.BOLD, 24));
		if(playerState){
			g2.setColor(Color.GREEN);
			g2.drawString("SAFE", 3, GamePanel.HEIGHT - 5);
		}
		else{
			g2.setColor(Color.RED);
			g2.drawString("DANGER", 3, GamePanel.HEIGHT - 5);
		}
		
		
		
		
		g2.dispose();		
	}
	
	
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();
		
		
		int xTile = (int)(xPos/tileWidth);
		int yTile = (int)(yPos/tileWidth);
		
		if(xTile < 0 || yTile < 0) return true;
		if(xTile >= tiles[0].length || yTile >= tiles.length) return true;
		if(tiles[yTile][xTile].getTileType() == Tile.ZOMBIE_BLOCKED) return true;
		
	
		
		return false;
	}
	
}
