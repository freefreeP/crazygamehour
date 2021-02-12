package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

public class Rook {

	
	public int xFrom; 
	public int yFrom;
	public int xTo;
	public int yTo;
	public char[][] spielfeld;
	
	Rook(int xFrom, int yFrom, int xTo, int yTo, char[][] spielfeld){
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.xTo = xTo;
		this.yTo = yTo;
		this.spielfeld = spielfeld;
	}
	
	
	public boolean canI() {
		int diffy = (int)Math.sqrt(Math.pow(this.yTo - this.yFrom,2));
		int diffx = (int)Math.sqrt(Math.pow(this.xTo - this.xFrom,2));
		if(diffy > 0) return diffx == 0;
		else return diffx > 0;
	}
	
	
}
