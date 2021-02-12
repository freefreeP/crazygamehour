package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

public class Pawn {
	
	public int xFrom; 
	public int yFrom;
	public int xTo;
	public int yTo;
	public char[][] spielfeld;
	
	Pawn(int xFrom, int yFrom, int xTo, int yTo, char[][] spielfeld){
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.xTo = xTo;
		this.yTo = yTo;
		this.spielfeld = spielfeld;
	}
	
	public boolean canI() {
		int ich = this.spielfeld[this.xFrom][this.yFrom];
		int diffy = this.yTo - this.yFrom;
		int diffx = this.xTo - this.xFrom;
		if(ich <= 90) {			//weiss
			if(Math.sqrt(Math.pow(diffx,2)) == 1 && diffy == 1) {//wir bewegen uns eins nach vorne und 1 links oder rechts
				return (this.spielfeld[this.xTo][this.yTo] != 0);//ist dort ein gegner?
			}
			if(this.yFrom == 1) {//wir sind an start position
				return ((diffy == 1 || diffy == 2) && this.xFrom == this.xTo);// bewegen wir uns 1 oder 2 NUR nach vorne?
			}
			return (diffy == 1 && this.xFrom == this.xTo);// nicht start pos. wir können NUR EINS nach vorne
		}
		else {					//schwarz rest gilt 'umgekehrt' für restl fälle
			if(Math.sqrt(Math.pow(diffx,2)) == 1 && diffy == -1) {
				return (this.spielfeld[this.xTo][this.yTo] != 0);//ist dort ein gegner?
			}
			if(this.yFrom == 6) {
				return ((diffy == -1 || diffy == -2) && this.xFrom == this.xTo);
			}
			return (diffy == -1 && this.xFrom == this.xTo);
		}
	}	
}
