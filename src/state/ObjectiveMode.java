package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import util.Audio;
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
	
	//inits the state
	public ObjectiveMode()
	{
		ObjectiveMode.map = new Map(64); //sets the map tile size to 64
		ObjectiveMode.player = Player.getInstance();
		ObjectiveMode.zombies = new ArrayList<Zombie>();
		
		ObjectiveMode.hud = new HUD();
		
		this.init();
	}
	
	//gets the objective
	public static Objective getObjective()
	{
		return ObjectiveMode.objective;
	}
	
	//decreases the speed of all zombies
	public static void decreaseZombieSpeedMultiplier()
	{
		for(Zombie z: ObjectiveMode.zombies)
			z.setSpeedMultiplier(z.getSpeedMultiplier() - 0.5);
	}
	
	//increases the speed of all zombies
	public static void increaseZombieSpeedMultiplier()
	{
		for(Zombie z: ObjectiveMode.zombies)
			z.setSpeedMultiplier(z.getSpeedMultiplier() + 0.5);
	}
	
	//adds zombies to the map
	public static void addZombies(int number)
	{
		for(int i = 0; i < number; ++i)
			ObjectiveMode.zombies.add(new Zombie());
	}

	@Override 
	public void init()
	{
		ObjectiveMode.map.loadTileset("/tilesSet.png"); //loads the tileset
		ObjectiveMode.map.loadMap("/map.map"); //loads a specific map
		
		//adds 500 zombies to the map
		for(int i = 0; i < 500; ++i)
			ObjectiveMode.zombies.add(new Zombie());
		
		ObjectiveMode.objective = new Objective();
		
		Audio.getInstance().startBackgroundMusic(); //starts the background music
	}

	@Override
	public void draw(Graphics g)
	{
		ObjectiveMode.map.draw(g); //draws the map
		
		//draws all zombies
		for(int i = 0; i < ObjectiveMode.zombies.size(); ++i)
			ObjectiveMode.zombies.get(i).draw(g);
		
		//draws the objective
		ObjectiveMode.objective.draw(g);
		
		//draws the player and hud
		ObjectiveMode.player.draw(g);
		ObjectiveMode.hud.draw(g);
	}
	
	@Override
	public void update()
	{
		//update the zombies
		for(int i = 0; i < ObjectiveMode.zombies.size(); ++i)
			ObjectiveMode.zombies.get(i).update();
		
		//update the objective
		ObjectiveMode.objective.update();
		
		//update the player and map position
		ObjectiveMode.player.update();
		ObjectiveMode.map.setPosition((int)ObjectiveMode.player.getX(), (int)ObjectiveMode.player.getY());
	}

	/*
	 * Action Listeners
	 */
	
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
