/*
 * Artyom Perov
 *
 * Represents a room
 */

package model;

public class Room{
	Room(RoomType type, boolean visited){
		this.type = type;
		this.visited = visited;
	}
	
	public RoomType type;
	public boolean visited;
}
