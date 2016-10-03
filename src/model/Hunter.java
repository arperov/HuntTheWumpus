/*
 * Artyom Perov
 *
 * Represents a hunter
 */

package model;

public class Hunter extends Entity {

	public Hunter(int row, int col) {
		super(row, col);
	}
	
	public void move(Direction dir){
		row = map.normalizeRow(row + Direction.getRowOffset(dir));
		col = map.normalizeCol(col + Direction.getColOffset(dir));

		map.getRoomAt(row,  col).visited = true;
	}
	
	public void shoot(Direction dir){
		map.addEntity(new Arrow(row, col, dir));
	}

	@Override
	public void update() {
		if(map.getRoomAt(row, col).type == RoomType.Pit)
			kill();
	}

}
