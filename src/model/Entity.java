/*
 * Artyom Perov
 *
 * Represents an entity
 */

package model;

public abstract class Entity {
	public Entity(int row, int col){
		this.row = row;
		this.col = col;
		isAlive = true;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public boolean isDead(){
		return !isAlive;
	}
	
	public void kill(){
		isAlive = false;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setPos(int row, int col){
		this.row = map.normalizeRow(row);
		this.col = map.normalizeCol(col);
	}
	
	public abstract void update();
	
	protected int row;
	protected int col;
	protected boolean isAlive;
	protected Map map;
}
