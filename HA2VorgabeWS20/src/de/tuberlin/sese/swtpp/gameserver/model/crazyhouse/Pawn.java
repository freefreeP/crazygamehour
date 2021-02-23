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
		int offset1, offset2;
		if(ich <= 90) {offset1 = 1; offset2 = 0;}//weiss
		else {offset1 = -1; offset2 = 5;}

		if(diffy == 1*offset1) {
			if(Math.sqrt(Math.pow(diffx,2)) == 1) {//wir bewegen uns eins nach vorne und 1 links oder rechts
				return (this.spielfeld[this.xTo][this.yTo] != 0);//ist dort ein gegner?
			}

			return ((this.xFrom == this.xTo));// nicht start pos. wir können NUR EINS nach vorne

		}else if(diffy == 2*offset1 ) {
			if(this.yFrom == 1 + offset2) {//wir sind an start position
				boolean keinerDazwischen = this.spielfeld[this.xFrom][this.yFrom+offset1] == 0;
				return ((this.xFrom == this.xTo) && keinerDazwischen );// bewegen wir uns 1 oder 2 NUR nach vorne?
			}
			return false;
		}
		
		return false;
	
	}
	
}
