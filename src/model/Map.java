package model;

import java.util.ArrayList;

public class Map {
	Map(int width, int height){
		roomGrid = new Room[height][width];
		entities = new ArrayList<Entity>();
		generate();
	}
	
	Map(String[] grid){
		entities = new ArrayList<Entity>();
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
			if(e.getClass().getSimpleName().equals(Wumpus.class.getSimpleName()))
				return (Wumpus)e;
		}
		return null;
	}

	public Hunter getHunter(){
		for(Entity e : entities){
			if(e.getClass().getSimpleName().equals(Hunter.class.getSimpleName()))
				return (Hunter)e;
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
	
	public int normalizeRow(int r){
		return (r % getHeight() + getHeight()) % getHeight();
	}
	
	public int normalizeCol(int c){
		return (c % getWidth() + getWidth()) % getWidth();
	}
	
	public void setAllRooms(boolean b){
		for(int j = 0; j < getHeight(); ++j){
			for(int k = 0; k < getWidth(); ++k){
				roomGrid[j][k].visited = b;
			}
		}
	}
	
	private void generate(){
		for(int j = 0; j < getHeight(); ++j){
			for(int k = 0; k < getWidth(); ++k){
				roomGrid[j][k] = new Room(RoomType.Empty, false);
			}
		}
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
		for(int j = -(dSize - 1) / 2; j <= (dSize - 1) / 2; ++j){
			int jval = Math.abs(j);
			for(int k = jval; k < jval + (dSize - (2 * jval)); ++k){
				setRoom(row + j, col + k - (dSize - 1) / 2, RoomType.Blood);
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
		addEntity(new Hunter(row, col));
		roomGrid[row][col].visited = true;
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
						 getRoomAt(row, col).type == RoomType.Blood ||
						 getRoomAt(row, col).type == RoomType.Slime);
			setRoom(row, col, RoomType.Pit);

			// Place slime around the pit
			final int dSize = 3;
			for(int j = -(dSize - 1) / 2; j <= (dSize - 1) / 2; ++j){
				int jval = Math.abs(j);
				for(int k = jval; k < jval + (dSize - (2 * jval)); ++k){
					if(getRoomAt(normalizeRow(row + j), normalizeCol(col + k - (dSize - 1) / 2)).type == RoomType.Blood ||
						 getRoomAt(normalizeRow(row + j), normalizeCol(col + k - (dSize - 1) / 2)).type == RoomType.Goop)
						setRoom(row + j, col + k - (dSize - 1) / 2, RoomType.Goop);
					else if(getRoomAt(normalizeRow(row + j), normalizeCol(col + k - (dSize - 1) / 2)).type != RoomType.Pit)
						setRoom(row + j, col + k - (dSize - 1) / 2, RoomType.Slime);
				}
			}
		}
	}

	private void setRoom(int row, int col, RoomType t){
		roomGrid[normalizeRow(row)][normalizeCol(col)] = new Room(t, false);
	}

	private Room[][] roomGrid;
	private ArrayList<Entity> entities;

	private static final int MIN_PITS = 3;
	private static final int MAX_PITS = 5;
	
}
