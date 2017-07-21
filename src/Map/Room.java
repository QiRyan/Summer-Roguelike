package Map;

import java.util.HashSet;
import java.util.Random;

public class Room {
	public Coordinate location;
	public int width;
	public int height;
	
	public Room(Coordinate location, int width, int height){
		this.location = location;
		this.width = width;
		this.height = height;
	}
	
	public Coordinate randomCoordinate(){
		Random rd = new Random();
		return new Coordinate(rd.nextInt(width) + location.x, rd.nextInt(height) + location.y);
	}
	
	//Connects a room to another room (assume the other room is a part of the room network)
	public void connect(Room other, int[][] map){
		Random r = new Random();
		//Choose random starting and ending points for a hallway from a starting and ending room
		Coordinate start = this.randomCoordinate();
		Coordinate end = other.randomCoordinate();
		
		//Draw up by default (up relative to screen)
		int above = 1;
		
		//If the start is above ("below" because of flipped coordinates) the end, draw the hallway downwards
		if(start.y > end.y){
			above = -1;
		}
		for(int i = 0; i < Math.abs(start.y - end.y) + 1; i++){
			int y = start.y + above * i;
			if(map[start.x][y] == 2 && r.nextInt(10) >= r.nextInt(5)){
				return; //Chance to end the hallway if it intersects another one.
			}
			map[start.x][y] = 2;	
		}

		//Following same logic as above, but drawing right by default instead
		int right = 1;
		if(start.x > end.x){
			right = -1;
		}
		//Calculate offset, since we draw vertically first
		int offsety = start.y + above * Math.abs(start.y - end.y);
		for(int i = 1; i < Math.abs(start.x - end.x); i++){
			int x = start.x + right * i;
			if(map[x][offsety] == 2 && r.nextInt(10) >= r.nextInt(5)){
				return; //Chance to end the hallway if it intersects another one.
			}
			map[x][offsety] = 2;
		}
	}
}
