package game;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.Audio;

/************************************************************************
 * Game															
 * Author: Tara Reeves											
 * 																		
 * Purpose: Sets up the initial functionality for the game																				
 ************************************************************************/
public class Game {
	private JFrame frame;
	private GamePanel gamePanel;
	
	public static void main(String[] args) {
		//Set the look and feel of the application to that of the user's system
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new Game();
	}
	
	public Game(){
		//Set up the properties for the main frame
		frame = new JFrame("HVZ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(800, 600);
				
		//Set the icon image for the application
		ImageIcon img = new ImageIcon("img\\icon.png");
		frame.setIconImage(img.getImage());
				
		//Prepare the gamePanel and add it to the frame
		gamePanel = new GamePanel();
		frame.setContentPane(gamePanel);
		frame.addKeyListener(gamePanel.getKeyListeners()[0]);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.gamePanel.gameLoop();
				
		//Audio.getInstance().startMenuMusic();
	}

}
