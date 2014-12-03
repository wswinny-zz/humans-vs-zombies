package map;

import java.awt.image.BufferedImage;

/************************************************************************
 * Class: Tile														
 * Author: William Swinny											
 * 																		
 * Purpose: Acts as one tile on the map and contains information about
 * 			the tile for the state to use																	
 ************************************************************************/
public class Tile
{
	private BufferedImage image;
	private int type;
	
	public static final int NORMAL = 0;
	public static final int ZOMBIE_BLOCKED = 1;
	public static final int BLOCKED = 2;
	
	public Tile(BufferedImage image, int type)
	{
		this.image = image; //set tile image
		this.type = type; //set type of tile
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public int getTileType()
	{
		return type;
	}
}
