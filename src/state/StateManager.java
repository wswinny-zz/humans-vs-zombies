package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/************************************************************************
 * Class: StateManager														
 * Author: William Swinny											
 * 																		
 * Purpose: The manager of all the states in the game such as the
 * 			MenuState or the ObjectiveMode																	
 ************************************************************************/
public class StateManager implements KeyListener, MouseListener
{
	public static final int MENU_STATE = 0;
	public static final int OBJECTIVE_STATE = 1;
	public static final int DEATH_STATE = 2;
	
	private static State currentState;
	
	public StateManager()
	{
		StateManager.setState(StateManager.MENU_STATE);
	}
	
	public static void setState(int state)
	{
		if(state == StateManager.MENU_STATE)
			currentState = new MenuState();
		else if(state == StateManager.OBJECTIVE_STATE)
			currentState = new ObjectiveMode();
		else if(state == StateManager.DEATH_STATE)
			currentState = new DeathState();
	}

	public void draw(Graphics g)
	{
		try
		{
			StateManager.currentState.draw(g);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: State Manager failed to draw current state.");
		}
	}
	
	public void update()
	{
		try
		{
			StateManager.currentState.update();
		}
		catch(Exception e)
		{
			System.out.println("ERROR: State Manager failed to update current state.");
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		StateManager.currentState.mouseTriggered(e);
	}

	public void mouseEntered(MouseEvent e)
	{
		StateManager.currentState.mouseTriggered(e);
	}

	public void mouseExited(MouseEvent e)
	{
		StateManager.currentState.mouseTriggered(e);
	}

	public void mousePressed(MouseEvent e)
	{
		StateManager.currentState.mouseTriggered(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		StateManager.currentState.mouseTriggered(e);
	}

	public void keyPressed(KeyEvent e)
	{
		StateManager.currentState.keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		StateManager.currentState.keyReleased(e);
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
}
