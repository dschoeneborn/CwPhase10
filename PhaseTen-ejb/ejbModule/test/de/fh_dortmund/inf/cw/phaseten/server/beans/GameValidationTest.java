/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.HashSet;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.TestType;

/**
 * @author Dennis Schöneborn
 *
 */
@RunWith(ApplicationComposer.class)
public class GameValidationTest {
	@EJB
	GameValidationBean gameValidation;

	@Module
	public PersistenceUnit persistence() {
		PersistenceUnit unit = new PersistenceUnit("PhaseTenDB");
		unit.setJtaDataSource("testTypeDatabase");
		unit.setNonJtaDataSource("testTypeDatabaseUnmanaged");
		unit.getClazz().add(TestType.class.getName());
		unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
		return unit;
	}

	@Module
	public StatelessBean app() throws Exception {
		final StatelessBean bean = new StatelessBean(GameValidationBean.class);
		bean.setLocalBean(new Empty());
		return bean;
	}

	@Test
	public void testHasEnoughPlayers() throws Exception {
		Lobby lobby = new Lobby();
		lobby.addPlayer(new Player("P1"));
		// One Player missing
		Assert.assertEquals(false, gameValidation.hasEnoughPlayers(lobby));
		lobby.addPlayer(new Player("P2"));
		Assert.assertEquals(true, gameValidation.hasEnoughPlayers(lobby));
	}

	@Test
	public void testIsLobbyFull() throws Exception {
		Lobby lobby = new Lobby();
		// 0 Player
		Assert.assertEquals(false, gameValidation.isLobbyFull(lobby));
		lobby.addPlayer(new Player("P1"));
		// 1 Player
		Assert.assertEquals(false, gameValidation.isLobbyFull(lobby));
		lobby.addPlayer(new Player("P2"));
		// 2 Player
		Assert.assertEquals(false, gameValidation.isLobbyFull(lobby));
		lobby.addPlayer(new Player("P3"));
		// 3 Player
		Assert.assertEquals(false, gameValidation.isLobbyFull(lobby));
		lobby.addPlayer(new Player("P4"));
		// 4 Player
		Assert.assertEquals(false, gameValidation.isLobbyFull(lobby));
		lobby.addPlayer(new Player("P5"));
		// 5 Player
		Assert.assertEquals(false, gameValidation.isLobbyFull(lobby));
		lobby.addPlayer(new Player("P6"));
		// 6 Player
		Assert.assertEquals(true, gameValidation.isLobbyFull(lobby));
	}

	@Test
	public void testIsValidDrawCardFromLiFoStack() throws Exception {
		HashSet<Player> players = new HashSet<>();
		Player p1 = new Player("P1");
		Player p2 = new Player("P2");
		players.add(p1);
		players.add(p2);
		Game game = new Game(players, new HashSet<Spectator>());
		game.setCurrentPlayer(p1);

		LiFoStack liFo = new LiFoStack();
		liFo.addCard(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		game.setInitialized();
		// Everthing ok
		Assert.assertEquals(true, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		liFo.addCard(new Card(Color.NONE, CardValue.SKIP));
		// SKIP on top of LiFo Stack
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		liFo.addCard(new Card(Color.BLUE, CardValue.SEVEN));
		p1.addRoundStage();
		// Player in wrong stage
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));
	}

	@Test
	public void testIsValidPushCardToLiFoStack() throws Exception {
		Card validCard = new Card(Color.RED, CardValue.EIGHT);
		Card notValidCard = new Card(Color.NONE, CardValue.SKIP);

		HashSet<Player> players = new HashSet<>();
		Player p1 = new Player("P1");
		p1.addRoundStage();
		p1.addCardToPlayerPile(validCard);
		Player p2 = new Player("P2");
		p2.addRoundStage();
		p2.addCardToPlayerPile(validCard);
		players.add(p1);
		players.add(p2);

		Game game = new Game(players, new HashSet<Spectator>());
		game.setCurrentPlayer(p1);

		LiFoStack liFo = new LiFoStack();
		liFo.addCard(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidPushCardToLiFoStack(game, p1, validCard));

		game.setInitialized();
		// Everything ok
		Assert.assertEquals(true, gameValidation.isValidPushCardToLiFoStack(game, p1, validCard));

		p1.resetRoundStage();
		// Player is in wrong roud stage
		Assert.assertEquals(false, gameValidation.isValidPushCardToLiFoStack(game, p1, validCard));
		p1.addRoundStage();

		p1.removeCardFromPlayerPile(validCard);
		// Player has not card validCard
		Assert.assertEquals(false, gameValidation.isValidPushCardToLiFoStack(game, p1, validCard));
		p1.addCardToPlayerPile(validCard);

		p1.givePlayerSkipCard();
		// Player has skip card
		Assert.assertEquals(false, gameValidation.isValidPushCardToLiFoStack(game, p1, notValidCard));
	}

