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
	
	//map boundaries to check if the player is trying to leave the map
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	
	// map and tile stuff
	private static int tileSize;
	private int numRows;
	private int numCols;
	
	private int numRowsToDraw;
	private int numColsToDraw;
	
	private int colOffset;
	private int rowOffset;
	
	private ArrayList <BufferedImage> tiles; //tiles in the tile set
	private static Tile[][] visibleMap; //tiles on the map
	
	public Map(int tileSize)
	{
		Map.tileSize = tileSize;
		
		this.xMin = 0;
		this.yMin = 0;
		
		this.numRowsToDraw = GamePanel.HEIGHT / tileSize + 2; //number of visible rows
		this.numColsToDraw = GamePanel.WIDTH / tileSize + 2; //number of visible columns
	}
	
	public static Tile[][] getVisibleMap()
	{
		return Map.visibleMap;
	}
	
	public static int getTileSize()
	{
		return Map.tileSize;
	}
	
	//loads the tile set and splits it up
	public void loadTileset(String tileSetResource)
	{
		BufferedImage tileset = null; //tile set image
		try
		{
			tileset = ImageIO.read(Map.class.getResourceAsStream(tileSetResource));
		}
		catch (IOException e)
		{
			System.out.println("ERROR: Failed to load tileset");
		}
		
		this.tiles = new ArrayList<BufferedImage>();
		
		for(int x = 0; x < tileset.getHeight(); x += Map.tileSize)
		{
			for(int y = 0; y < tileset.getWidth(); y += Map.tileSize)
			{
				try
				{
					this.tiles.add(tileset.getSubimage(y, x, Map.tileSize, Map.tileSize)); //gets tile image at x, y
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
	
	//loads the map and parses it
	public void loadMap(String mapFileResource)
	{
		BufferedReader br; //map file reader
		try
		{
			InputStream in = getClass().getResourceAsStream(mapFileResource);
			br = new BufferedReader(new InputStreamReader(in));
			
			String [] WH = br.readLine().split(",");
			
			this.numCols = Integer.parseInt(WH[0]); //width of the map
			this.numRows = Integer.parseInt(WH[1]); //height of the map
			
			this.xMax = (this.numCols * Map.tileSize) - GamePanel.WIDTH;
			this.yMax = (this.numRows * Map.tileSize) - GamePanel.HEIGHT;
			
			Map.visibleMap = new Tile[this.numRows][this.numCols];
			
			String line;
			for(int row = 0; row < this.numRows; ++row)
			{
				line = br.readLine();
				
				String [] numbers = line.split(",");
				
				for(int col = 0; col < numbers.length; ++col)
				{
					try
					{
						//makes a tile by getting the tile image from the tile array
						Map.visibleMap[row][col] = new Tile(this.tiles.get(Integer.parseInt(numbers[col].split(":")[0]) - 1), Integer.parseInt(numbers[col].split(":")[1]));
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}
		catch (NumberFormatException | IOException e)
		{
			System.out.println("ERROR: Failed to read map file.");
		}
	}
	
	//draws the visible part of the map
	public void draw(Graphics g)
	{
		for(int row = this.rowOffset, y = 0; row < this.rowOffset + this.numRowsToDraw; ++row, y += Map.tileSize)
		{
			if(row > this.numRows)
				break;
			
			for(int col = this.colOffset, x = 0; col < this.colOffset + this.numColsToDraw; ++col, x += Map.tileSize)
			{
				if(col > this.numCols)
					break;
				
				try
				{
					//draws the tile at row, col
					g.drawImage(Map.visibleMap[row][col].getImage(), x - (this.x % Map.tileSize), y - (this.y % Map.tileSize), null);
				}
				catch(Exception e) 
				{
					
				}
			}
		}
	}
	
	//sets the position of the map based on x and y being the center
	public void setPosition(int x, int y)
	{
		this.x = x - (GamePanel.WIDTH / 2);
		this.y = y - (GamePanel.HEIGHT / 2);
		
		this.checkBounds(); //checks the bounds of the map to make sure that the player is not trying to walk out of bounds
		
		this.colOffset = this.x / Map.tileSize;
		this.rowOffset = this.y / Map.tileSize;
	}
	
	//checks thet the player is not trying to walk off the map
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
