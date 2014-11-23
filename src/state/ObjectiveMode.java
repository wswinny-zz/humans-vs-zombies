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
	private HUD hud;
	
	public ObjectiveMode()
	{
		this.map = new Map(32);
		this.player = Player.getInstance();
		
		this.hud = new HUD();
		
		this.init();
	}

	@Override
	public void init()
	{
		this.map.loadTileset("/tiles.png");
		this.map.loadMap("/map2.map");
	}

	@Override
	public void draw(Graphics g)
	{
		this.map.draw(g);
		this.player.draw(g);
		this.hud.draw(g);
	}
	
	@Override
	public void update()
	{
		this.player.update();
		this.map.setPosition((int)this.player.getX(), (int)this.player.getY());
	}

	@Override
	public void mouseTriggered(MouseEvent e)
	{
		if(e.getID() == MouseEvent.MOUSE_MOVED)
			Player.getInstance().mouseMoved(e);
		else if(e.getID() == MouseEvent.MOUSE_CLICKED)
			Player.getInstance().mouseClicked(e);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		Player.getInstance().keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Player.getInstance().keyReleased(e);
	}
}
