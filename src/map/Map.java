package map;

import entity.Player;
import game.GamePanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/************************************************************************
 * Class: Map Author: William Swinny
 * 
 * Purpose: A class that will load in the main map for the game and draw the map
 * to the screen using the draw method
 ************************************************************************/
public class Map
{
	// map position center of map
	private int x; //x pixel of map image
	private int y; //y pixel of map image
	
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	
	// map and tile stuff
	private int tileSize;
	private int numRows;
	private int numCols;
	
	private int numRowsToDraw;
	private int numColsToDraw;
	
	private int colOffset;
	private int rowOffset;
	
	private ArrayList <BufferedImage> tiles;
	private Tile[][] visibleMap;
	
	public Map(int tileSize)
	{
		this.tileSize = tileSize;
		
		this.xMin = 0;
		this.yMin = 0;
		
		this.numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		this.numColsToDraw = GamePanel.WIDTH / tileSize + 2;
	}
	
	public void loadTileset(String tileSetResource)
	{
		BufferedImage tileset = null;
		try
		{
			tileset = ImageIO.read(Map.class.getResourceAsStream(tileSetResource));
		}
		catch (IOException e)
		{
			System.out.println("ERROR: Failed to load tileset");
		}
		
		this.tiles = new ArrayList<BufferedImage>();
		
		for(int x = 0; x < tileset.getWidth(null); x += this.tileSize)
		{
			for(int y = 0; y < tileset.getHeight(null); y += this.tileSize)
			{
				this.tiles.add(tileset.getSubimage(x, y, this.tileSize, this.tileSize));
			}
		}
	}
	
	public void loadMap(String mapFileResource)
	{
		BufferedReader br;
		try
		{
			InputStream in = getClass().getResourceAsStream(mapFileResource);
			br = new BufferedReader(new InputStreamReader(in));
			
			String [] WH = br.readLine().split(",");
			
			this.numCols = Integer.parseInt(WH[0]);
			this.numRows = Integer.parseInt(WH[1]);
			
			this.xMax = (this.numCols * this.tileSize) - GamePanel.WIDTH;
			this.yMax = (this.numRows * this.tileSize) - GamePanel.HEIGHT;
			
			this.visibleMap = new Tile[this.numRows][this.numCols];
			
			String line;
			for(int row = 0; row < this.numRows; ++row)
			{
				line = br.readLine();
				
				String [] numbers = line.split(",");
				
				for(int col = 0; col < numbers.length; ++col)
					this.visibleMap[row][col] = new Tile(this.tiles.get(Integer.parseInt(numbers[col])), Tile.NORMAL);
			}
		}
		catch (NumberFormatException | IOException e)
		{
			System.out.println("ERROR: Failed to read map file.");
		}
	}
	
	public void draw(Graphics g)
	{
		for(int row = this.rowOffset, y = 0; row < this.rowOffset + this.numRowsToDraw; ++row, y += this.tileSize)
		{
			if(row > this.numRows)
				break;
			
			for(int col = this.colOffset, x = 0; col < this.colOffset + this.numColsToDraw; ++col, x += this.tileSize)
			{
				if(col > this.numCols)
					break;
				
				try
				{
					g.drawImage(this.visibleMap[row][col].getImage(), x - (this.x % this.tileSize), y - (this.y % this.tileSize), null);
				}
				catch(Exception e) 
				{
					
				}
			}
		}
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x - (GamePanel.WIDTH / 2);
		this.y = y - (GamePanel.HEIGHT / 2);
		
		this.checkBounds();
		
		this.colOffset = this.x / this.tileSize;
		this.rowOffset = this.y / this.tileSize;
	}
	
	public void checkBounds()
	{
		if(this.x <= this.xMin)
		{
			this.x = this.xMin;
			Player.getInstance().setX(this.x + (GamePanel.WIDTH / 2));
		}
		
		if(this.y <= this.yMin)
		{
			this.y = this.yMin;
			Player.getInstance().setY(this.y + (GamePanel.HEIGHT / 2));
		}
		
		if(this.x >= this.xMax)
		{
			this.x = this.xMax;
			Player.getInstance().setX(this.x + (GamePanel.WIDTH / 2));
		}
		
		if(this.y >= this.yMax)
		{
			this.y = this.yMax;
			Player.getInstance().setY(this.y + (GamePanel.HEIGHT / 2));
		}
	}
}
