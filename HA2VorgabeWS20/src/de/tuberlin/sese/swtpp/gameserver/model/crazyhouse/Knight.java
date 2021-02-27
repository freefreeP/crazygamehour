package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

public class Knight {
	
	
	public int xFrom; 
	public int yFrom;
	public int xTo;
	public int yTo;
	public char[][] spielfeld;
	
	Knight(int xFrom, int yFrom, int xTo, int yTo, char[][] spielfeld){
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.xTo = xTo;
		this.yTo = yTo;
		this.spielfeld = spielfeld;
	}
	
	
	public boolean canI() {
		int diffy = (int)Math.sqrt(Math.pow(this.yTo - this.yFrom,2));
		int diffx = (int)Math.sqrt(Math.pow(this.xTo - this.xFrom,2));
		
		if(diffy == 2) return diffx == 1;
		else if(diffy == 1) return diffx == 2;
	
		return false;
	}
	

}
