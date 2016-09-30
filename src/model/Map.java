package model;

import java.util.ArrayList;

public class Map {
	Map(int width, int height){
		roomGrid = new Room[height][width];
		generate();
	}
	
	Map(String[] grid){
		roomGrid = new Room[grid.length][grid[0].length()];
		for(int j = 0; j < roomGrid.length; ++j){
			for(int k = 0; k < roomGrid[0].length; ++k){
				if(grid[j].length() > k){
					for(RoomType r : RoomType.values()){
						if(r.getChar() == grid[j].charAt(k)){
							roomGrid[j][k].type = r;
							break;
						}
					}
					if(roomGrid[j][k] == null)
						roomGrid[j][k] = new Room(RoomType.Empty, false);
				}else{
					roomGrid[j][k] = new Room(RoomType.Empty, false);
				}
			}
		}
	}
	
	public int getHeight(){
		return roomGrid.length;
	}
	
	public int getWidth(){
		return roomGrid[0].length;
	}
	
	public Room getRoomAt(int row, int col){
		if(row < 0 || col < 0 || row >= roomGrid.length || col >= roomGrid[0].length)
			return null;
		else
			return roomGrid[row][col];
	}
	
	private void generate(){
		//TODO
	}
	
	public void addEntity(Entity e){
		e.setMap(this);
		entities.add(e);
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	private Room[][] roomGrid;
	private ArrayList<Entity> entities;
	
}
