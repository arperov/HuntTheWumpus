package model;

import java.util.Observable;

public class Game extends Observable{
	Game(){
		map = new Map(MAP_WIDTH, MAP_HEIGHT);
		placeWumpus();
		placeHunter();
	}
	
	Game(Map m){
		map = m;
		placeWumpus();
		placeHunter();
	}
	
	// Handle input in the GUI class
	// Don't forget to set room to visited when player visits it
	// move/shoot do that then update entities then check if one of them is dead
	// Get status (DEATH_PIT ...
	private void updateEntities(){
		for(Entity e : map.getEntities())
			e.update();
	}
	
	private static void placeWumpus(){
		int row = Math.random();
		int col = -1;
		while(map.getRoomAt(row, col))
	}
	
	private static void placeHunter(){
		
	}
	
	private static final int MAP_HEIGHT = 10;
	private static final int MAP_WIDTH = 10;
	private Map map;
	private Hunter hunter;
	private Wumpus wumpus;
}
