package model;

public class Hunter extends Entity {

	public Hunter(int row, int col) {
		super(row, col);
	}
	
	public void move(Direction dir){
		row += Direction.getRowOffset(dir);
		col += Direction.getColOffset(dir);
		row %= map.getHeight();
		col %= map.getWidth();
	}
	
	public void shoot(Direction dir){
		map.getEntities().add(new Arrow(row, col, dir));
	}

	@Override
	public void update() {
		if(map.getRoomAt(row, col).type == RoomType.Pit)
			kill();
	}

}
