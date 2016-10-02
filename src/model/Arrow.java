package model;

public class Arrow extends Entity{
	public Arrow(int row, int col, Direction dir){
		super(row, col);
		this.dir = dir;
	}
	
	@Override
	public void update() {
		for(;;){
			row = map.normalizeRow(
					row += 
					Direction.getRowOffset(dir));
			col = map.normalizeCol(col += Direction.getColOffset(dir));
			
			for(Entity e : map.getEntities()){
				if(e != this && e.getRow() == row && e.getCol() == col){
					e.kill();
					return;
				}
			}
		}
	}
	
	private Direction dir;
}
