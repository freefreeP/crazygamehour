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
		int diffy = Math.abs(this.yTo - this.yFrom);
		int diffx = Math.abs(this.xTo - this.xFrom);
		
		return (this.canIDiagonal(diffx, diffy) || this.canILine(diffx, diffy));	
	}
	
	
	public boolean canIDiagonal(int diffx, int diffy) {
		if(diffx == diffy) {
			int anfangY;
			int endeY;
			if(this.xTo - this.xFrom < 0) {
				anfangY = Math.max(this.yFrom, this.yTo)-1;
				endeY = Math.min(this.yFrom, this.yTo)+1;
			}else {
				endeY = Math.max(this.yFrom, this.yTo)+1;
				anfangY = Math.min(this.yFrom, this.yTo)-1;
			}
			int anfangX = Math.min(this.xFrom, this.xTo)+1;
			int endeX = Math.max(this.xFrom, this.xTo)-1;
			int iter;
			if(anfangY < endeY) iter = 1;
			else iter = -1;
			int j = anfangY;
			for(int i = anfangX; i <= endeX; i++) {
				if(this.spielfeld[i][j] != 0) return false;
				j+=iter;
			}
			return true;
		}
		return false;
	}
	
public boolean canILine(int diffx, int diffy) {
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