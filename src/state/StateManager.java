package state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/************************************************************************
 * Class: StateManager														
 * Author: William Swinny											
 * 																		
 * Purpose: The manager of all the states in the game such as the
 * 			MenuState or the ObjectiveMode																	
 ************************************************************************/
public class StateManager implements KeyListener, MouseListener, MouseMotionListener
{
	private ArrayList<State> states;
	private int currentState;
	
	public StateManager()
	{
		this.states = new ArrayList<State>();
		this.currentState = 0;
	}
	
	public void switchState(int state)
	{
		
	}
	
	public void registerState(State state)
	{
		
	}
	
	public void removeState(State state)
	{
		
	}

	public void draw(Graphics2D g)
	{
		try
		{
			this.states.get(this.currentState).draw();
		}
		catch(Exception e)
		{
			System.out.println("ERROR: State Manager failed to draw current state.");
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void mouseMoved(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void mouseClicked(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void mouseEntered(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void mouseExited(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void mousePressed(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		this.states.get(this.currentState).mouseTriggered(e);
	}

	public void keyPressed(KeyEvent e)
	{
		this.states.get(this.currentState).keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		this.states.get(this.currentState).keyReleased(e);
	}

	public void keyTyped(KeyEvent e)
	{
		//not used
	}
}
