package Map;

import java.util.Random;

public class Enemy extends Unit{
	int ai;
	public Enemy(Coordinate position, int ai){
		super(position);
		this.ai = ai;
	}
	
	public void chase(int[][] map, Coordinate player){
		Random rd = new Random();
		String[] move = {"UP", "DOWN", "LEFT", "RIGHT"};
		this.move(map, move[rd.nextInt(move.length)]);
	}
}
