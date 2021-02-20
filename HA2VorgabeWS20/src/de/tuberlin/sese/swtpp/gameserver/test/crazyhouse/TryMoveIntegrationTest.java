
package de.tuberlin.sese.swtpp.gameserver.test.crazyhouse;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.User;

public class TryMoveIntegrationTest {

	User user1 = new User("Alice", "alice");
	User user2 = new User("Bob", "bob");
	
	Player whitePlayer = null;
	Player blackPlayer = null;
	Game game = null;
	GameController controller;
	
	@Before
	public void setUp() throws Exception {
		controller = GameController.getInstance();
		controller.clear();
		
		int gameID = controller.startGame(user1, "", "crazyhouse");
		
		game =  controller.getGame(gameID);
		whitePlayer = game.getPlayer(user1);

	}
	
	public void startGame() {
		controller.joinGame(user2, "crazyhouse");		
		blackPlayer = game.getPlayer(user2);
	}
	
	public void startGame(String initialBoard, boolean whiteNext) {
		startGame();
		
		game.setBoard(initialBoard);
		game.setNextPlayer(whiteNext? whitePlayer:blackPlayer);
	}
	
	public void assertMove(String move, boolean white, boolean expectedResult) {
		if (white)
			assertEquals(expectedResult, game.tryMove(move, whitePlayer));
		else 
			assertEquals(expectedResult,game.tryMove(move, blackPlayer));
	}
	