	@Test
	public void testIsValidDrawCardFromPullStack() throws Exception {
		Card validCard = new Card(Color.RED, CardValue.EIGHT);

		HashSet<Player> players = new HashSet<>();
		Player p1 = new Player("P1");
		p1.addCardToPlayerPile(validCard);
		Player p2 = new Player("P2");
		p2.addCardToPlayerPile(validCard);
		players.add(p1);
		players.add(p2);

		Game game = new Game(players, new HashSet<Spectator>());
		game.setCurrentPlayer(p1);

		LiFoStack liFo = new LiFoStack();
		liFo.addCard(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromPullStack(game, p1));

		game.setInitialized();
		// Everything ok
		Assert.assertEquals(true, gameValidation.isValidDrawCardFromPullStack(game, p1));

		p1.addRoundStage();
		// Player is in wrong round stage
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromPullStack(game, p1));
		p1.resetRoundStage();

		p1.givePlayerSkipCard();
		// Player has skip card
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromPullStack(game, p1));
	}

	@Test
	public void testIsValidLayStageToTable() throws Exception {
		Card validCard = new Card(Color.RED, CardValue.EIGHT);

		HashSet<Player> players = new HashSet<>();
		Player p1 = new Player("P1");
		p1.addRoundStage();
		p1.addCardToPlayerPile(validCard);
		Player p2 = new Player("P2");
		p2.addCardToPlayerPile(validCard);
		players.add(p1);
		players.add(p2);

		Game game = new Game(players, new HashSet<Spectator>());
		game.setCurrentPlayer(p1);

		LiFoStack liFo = new LiFoStack();
		liFo.addCard(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		ArrayList<DockPile> piles = new ArrayList<>();

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidLayStageToTable(game, p1, piles));
		game.isInitialized();

		p1.resetRoundStage();
		// Player is in wrong round stage
		Assert.assertEquals(false, gameValidation.isValidLayStageToTable(game, p1, piles));
		p1.addRoundStage();
	}

	@Test
	public void testIsValidLaySkipCard() throws Exception {
		Card skipCard = new Card(Color.NONE, CardValue.SKIP);
		Card otherCard = new Card(Color.BLUE, CardValue.EIGHT);

		HashSet<Player> players = new HashSet<>();
		Player p1 = new Player("P1");
		p1.addCardToPlayerPile(otherCard);
		p1.addCardToPlayerPile(skipCard);
		p1.addRoundStage();
		Player p2 = new Player("P2");
		p2.addCardToPlayerPile(otherCard);
		players.add(p1);
		players.add(p2);

		Game game = new Game(players, new HashSet<Spectator>());
		game.setCurrentPlayer(p1);

		LiFoStack liFo = new LiFoStack();
		liFo.addCard(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		game.setInitialized();
		;

		// Everything ok
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));

		p1.removeCardFromPlayerPile(skipCard);
		// Player p1 has no skip card
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		p1.addCardToPlayerPile(skipCard);
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));

		p1.resetRoundStage();
		// Player p1 is in wrong round stage
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		p1.addRoundStage();
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));

		p1.givePlayerSkipCard();
		// Player p1 has skip card
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		p1.removeSkipCard();
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));

		p2.givePlayerSkipCard();
		// Player p2 has skip card
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		p2.removeSkipCard();
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));

		p2.removeCardFromPlayerPile(otherCard);
		// Player p2 has no cards
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		p2.addCardToPlayerPile(otherCard);
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));
		
		p1.removeCardFromPlayerPile(otherCard);
		//Player p1 has only skip card
		Assert.assertEquals(false, gameValidation.isValidLaySkipCard(p1, p2, game));
		p1.addCardToPlayerPile(otherCard);
		Assert.assertEquals(true, gameValidation.isValidLaySkipCard(p1, p2, game));
	}
}
