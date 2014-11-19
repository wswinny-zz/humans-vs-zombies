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
	private boolean type;
	
	public static final boolean NORMAL = true;
	public static final boolean BLOCKED = false;
	
	public Tile(BufferedImage image, boolean type)
	{
		this.image = image;
		this.type = type;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public boolean getTileType()
	{
		return type;
	}
}
