package game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import state.StateManager;

/************************************************************************
 * GamePanel														
 * Author: Tara Reeves											
 * 																		
 * Purpose: Panel in which game will take place																			
 ************************************************************************/
public class GamePanel extends JPanel{
	private static final long serialVersionUID = -4228654156182182574L;
	private BufferedImage img;
	private StateManager stateManager;
	private boolean running;
	
	public static final int HEIGHT = 320;
	public static final int WIDTH = 480;
	public static final int SCALE = 2;
	public static final int FPS = 60;
	
	public GamePanel(){
		this.setPreferredSize(new Dimension((WIDTH * SCALE), (HEIGHT * SCALE)));
		this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.stateManager = new StateManager();
		this.running = true;
		
		//Add a mouse listener and key listener to the panel
		this.addMouseListener(this.stateManager);
		this.addMouseMotionListener(this.stateManager);
		this.addKeyListener(this.stateManager);
	}
	
	//Draw the graphics
	public void draw(){
		stateManager.draw(this.img.getGraphics());
	}
	
	public void update(){
		stateManager.update();
	}
	
	//Paint the image onto the component
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, (WIDTH * SCALE), (HEIGHT * SCALE), null);
	}
	
	//Main game loop
	public void gameLoop(){
		long start;
		long elapsed;
		long wait;
		
		//Allow for true FPS
		while(running){
			start = System.nanoTime();
			
			this.update();
			this.draw();
			this.repaint();
			
			//Calculate the time to wait
			elapsed = System.nanoTime() - start;
			wait = ((1000/FPS) - (elapsed/1000000));
			if(wait < 0){
				wait = 5;
			}
			
			//Wait for the specified time
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
