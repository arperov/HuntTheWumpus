/*
 * Artyom Perov
 *
 * Represents a direction in the game world
 */
package model;

public enum Direction {
	North,
	West,
	South,
	East;
	
	public static int getRowOffset(Direction dir){
		switch(dir){
			case North:
				return -1;
			case South:
				return 1;
			default:
				return 0;
		}
	}
	
	public static int getColOffset(Direction dir){
		switch(dir){
			case West:
				return -1;
			case East:
				return 1;
			default:
				return 0;
		}
	}
}
