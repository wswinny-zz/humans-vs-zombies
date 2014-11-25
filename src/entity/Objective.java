package entity;

import java.util.Random;

import game.GamePanel;
import map.Map;
import map.Tile;

/************************************************************************
 * Objective														
 * Author: Tara Reeves											
 * 																		
 * Purpose: Acts as an objective the player must reach																		
 ************************************************************************/
public class Objective extends Entity{
	private static int objectiveDuration;
	
	//Constructor
	public Objective(){
		//Generate position 200 from the left and right
		Random random = new Random();
		double xPos = 100 + random.nextInt((GamePanel.WIDTH*GamePanel.SCALE*2)-200);
		double yPos = 100 + random.nextInt((GamePanel.HEIGHT*GamePanel.SCALE*2)-200);

		//if it intersected try again
		while(intersectsWithMap(xPos, yPos)){
			xPos = 100 + random.nextInt((GamePanel.WIDTH*GamePanel.SCALE*2)-200);
			yPos = 100 + random.nextInt((GamePanel.HEIGHT*GamePanel.SCALE*2)-200);
		}		
	}
	
	//Determines whether or not the position given intersects with the map
	public boolean intersectsWithMap(double xPos,double yPos){
		Tile[][] tiles = Map.getVisibleMap();
		int tileWidth = tiles[0][0].getImage().getWidth();
		
		//for each row
		for(int row = 0; row < tiles.length; row++){
			//for each column in row
			for(int col = 0; col < tiles[row].length; col++){
				//if the tile is non-walkable
				if(tiles[row][col].getTileType() == Tile.BLOCKED){
					//if they're within this tile on the x axis
					if(col*tileWidth < xPos+10 && col*tileWidth + tileWidth  > xPos -10){
						//if they're within this tile on y axis
						if(row*tileWidth < yPos+16 && row*tileWidth + tileWidth > yPos -7){
							//tell them no!
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static int getObjectiveDuration() {
		return objectiveDuration;
	}

	public static void setObjectiveDuration(int objectiveDuration) {
		Objective.objectiveDuration = objectiveDuration;
	}
}
