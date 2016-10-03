/*
 * Artyom Perov
 * 
 * Tests for the model package
 */

package tests;

import static org.junit.Assert.*;

import model.Direction;
import model.Game;
import model.GameAction;
import model.Hunter;
import model.Map;
import model.RoomType;
import model.Status;
import model.Wumpus;
import model.Entity;

public class Test {

	@org.junit.Test
	public void test() {
		Game g = new Game();
		assertNotEquals(null, g.getMap());
		Hunter h = g.getMap().getHunter();
		assertNotEquals(null, h);
		int row = h.getRow();
		int col = h.getCol();
		g.perform(GameAction.Move, Direction.North);
		assertEquals(h.getRow(), g.getMap().normalizeRow(row - 1));
		assertEquals(h.getCol(), col);
		h.kill();
		g.perform(GameAction.Move, Direction.South);
		assertEquals(g.getStatus(), Status.DeathByArrow);

		g = new Game();
		g.perform(GameAction.Shoot, Direction.North);
		h = g.getMap().getHunter();
		g.getMap().setAllRooms(true);
		
		for(Entity e : g.getMap().getEntities()){
			if(e.getClass().getSimpleName().equals("Arrow")){
				if(e.getRow() == h.getRow() && e.getCol() == h.getCol())
					assertEquals(g.getStatus(), Status.DeathByArrow);
				else
					assertEquals(g.getStatus(), Status.Success);
				g.getMap().getRoomAt(e.getRow(), e.getCol()).type.getChar();
			}
		}
		
		g = new Game();
		g.getMap().getWumpus().kill();
		g.perform(GameAction.Move, Direction.North);
		assertEquals(Status.Success, g.getStatus());
		
		
		g = new Game();
		Map m = g.getMap();
		m.getHunter().setPos(m.getWumpus().getRow(), m.getWumpus().getCol());
		g.perform(null, null);
		assertEquals(Status.DeathByWumpus, g.getStatus());
		
		g = new Game();
		m = g.getMap();
		for(row = 0; row < m.getHeight(); ++row){
			for(col = 0; col < m.getWidth(); ++col){
				if(m.getRoomAt(row, col).type == RoomType.Pit){
					m.getHunter().setPos(row, col);
					g.perform(null, null);
					assertEquals(Status.DeathByPit, g.getStatus());
					break;
				}
			}
		}
		
	}

}
