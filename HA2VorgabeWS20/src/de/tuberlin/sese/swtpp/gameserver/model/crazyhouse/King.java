

package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;
//import java.lang.*;
public class King {
	
	
	
	
	
	public int xFrom; 
	public int yFrom;
	public int xTo;
	public int yTo;
	public char[][] spielfeld;
	
	King(int xFrom, int yFrom, int xTo, int yTo, char[][] spielfeld){
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.xTo = xTo;
		this.yTo = yTo;
		this.spielfeld = spielfeld;
	}
	
	public boolean canI() {
		int diffy = this.yTo - this.yFrom;
		int diffx = this.xTo - this.xFrom;
		if(  (  (Math.abs(diffx) == 1) && (Math.abs(diffy) == 0)    )      ||      (  (Math.abs(diffx) == 0) && (Math.abs(diffy) == 1)    )                    ||      (  (Math.abs(diffx) == 1) && (Math.abs(diffy) == 1)    )                   )                                                            {	
		
			return false;
		}
		
		return rasiert_mich_einer(this.spielfeld[this.xFrom][this.yFrom],xFrom,yFrom);	// gehe ich ins schach ?			
	
}
	
	////////////////////// begebe ich mich ins Schach ? ////////////////////////////

	public boolean rasiert_mich_einer(int farbe,int xTo, int yTo) {
		if(farbe <= 90) {
			if(!w_rasiert_mich_pawn(farbe,xTo,yTo)) {return false;}
			if(!w_rasiert_mich_rook(farbe,xTo,yTo)) {return false;}
			if(!w_rasiert_mich_bishop(farbe,xTo,yTo)) {return false;}
			if(!w_rasiert_mich_pferd(farbe,xTo,yTo)) {return false;}
			
			return true;
		}else {
			if(!s_rasiert_mich_pawn(farbe,xTo,yTo)) {return false;}
			if(!s_rasiert_mich_rook(farbe,xTo,yTo)) {return false;}
			if(!s_rasiert_mich_bishop(farbe,xTo,yTo)) {return false;}
			if(!s_rasiert_mich_pferd(farbe,xTo,yTo)) {return false;}
			
			
			return true;
		}
	}
	
	////////////////////// Bauer prüfen ////////////////////////////
	public boolean w_rasiert_mich_pawn(int farbe,int xTo, int yTo) {			
			if( ( (xTo-1 >= 0 && xTo-1 <= 7) && (yTo+1 >= 0 && yTo+1 <= 7) ) && (this.spielfeld[xTo-1][yTo+1] == 'p') ) {return false;}
			if( ( (xTo+1 >= 0 && xTo+1 <= 7) && (yTo+1 >= 0 && yTo+1 <= 7) ) && (this.spielfeld[xTo+1][yTo+1] == 'p') ) {return false;}

			
			
			
			
			return true;
	}
	public boolean s_rasiert_mich_pawn(int farbe,int xTo, int yTo) {	
		if( ( (xTo-1 >= 0 && xTo-1 <= 7) && (yTo-1 >= 0 && yTo-1 <= 7) ) && (this.spielfeld[xTo-1][yTo-1] == 'P') ) {return false;}
		if( ( (xTo+1 >= 0 && xTo+1 <= 7) && (yTo-1 >= 0 && yTo-1 <= 7) ) && (this.spielfeld[xTo+1][yTo-1] == 'P') ) {return false;}
		return true;
		
	}
	
	////////////////////// rook bzw. gerade wege ////////////////////////////
	public boolean w_rasiert_mich_rook(int farbe,int xTo, int yTo) {
		for(int i = this.xTo; i <= 7; i++) {
			if(this.spielfeld[i][this.yTo] >= 97) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[i][this.yTo] >=  97) return false; 
		}
		
		for(int i = this.yTo; i <= 7; i++) {
			if(this.spielfeld[this.xTo][i] >= 97) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[this.xTo][i] >=  97) return false; 
		}
		
		

		
		return true;
	}
	public boolean s_rasiert_mich_rook(int farbe,int xTo, int yTo) {
		for(int i = this.xTo; i <= 7; i++) {
			if(this.spielfeld[i][this.yTo] < 97 && this.spielfeld[this.xTo][i] > 0) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[i][this.yTo] <  97 && this.spielfeld[this.xTo][i] > 0) return false; 
		}
		
		for(int i = this.yTo; i <= 7; i++) {
			if(this.spielfeld[this.xTo][i] < 97 && this.spielfeld[this.xTo][i] > 0) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[this.xTo][i] <  97 && this.spielfeld[this.xTo][i] > 0) return false; 
		}
		
		

		
		return true;
	}
	
	////////////////////// bishop bzw. diagonal ////////////////////////////
	public boolean w_rasiert_mich_bishop(int farbe,int xTo, int yTo) {
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y++;
			if(this.spielfeld[x][y] >= 97) return false; }
			
		
		x = xTo;
		y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y++;
			if(this.spielfeld[x][y] >= 97) return false; }
			
		
		x = xTo;
		y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y--;
			if(this.spielfeld[x][y] >= 97) return false; }
		
		x = xTo;
		y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y--;
			if(this.spielfeld[x][y] >= 97) return false; }
		
		return true;
	}
	public boolean s_rasiert_mich_bishop(int farbe,int xTo, int yTo) {
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y++;
			if(this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0) return false; }
			
		
		x = xTo;
		y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y++;
			if(this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0) return false; }
			
		
		x = xTo;
		y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y--;
			if(this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0) return false; }
		
		x = xTo;
		y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y--;
			if(this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0) return false; }
		
		return true;
	}

	////////////////////// pferd ////////////////////////////
	public boolean w_rasiert_mich_pferd(int farbe,int xTo, int yTo) {
		int x = xTo, y = yTo;
		//1 nach vorne dann 3 links oder 3 rechts
		if( (y+1 >= 0 && y+1 <= 7 ) && (x+3 >= 0 && x+3 <= 7 ) && this.spielfeld[x][y] >= 97  ) {return false;}
		if( (y+1 >= 0 && y+1 <= 7 ) && (x-3 >= 0 && x-3 <= 7 ) && this.spielfeld[x][y] >= 97  ) {return false;}
		if( (y-1 >= 0 && y-1 <= 7 ) && (x-3 >= 0 && x-3 <= 7 ) && this.spielfeld[x][y] >= 97  ) {return false;}
		if( (y-1 >= 0 && y-1 <= 7 ) && (x+3 >= 0 && x+3 <= 7 ) && this.spielfeld[x][y] >= 97  ) {return false;}

		
		
		return true;
	}
	public boolean s_rasiert_mich_pferd(int farbe,int xTo, int yTo) {
		int x = xTo, y = yTo;
		//1 nach vorne dann 3 links oder 3 rechts
		if( (y+1 >= 0 && y+1 <= 7 ) && (x+3 >= 0 && x+3 <= 7 ) && this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0  ) {return false;}
		if( (y+1 >= 0 && y+1 <= 7 ) && (x-3 >= 0 && x-3 <= 7 ) && this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0  ) {return false;}
		if( (y-1 >= 0 && y-1 <= 7 ) && (x-3 >= 0 && x-3 <= 7 ) && this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0  ) {return false;}
		if( (y-1 >= 0 && y-1 <= 7 ) && (x+3 >= 0 && x+3 <= 7 ) && this.spielfeld[x][y] <  97 && this.spielfeld[x][y] > 0  ) {return false;}

		
		
		return true;
	}
	


}
	
	
	
	