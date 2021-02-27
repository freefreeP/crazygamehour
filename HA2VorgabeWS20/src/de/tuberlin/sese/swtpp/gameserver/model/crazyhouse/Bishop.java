package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

public class Bishop {
	
	
	public int xFrom; 
	public int yFrom;
	public int xTo;
	public int yTo;
	public char[][] spielfeld;
	
	Bishop(int xFrom, int yFrom, int xTo, int yTo, char[][] spielfeld){
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.xTo = xTo;
		this.yTo = yTo;
		this.spielfeld = spielfeld;
	}
	public void richtungsBestimmung() {
		
		if(this.xTo < this.xFrom) {
			int tmp = this.xTo;
			this.xTo = this.xFrom;
			this.xFrom = tmp;
			tmp = this.yTo;
			this.yTo = this.yFrom;
			this.yFrom = tmp;
		}
		
	}
	
	public boolean canI() {
		int diffy = Math.abs(this.yTo - this.yFrom);
		int diffx = Math.abs(this.xTo - this.xFrom);
		if(diffx == diffy) {
			this.richtungsBestimmung();
			int anfangY = Math.max(this.yFrom, this.yTo)-1;
			int endeY = Math.min(this.yFrom, this.yTo)+1;
			if(this.yTo > this.yFrom) {
				anfangY = Math.min(this.yFrom, this.yTo)+1;
				endeY = Math.max(this.yFrom, this.yTo)-1;
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
}
