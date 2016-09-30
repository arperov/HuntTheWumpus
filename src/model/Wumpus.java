package model;

public class Wumpus extends Entity {

	public Wumpus(int row, int col) {
		super(row, col);
	}

	@Override
	public void update() {
		for(Entity e : map.getEntities()){
			if(e != this && e.getCol() == col && e.getRow() == row){
				e.kill();
				break;
			}
		}
	}

}
