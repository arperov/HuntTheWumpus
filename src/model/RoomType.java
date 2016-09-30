package model;

public enum RoomType {
	Empty(' '),
	Blood('B'),
	Slime('S'),
	Pit('P'),
	Goop('G');
	
	private RoomType(char c){
		cVal = c;
	}
	
	public char getChar(){
		return cVal;
	}
	
	private char cVal;
}
