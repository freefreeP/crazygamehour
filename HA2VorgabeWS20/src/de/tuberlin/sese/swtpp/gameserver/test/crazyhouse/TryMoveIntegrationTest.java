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
	public void schwarzAlsErstesTest() {
		startGame("rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR/",false);
		assertMove("e7-e6",false,true);
		assertGameState("rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR/",true,false,false);
	}
	
	
	@Test
	public void wbeinsnachvorne() {
		startGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR/",true);
		assertMove("b2-b3",true,true);
		assertGameState("rnbqkbnr/pppppppp/8/8/8/1P6/P1PPPPPP/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void rookTestKannLinieSeite() {
		startGame("rnbqkbn1/8/8/2P1r3/8/4P3/8/RNBQKBNR/", false);
		assertMove("e5-c5",false,true);
		assertGameState("rnbqkbn1/8/8/2r5/8/4P3/8/RNBQKBNR/P",true,false,false);
	}
	
	@Test
	public void rookTestKannLinieHoehe() {
		startGame("rnbqkbn1/8/8/2P1r3/8/4P3/8/RNBQKBNR/", false);
		assertMove("e5-e3",false,true);
		assertGameState("rnbqkbn1/8/8/2P5/8/4r3/8/RNBQKBNR/P",true,false,false);
	}
	
	@Test
	public void rookTestKannNichtKomplett() {
		startGame("rnbqkbn1/8/8/2P1r3/8/4P3/8/RNBQKBNR/", false);
		assertMove("e5-f6",false,false);
		assertGameState("rnbqkbn1/8/8/2P1r3/8/4P3/8/RNBQKBNR/",false,false,false);
	}
	
	
	@Test
	public void rookTestKannLinieHoeheNichtDazwischenEtwas() {
		startGame("rnbqkbn1/8/8/2P1r3/4P3/4P3/8/RNBQKBNR/", false);
		assertMove("e5-e3",false,false);
		assertGameState("rnbqkbn1/8/8/2P1r3/4P3/4P3/8/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void rookTestKannLinieSeiteNichtDazwischenEtwas() {
		startGame("rnbqkbn1/8/8/2PPr3/8/4P3/8/RNBQKBNR/", false);
		assertMove("e5-c5",false,false);
		assertGameState("rnbqkbn1/8/8/2PPr3/8/4P3/8/RNBQKBNR/",false,false,false);
	}
	
	@Test
	public void rookTestBleibtStehen() {
		startGame("rnbqkbn1/8/8/2PPr3/8/4P3/8/RNBQKBNR/", false);
		assertMove("e5-e5",false,false);
		assertGameState("rnbqkbn1/8/8/2PPr3/8/4P3/8/RNBQKBNR/",false,false,false);
	}
	
	
	
	
	
	

	//TODO: implement test cases of same kind as example here
}
