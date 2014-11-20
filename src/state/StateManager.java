package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/************************************************************************
 * Class: StateManager														
 * Author: William Swinny											
 * 																		
 * Purpose: The manager of all the states in the game such as the
 * 			MenuState or the ObjectiveMode																	
 ************************************************************************/
public class StateManager implements KeyListener, MouseListener, MouseMotionListener
{
	public static final int MENU_STATE = 0;
	public static final int OBJECTIVE_STATE = 1;
	
	private State currentState;
	
	public StateManager()
	{
		this.setState(StateManager.OBJECTIVE_STATE);
	}
	
	public void setState(int state)
	{
		if(state == StateManager.MENU_STATE)
			this.currentState = new MenuState();
		else if(state == StateManager.OBJECTIVE_STATE)
			this.currentState = new ObjectiveMode();
	}

	public void draw(Graphics g)
	{
		try
		{
			this.currentState.draw(g);
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
			this.currentState.update();
		}
		catch(Exception e)
		{
			System.out.println("ERROR: State Manager failed to update current state.");
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void mouseMoved(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void mouseClicked(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void mouseEntered(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void mouseExited(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void mousePressed(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		this.currentState.mouseTriggered(e);
	}

	public void keyPressed(KeyEvent e)
	{
		this.currentState.keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		this.currentState.keyReleased(e);
	}

	public void keyTyped(KeyEvent e)
	{
		//not used
	}
}
