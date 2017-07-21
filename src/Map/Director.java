package Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Director {
	private static Random rd = new Random();
	public static HashMap<Coordinate, Enemy> enemies = new HashMap<Coordinate, Enemy>();;
	
	public Director(){
		//Default
	}
	
	public static void buildRandomMap(int[][] gameMap, int width, int height, int maxRoom, ArrayList<Room> rooms){
		for(int[] i : gameMap){
			Arrays.fill(i, 0);
		}
		for(int i = 0; i < maxRoom; i++){
			int rheight = rd.nextInt(9) + 1;
			int rwidth = rd.nextInt(9) + 1; 
			int rx = rd.nextInt(width - rwidth - 1) + 1;
			int ry = rd.nextInt(height - rheight - 1) + 1;
			
			Coordinate roomcoord = new Coordinate(rx, ry);
			boolean newRoom = true;
			if((rx - 1 >= 0 && gameMap[rx - 1][ry] == 1) || (ry - 1 >= 0 && gameMap[rx][ry - 1] == 1)){
				newRoom = false;
			}
			
			for(int j = rx; j < rx + rwidth; j++){
				for(int k = ry; k < ry + rheight; k++){
					if(gameMap[j][k] == 1 || gameMap[j][k + 1] == 1 || gameMap[j + 1][k] == 1){
						newRoom = false;
					}
					gameMap[j][k] = 1;
				}
			}
			
			if(newRoom){
				rooms.add(new Room(roomcoord, rwidth, rheight));
			}
		}
		
		HashSet<Room> connected = new HashSet<Room>();
		for(Room r : rooms){
			if(connected.isEmpty()){
				connected.add(r);
			}
			else{
				int index = rd.nextInt(connected.size());
				int temp = 0;
				for(Room connect : connected){
					if(temp == index){
						connected.add(r);
						r.connect(connect, gameMap);
						break;
					}
					else{
						temp++;
					}
				}
			}
		}
	}
	
	public static void initializeEnemies(int number, ArrayList<Room> rooms){
		Random r = new Random();
		enemies.clear();
		for(int i = 0; i < number; i++){
			Room spawn = rooms.get(r.nextInt(rooms.size() - 1) + 1);
			Coordinate random = spawn.randomCoordinate();
			enemies.put(random, new Enemy(random, 0));
		}
	}
}
