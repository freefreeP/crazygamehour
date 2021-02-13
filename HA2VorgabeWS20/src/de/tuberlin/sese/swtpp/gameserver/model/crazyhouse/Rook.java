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
		int diffy = Math.abs(this.yTo - this.yFrom);
		int diffx = Math.abs(this.xTo - this.xFrom);
		
		if(diffy > 0 && diffx == 0) {
			int anfang = Math.min(this.yFrom,this.yTo)+1;
			int ende = Math.max(this.yFrom,this.yTo)-1;
			for(int i = anfang; i <= ende; i++) {
				if(this.spielfeld[this.xFrom][i] != 0) return false; 
			}
			return true;
		}
		else if(diffy == 0 && diffx > 0) {
			int anfang = Math.min(this.xFrom,this.xTo)+1;
			int ende = Math.max(this.xFrom,this.xTo)-1;
			for(int i = anfang; i <= ende; i++) {
				if(this.spielfeld[i][this.yFrom] != 0) return false; 
			}
			return true;
		}
		return false;
	}
	
	
}