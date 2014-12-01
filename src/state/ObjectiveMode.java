package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import map.Map;
import entity.Objective;
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
	private static Map map;
	
	private static Player player;
	private static ArrayList<Zombie> zombies;
	private static Objective objective;
	
	private static HUD hud;
	
	public ObjectiveMode()
	{
		ObjectiveMode.map = new Map(64);
		ObjectiveMode.player = Player.getInstance();
		ObjectiveMode.zombies = new ArrayList<Zombie>();
		
		ObjectiveMode.hud = new HUD();
		
		this.init();
	}
	
	public static Objective getObjective()
	{
		return ObjectiveMode.objective;
	}
	
	public static void decreaseZombieSpeedMultiplier()
	{
		for(Zombie z: ObjectiveMode.zombies)
			z.setSpeedMultiplier(z.getSpeedMultiplier() - 0.5);
	}
	
	public static void increaseZombieSpeedMultiplier()
	{
		for(Zombie z: ObjectiveMode.zombies)
			z.setSpeedMultiplier(z.getSpeedMultiplier() + 0.5);
	}

	@Override 
	public void init()
	{
		ObjectiveMode.map.loadTileset("/tilesSet.png");
		ObjectiveMode.map.loadMap("/map.map");
		
		for(int i = 0; i < 100; ++i)
			ObjectiveMode.zombies.add(new Zombie());
		
		ObjectiveMode.objective = new Objective();
	}

	@Override
	public void draw(Graphics g)
	{
		ObjectiveMode.map.draw(g);
		
		for(int i = 0; i < ObjectiveMode.zombies.size(); ++i)
			ObjectiveMode.zombies.get(i).draw(g);
		
		ObjectiveMode.objective.draw(g);
		
		ObjectiveMode.player.draw(g);
		ObjectiveMode.hud.draw(g);
	}
	
	@Override
	public void update()
	{
		for(int i = 0; i < ObjectiveMode.zombies.size(); ++i)
			ObjectiveMode.zombies.get(i).update();
		
		ObjectiveMode.objective.update();
		
		ObjectiveMode.player.update();
		ObjectiveMode.map.setPosition((int)ObjectiveMode.player.getX(), (int)ObjectiveMode.player.getY());
	}

	@Override
	public void mouseTriggered(MouseEvent e)
	{
		if(e.getID() == MouseEvent.MOUSE_PRESSED)
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
