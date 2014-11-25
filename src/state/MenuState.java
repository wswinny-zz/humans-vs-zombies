package state;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Audio;

/************************************************************************
 * Class: MenuState														
 * Author: William Swinny											
 * 																		
 * Purpose: The menu that will be shown when the user starts the game																	
 ************************************************************************/
public class MenuState extends State
{
	private BufferedImage background;
	
	private BufferedImage play_light;
	private BufferedImage play_dark;
	private BufferedImage options_light;
	private BufferedImage options_dark;
	private BufferedImage quit_light;
	private BufferedImage quit_dark;
	
	private int selected;
	
	public MenuState()
	{
		this.init();
	}

	@Override
	public void init()
	{
		try
		{
			this.background = ImageIO.read(MenuState.class.getResourceAsStream("/background.png"));
			
			this.play_light = ImageIO.read(MenuState.class.getResourceAsStream("/play_light.png"));
			this.play_dark = ImageIO.read(MenuState.class.getResourceAsStream("/play_dark.png"));
			this.options_light = ImageIO.read(MenuState.class.getResourceAsStream("/options_light.png"));
			this.options_dark = ImageIO.read(MenuState.class.getResourceAsStream("/options_dark.png"));
			this.quit_light = ImageIO.read(MenuState.class.getResourceAsStream("/quit_light.png"));
			this.quit_dark = ImageIO.read(MenuState.class.getResourceAsStream("/quit_dark.png"));
		}
		catch (IOException e)
		{
			System.out.println("ERROR: Failed to load menu images.");
		}
		
		this.selected = 0;
		
		Audio.getInstance().startMenuMusic();
	}

	@Override
	public void draw(Graphics g)
	{
		g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.drawImage(this.background, (GamePanel.WIDTH / 2) - (this.background.getWidth() / 2), 0, null);
		
		if(this.selected == 0)
			g.drawImage(this.play_light, (GamePanel.WIDTH / 2) - (this.play_light.getWidth() / 2), GamePanel.HEIGHT - 100, null);
		else
			g.drawImage(this.play_dark, (GamePanel.WIDTH / 2) - (this.play_dark.getWidth() / 2), GamePanel.HEIGHT - 100, null);
		
		if(this.selected == 1)
			g.drawImage(this.options_light, (GamePanel.WIDTH / 2) - (this.options_light.getWidth() / 2), GamePanel.HEIGHT - 70, null);
		else
			g.drawImage(this.options_dark, (GamePanel.WIDTH / 2) - (this.options_dark.getWidth() / 2), GamePanel.HEIGHT - 70, null);
		
		if(this.selected == 2)
			g.drawImage(this.quit_light, (GamePanel.WIDTH / 2) - (this.quit_light.getWidth() / 2), GamePanel.HEIGHT - 40, null);
		else
			g.drawImage(this.quit_dark, (GamePanel.WIDTH / 2) - (this.quit_dark.getWidth() / 2), GamePanel.HEIGHT - 40, null);
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void mouseTriggered(MouseEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			this.selected++;
			if(this.selected == 3) this.selected = 0;
			
			Audio.getInstance().playMenuEffect();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			this.selected--;
			if(this.selected == -1) this.selected = 2;
			
			Audio.getInstance().playMenuEffect();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			Audio.getInstance().stopMusic();
			
			if(this.selected == 0)
				StateManager.setState(StateManager.OBJECTIVE_STATE);
			else if(this.selected == 1)
				System.out.println("OPTIONS");
			else if(this.selected == 2)
				System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
