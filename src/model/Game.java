/*
 * Artyom Perov
 *
 * A game instance
 */

package model;

import java.util.Observable;

public class Game extends Observable{
	public Game(){
		map = new Map(MAP_WIDTH, MAP_HEIGHT);
		hunter = map.getHunter();
		wumpus = map.getWumpus();
		stat = Status.Running;
	}

	public void perform(GameAction act, Direction dir){
		
		if(act != null && dir != null & stat == Status.Running){
			switch(act){
				case Move:
					hunter.move(dir);
					break;
				case Shoot:
					hunter.shoot(dir);
					break;
			}
		}
		updateEntities();
		updateStatus();
		setChanged();
		notifyObservers();
	}

	public Status getStatus(){
		return stat;
	}
	
	public Map getMap(){
		return map;
	}
	
	private void updateEntities(){
		for(Entity e : map.getEntities())
			e.update();
	}

	private void updateStatus(){
		if(wumpus.isDead()){
			stat = Status.Success;
		}else if(hunter.isDead()){
			if(wumpus.getRow() == hunter.getRow() && wumpus.getCol() == hunter.getCol())
				stat = Status.DeathByWumpus;
			else if(map.getRoomAt(hunter.getRow(), hunter.getCol()).type == RoomType.Pit)
				stat = Status.DeathByPit;
			else
				stat = Status.DeathByArrow;
		}
	}

	private Map map;
	private Hunter hunter;
	private Wumpus wumpus;
	private Status stat;

	private static final int MAP_HEIGHT = 15;
	private static final int MAP_WIDTH = 15;
}

