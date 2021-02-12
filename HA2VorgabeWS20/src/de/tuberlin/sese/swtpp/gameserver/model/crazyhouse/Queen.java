package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

public class Queen {

	public int xFrom; 
	public int yFrom;
	public int xTo;
	public int yTo;
	public char[][] spielfeld;
	
	Queen(int xFrom, int yFrom, int xTo, int yTo, char[][] spielfeld){
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.xTo = xTo;
		this.yTo = yTo;
		this.spielfeld = spielfeld;
	}
	
	public boolean canI() {
		int diffy = (int)Math.sqrt(Math.pow(this.yTo - this.yFrom,2));
		int diffx = (int)Math.sqrt(Math.pow(this.xTo - this.xFrom,2));
		
		return (this.canIDiagonal(diffx, diffy) || this.canILine(diffx, diffy));	
	}
	
	
	public boolean canIDiagonal(int diffx, int diffy) {
		return diffx == diffy;
	}
	
public boolean canILine(int diffx, int diffy) {
		if(diffy > 0) return diffx == 0;
		else return diffx > 0;
	}
}
