package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/************************************************************************
 * Class: State														
 * Author: William Swinny											
 * 																		
 * Purpose: An abstract class that will define the requirements for a
 * 			game state																	
 ************************************************************************/
public abstract class State
{
	public abstract void init();
	
	public abstract void draw(Graphics g);
	
	public abstract void update();
	
	public abstract void mouseTriggered(MouseEvent e);
	
	public abstract void keyPressed(KeyEvent e);
	
	public abstract void keyReleased(KeyEvent e);
}
