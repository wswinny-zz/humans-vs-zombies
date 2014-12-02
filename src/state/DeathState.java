package state;

import entity.Player;
import game.GamePanel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Audio;

public class DeathState extends State
{
	private BufferedImage background;
	
	private BufferedImage replay_light;
	private BufferedImage replay_dark;
	private BufferedImage quit_light;
	private BufferedImage quit_dark;
	
	private int selected;
	
	public DeathState()
	{
		try
		{
			this.background = ImageIO.read(MenuState.class.getResourceAsStream("/death_background.png"));
			
			this.replay_light = ImageIO.read(MenuState.class.getResourceAsStream("/replay_light.png"));
			this.replay_dark = ImageIO.read(MenuState.class.getResourceAsStream("/replay_dark.png"));
			this.quit_light = ImageIO.read(MenuState.class.getResourceAsStream("/quit_light.png"));
			this.quit_dark = ImageIO.read(MenuState.class.getResourceAsStream("/quit_dark.png"));
		}
		catch (IOException e)
		{
			System.out.println("ERROR: Failed to load menu images.");
		}
		
		this.selected = 0;
		
		Player.reset();
		
		this.init();
	}
	
	@Override
	public void init()
	{
		Audio.getInstance().playDeath();
	}

	@Override
	public void draw(Graphics g)
	{
		g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.drawImage(this.background, (GamePanel.WIDTH / 2) - (this.background.getWidth() / 2), 0, null);
		
		if(this.selected == 0)
			g.drawImage(this.replay_light, (GamePanel.WIDTH / 2) - (this.replay_light.getWidth() / 2), GamePanel.HEIGHT - 70, null);
		else
			g.drawImage(this.replay_dark, (GamePanel.WIDTH / 2) - (this.replay_dark.getWidth() / 2), GamePanel.HEIGHT - 70, null);
		
		if(this.selected == 1)
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
			if(this.selected == 2) this.selected = 0;
			
			Audio.getInstance().playMenuEffect();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			this.selected--;
			if(this.selected == -1) this.selected = 1;
			
			Audio.getInstance().playMenuEffect();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			Audio.getInstance().stopMusic();
			
			if(this.selected == 0)
				StateManager.setState(StateManager.OBJECTIVE_STATE);
			else if(this.selected == 1)
				System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
	
}
