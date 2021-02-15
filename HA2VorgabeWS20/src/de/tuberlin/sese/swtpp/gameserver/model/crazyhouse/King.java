

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
		if( !((xTo >= 0 && xTo <= 7 ) && (yTo >= 0 && yTo <= 7))  ) return false;
		int diffy = this.yTo - this.yFrom;
		int diffx = this.xTo - this.xFrom;
		if(  !((  (Math.abs(diffx) == 1) && (Math.abs(diffy) == 0)    )      ||      (  (Math.abs(diffx) == 0) && (Math.abs(diffy) == 1)    )                    ||      (  (Math.abs(diffx) == 1) && (Math.abs(diffy) == 1)    )                 )  )                                                            {	
		
			return false;
		}
		
		//if(spielfeld[this.xTo][this.xTo])
		
		return rasiert_mich_einer(this.spielfeld[this.xFrom][this.yFrom],xTo,yTo);	// gehe ich ins schach ?			
	
}
	
	public boolean rasiert_mich_könig(int farbe,int xTo, int yTo) {
		if(farbe <= 90) {
			if(this.spielfeld[xTo+1][yTo+1] == 107) return false;
			return true;
		} else {
			if(this.spielfeld[xTo+1][yTo+1] == 75) return false;
			return true;
		}
	}

	
	
	
	////////////////////// begebe ich mich ins Schach ? ////////////////////////////

	public boolean rasiert_mich_einer(int farbe,int xTo, int yTo) {
		if(farbe <= 90) {
			if(!w_rasiert_mich_pawn(farbe,xTo,yTo)) {return false;}
			if(!w_rookrasiert(farbe,xTo,yTo)) {return false;}
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
			if( ( (xTo-1 >= 0 && xTo-1 <= 7) && (yTo+1 >= 0 && yTo+1 <= 7) ) && ( (this.spielfeld[xTo-1][yTo+1] == 'p') ||(this.spielfeld[xTo-1][yTo+1] == 107)  )) {return false;}
			return w_rasiert_rest(farbe, xTo,  yTo);
			
			
			//return true;
	}
	
	public boolean w_rasiert_rest(int farbe,int xTo, int yTo) {
		if( ( (xTo+1 >= 0 && xTo+1 <= 7) && (yTo+1 >= 0 && yTo+1 <= 7) ) && (this.spielfeld[xTo+1][yTo+1] == 'p') ||(this.spielfeld[xTo-1][yTo+1] == 107) ) {return false;} else {return true;}

	}
	
	public boolean s_rasiert_mich_pawn(int farbe,int xTo, int yTo) {	
		if( ( (xTo-1 >= 0 && xTo-1 <= 7) && (yTo-1 >= 0 && yTo-1 <= 7) ) && (this.spielfeld[xTo-1][yTo-1] == 'P') ||(this.spielfeld[xTo-1][yTo+1] == 75) ) {return false;}
		return w_rasiert_rest(farbe, xTo,  yTo);		
	}
	
	public boolean s_rasiert_rest(int farbe,int xTo, int yTo) {
		if( ( (xTo+1 >= 0 && xTo+1 <= 7) && (yTo+1 >= 0 && yTo+1 <= 7) ) && (this.spielfeld[xTo+1][yTo+1] == 'p') ||(this.spielfeld[xTo-1][yTo+1] == 75) ) {return false;} else {return true;}

	}
	
	////////////////////// rook bzw. gerade wege ////////////////////////////
	
	
	
	public boolean w_rookrasiert(int farbe,int xTo, int yTo) {
		
		for(int i = this.xTo; i <= 7; i++) {
			if(this.spielfeld[i][this.yTo] == 107 || this.spielfeld[i][this.yTo] == 114  ) return false; 
		}
		
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[i][this.yTo] == 107 || this.spielfeld[i][this.yTo] == 114) return false; 
		}
		
		
		
		if( w_rasiert_mich_rook_rest( farbe, xTo,  yTo) == false ) return false;

		
		

		
		return true;
	}
	
	
	public boolean w_rasiert_mich_rook_rest(int farbe,int xTo, int yTo) {
		for(int i = this.yTo; i <= 7; i++) {
			if(this.spielfeld[this.xTo][i] == 107 || this.spielfeld[this.xTo][i] == 114) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[this.xTo][i] == 107 || this.spielfeld[this.xTo][i] == 114) return false; 
		}
		
		
		
		
		
		
		return true;
	}
	
	
	
	
	
	
	
	public boolean s_rasiert_mich_rook(int farbe,int xTo, int yTo) {
		for(int i = this.xTo; i <= 7; i++) {
			if(this.spielfeld[i][this.yTo] == 75 || this.spielfeld[i][this.yTo] == 82) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[i][this.yTo] == 75 || this.spielfeld[i][this.yTo] == 82) return false; 
		}
		
		
		if( s_rasiert_mich_rook_rest( farbe, xTo,  yTo) == false ) return false;
		
		
		
		
		
		

		
		return true;
	}

	public boolean s_rasiert_mich_rook_rest(int farbe,int xTo, int yTo) {
		for(int i = this.yTo; i <= 7; i++) {
			if(this.spielfeld[this.xTo][i] == 75 || this.spielfeld[this.xTo][i] == 82) return false; 
		}
		
		for(int i = this.xTo; i >= 0; i = i-1) {
			if(this.spielfeld[this.xTo][i] == 75 || this.spielfeld[this.xTo][i] == 82) return false; 
		}
		
		return true;
	}
	
	
	
	
	
	////////////////////// bishop bzw. diagonal ////////////////////////////
	
	
	
	
	public boolean w_diagonal_oben_rechts(int farbe,int xTo, int yTo){
		
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y++;
			if(this.spielfeld[x][y] == 107 || this.spielfeld[x][y] == 113) return false; }
	

	return true;}	
	public boolean w_diagonal_oben_links(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y++;
			if(this.spielfeld[x][y] == 107 || this.spielfeld[x][y] == 113) return false; }
		return true;}
	public boolean w_diagonal_unten_rechts(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y--;
			if(this.spielfeld[x][y]== 107|| this.spielfeld[x][y] == 113) return false; }
		return true;
	}
	public boolean w_diagonal_unten_links(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y--;
			if(this.spielfeld[x][y] == 107|| this.spielfeld[x][y] == 113) return false; }
		
		return true;
	}
	

	public boolean s_diagonal_oben_rechts(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y++;
			if(this.spielfeld[x][y] == 75 || this.spielfeld[x][y] == 81) return false; }
		
		return true;
	}
	public boolean s_diagonal_oben_links(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo; 
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y++;
			if(this.spielfeld[x][y] == 75|| this.spielfeld[x][y] == 81) return false; }
		return true;
		
	}
	public boolean s_diagonal_unten_rechts(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x++;
			y--;
			if(this.spielfeld[x][y] == 75|| this.spielfeld[x][y] == 81) return false; }
		
		return true;
	}
	public boolean s_diagonal_unten_links(int farbe,int xTo, int yTo){
		int x = xTo;
		int y = yTo;
		while( (x > 0 && x < 7) && (y > 0 && y < 7) ) {
			x--;
			y--;
			if(this.spielfeld[x][y] == 75|| this.spielfeld[x][y] == 81) return false; }
		return true;
	}
	
	public boolean w_rasiert_mich_bishop(int farbe,int xTo, int yTo) {
		if(w_diagonal_oben_rechts( farbe, xTo,  yTo) == false) return false;
		if(w_diagonal_oben_links( farbe, xTo,  yTo) == false) return false;
		if(w_diagonal_unten_rechts( farbe, xTo,  yTo) == false) return false;
		if(w_diagonal_unten_links( farbe, xTo,  yTo) == false) return false;
				
			
			return true;
		
	}
	public boolean s_rasiert_mich_bishop(int farbe,int xTo, int yTo) {
		
	if(s_diagonal_oben_rechts( farbe, xTo,  yTo) == false) return false;
	if(s_diagonal_oben_links( farbe, xTo,  yTo) == false) return false;
	if(s_diagonal_unten_rechts( farbe, xTo,  yTo) == false) return false;
	if(s_diagonal_unten_links( farbe, xTo,  yTo) == false) return false;
			
		
		return true;
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	////////////////////// pferd ////////////////////////////
	public boolean w_pferd1(int farbe,int xTo, int yTo) {
		int x = xTo, y = yTo;
		if( (y+1 >= 0 && y+1 <= 7 ) && (x+2 >= 0 && x+2 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
		return true;

	}
public boolean w_pferd2(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y+1 >= 0 && y+1 <= 7 ) && (x-2 >= 0 && x-2 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
	return true;

	}
public boolean w_pferd3(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y-1 >= 0 && y-1 <= 7 ) && (x-2 >= 0 && x-2 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
	return true;

}
public boolean w_pferd4(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y-1 >= 0 && y-1 <= 7 ) && (x+2 >= 0 && x+2 <= 7 ) && this.spielfeld[x][y]==  98  ) {return false;}
	return true;
}


public boolean w_pferd5(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (x+1 >= 0 && x+1 <= 7 ) && (y+2 >= 0 && y+2 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
	return true;

}
public boolean w_pferd6(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x+1 >= 0 && x+1 <= 7 ) && (y-2 >= 0 && y-2 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd7(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-1 >= 0 && x-1 <= 7 ) && (y-2 >= 0 && y-2 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd8(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-1 >= 0 && x-1 <= 7 ) && (y+2 >= 0 && y+2 <= 7 ) && this.spielfeld[x][y]==  98  ) {return false;}
return true;
}


/* trivial brauch nicht
public boolean w_pferd9(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y+2 >= 0 && y+2 <= 7 ) && (x+1 >= 0 && x+1 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
	return true;

}
public boolean w_pferd10(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (y+2 >= 0 && y+2 <= 7 ) && (x-1 >= 0 && x-1 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd11(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (y-2 >= 0 && y-2 <= 7 ) && (x-1 >= 0 && x-1 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd12(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (y-2 >= 0 && y-2 <= 7 ) && (x+1 >= 0 && x+1 <= 7 ) && this.spielfeld[x][y]==  98  ) {return false;}
return true;
}


public boolean w_pferd13(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x+2 >= 0 && x+2 <= 7 ) && (y+1 >= 0 && y+1 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd14(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x+2 >= 0 && x+1 <= 7 ) && (y-1 >= 0 && y-1 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd15(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-2 >= 0 && x-2 <= 7 ) && (y-1 >= 0 && y-1 <= 7 ) && this.spielfeld[x][y] ==  98  ) {return false;}
return true;

}
public boolean w_pferd16(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-2 >= 0 && x-2 <= 7 ) && (y+1 >= 0 && y+1 <= 7 ) && this.spielfeld[x][y]==  98  ) {return false;}
return true;
}

*/






public boolean w_rasiert_mich_pferd(int farbe,int xTo, int yTo) {
	if(w_rasiert_mich_pferd8( farbe, xTo,  yTo)==false) return false;
	if(w_rasiert_mich_pferd16( farbe, xTo,  yTo)==false) return false;
	
	return true;
}


public boolean w_rasiert_mich_pferd8(int farbe,int xTo, int yTo) {
	if(w_pferd8( farbe, xTo,  yTo)==false) return false;
	if(w_pferd7( farbe, xTo,  yTo)==false) return false;
	if(w_pferd6( farbe, xTo,  yTo)==false) return false;
	if(w_pferd5( farbe, xTo,  yTo)==false) return false;
	if(w_pferd4( farbe, xTo,  yTo)==false) return false;
	if(w_pferd3( farbe, xTo,  yTo)==false) return false;
	if(w_pferd2( farbe, xTo,  yTo)==false) return false;
	if(w_pferd1( farbe, xTo,  yTo)==false) return false;
	
	
	return true;
}


/*public boolean w_rasiert_mich_pferd16(int farbe,int xTo, int yTo) {
	if(w_pferd9( farbe, xTo,  yTo)==false) return false;
	if(w_pferd10( farbe, xTo,  yTo)==false) return false;
	if(w_pferd11( farbe, xTo,  yTo)==false) return false;
	if(w_pferd12( farbe, xTo,  yTo)==false) return false;
	if(w_pferd13( farbe, xTo,  yTo)==false) return false;
	if(w_pferd14( farbe, xTo,  yTo)==false) return false;
	if(w_pferd15( farbe, xTo,  yTo)==false) return false;
	if(w_pferd16( farbe, xTo,  yTo)==false) return false;
	
	
	return true;
}*/


	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean s_pferd1(int farbe,int xTo, int yTo) {
		int x = xTo, y = yTo;
		if( (y+1 >= 0 && y+1 <= 7 ) && (x+2 >= 0 && x+2 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
		return true;

	}
public boolean s_pferd2(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y+1 >= 0 && y+1 <= 7 ) && (x-2 >= 0 && x-2 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
	return true;

	}
public boolean s_pferd3(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y-1 >= 0 && y-1 <= 7 ) && (x-2 >= 0 && x-2 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
	return true;

}
public boolean s_pferd4(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y-1 >= 0 && y-1 <= 7 ) && (x+2 >= 0 && x+2 <= 7 ) && this.spielfeld[x][y]==  66  ) {return false;}
	return true;
}


public boolean s_pferd5(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (x+1 >= 0 && x+1 <= 7 ) && (y+2 >= 0 && y+2 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
	return true;

}
public boolean s_pferd6(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x+1 >= 0 && x+1 <= 7 ) && (y-2 >= 0 && y-2 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd7(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-1 >= 0 && x-1 <= 7 ) && (y-2 >= 0 && y-2 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd8(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-1 >= 0 && x-1 <= 7 ) && (y+2 >= 0 && y+2 <= 7 ) && this.spielfeld[x][y]==  66  ) {return false;}
return true;
}


/* trivial - brauch nicht

public boolean s_pferd9(int farbe,int xTo, int yTo) {
	int x = xTo, y = yTo;
	if( (y+2 >= 0 && y+2 <= 7 ) && (x+1 >= 0 && x+1 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
	return true;

}
public boolean s_pferd10(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (y+2 >= 0 && y+2 <= 7 ) && (x-1 >= 0 && x-1 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd11(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (y-2 >= 0 && y-2 <= 7 ) && (x-1 >= 0 && x-1 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd12(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (y-2 >= 0 && y-2 <= 7 ) && (x+1 >= 0 && x+1 <= 7 ) && this.spielfeld[x][y]==  66  ) {return false;}
return true;
}


public boolean s_pferd13(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x+2 >= 0 && x+2 <= 7 ) && (y+1 >= 0 && y+1 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd14(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x+2 >= 0 && x+1 <= 7 ) && (y-1 >= 0 && y-1 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd15(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-2 >= 0 && x-2 <= 7 ) && (y-1 >= 0 && y-1 <= 7 ) && this.spielfeld[x][y] ==  66  ) {return false;}
return true;

}
public boolean s_pferd16(int farbe,int xTo, int yTo) {
int x = xTo, y = yTo;
if( (x-2 >= 0 && x-2 <= 7 ) && (y+1 >= 0 && y+1 <= 7 ) && this.spielfeld[x][y]==  66  ) {return false;}
return true;
}*/






public boolean s_rasiert_mich_pferd(int farbe,int xTo, int yTo) {
	if(s_rasiert_mich_pferd8( farbe, xTo,  yTo)==false) return false;
	//if(s_rasiert_mich_pferd16( farbe, xTo,  yTo)==false) return false;
	
	return true;
}


public boolean s_rasiert_mich_pferd8(int farbe,int xTo, int yTo) {
	if(s_pferd8( farbe, xTo,  yTo)==false) return false;
	if(s_pferd7( farbe, xTo,  yTo)==false) return false;
	if(s_pferd6( farbe, xTo,  yTo)==false) return false;
	if(s_pferd5( farbe, xTo,  yTo)==false) return false;
	if(s_pferd4( farbe, xTo,  yTo)==false) return false;
	if(s_pferd3( farbe, xTo,  yTo)==false) return false;
	if(s_pferd2( farbe, xTo,  yTo)==false) return false;
	if(s_pferd1( farbe, xTo,  yTo)==false) return false;
	
	
	return true;
}


/*public boolean s_rasiert_mich_pferd16(int farbe,int xTo, int yTo) {
	if(s_pferd9( farbe, xTo,  yTo)==false) return false;
	if(s_pferd10( farbe, xTo,  yTo)==false) return false;
	if(s_pferd11( farbe, xTo,  yTo)==false) return false;
	if(s_pferd12( farbe, xTo,  yTo)==false) return false;
	if(s_pferd13( farbe, xTo,  yTo)==false) return false;
	if(s_pferd14( farbe, xTo,  yTo)==false) return false;
	if(s_pferd15( farbe, xTo,  yTo)==false) return false;
	if(s_pferd16( farbe, xTo,  yTo)==false) return false;
	
	
	return true;
}*/











	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	
	


}
	
	
	
	