	public void assertGameState(String expectedBoard, boolean whiteNext, boolean finished, boolean whiteWon) {
		String board = game.getBoard().replaceAll("e", "");
		
		assertEquals(expectedBoard,board);
		assertEquals(finished, game.isFinished());

		if (!game.isFinished()) {
			assertEquals(whiteNext, game.getNextPlayer() == whitePlayer);
		} else {
			assertEquals(whiteWon, whitePlayer.isWinner());
			assertEquals(!whiteWon, blackPlayer.isWinner());
		}
	}
	

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 *******************************************/
	
	
	
	
	
	
	
	
	@Test
	public void exampleTest() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true);
		assertMove("b2-b7",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	@Test
	public void wirSindNichtDran() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true);
		assertMove("b2-b7",false,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	@Test
	public void schwarzAlsErstesTest() {
		startGame("rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR/",false);
		assertMove("e7-e6",false,true);
		assertGameState("rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void schwarzDortIstEinWeisserKoenig() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",false);
		assertMove("a7-e1",false,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",false,false,false);
	}
	
	
	
	@Test
	public void WeissAngreiferWirFuegenRandSPHinzuMitAnderenSachenSchonDa() {//SP -> schwazer pawn
		startGame("rnbqkbnr/pppppppp/8/4p3/3P4/8/PPPPPPPP/RNBQKBNR/BBNRbrr",true);
		assertMove("d4-e5",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/4P3/8/8/PPPPPPPP/RNBQKBNR/BBNRbprr",false,false,false);
	}
	
	
	
	@Test
	public void wHoltAusRandEineFigur() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/BNNPnprr",true);
		assertMove("N-d4",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/RNBQKBNR/BNPnprr",false,false,false);
	}
	
	
	@Test
	public void wHoltAusRandEineFigurAberGegnerSchonDa() {
		startGame("rnbqkbnr/pppppppp/8/8/3p4/8/PPPPPPPP/RNBQKBNR/BNNPnprr",true);
		assertMove("N-d4",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/3p4/8/PPPPPPPP/RNBQKBNR/BNNPnprr",true,false,false);
	}
	
	
	@Test
	public void wHoltAusRandEineFigurAberKeineDa() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/nprr",true);
		assertMove("N-d4",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/nprr",true,false,false);
	}
	
	
	
	@Test
	public void wbeinsnachvorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true);
		assertMove("b2-b3",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/1P6/P1PPPPPP/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void wbEinNachVorneWirdZuQueen() {
		startGame("2bqkbnr/P1pppppp/8/8/8/8/p1PPPPPP/2BQKBNR/",true);
		assertMove("a7-a8",true,true);
		assertGameState("Q1bqkbnr/2pppppp/8/8/8/8/p1PPPPPP/2BQKBNR/",false,false,false);
	}
	
	@Test
	public void sbEinsNachVorneWirdZuQueen() {
		startGame("2bqkbnr/P1pppppp/8/8/8/8/p1PPPPPP/2BQKBNR/",true);
		assertMove("a2-a1",true,true);
		assertGameState("2bqkbnr/P1pppppp/8/8/8/8/2PPPPPP/q1BQKBNR/",false,false,false);
	}
	
	
	@Test
	public void rookTestKannLinieSeite() {
		startGame("rnbqkbn1/3ppp2/8/2P1r3/8/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-c5",false,true);
		assertGameState("rnbqkbn1/3ppp2/8/2r5/8/4P3/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	
	
	@Test
	public void queenTestKannLinieSeite() {
		startGame("rnbqkbn1/3ppp2/8/2P1q3/8/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-c5",false,true);
		assertGameState("rnbqkbn1/3ppp2/8/2q5/8/4P3/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	@Test
	public void rookTestKannLinieHoehe() {
		startGame("rnbqkbn1/3ppp2/8/2P1r3/8/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-e3",false,true);
		assertGameState("rnbqkbn1/3ppp2/8/2P5/8/4r3/2PPPPP1/RNBQKBNR/P",true,false,false);
	}

	@Test
	public void queenTestKannLinieHoehe() {
		startGame("rnbqkbn1/3ppp2/8/2P1q3/8/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-e3",false,true);
		assertGameState("rnbqkbn1/3ppp2/8/2P5/8/4q3/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	@Test
	public void rookTestKannNichtKomplett() {
		startGame("rnbqkbn1/3ppp2/8/2P1r3/8/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-f6",false,false);
		assertGameState("rnbqkbn1/3ppp2/8/2P1r3/8/4P3/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void rookTestKannLinieHoeheNichtDazwischenEtwas() {
		startGame("rnbqkbn1/3ppp2/8/2P1r3/4P3/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-e3",false,false);
		assertGameState("rnbqkbn1/3ppp2/8/2P1r3/4P3/4P3/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void queenTestKannLinieHoeheNichtDazwischenEtwas() {
		startGame("rnbqkbn1/3ppp2/8/2P1q3/4P3/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-e3",false,false);
		assertGameState("rnbqkbn1/3ppp2/8/2P1q3/4P3/4P3/2PPPPP1/RNBQKBNR/",false,false,false);
	}

	@Test
	public void rookTestKannLinieSeiteNichtDazwischenEtwas() {
		startGame("rnbqkbn1/3ppp2/8/2PPr3/4P3/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-c5",false,false);
		assertGameState("rnbqkbn1/3ppp2/8/2PPr3/4P3/4P3/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void queenTestKannLinieSeiteNichtDazwischenEtwas() {
		startGame("rnbqkbn1/3ppp2/8/2PPq3/4P3/4P3/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-c5",false,false);
		assertGameState("rnbqkbn1/3ppp2/8/2PPq3/4P3/4P3/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void rookTestBleibtStehen() {
		startGame("rnbqkbn1/8/8/2PPr3/8/4P3/8/RNBQKBNR/", false);
		assertMove("e5-e5",false,false);
		assertGameState("rnbqkbn1/8/8/2PPr3/8/4P3/8/RNBQKBNR/",false,false,false);
	}

	@Test
	public void bishopTestHoltUntenLinks() {
		startGame("rnbqkbn1/8/8/1P2b3/3P4/8/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-d4",false,true);
		assertGameState("rnbqkbn1/8/8/1P6/3b4/8/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	@Test
	public void queenTestHoltUntenLinks() {
		startGame("rnbqkbn1/8/8/1P2q3/3P4/8/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-d4",false,true);
		assertGameState("rnbqkbn1/8/8/1P6/3q4/8/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	
	@Test
	public void bishopTestHoltObenRechts() {
		startGame("rnbqkbn1/8/3P1P2/1P2b3/3P1P2/8/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-f6",false,true);
		assertGameState("rnbqkbn1/8/3P1b2/1P6/3P1P2/8/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	
	
	@Test
	public void queenTestHoltObenRechts() {
		startGame("rnbqkbn1/8/3P1P2/1P2q3/3P1P2/8/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-f6",false,true);
		assertGameState("rnbqkbn1/8/3P1q2/1P6/3P1P2/8/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	@Test
	public void bishopTestHoltObenLinks() {
		startGame("rnbqkbn1/1P2p3/8/1P3P2/4b3/3PRP2/2PPPPP1/RNBQKBNR/", false);
		assertMove("e4-b7",false,true);
		assertGameState("rnbqkbn1/1b2p3/8/1P3P2/8/3PRP2/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	
	@Test
	public void queenTestHoltObenLinks() {
		startGame("rnbqkbn1/1P2p3/8/1P3P2/4q3/3PRP2/2PPPPP1/RNBQKBNR/", false);
		assertMove("e4-b7",false,true);
		assertGameState("rnbqkbn1/1q2p3/8/1P3P2/8/3PRP2/2PPPPP1/RNBQKBNR/P",true,false,false);
	}
	
	
	@Test
	public void bishopTestKannNichtNurNachRechts() {
		startGame("rnbqkbn1/8/3P1P2/1P2b3/3P1P2/8/2PPPPP1/RNBQKBNR/", false);
		assertMove("e5-f5",false,false);
		assertGameState("rnbqkbn1/8/3P1P2/1P2b3/3P1P2/8/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void bishopTestKannNichtObenLinksHolenEtwasDazw() {
		startGame("rnbqkbn1/1P2p3/8/1P1p1P2/4b3/3PRP2/2PPPPP1/RNBQKBNR/", false);
		assertMove("e4-b7",false,false);
		assertGameState("rnbqkbn1/1P2p3/8/1P1p1P2/4b3/3PRP2/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	
	
	
	@Test
	public void queenTestKannNichtObenLinksHolenEtwasDazw() {
		startGame("rnbqkbn1/1P2p3/8/1P1p1P2/4q3/3PRP2/2PPPPP1/RNBQKBNR/", false);
		assertMove("e4-b7",false,false);
		assertGameState("rnbqkbn1/1P2p3/8/1P1p1P2/4q3/3PRP2/2PPPPP1/RNBQKBNR/",false,false,false);
	}
	

	@Test
	public void pawnSchwarzEinNachVorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", false);
		assertMove("f7-f6",false,true);
		assertGameState("rnbqkbnr/ppppp1pp/5p2/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void pawnSchwarzHoltUntenLinks() {
		startGame("rnbqkbnr/1ppppppp/8/6p1/5P2/8/PPPPP1PP/RNBQKBNR/", false);
		assertMove("g5-f4",false,true);
		assertGameState("rnbqkbnr/1ppppppp/8/8/5p2/8/PPPPP1PP/RNBQKBNR/P",true,false,false);
	}
	
	
	@Test
	public void pawnSchwarzHoltKeinen() {
		startGame("rnbqkbnr/1ppppppp/8/8/8/4p3/PPPPP1PP/RNBQKBNR/", false);
		assertMove("b7-c6",false,false);
		assertGameState("rnbqkbnr/1ppppppp/8/8/8/4p3/PPPPP1PP/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void pawnSchwarzZweiNachVorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", false);
		assertMove("f7-f5",false,true);
		assertGameState("rnbqkbnr/ppppp1pp/8/5p2/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void pawnSchwarzZweiNachVorneOhneStart() {
		startGame("rnbqkbnr/2pppppp/1p6/8/8/4p3/PPPPP1PP/RNBQKBNR/", false);
		assertMove("b6-b4",false,false);
		assertGameState("rnbqkbnr/2pppppp/1p6/8/8/4p3/PPPPP1PP/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void pawnSchwarzMehrAlsZweiNachVorne() {
		startGame("rnbqkbnr/2pppppp/1p6/8/8/4p3/PPPPP1PP/RNBQKBNR/", false);
		assertMove("b6-c4",false,false);
		assertGameState("rnbqkbnr/2pppppp/1p6/8/8/4p3/PPPPP1PP/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void pawnSchwarzNichtStartEinNachRechts() {
		startGame("rnbqkbnr/2pppppp/1p6/8/8/4p3/PPPPP1PP/RNBQKBNR/", false);
		assertMove("e3-f3",false,false);
		assertGameState("rnbqkbnr/2pppppp/1p6/8/8/4p3/PPPPP1PP/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void pawnWeissZweiVorneEinSeiteStart() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("g2-h4",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void pawnWeissEinVorneZweiSeiteStart() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("a2-c3",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	
	
	
	@Test
	public void pawnWeissEinNachVorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("f2-f3",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/5P2/PPPPP1PP/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void pawnWeissEinNachRechtsNur() {
		startGame("rnbqkbnr/1ppppppp/8/8/8/4p3/PPPPP1PP/RNBQKBNR/", true);
		assertMove("e2-f2",true,false);
		assertGameState("rnbqkbnr/1ppppppp/8/8/8/4p3/PPPPP1PP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void pawnWeissZweiNachVorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("f2-f4",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void knightWeissZweiNachVorneEinLinks() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("g1-f3",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R/",false,false,false);
	}
	
	@Test
	public void knightWeissNachVorneUmZweiNur() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("g1-g3",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void knightWeissEinNachVorneZweiLinks() {
		startGame("rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R/", true);
		assertMove("f3-d4",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/RNBQKB1R/",false,false,false);
	}
	
	
	@Test
	public void knightWeissEinNachVorneEinLinks() {
		startGame("rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R/", true);
		assertMove("f3-e4",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R/",true,false,false);
	}
	
	
	@Test
	public void knightWeissKannGarNichtDaNurDreiNachVorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/", true);
		assertMove("g1-g4",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true,false,false);
	}
	
	
	
	
	
	
	
	////////////////////////////////////////
	
	
	@Test
	public void könig_korrekter_zur_seite_move() {
		startGame("rnbqkbnr/pppppppp/8/8/8/4K3/PPPPPPPP/RNBQ1BNR/",true);
		assertMove("e3-f3",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/5K2/PPPPPPPP/RNBQ1BNR/",false,false,false);
	}
	
	
	@Test
	public void könig_falscher_move1() {
		startGame("rnbqkbnr/pppppppp/8/8/8/4K3/PPPPPPPP/RNBQ1BNR/",true);
		assertMove("e3-g3",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/4K3/PPPPPPPP/RNBQ1BNR/",true,false,false);
	}
	
	
	@Test
	public void könig_falscher_move2() {
		startGame("rnbqkbnr/pppppppp/8/8/8/2P1K3/PPPP1PPP/RNBQ1BNR/",true);
		assertMove("e3-e5",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/2P1K3/PPPP1PPP/RNBQ1BNR/",true,false,false);
	}
	
	@Test
	public void könig_falscher_move3() {
		startGame("rnbqkbnr/pppppppp/8/8/8/2P1K3/PPPP1PPP/RNBQ1BNR/",true);
		assertMove("e3-e3",true,false);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/2P1K3/PPPP1PPP/RNBQ1BNR/",true,false,false);
	}
	
	
	
	//############################## Alles für König weis #####################################//
	
				//################## Pawn #########################################
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_links() {
		startGame("rnbqkbnr/pp1ppppp/8/2p5/8/3K4/PPPPPPPP/RNBQ1BNR/",true);
		assertMove("d3-d4",true,false);
		assertGameState("rnbqkbnr/pp1ppppp/8/2p5/8/3K4/PPPPPPPP/RNBQ1BNR/",true,false,false);
	}
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_rechts() {
		startGame("rnbqkbnr/pp1ppppp/8/4p3/8/3K4/PPPPPPPP/RNBQ1BNR/",true);
		assertMove("d3-d4",true,false);
		assertGameState("rnbqkbnr/pp1ppppp/8/4p3/8/3K4/PPPPPPPP/RNBQ1BNR/",true,false,false);
	}
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_links2() {
		startGame("rnbq1bnr/pppppppp/8/4k3/8/3K4/PPPPPPPP/RNBQ1BNR/",true);
		assertMove("d3-d4",true,false);
		assertGameState("rnbq1bnr/pppppppp/8/4k3/8/3K4/PPPPPPPP/RNBQ1BNR/",true,false,false);
	}
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_rechts1() {
		startGame("rnbq1bnr/pppppppp/8/2k5/8/3K4/PPPPPPPP/RNBQ1BNR/",true);
		assertMove("d3-d4",true,false);
		assertGameState("rnbq1bnr/pppppppp/8/2k5/8/3K4/PPPPPPPP/RNBQ1BNR/",true,false,false);
	}
	
	 ///////
	
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_außerhalbfeld2() {
		startGame("rrbqk3/pppnpp2/pnb5/p6K/8/p3P3/2PPNPPP/1RBQPBNR/",true);
		assertMove("h5-h6",true,true);
		assertGameState("rrbqk3/pppnpp2/pnb4K/p7/8/p3P3/2PPNPPP/1RBQPBNR/",false,false,false);
	}
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_außerhalbfeld21() {
		startGame("4knrb/K1pnppqr/3pppb1/3p1p2/8/4P3/2PPNPPP/1RBQPBNR/",true);
		assertMove("a7-a8",true,true);
		assertGameState("K3knrb/2pnppqr/3pppb1/3p1p2/8/4P3/2PPNPPP/1RBQPBNR/",false,false,false);
	}
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_außerhalbfeld22() {
		startGame("2rnkbp1/2p1p3/8/8/8/2NPPP2/1RPPNP1K/1RBQPB2/",true);
		assertMove("h2-h1",true,true);
		assertGameState("2rnkbp1/2p1p3/8/8/8/2NPPP2/1RPPNP2/1RBQPB1K/",false,false,false);
	}
	
	
	@Test
	public void w_könig_w_rasiert_mich_pawn_außerhalbfeld() {
		startGame("2rnkb2/2ppp2K/8/8/8/2NPPP2/1RPPNP2/1RBQPB2/",true);
		assertMove("h7-h8",true,true);
		assertGameState("2rnkb1K/2ppp3/8/8/8/2NPPP2/1RPPNP2/1RBQPB2/",false,false,false);
	}
	
	//################# alles korrekt bisher für weiß pawn jetzt schwarz das gleiche

	@Test
	public void s_könig_w_rasiert_mich_pawn_links() {
		startGame("rnbq1bnr/pppppppp/4k3/8/3P4/8/PPP1PPPP/RNBQKBNR/",false);
		assertMove("e6-e5",false,false);
		assertGameState("rnbq1bnr/pppppppp/4k3/8/3P4/8/PPP1PPPP/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void s_könig_w_rasiert_mich_pawn_rechts() {
		startGame("rnbq1bnr/pppppppp/4k3/8/5P2/8/PPP1PPPP/RNBQKBNR/",false);
		assertMove("e6-e5",false,false);
		assertGameState("rnbq1bnr/pppppppp/4k3/8/5P2/8/PPP1PPPP/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void s_könig_w_rasiert_mich_pawn_links2() {
		startGame("rnbq1bnr/pppppppp/4k3/P7/5K2/8/PPP1PPPP/RNBQ1BNR/",false);
		assertMove("e6-e5",false,false);
		assertGameState("rnbq1bnr/pppppppp/4k3/P7/5K2/8/PPP1PPPP/RNBQ1BNR/",false,false,false);
	}
	
	
	@Test
	public void s_könig_w_rasiert_mich_pawn_rechts1() {
		startGame("rnbq1bnr/pppppppp/4k3/P7/3K4/8/PPP1PPPP/RNBQ1BNR/",false);
		assertMove("e6-e5",false,false);
		assertGameState("rnbq1bnr/pppppppp/4k3/P7/3K4/8/PPP1PPPP/RNBQ1BNR/",false,false,false);
	}
	
	
	
	
	@Test
	public void s_könig_w_rasiert_mich_pawn_außerhalbfeld2() {
		startGame("rnbq1bnr/pppppppp/8/7R/6QB/5PPN/k1PPPPPP/4KBNR/",false);
		assertMove("a2-a1",false,true);
		assertGameState("rnbq1bnr/pppppppp/8/7R/6QB/5PPN/2PPPPPP/k3KBNR/",true,false,false);
	}

	@Test
	public void s_könig_w_rasiert_mich_pawn_außerhalbfeld21() {
		startGame("5nrb/2pnppqr/2bppp2/3p1p2/8/4P3/PRPPNPPk/KRBQPBN1/",false);
		assertMove("h2-h1",false,true);
		assertGameState("5nrb/2pnppqr/2bppp2/3p1p2/8/4P3/PRPPNPP1/KRBQPBNk/",true,false,false);
	}
	
	
	
	@Test
	public void w_rasiert_mich_rook_test() {
		startGame("8/8/8/5q2/2K5/5r2/8/8/",true);
		assertMove("c4-c5",true,false);
		assertGameState("8/8/8/5q2/2K5/5r2/8/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_rook_test1() {
		startGame("8/8/8/5q2/2K5/5r2/8/8/",true);
		assertMove("c4-c3",true,false);
		assertGameState("8/8/8/5q2/2K5/5r2/8/8/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_rook_test3() {
		startGame("8/8/8/q7/2K5/8/r7/8/",true);
		assertMove("c4-c5",true,false);
		assertGameState("8/8/8/q7/2K5/8/r7/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_rook_test4() {
		startGame("8/8/8/q7/2K5/r7/8/8/",true);
		assertMove("c4-c3",true,false);
		assertGameState("8/8/8/q7/2K5/r7/8/8/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_rook_test5() {
		startGame("8/1q6/8/8/2K5/8/3r4/8/",true);
		assertMove("c4-b4",true,false);
		assertGameState("8/1q6/8/8/2K5/8/3r4/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_rook_test6() {
		startGame("8/1q6/8/8/2K5/8/3r4/8/",true);
		assertMove("c4-d4",true,false);
		assertGameState("8/1q6/8/8/2K5/8/3r4/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_rook_test7() {
		startGame("8/8/3r4/8/2K5/8/1q6/8/",true);
		assertMove("c4-d4",true,false);
		assertGameState("8/8/3r4/8/2K5/8/1q6/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_rook_test8() {
		startGame("8/8/1r6/8/2K5/8/3q4/8/",true);
		assertMove("c4-d4",true,false);
		assertGameState("8/8/1r6/8/2K5/8/3q4/8/",true,false,false);
	}

	@Test
	public void s_rasiert_mich_rook_test() {
		startGame("8/8/8/Q7/3k4/7R/8/8/",false);
		assertMove("d4-d5",false,false);
		assertGameState("8/8/8/Q7/3k4/7R/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_rook_test1() {
		startGame("8/8/8/R7/3k4/7Q/8/8/",false);
		assertMove("d4-d5",false,false);
		assertGameState("8/8/8/R7/3k4/7Q/8/8/",false,false,false);
	}
//	
	@Test
	public void s_rasiert_mich_rook_test3() {
		startGame("8/8/8/Q7/3k4/7R/8/8/",false);
		assertMove("d4-d3",false,false);
		assertGameState("8/8/8/Q7/3k4/7R/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_rook_test4() {
		startGame("8/8/2R5/8/3k4/7Q/8/8/",false);
		assertMove("d4-d3",false,false);
		assertGameState("8/8/2R5/8/3k4/7Q/8/8/",false,false,false);
	}

	@Test
	public void s_rasiert_mich_rook_test5() {
		startGame("8/1R6/8/8/2k5/8/8/3Q4/",false);
		assertMove("c4-b4",false,false);
		assertGameState("8/1R6/8/8/2k5/8/8/3Q4/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_rook_test6() {
		startGame("8/1R6/8/8/2k5/8/8/3Q4/",false);
		assertMove("c4-d4",false,false);
		assertGameState("8/1R6/8/8/2k5/8/8/3Q4/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_rook_test7() {
		startGame("8/1Q6/8/8/2k5/8/8/3R4/",false);
		assertMove("c4-d4",false,false);
		assertGameState("8/1Q6/8/8/2k5/8/8/3R4/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_rook_test8() {
		startGame("8/1Q6/8/8/2k5/8/8/3R4/",false);
		assertMove("c4-b4",false,false);
		assertGameState("8/1Q6/8/8/2k5/8/8/3R4/",false,false,false);
	}
	
	/////////////////////Jetzt Bischof weiß/////////////////////////////
	
	@Test
	public void w_rasiert_mich_bischof_test1() {
		startGame("6q1/7b/8/8/8/2K5/8/8/",true);
		assertMove("c3-c4",true,false);
		assertGameState("6q1/7b/8/8/8/2K5/8/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_bischof_test2() {
		startGame("6q1/7b/8/8/8/2K5/8/8/",true);
		assertMove("c3-c2",true,false);
		assertGameState("6q1/7b/8/8/8/2K5/8/8/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_bischof_test3() {
		startGame("8/8/8/1b6/8/2K5/8/8/",true);
		assertMove("c3-c4",true,false);
		assertGameState("8/8/8/1b6/8/2K5/8/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_bischof_test4() {
		startGame("8/8/8/1q6/8/2K5/8/8/",true);
		assertMove("c3-c4",true,false);
		assertGameState("8/8/8/1q6/8/2K5/8/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_bischof_test5() {
		startGame("8/8/8/8/8/2K5/8/1q6/",true);
		assertMove("c3-c2",true,false);
		assertGameState("8/8/8/8/8/2K5/8/1q6/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_bischof_test6() {
		startGame("8/8/8/8/8/2K5/8/1b6/",true);
		assertMove("c3-c2",true,false);
		assertGameState("8/8/8/8/8/2K5/8/1b6/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_bischof_test7() {
		startGame("8/8/8/8/8/2K5/8/3b4/",true);
		assertMove("c3-c2",true,false);
		assertGameState("8/8/8/8/8/2K5/8/3b4/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_bischof_test8() {
		startGame("8/8/8/8/8/2K5/8/3q4/",true);
		assertMove("c3-c2",true,false);
		assertGameState("8/8/8/8/8/2K5/8/3q4/",true,false,false);
	}
	
	
	/////////in scghwarz diagonal bischof
	

	@Test
	public void s_rasiert_mich_bischof_test1() {
		startGame("6Q1/7B/8/8/8/2k5/8/8/",false);
		assertMove("c3-c4",false,false);
		assertGameState("6Q1/7B/8/8/8/2k5/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_bischof_test2() {
		startGame("6Q1/7B/8/8/8/2k5/8/8/",false);
		assertMove("c3-c2",false,false);
		assertGameState("6Q1/7B/8/8/8/2k5/8/8/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_bischof_test3() {
		startGame("8/8/8/1B6/8/2k5/8/8/",false);
		assertMove("c3-c4",false,false);
		assertGameState("8/8/8/1B6/8/2k5/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_bischof_test4() {
		startGame("8/8/8/1Q6/8/2k5/8/8/",false);
		assertMove("c3-c4",false,false);
		assertGameState("8/8/8/1Q6/8/2k5/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_bischof_test5() {
		startGame("8/8/8/8/8/2k5/8/1Q6/",false);
		assertMove("c3-c2",false,false);
		assertGameState("8/8/8/8/8/2k5/8/1Q6/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_bischof_test6() {
		startGame("8/8/8/8/8/2k5/8/1B6/",false);
		assertMove("c3-c2",false,false);
		assertGameState("8/8/8/8/8/2k5/8/1B6/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_bischof_test7() {
		startGame("8/8/8/8/8/2k5/8/3B4/",false);
		assertMove("c3-c2",false,false);
		assertGameState("8/8/8/8/8/2k5/8/3B4/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_bischof_test8() {
		startGame("8/8/8/8/8/2k5/8/3Q4/",false);
		assertMove("c3-c2",false,false);
		assertGameState("8/8/8/8/8/2k5/8/3Q4/",false,false,false);
	}
	
	//////////////// jetzt pferd weiß
	
	
	@Test
	public void w_rasiert_mich_knight_test() {
		startGame("8/n5n1/8/3K4/8/n5n1/8/8/",true);
		assertMove("d5-c6",true,false);
		assertGameState("8/n5n1/8/3K4/8/n5n1/8/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_knight_test1() {
		startGame("8/n5n1/8/3K4/8/n5n1/8/8/",true);
		assertMove("d5-e6",true,false);
		assertGameState("8/n5n1/8/3K4/8/n5n1/8/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_knight_test2() {
		startGame("8/n5n1/8/3K4/8/n5n1/8/8/",true);
		assertMove("d5-c4",true,false);
		assertGameState("8/n5n1/8/3K4/8/n5n1/8/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_knight_test3() {
		startGame("8/n5n1/8/3K4/8/n5n1/8/8/",true);
		assertMove("d5-e4",true,false);
		assertGameState("8/n5n1/8/3K4/8/n5n1/8/8/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_knight_test4() {
		startGame("1n3n2/8/8/3K4/8/8/1n3n2/8",true);
		assertMove("d5-c6",true,false);
		assertGameState("1n3n2/8/8/3K4/8/8/1n3n2/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_knight_test5() {
		startGame("1n3n2/8/8/3K4/8/8/1n3n2/8",true);
		assertMove("d5-e6",true,false);
		assertGameState("1n3n2/8/8/3K4/8/8/1n3n2/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_knight_test6() {
		startGame("1n3n2/8/8/3K4/8/8/1n3n2/8",true);
		assertMove("d5-c4",true,false);
		assertGameState("1n3n2/8/8/3K4/8/8/1n3n2/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_knight_test7() {
		startGame("1n3n2/8/8/3K4/8/8/1n3n2/8",true);
		assertMove("d5-e4",true,false);
		assertGameState("1n3n2/8/8/3K4/8/8/1n3n2/8/",true,false,false);
	}
	
	
	
	///////////////////////////////////////////////////////
	
	
	@Test
	public void s_rasiert_mich_knight_test() {
		startGame("8/N5N1/8/3k4/8/N5N1/8/8/",false);
		assertMove("d5-c6",false,false);
		assertGameState("8/N5N1/8/3k4/8/N5N1/8/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_knight_test1() {
		startGame("8/N5N1/8/3k4/8/N5N1/8/8/",false);
		assertMove("d5-e6",false,false);
		assertGameState("8/N5N1/8/3k4/8/N5N1/8/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_knight_test2() {
		startGame("8/N5N1/8/3k4/8/N5N1/8/8/",false);
		assertMove("d5-c4",false,false);
		assertGameState("8/N5N1/8/3k4/8/N5N1/8/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_knight_test3() {
		startGame("8/N5N1/8/3k4/8/N5N1/8/8/",false);
		assertMove("d5-e4",false,false);
		assertGameState("8/N5N1/8/3k4/8/N5N1/8/8/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_knight_test4() {
		startGame("1N3N2/8/8/3k4/8/8/1N3N2/8",false);
		assertMove("d5-c6",false,false);
		assertGameState("1N3N2/8/8/3k4/8/8/1N3N2/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_knight_test5() {
		startGame("1N3N2/8/8/3k4/8/8/1N3N2/8",false);
		assertMove("d5-e6",false,false);
		assertGameState("1N3N2/8/8/3k4/8/8/1N3N2/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_knight_test6() {
		startGame("1N3N2/8/8/3k4/8/8/1N3N2/8",false);
		assertMove("d5-c4",false,false);
		assertGameState("1N3N2/8/8/3k4/8/8/1N3N2/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_knight_test7() {
		startGame("1N3N2/8/8/3k4/8/8/1N3N2/8",false);
		assertMove("d5-e4",false,false);
		assertGameState("1N3N2/8/8/3k4/8/8/1N3N2/8/",false,false,false);
	}
	
	///// könig vs könig
	
	
	@Test
	public void w_rasiert_mich_könig_test(){
		startGame("8/3k4/8/1k1K1k2/8/3k4/8/8/",true);
		assertMove("d5-c5",true,false);
		assertGameState("8/3k4/8/1k1K1k2/8/3k4/8/8/",true,false,false);
	}
	
	@Test
	public void w_rasiert_mich_könig_test2(){
		startGame("8/3k4/8/1k1K1k2/8/3k4/8/8/",true);
		assertMove("d5-e5",true,false);
		assertGameState("8/3k4/8/1k1K1k2/8/3k4/8/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_könig_test1(){
		startGame("8/3k4/8/1k1K1k2/8/3k4/8/8/",true);
		assertMove("d5-d4",true,false);
		assertGameState("8/3k4/8/1k1K1k2/8/3k4/8/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_könig_test3(){
		startGame("8/3k4/8/1k1K1k2/8/3k4/8/8/",true);
		assertMove("d5-d6",true,false);
		assertGameState("8/3k4/8/1k1K1k2/8/3k4/8/8/",true,false,false);
	}
	
	
	@Test
	public void w_rasiert_mich_könig_test4(){
		startGame("8/8/8/3K4/8/1k3k2/8/8/",true);
		assertMove("d5-c4",true,false);
		assertGameState("8/8/8/3K4/8/1k3k2/8/8/",true,false,false);
	}
	@Test
	public void w_rasiert_mich_könig_test5(){
		startGame("8/8/8/3K4/8/1k3k2/8/8/",true);
		assertMove("d5-e4",true,false);
		assertGameState("8/8/8/3K4/8/1k3k2/8/8/",true,false,false);
	}
	
	@Test
	public void s_rasiert_mich_könig_test(){
		startGame("8/3K4/8/1K1k1K2/8/3K4/8/8/",false);
		assertMove("d5-c5",false,false);
		assertGameState("8/3K4/8/1K1k1K2/8/3K4/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_könig_test2(){
		startGame("8/3K4/8/1K1k1K2/8/3K4/8/8/",false);
		assertMove("d5-e5",false,false);
		assertGameState("8/3K4/8/1K1k1K2/8/3K4/8/8/",false,false,false);
	}
	
	@Test
	public void s_rasiert_mich_könig_test1(){
		startGame("8/3K4/8/1K1k1K2/8/3K4/8/8/",false);
		assertMove("d5-d4",false,false);
		assertGameState("8/3K4/8/1K1k1K2/8/3K4/8/8/",false,false,false);
	}
	

	@Test
	public void s_rasiert_mich_könig_test3(){
		startGame("8/3K4/8/1K1k1K2/8/3K4/8/8/",false);
		assertMove("d5-d6",false,false);
		assertGameState("8/3K4/8/1K1k1K2/8/3K4/8/8/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_könig_test4(){
		startGame("8/8/1K3K2/3k4/8/8/8/8/",false);
		assertMove("d5-c5",false,false);
		assertGameState("8/8/1K3K2/3k4/8/8/8/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_könig_test5(){
		startGame("8/8/1K3K2/3k4/8/8/8/8/",false);
		assertMove("d5-e5",false,false);
		assertGameState("8/8/1K3K2/3k4/8/8/8/8/",false,false,false);
	}
	
	
	@Test
	public void s_rasiert_mich_könig_test6(){
		startGame("8/8/kk4kk/8/8/8/8/8/",false);
		assertMove("d5-c5",false,false);
		assertGameState("8/8/kk4kk/8/8/8/8/8/",false,false,false);
	}
	@Test
	public void s_rasiert_mich_könig_test7(){
		startGame("8/4k3/8/8/8/8/8/8/",false);
		assertMove("e7-e8",false,true);
		assertGameState("4k3/8/8/8/8/8/8/8/",true,false,false);
	}
	
	
	
	@Test
	public void IchBinImSchachWeiss(){
		startGame("rnbkqbnr/pppp1ppp/8/8/8/8/PPPPBPPP/RNBQKBNR/",true);
		assertMove("e2-f3",true,false);
		assertGameState("rnbkqbnr/pppp1ppp/8/8/8/8/PPPPBPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void IchBinImSchachSchwarz(){
		startGame("rnbkqbnr/pppb1ppp/8/8/8/8/PPP1BPPP/RNBQKBNR/",false);
		assertMove("d7-c6",false,false);
		assertGameState("rnbkqbnr/pppb1ppp/8/8/8/8/PPP1BPPP/RNBQKBNR/",false,false,false);
	}
	
	
	
	@Test
	public void IchBinImSchachSchwarzNachPlace(){
		startGame("rbbkqbnr/ppp2ppp/8/8/8/8/PPP1BPPP/RNBQKBNR/p",false);
		assertMove("p-g4",false,false);
		assertGameState("rbbkqbnr/ppp2ppp/8/8/8/8/PPP1BPPP/RNBQKBNR/p",false,false,false);
	}
	
	

	
	
	
	@Test
	public void SchwarzIstSchachMatt(){
		startGame("7k/8/5K2/6Q1/8/8/8/8/",true);
		assertMove("g5-g7",true,true);
		assertGameState("7k/6Q1/5K2/8/8/8/8/8/",true,true,true);
	}
	
	
	@Test
	public void WeisIstSchachMatt(){
		startGame("7K/8/5k2/6q1/8/8/8/8/",false);
		assertMove("g5-g7",false,true);
		assertGameState("7K/6q1/5k2/8/8/8/8/8/",false,true,false);
	}
	
	
	
	
	
	
	
	
	

}

   
