package Map;
import java.util.HashSet;

public class Player extends Unit{	
	public int fog;
	int floor;
	
	public Player(Coordinate position){
		super(position);
		fog = 5;
		floor = 0;
		health = 10;
	}

	public boolean inVision(int x, int y){
		return Math.abs(position.x - x) < fog && Math.abs(position.y - y) < fog;
	}
	
}
