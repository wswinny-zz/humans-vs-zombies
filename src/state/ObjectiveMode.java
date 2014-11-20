package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import map.Map;
import entity.Player;
import entity.Zombie;

/************************************************************************
 * Class: ObjectiveMode														
 * Author: William Swinny											
 * 																		
 * Purpose: The main level of the game that will call the draw functions
 * 			of everything on the screen and draw them in the appropriate
 * 			order															
 ************************************************************************/
public class ObjectiveMode extends State
{
	private Map map;
	private Player player;
	private ArrayList<Zombie> zombies;
	
	public ObjectiveMode()
	{
		this.map = new Map(32);
		
		this.init();
	}

	@Override
	public void init()
	{
		this.map.loadTileset("/tiles.png");
		this.map.loadMap("/map.map");
	}

	@Override
	public void draw(Graphics g)
	{
		this.map.draw(g);
	}
	
	@Override
	public void update()
	{
		//get pos from player and then set map pos
		this.map.setPosition(500, 500);
	}

	@Override
	public void mouseTriggered(MouseEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
