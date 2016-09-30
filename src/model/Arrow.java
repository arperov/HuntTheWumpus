package model;

public class Arrow extends Entity{
	public Arrow(int row, int col, Direction dir){
		super(row, col);
		this.dir = dir;
	}
	
	@Override
	public void update() {
		for(;;){
			row += Direction.getRowOffset(dir);
			col += Direction.getColOffset(dir);
			row %= map.getHeight();
			col %= map.getWidth();
			
			for(Entity e : map.getEntities()){
				if(e != this && e.getRow() == row && e.getCol() == col){
					e.kill();
					map.getEntities().remove(this);
					return;
				}
			}
		}
	}
	
	private Direction dir;
}
