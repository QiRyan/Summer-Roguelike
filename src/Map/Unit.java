package Map;

public class Unit {
	Coordinate position;
	int health;
	int stamina;
	int level;
	
	public Unit(Coordinate position){
		this.position = position;
	}
	public void move(int[][] map, String move){
		int vert = 0;
		int hori = 0;
	
		
		if(move.equals("UP")){
			vert = -1;
		}
		else if(move.equals("DOWN")){
			vert = 1;
		}
		else if(move.equals("LEFT")){
			hori = -1;
		}
		else{ //RIGHT
			hori = 1;
		}
	
		if(map[position.x + hori][position.y + vert] == 0){
			return;
		}

		switch(move){
		case "UP":
			position.y -= 1;
			break;
		case "DOWN":
			position.y += 1;
			break;
		case "LEFT":
			position.x -= 1;
			break;
		case "RIGHT":
			position.x += 1;
			break;
		}
	}
}
