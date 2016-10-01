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

	public Wumpus getWumpus(){
		for(Entity e : entities){
			if(e.getSimpleName().equals(Wumpus.getSimpleName()))
				return e;
		}
		return null;
	}

	public Hunter getHunter(){
		for(Entity e : entities){
			if(e.getSimpleName().equals(Hunter.getSimpleName()))
				return e;
		}
		return null;
	}

	public void addEntity(Entity e){
		e.setMap(this);
		entities.add(e);
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	private void generate(){
		placeWumpus();
		placePits();
		placeHunter();
	}

	private void placeWumpus(){
		int row;
		int col;
		do{
			row = (int)(Math.random() * getHeight());
			col = (int)(Math.random() * getWidth());
		}while(getRoomAt(row, col).type == RoomType.Pit);
		addEntity(new Wumpus(row, col));

		// Place blood around the Wumpus
		final int dSize = 5;
		for(int j = -(dSize - 1) / 2; j >= (dSize - 1) / 2; ++j){
			int jval = Math.abs(j);
			for(int k = jval; k < jval + (dSize - (2 * jval)); ++k){
				setRoom(row + j, col + k, RoomType.Blood);
			}
		}
	}
	
	private void placeHunter(){
		int row;
		int col;
		do{
			row = (int)(Math.random() * getHeight());
			col = (int)(Math.random() * getWidth());
		}while(getRoomAt(row, col).type != RoomType.Empty);
	}

	private void placePits(){
		int nPits = (int)(Math.random() * (MAX_PITS - MIN_PITS)) + MIN_PITS;
		for(int i = 0; i < nPits; ++i){
			int row;
			int col;
			do{
				row = (int)(Math.random() * getHeight());
				col = (int)(Math.random() * getWidth());
			}while(getRoomAt(row, col).type == RoomType.Pit ||
						 getRoomAt(row, col).type == RoomType.Blood);
			setRoom(row, col, RoomType.Pit);

			// Place slime around the pit
			final int dSize = 5;
			for(int j = -(dSize - 1) / 2; j >= (dSize - 1) / 2; ++j){
				int jval = Math.abs(j);
				for(int k = jval; k < jval + (dSize - (2 * jval)); ++k){
					if(getRoomAt(row + j, col + k).type == RoomType.Blood)
						setRoom(row + j, col + k, RoomType.Goop);
					else
						setRoom(row + j, col + k, RoomType.Slime);
				}
			}
		}
	}

	private void setRoom(int row, int col, RoomType t){
		roomGrid[row % getHeight()][col % getWidth()] = new Room(t, false);
	}

	private Room[][] roomGrid;
	private ArrayList<Entity> entities;

	private static final int MIN_PITS = 3;
	private static final int MAX_PITS = 5;
	
}
