package Map;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


import Map.Coordinate;
public class Game extends JPanel implements ActionListener, KeyListener {

	public int[][] gameMap;
	public int[][] vision;
	public ArrayList<Room> rooms;
	public Player player;
	public Director dm;
	
	int clock = 0;
	private Timer tm = new Timer(1, this);
	private Coordinate stairs; 
	
	public static final int MAP_HEIGHT = 48;
	public static final int MAP_WIDTH = 64;
	public static final int MAP_DEPTH = 5;
	public static final int PIXELS = 17;
	public static final int ROOMS = 25;
	
	public Game(){
		super();
		dm = new Director();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		this.rooms = new ArrayList<Room>();
		this.gameMap = new int[MAP_WIDTH][MAP_HEIGHT];
		this.vision = new int[MAP_WIDTH][MAP_HEIGHT];
		
		Director.buildRandomMap(gameMap, MAP_WIDTH, MAP_HEIGHT, ROOMS, rooms);

		Room spawn = rooms.get(0);
		Room stair = rooms.get(1);
		Coordinate spw = spawn.randomCoordinate();
		stairs = stair.randomCoordinate();
		
		gameMap[stairs.x][stairs.y] = 3;
		player = new Player(spw);
		
		Director.initializeEnemies(4, rooms);
		tm.start();
	}
	
	@Override
	//0 = Wall
	//1 = Room
	//2 = Hall
	//3 = Stairs
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		for(int i = 0; i < gameMap.length; i++){
			for(int j = 0; j < gameMap[i].length; j++){
				switch(gameMap[i][j]){
				case 0:
					g.setColor(Color.gray);
					break;
				case 1:
					g.setColor(Color.white);
					break;
				case 2: 
					g.setColor(Color.white);
					break;
				case 3:
					g.setColor(Color.blue);
					break;
				}
				
				if(!player.inVision(i, j) && vision[i][j] == 0){
					g.setColor(Color.black);
				}
				else if(!player.inVision(i, j) && vision[i][j] == 1){
					g.setColor(g.getColor().darker().darker().darker());
				}
				else{
					vision[i][j] = 1;
				}
				g.fillRect(i*PIXELS, j*PIXELS, PIXELS, PIXELS);
				
				g.setColor(Color.red);
				g.fillRect(player.position.x * PIXELS, player.position.y * PIXELS, PIXELS, PIXELS);
				
				g.setColor(Color.green);
				for(Enemy enm : Director.enemies.values()){
					if(player.inVision(enm.position.x, enm.position.y)){
						g.fillRect(enm.position.x * PIXELS, enm.position.y * PIXELS, PIXELS, PIXELS);
					}
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			player.move(gameMap, "LEFT");
			break;
		case KeyEvent.VK_RIGHT:
			player.move(gameMap, "RIGHT");
			break;
		case KeyEvent.VK_UP:
			player.move(gameMap, "UP");
			break;
		case KeyEvent.VK_DOWN:
			player.move(gameMap, "DOWN");
			break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(clock < 1000){
			clock++;
			if(Director.enemies.containsKey(player.position)){
				System.out.println(player.health);
			}
			return;
		}
		
		clock = 0;
		if(player.position.equals(stairs)){
			player.floor += 1;
			rooms.clear();

			Director.buildRandomMap(gameMap, MAP_WIDTH, MAP_HEIGHT, ROOMS, rooms);
			
			for(int[] i : vision){
				Arrays.fill(i, 0);
			}
			Room spawn = rooms.get(0);
			Room stair = rooms.get(1);
			player.position = spawn.randomCoordinate();
			stairs = stair.randomCoordinate();
			
			gameMap[stairs.x][stairs.y] = 3;
			
			Director.initializeEnemies(4, rooms);
		}
		
		for(Enemy enm : Director.enemies.values()){
			enm.chase(gameMap, player.position);
		}
		repaint();
	}
}
