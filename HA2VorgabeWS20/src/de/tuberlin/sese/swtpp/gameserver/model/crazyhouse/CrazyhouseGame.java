package de.tuberlin.sese.swtpp.gameserver.model.crazyhouse;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class CrazyhouseGame extends Game implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 5424778147226994452L;

	/************************
	 * member
	 ***********************/

	// just for better comprehensibility of the code: assign white and black player
	private Player blackPlayer;
	private Player whitePlayer;
	char[][] Spielfeld;
	char[] Rand;
	int kingw_x;
	int kingw_y;
	
	int kings_x;
	int kings_y;

	// internal representation of the game state
	// TODO: insert additional game data here

	/************************
	 * constructors
	 ***********************/

	public CrazyhouseGame() {
		super();
		// TODO: initialize internal model if necessary
		Spielfeld = new char[8][8];
		//this.addPlayer(blackPlayer);
		//this.addPlayer(whitePlayer);
		this.setBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/");
		//this.setNextPlayer(whitePlayer);
	}

	public String getType() {
		return "crazyhouse";
	}

	/*******************************************
	 * Game class functions already implemented
	 ******************************************/

	@Override
	public boolean addPlayer(Player player) {
		if (!started) {
			players.add(player);

			// game starts with two players
			if (players.size() == 2) {
				started = true;
				this.whitePlayer = players.get(0);
				this.blackPlayer= players.get(1);
				nextPlayer = whitePlayer;
			}
			return true;
		}

		return false;
	}

	@Override
	public String getStatus() {
		if (error)
			return "Error";
		if (!started)
			return "Wait";
		if (!finished)
			return "Started";
		if (surrendered)
			return "Surrendered";
		if (draw)
			return "Draw";

		return "Finished";
	}

	@Override
	public String gameInfo() {
		String gameInfo = "";

		if (started) {
			if (blackGaveUp())
				gameInfo = "black gave up";
			else if (whiteGaveUp())
				gameInfo = "white gave up";
			else if (didWhiteDraw() && !didBlackDraw())
				gameInfo = "white called draw";
			else if (!didWhiteDraw() && didBlackDraw())
				gameInfo = "black called draw";
			else if (draw)
				gameInfo = "draw game";
			else if (finished)
				gameInfo = blackPlayer.isWinner() ? "black won" : "white won";
		}

		return gameInfo;
	}

	@Override
	public String nextPlayerString() {
		return isWhiteNext() ? "w" : "b";
	}

	@Override
	public int getMinPlayers() {
		return 2;
	}

	@Override
	public int getMaxPlayers() {
		return 2;
	}

	@Override
	public boolean callDraw(Player player) {

		// save to status: player wants to call draw
		if (this.started && !this.finished) {
			player.requestDraw();
		} else {
			return false;
		}

		// if both agreed on draw:
		// game is over
		if (players.stream().allMatch(Player::requestedDraw)) {
			this.draw = true;
			finish();
		}
		return true;
	}

	@Override
	public boolean giveUp(Player player) {
		if (started && !finished) {
			if (this.whitePlayer == player) {
				whitePlayer.surrender();
				blackPlayer.setWinner();
			}
			if (this.blackPlayer == player) {
				blackPlayer.surrender();
				whitePlayer.setWinner();
			}
			surrendered = true;
			finish();

			return true;
		}

		return false;
	}

	/* ******************************************
	 * Helpful stuff
	 ***************************************** */

	/**
	 *
	 * @return True if it's white player's turn
	 */
	public boolean isWhiteNext() {
		return nextPlayer == whitePlayer;
	}

	/**
	 * Ends game after regular move (save winner, finish up game state,
	 * histories...)
	 *
	 * @param winner player who won the game
	 * @return true if game was indeed finished
	 */
	public boolean regularGameEnd(Player winner) {
		// public for tests
		if (finish()) {
			winner.setWinner();
			winner.getUser().updateStatistics();
			return true;
		}
		return false;
	}

	public boolean didWhiteDraw() {
		return whitePlayer.requestedDraw();
	}

	public boolean didBlackDraw() {
		return blackPlayer.requestedDraw();
	}

	public boolean whiteGaveUp() {
		return whitePlayer.surrendered();
	}

	public boolean blackGaveUp() {
		return blackPlayer.surrendered();
	}

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 ******************************************/

	public void sortRand(char[] Rand) {	
		Arrays.sort(Rand);
		this.Rand = Rand;
	}
	
	
	
	public void wo_ist_king(int element,int x,int y) { // wir m�ssen immer wissen wo der king ist
		if(element  == 75) {						  // auch schon am anfang bei Setboard, es k�nnte ja Schach(matt) sein
			kingw_x = x;
			kingw_y = y;
		}
		
		else if( element  == 107) {
			kings_x = x;
			kings_y = y;
		}
		
	}
	
	
	public void setBoard(String state) {
		// Note: This method is for automatic testing. A regular game would not start at some artificial state.
		//       It can be assumed that the state supplied is a regular board that can be reached during a game.
		// TODO: implement
		
		String[] teiler = state.split("\\/"); // Format aufspliten
		
		state = "";			// Hier Spielfeldformat laden
		for(int i = 0; i<= 7; i++) {
			for(char buchstaben: teiler[i].toCharArray() ) {
				state = state + buchstaben;
			}
			state = state + "/";
		}
		
		
		this.Spielfeld = new char[8][8];
		int x = 0;
		int y = 7;
		for(char element : state.toCharArray()) {
			if( ((int) element) == 47) {
				y = y -1;
				x = 0;
			}else if(    ( ( ( (int) element) >= 65 ) && ( ( (int) element) <= 90) ) ||  ( ( ((int) element) >= 97 ) && ( ( (int) element) <= 122) )  ) {
				this.Spielfeld[x][y] = element;
				wo_ist_king(element,x,y); // ist es der King ?
				x = x+1;
			}else {
				x = x + ( ((int)element) - 48  );
			}
		}
		
		
		if(teiler.length == 9) { 	//wenn es Reserve gibt, Reserve laden
			Rand = teiler[8].toCharArray();
		}
								
		
		
		
	}
	
	

	@Override
	public String getBoard() {
		
		// TODO: implement
		String ruckgabe = "";
		boolean check = true;
		for(int y = 7; y >= 0;y--) {
			int counter = 0;
			for(int x = 0; x<=7;x++) {
				check = true;
				if(this.Spielfeld[x][y] == 0) {
					counter++;
					check = false;
				}else if(counter > 0)  {
						ruckgabe = ruckgabe + String.valueOf(counter);
						counter = 0;
					
					
				} if(check) {
					ruckgabe = ruckgabe + this.Spielfeld[x][y];
					check = false;
				}
			}
			if(counter > 0 ) {
				ruckgabe = ruckgabe + String.valueOf(counter);
				counter = 0;
			}
			ruckgabe = ruckgabe + "/";
		}
		
		///////// Jetzt Rand //////////
		if(this.Rand != null) {
			Arrays.sort(this.Rand);
			ruckgabe = ruckgabe + new String(Rand); 
		}
				
		
		
		
		
		
		// replace with real implementation
		//return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/";
		return ruckgabe;
	}

	@Override
	public boolean tryMove(String moveString, Player player) {
		int[] offsets= offsets(player);
		int offset1 = offsets[0], offset2 = offsets[1];
		boolean gueltig = false;
		boolean schachMatt = false;
		if(this.isPlayersTurn(player) && (moveString.matches("[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}") || moveString.matches("[p,n,k,r,b,q]{1}-[a-h]{1}[1-8]{1}") || moveString.matches("[P,B,Q,N,K,R]{1}-[a-h]{1}[1-8]{1}") )){
			int n = moveString.length();
			int xTo = moveString.charAt(n-2)-97;
			int yTo = moveString.charAt(n-1)-49;
			int moveZu = this.Spielfeld[xTo][yTo];
			if(moveZu >= 97-offset1 && moveZu <= 122-offset1 || moveZu == 107+offset2) return false;
			gueltig = this.Befehl(moveString, xTo, yTo);
			schachMatt = this.istMeinGegnerImMatt(gueltig, player);
		}
		
		return this.prepGame(schachMatt,gueltig, moveString, player);
	}	
	
	public int[] offsets(Player player) {
		
		int[] offsets = new int[2];
		
		int offset1;
		if(player == this.blackPlayer) offset1 = 0;//wir sind schwarz
		else offset1 = 32;							//wir sind weiss
		int offset2 = offset1-32;
		
		offsets[0] = offset1;
		offsets[1] = offset2;
		
		return offsets;
	}
	
	public boolean Befehl(String moveString,int xTo, int yTo) {
		
		boolean gueltig;
		if(moveString.charAt(2) == 45) {
			int xFrom = moveString.charAt(0)-97;
			int yFrom = moveString.charAt(1)-49;
			gueltig = this.realMove(xFrom, yFrom, xTo, yTo,this.Spielfeld[xFrom][yFrom]);
		}
		else {
			char figur = moveString.charAt(0);
			gueltig = this.realPlace(figur,xTo,yTo);
		}
		
		return gueltig;
	}
	
	public boolean prepGame(boolean schachMatt, boolean gueltig, String move, Player player) {
		
		if(!gueltig) return false;
		
		if(schachMatt) this.finish();
		
		List<Move> liste = this.getHistory();
		
		Move neuerMove = new Move(move,this.getBoard(),player);
		
		liste.add(neuerMove);
		
		this.setHistory(liste);
		//this.nextPlayer = this.getNextPlayer();
		
		if(player == this.blackPlayer) {
			this.nextPlayer = this.whitePlayer;
		}else {
			this.nextPlayer = this.blackPlayer;
		}
		
		return true;
	}
	
	
	public boolean realMove(int xFrom, int yFrom, int xTo, int yTo, int figur) {
		if(figur < 97) {
			return this.weissRealMove(xFrom, yFrom, xTo, yTo, figur);
		}
		return this.schwarzRealMove(xFrom, yFrom, xTo, yTo, figur);
	}
	
	public boolean realPlace(char figur, int xTo, int yTo) {//kein move, sondern von ausserhalb platzieren
		//muss noch gemacht werden
		int n = this.Rand.length;
		boolean istImRand = false;
		for(int i = 0; i < n; i++) {
			if(this.Rand[i] == figur) { 
				istImRand = true;
				this.RandEntferneI(i);
				break;
			}
		}
		if(this.Spielfeld[xTo][yTo] == 0 && istImRand) {
			this.Spielfeld[xTo][yTo] = figur;
		}
		return false;
	}
	
	
	public void RandEntferneI(int i) {
		
		int n = this.Rand.length;
		
		char[] neuerRand = new char[n-1];
		int k = 0;
		for(int j = 0; j < n; j++) {
			if(j == i) continue;
			neuerRand[k] = this.Rand[j];
			k++;
		}
		this.Rand = neuerRand;
	}
	
	public boolean weissRealMove(int xFrom, int yFrom, int xTo, int yTo, int figur) {
		if(figur == 80 ){//Bauer
			Pawn bauer = new Pawn(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(bauer.canI(),xFrom,yFrom,xTo,yTo);
		}else if(figur == 82) {//Turm
			Rook turm = new Rook(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(turm.canI(), xFrom, yFrom, xTo, yTo);
		}else if(figur == 78) {//Pferd
			Knight pferd = new Knight(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(pferd.canI(), xFrom, yFrom, xTo, yTo);
		}else if(figur == 66) {//Leufer
			Bishop laeufer = new Bishop(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(laeufer.canI(), xFrom, yFrom, xTo, yTo);
		}else if(figur == 81) {
			Queen koenigin = new Queen(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(koenigin.canI(), xFrom, yFrom, xTo, yTo);
		}
		King koenig = new King(xFrom,yFrom,xTo,yTo,this.Spielfeld);
		return this.feldBearbeiten(koenig.canI(), xFrom, yFrom, xTo, yTo);
	}
	
	
	public boolean schwarzRealMove(int xFrom, int yFrom, int xTo, int yTo, int figur) {
		if(figur == 112 ){//Bauer
			Pawn bauer = new Pawn(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(bauer.canI(),xFrom,yFrom,xTo,yTo);
		}else if(figur == 114) {//Turm
			Rook turm = new Rook(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(turm.canI(), xFrom, yFrom, xTo, yTo);
		}else if(figur == 110) {//Pferd
			Knight pferd = new Knight(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(pferd.canI(), xFrom, yFrom, xTo, yTo);
		}else if(figur == 98) {//Leufer
			Bishop laeufer = new Bishop(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(laeufer.canI(), xFrom, yFrom, xTo, yTo);
		}else if(figur == 113) {
			Queen koenigin = new Queen(xFrom,yFrom,xTo,yTo,this.Spielfeld);
			return this.feldBearbeiten(koenigin.canI(), xFrom, yFrom, xTo, yTo);
		}
		King koenig = new King(xFrom,yFrom,xTo,yTo,this.Spielfeld);
		return this.feldBearbeiten(koenig.canI(), xFrom, yFrom, xTo, yTo);
	}
	
	public char[] randHinzu(char[] Rand, char Figur) {
		
		String StrRand = new String(Rand);
		StrRand += Figur;
		return StrRand.toCharArray();
	}
	
	public boolean feldBearbeiten(boolean machen,int xFrom,int yFrom,int xTo,int yTo) {
		if(!machen) return false;
		char figur = this.Spielfeld[xFrom][yFrom];	//wer bewegt sich?
		this.Spielfeld[xFrom][yFrom] = 0;			//die figur wird sich bewegen und nicht mehr am gleichen platz bleiben
		char ziel = this.Spielfeld[xTo][yTo];	//wer wird angegriffen?
		char[] alterRand = this.Rand;
		if(ziel != 0) {	//wir haben einen gegner
			this.sortRand(this.randHinzu(this.Rand, ziel));// wir f�gen die entfernte figur zum Rand hinzu und sortieren den Rand
		}
		this.Spielfeld[xTo][yTo] = figur;	//wir bewegen den angreifer auf das neue feld
		if(this.Spielfeld[xTo][yTo] ==75) {
			kingw_x = xTo;
			kingw_y = yTo;
		}
		
		if(this.Spielfeld[xTo][yTo] == 107) {
			kings_x = xTo;
			kings_y = yTo;
		}
		
		if(figur == 112 && yTo == 0) this.Spielfeld[xTo][yTo] = 113;
		else if(figur == 80 && yTo == 7) this.Spielfeld[xTo][yTo] = 81;
		boolean binIchImSchach;
		if(figur < 97) binIchImSchach = this.binIchImSchach(this.Spielfeld, this.whitePlayer);
		else binIchImSchach = this.binIchImSchach(this.Spielfeld, this.whitePlayer);
		if(binIchImSchach) {
			this.Spielfeld[xFrom][yFrom] = figur;
			this.Spielfeld[xTo][yTo] = ziel;
			this.Rand = alterRand;
			return false;
		}
		return true;
	}
	
	public boolean binIchImSchach(char[][] Spielfeld, Player player) {
		if(player == this.whitePlayer) {
			King koenig = new King(kingw_x,kingw_y-1,kingw_x,kingw_y,this.Spielfeld);
			return koenig.canI();
		}else if(player == this.blackPlayer) {
			King koenig = new King(kings_x,kings_y-1,kings_x,kings_y,this.Spielfeld);
			return koenig.canI();
		}
		
		
		
		return false;
	}
	
	
	
	public boolean schwarzistMatt() {
		//binichimSchach
		King koenig1 = new King(kings_x,kings_y,kings_x,kings_y+1,this.Spielfeld);
		King koenig2= new King(kings_x,kings_y,kings_x+1,kings_y,this.Spielfeld);
		King koenig3= new King(kings_x,kings_y,kings_x+1,kings_y+1,this.Spielfeld);
		King koenig4= new King(kings_x,kings_y,kings_x-1,kings_y,this.Spielfeld);
		King koenig5= new King(kings_x,kings_y,kings_x,kings_y-1,this.Spielfeld);
		King koenig6= new King(kings_x,kings_y,kings_x-1,kings_y-1,this.Spielfeld);
		King koenig7= new King(kings_x,kings_y,kings_x+1,kings_y-1,this.Spielfeld);
		King koenig8= new King(kings_x,kings_y,kings_x-1,kings_y+1,this.Spielfeld);
		return(!(koenig1.canI() || koenig2.canI() ||koenig3.canI() ||koenig4.canI() ||koenig5.canI() ||koenig6.canI() ||koenig7.canI() ||koenig8.canI()) );
		
	}
	
	public boolean weisistMatt() {
		King koenig1 = new King(kingw_x,kingw_y,kingw_x,kingw_y+1,this.Spielfeld);
		King koenig2= new King(kingw_x,kingw_y,kingw_x+1,kingw_y,this.Spielfeld);
		King koenig3= new King(kingw_x,kingw_y,kingw_x+1,kingw_y+1,this.Spielfeld);
		King koenig4= new King(kingw_x,kingw_y,kingw_x-1,kingw_y,this.Spielfeld);
		King koenig5= new King(kingw_x,kingw_y,kingw_x,kingw_y-1,this.Spielfeld);
		King koenig6= new King(kingw_x,kingw_y,kingw_x-1,kingw_y-1,this.Spielfeld);
		King koenig7= new King(kingw_x,kingw_y,kingw_x+1,kingw_y-1,this.Spielfeld);
		King koenig8= new King(kingw_x,kingw_y,kingw_x-1,kingw_y+1,this.Spielfeld);
		return(!(koenig1.canI() || koenig2.canI() ||koenig3.canI() ||koenig4.canI() ||koenig5.canI() ||koenig6.canI() ||koenig7.canI() ||koenig8.canI()) );
	}
	
	
	public boolean istMeinGegnerImMatt( boolean gueltig,Player player) {
		
		//if(!gueltig) return false;		// wenn mein zug nicht gueltig war dann kann er noch nicht im matt sein
		
		
		//hier kommt die untersuchung, wenn schach-matt dann true;
		if( player == whitePlayer) {
			return schwarzistMatt();
		} else {
			return weisistMatt();
		}
		
		//return false;
	}
	
	
	
	
	/*public static void main(String[] args) {
        CrazyhouseGame spiel = new CrazyhouseGame();
        String brett = "rnbqkbnr/p6P/8/1b6/8/8/4p3/RNBQKBNR/";
        spiel.setBoard(brett);
       
        Player weis = null;
        String ali = spiel.getBoard();
        System.out.print(ali + "\n");
        
        spiel.tryMove("e1-e2", weis);
        
        System.out.print(spiel.getBoard());
        
        
    }*/

}