package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

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
		int offset;
		if(this.spielfeld[this.xFrom][this.yFrom] >= 97 ) offset = -32;
		else offset = 0;
		if(this.amISaveFromBishopLeftBot(this.xTo, this.yTo, offset) && this.amISaveFromBishopRightBot(this.xTo, this.yTo, offset) && this.amISaveFromBishopLeftTop(this.xTo, this.yTo, offset) && this.amISaveFromBishopRightTop(this.xTo, this.yTo, offset)) return true;
		return false;	
	}
	
	
	public boolean amISaveFromBishopRightTop(int xNeu, int yNeu, int offset) {
		for(int i = 0; i < 7; i++) {
			int xro = xNeu +i;
			int yro = yNeu +i;
			if(xro >= 0 && xro <= 7 && yro >= 0 && yro <= 7 && this.spielfeld[xro][yro]  == 98-offset) {
				return false;
			}
		}
		return true;
	}
	
	public boolean amISaveFromBishopLeftTop(int xNeu, int yNeu, int offset) {
		for(int i = 0; i < 7; i++) {
			int xlo = xNeu -i;
			int ylo = yNeu +i;
			if(xlo >= 0 && xlo <= 7 && ylo >= 0 && ylo <= 7 && this.spielfeld[xlo][ylo]  == 98-offset) {
				return false;
			}
		}
		return true;
	}
	
	public boolean amISaveFromBishopRightBot(int xNeu, int yNeu, int offset) {
		for(int i = 0; i < 7; i++) {
			int xru = xNeu +i;
			int yru = yNeu -i;
			if(xru >= 0 && xru <= 7 && yru >= 0 && yru <= 7 && this.spielfeld[xru][yru]  == 98-offset) {
				return false;
			}
		}
		return true;
	}
	
	public boolean amISaveFromBishopLeftBot(int xNeu, int yNeu, int offset) {
		for(int i = 0; i < 7; i++) {
			int xlu = xNeu -i;
			int ylu = yNeu -i;
			if(xlu >= 0 && xlu <= 7 && ylu >= 0 && ylu <= 7 && this.spielfeld[xlu][ylu]  == 98-offset) {
				return false;
			}
		}
		return true;
	}
	
	/*int xru = xNeu +i, xro = xNeu +i;
	//int xlu = xNeu -i, xlo = xNeu -i;
	int yru = yNeu -i, yro = yNeu +i;
	//int ylu = yNeu -i, ylo = yNeu +i;*/
	

}
