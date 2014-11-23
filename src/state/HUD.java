package state;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import game.GamePanel;

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
	public HUD(){
		try {
			sock = ImageIO.read(Player.class.getResourceAsStream("/sock.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < Player.getInstance().getNumSocks(); i++){
			g.drawImage(sock, GamePanel.WIDTH-(32*(i+1)), GamePanel.HEIGHT-32, sock.getWidth(), sock.getHeight(), null);
		}
	}
}
