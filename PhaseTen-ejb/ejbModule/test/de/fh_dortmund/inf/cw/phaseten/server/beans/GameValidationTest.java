/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.RoundStage;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;

/**
 * @author Dennis Schöneborn
 */
@RunWith(ApplicationComposer.class)
public class GameValidationTest {
	@EJB
	GameValidationBean gameValidation;

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

	// ENDE DER RUNDE
	// B: 5 Punkte
	// T: 0 Punkte

	// T: 70
	// B: 5 - Phase 2

	// B: 5 5 5 W | 1 2 3 4
	// T: 8 8 8 | 9 10 11 12
	// B: 5
	// T: 90

	// B: 4 5 W 7 8 9 10 11
	// T: 170
	// B: 5

	// B: 3 4 W 6 7 8 9 10
	// T: 220
	// B: 5

	// B: 3 4 5 6 7 8 9 W 11 12
	// T: 290
	// B: 5

	// T: W W 3 3 |
	// B: 90
	// T: 290

	// B: 4 4 4 4 | 3 3 W W
	// T: 5 6 7 W 9 10 11
	// T: 290
	// B: 95

	// T: 1 2 3 W 5 6 7 8
	// B: 195
	// T: 290

	// T: 1 2 3 4 W 6 W 8 9
	// B: 1 9 9 12 11 W W (rot)
	// T: 290
	// B: 200

	// B + T haben phase geschafft
	// B: 215
	// T: 2

	// T: (2x wild) nur blaue karten
	// T: 290
	// B: 285

	/**
	 * @author Björn Merschmeier
	 * @author Tim Prange
	 */
	@Test
	public void game() {
		Player t = new Player("Tim");
		Card tCard1 = new Card(Color.BLUE, CardValue.TWO);
		Card tCard2 = new Card(Color.BLUE, CardValue.FOUR);
		Card tCard3 = new Card(Color.BLUE, CardValue.FOUR);
		Card tCard4 = new Card(Color.BLUE, CardValue.FIVE);
		Card tCard5 = new Card(Color.BLUE, CardValue.FIVE);
		Card tCard6 = new Card(Color.BLUE, CardValue.SIX);
		Card tCard7 = new Card(Color.BLUE, CardValue.SEVEN);
		Card tCard8 = new Card(Color.BLUE, CardValue.NINE);
		Card tCard9 = new Card(Color.BLUE, CardValue.TWELVE);
		Card tCard10 = new Card(Color.NONE, CardValue.SKIP);
		t.addCardToPlayerPile(tCard1);
		t.addCardToPlayerPile(tCard2);
		t.addCardToPlayerPile(tCard3);
		t.addCardToPlayerPile(tCard4);
		t.addCardToPlayerPile(tCard5);
		t.addCardToPlayerPile(tCard6);
		t.addCardToPlayerPile(tCard7);
		t.addCardToPlayerPile(tCard8);
		t.addCardToPlayerPile(tCard9);
		t.addCardToPlayerPile(tCard10);

		Player b = new Player("Björn");
		Card bCard1 = new Card(Color.BLUE, CardValue.ONE);
		Card bCard2 = new Card(Color.BLUE, CardValue.FOUR);
		Card bCard3 = new Card(Color.BLUE, CardValue.SIX);
		Card bCard4 = new Card(Color.BLUE, CardValue.SEVEN);
		Card bCard5 = new Card(Color.BLUE, CardValue.NINE);
		Card bCard6 = new Card(Color.BLUE, CardValue.NINE);
		Card bCard7 = new Card(Color.BLUE, CardValue.ELEVEN);
		Card bCard8 = new Card(Color.BLUE, CardValue.ELEVEN);
		Card bCard9 = new Card(Color.BLUE, CardValue.TWELVE);
		Card bCard10 = new Card(Color.NONE, CardValue.WILD);
		b.addCardToPlayerPile(bCard1);
		b.addCardToPlayerPile(bCard2);
		b.addCardToPlayerPile(bCard3);
		b.addCardToPlayerPile(bCard4);
		b.addCardToPlayerPile(bCard5);
		b.addCardToPlayerPile(bCard6);
		b.addCardToPlayerPile(bCard7);
		b.addCardToPlayerPile(bCard8);
		b.addCardToPlayerPile(bCard9);
		b.addCardToPlayerPile(bCard10);

		Set<Player> players = new HashSet<>();
		players.add(t);
		players.add(b);

		Game g = new Game(players, new HashSet<Spectator>());

		PullStack pullStack = new PullStack();
		Card pullStack0 = new Card(Color.BLUE, CardValue.SEVEN);
		pullStack.addCard(pullStack0);
		Card pullStack1 = new Card(Color.BLUE, CardValue.ELEVEN);
		pullStack.addCard(pullStack1);
		Card pullStack2 = new Card(Color.BLUE, CardValue.FOUR);
		pullStack.addCard(pullStack2);
		Card pullStack3 = new Card(Color.BLUE, CardValue.NINE);
		pullStack.addCard(pullStack3);
		Card pullStack4 = new Card(Color.BLUE, CardValue.TWELVE);
		pullStack.addCard(pullStack4);
		Card pullStack5 = new Card(Color.BLUE, CardValue.TEN);
		pullStack.addCard(pullStack5);
		Card pullStack6 = new Card(Color.NONE, CardValue.WILD);
		pullStack.addCard(pullStack6);
		Card pullStack7 = new Card(Color.BLUE, CardValue.TWO);
		pullStack.addCard(pullStack7);
		Card pullStack8 = new Card(Color.BLUE, CardValue.FOUR);
		pullStack.addCard(pullStack8);
		Card pullStack9 = new Card(Color.BLUE, CardValue.THREE);
		pullStack.addCard(pullStack9);
		Card pullStack10 = new Card(Color.BLUE, CardValue.FIVE);
		pullStack.addCard(pullStack10);
		Card pullStack11 = new Card(Color.BLUE, CardValue.SIX);
		pullStack.addCard(pullStack11);
		Card pullStack12 = new Card(Color.BLUE, CardValue.ONE);
		pullStack.addCard(pullStack12);
		Card pullStack13 = new Card(Color.BLUE, CardValue.THREE);
		pullStack.addCard(pullStack13);
		Card pullStack14 = new Card(Color.NONE, CardValue.WILD);
		pullStack.addCard(pullStack14);
		Card pullStack15 = new Card(Color.BLUE, CardValue.SIX);
		pullStack.addCard(pullStack15);
		Card pullStack16 = new Card(Color.BLUE, CardValue.TWO);
		pullStack.addCard(pullStack16);
		Card pullStack17 = new Card(Color.NONE, CardValue.SKIP);
		pullStack.addCard(pullStack17);
		Card pullStack18 = new Card(Color.BLUE, CardValue.SEVEN);
		pullStack.addCard(pullStack18);
		Card pullStack19 = new Card(Color.BLUE, CardValue.TWO);
		pullStack.addCard(pullStack19);
		Card pullStack20 = new Card(Color.BLUE, CardValue.TEN);
		pullStack.addCard(pullStack20);
		Card pullStack21 = new Card(Color.BLUE, CardValue.SEVEN);
		pullStack.addCard(pullStack21);
		Card pullStack22 = new Card(Color.NONE, CardValue.SKIP);
		pullStack.addCard(pullStack22);
		g.setPullstack(pullStack);

		LiFoStack lifoStack = new LiFoStack();
		Card lifoStack1 = new Card(Color.BLUE, CardValue.TWELVE);
		lifoStack.addCard(lifoStack1);
		g.setLiFoStack(lifoStack);
		g.setCurrentPlayer(t);
		g.setInitialized();

		pullFromLiFo(t, g, lifoStack);

		laySkipCard(t, tCard10, b, g, lifoStack);

		Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, b));

		pullFromPullStack(t, g, pullStack);

		Assert.assertFalse(gameValidation.isValidDrawCardFromPullStack(g, t));

		laySkipCard(t, tCard10, b, g, lifoStack);

		Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, b));

		Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, t));

		pullFromPullStack(t, g, pullStack);

		layCardToLiFoStack(t, tCard1, g, lifoStack);

		pullFromPullStack(b, g, pullStack);

		layCardToLiFoStack(b, pullStack20, g, lifoStack);

		pullFromPullStack(t, g, pullStack);

		layCardToLiFoStack(t, tCard8, g, lifoStack);

		pullFromLiFo(b, g, lifoStack);

		Assert.assertTrue(b.getPlayerPile().getCards().contains(tCard8));

		Collection<DockPile> pilesInCollection = new ArrayList<>();
		DockPile phase1b1 = new SetDockPile(CardValue.NINE);
		Assert.assertTrue(phase1b1.addCard(bCard5));
		Assert.assertTrue(phase1b1.addCard(bCard6));
		Assert.assertTrue(phase1b1.addCard(tCard8));
		Assert.assertFalse(phase1b1.addCard(bCard4));

		DockPile phase1b2 = new SetDockPile(CardValue.ELEVEN);
		Assert.assertTrue(phase1b2.addCard(bCard7));
		Assert.assertTrue(phase1b2.addCard(bCard8));
		Assert.assertTrue(phase1b2.addCard(bCard10));

		pilesInCollection.add(phase1b1);
		pilesInCollection.add(phase1b2);

		Assert.assertTrue(gameValidation.isValidLayStageToTable(g, b, pilesInCollection));

		b.setPlayerLaidStage(true);

		Assert.assertFalse(gameValidation.isValidLayStageToTable(g, b, pilesInCollection));

		b.removeCardFromPlayerPile(bCard5);
		b.removeCardFromPlayerPile(bCard6);
		b.removeCardFromPlayerPile(bCard8);
		b.removeCardFromPlayerPile(bCard7);
		b.removeCardFromPlayerPile(bCard10);
		b.removeCardFromPlayerPile(tCard8);

		g.addOpenPile(phase1b1);
		g.addOpenPile(phase1b2);

		layCardToLiFoStack(b, bCard9, g, lifoStack);

		Assert.assertFalse(gameValidation.isValidToAddCard(g, t, phase1b1, tCard1));
		
		pullFromLiFo(t, g, lifoStack);
		Assert.assertTrue(t.getPlayerPile().getCards().contains(bCard9));
		// T: 12 von LIFO
		
		layCardToLiFoStack(t, tCard6, g, lifoStack);
		// T: 6 auf LIFO
		
		pullFromLiFo(b, g, lifoStack);
		// B: 6 von LIFO
		
		layCardToLiFoStack(b, bCard4, g, lifoStack);
		// B: 7 auf LIFO
		
		pullFromLiFo(t, g, lifoStack);
		// T: 7 von LIFO
		
		Collection<DockPile> pilesInCollectionT = new ArrayList<>();
		DockPile phase1t1 = new SetDockPile(CardValue.SEVEN);
		Assert.assertTrue(phase1t1.addCard(bCard4));
		Assert.assertTrue(phase1t1.addCard(tCard7));
		Assert.assertTrue(phase1t1.addCard(pullStack21));

		DockPile phase1t2 = new SetDockPile(CardValue.TWELVE);
		Assert.assertTrue(phase1t2.addCard(tCard9));
		Assert.assertTrue(phase1t2.addCard(bCard9));
		Assert.assertTrue(phase1t2.addCard(lifoStack1));

		pilesInCollectionT.add(phase1t1);
		pilesInCollectionT.add(phase1t2);

		Assert.assertTrue(gameValidation.isValidLayStageToTable(g, t, pilesInCollectionT));

		t.setPlayerLaidStage(true);

		Assert.assertFalse(gameValidation.isValidLayStageToTable(g, t, pilesInCollectionT));

		t.removeCardFromPlayerPile(bCard4);
		t.removeCardFromPlayerPile(tCard7);
		t.removeCardFromPlayerPile(pullStack21);
		t.removeCardFromPlayerPile(tCard9);
		t.removeCardFromPlayerPile(bCard9);
		t.removeCardFromPlayerPile(lifoStack1);

		g.addOpenPile(phase1t1);
		g.addOpenPile(phase1t2);
		// T: PHASE 7,7,7 12,12,12
		
		layCardToLiFoStack(t, tCard1, g, lifoStack);
		// T: 2 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 7 von Pull
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, b, phase1t1, pullStack18));
		phase1t1.addCard(pullStack18);
		b.removeCardFromPlayerPile(pullStack18);
		// B: 7 Anlegen
		
		layCardToLiFoStack(b, bCard1, g, lifoStack);
		// B: 1 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: s von Pull
		
		laySkipCard(t, pullStack17, b, g, lifoStack);
		// T: s auf LIFO B auswählen

		pullFromPullStack(t, g, pullStack);
		// T: 2 von PULL
		
		layCardToLiFoStack(t, pullStack16, g, lifoStack);
		// T: 2 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 6 vom PULL
		
		layCardToLiFoStack(b, bCard2, g, lifoStack);
		// B: 4 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: W von PULL
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1b2, pullStack14));
		phase1b2.addCard(pullStack14);
		t.removeCardFromPlayerPile(pullStack14);
		// T: W an 11 anlegen
		
		layCardToLiFoStack(t, tCard4, g, lifoStack);
		// T: 5 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 3 von PULL
		
		layCardToLiFoStack(b, pullStack13, g, lifoStack);
		// B: 3 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 1 von PULL
		
		layCardToLiFoStack(t, pullStack12, g, lifoStack);
		// T: 1 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 6 von PULL
		
		layCardToLiFoStack(b, pullStack11, g, lifoStack);
		// B: 6 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: 5 von PULL
		
		layCardToLiFoStack(t, pullStack10, g, lifoStack);
		// T: 5 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 3 von PULL
		
		layCardToLiFoStack(b, pullStack9, g, lifoStack);
		// B: 3 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: 4 von PULL
		
		layCardToLiFoStack(t, tCard5, g, lifoStack);
		// T: 5 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 2 von PULL
		
		layCardToLiFoStack(b, pullStack7, g, lifoStack);
		// B: 2 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: W von PULL
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1b1, pullStack6));
		phase1b1.addCard(pullStack6);
		t.removeCardFromPlayerPile(pullStack6);
		// T: W an 9
		
		layCardToLiFoStack(t, tCard2, g, lifoStack);
		// T: 4 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 10 von PULL
		
		layCardToLiFoStack(b, pullStack5, g, lifoStack);
		// B: 10 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: 12 von PULL
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1t2, pullStack4));
		phase1t2.addCard(pullStack4);
		t.removeCardFromPlayerPile(pullStack4);
		// T: 12 an 12 anlegen
		
		layCardToLiFoStack(t, tCard3, g, lifoStack);
		// T: 4 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 9 von PULL
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, b, phase1b1, pullStack3));
		phase1b1.addCard(pullStack3);
		b.removeCardFromPlayerPile(pullStack3);
		// B: 9 anlegen an 9en
		
		layCardToLiFoStack(b, bCard3, g, lifoStack);
		// B: 6 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: 4 von PULL
		
		layCardToLiFoStack(t, pullStack2, g, lifoStack);
		// T: 4 auf LIFO
		
		pullFromPullStack(b, g, pullStack);
		// B: 11 von PULL
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, b, phase1b2, pullStack1));
		phase1b2.addCard(pullStack1);
		b.removeCardFromPlayerPile(pullStack1);
		// B: 11 an 11en anlegen
		
		layCardToLiFoStack(b, pullStack15, g, lifoStack);
		// B: 6 auf LIFO
		
		pullFromPullStack(t, g, pullStack);
		// T: 7 von PULL
		
		Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1t1, pullStack0));
		phase1t1.addCard(pullStack0);
		t.removeCardFromPlayerPile(pullStack0);
		// T: 7 an 7en anlegen
		
		layCardToLiFoStack(t, pullStack8, g, lifoStack);
		// T: 4 auf LIFO
		
		Assert.assertEquals(0, t.getPlayerPile().getCards().size());
		Assert.assertEquals(1, b.getPlayerPile().getCards().size());
	}
	
	//TODO - BM - 13.01.2018 - Es müssen noch die einzelnen Phasen getestet werden

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param player
	 * @param card
	 * @param g
	 * @param lifoStack
	 */
	private void layCardToLiFoStack(Player player, Card card, Game g, LiFoStack lifoStack) {
		Assert.assertTrue(gameValidation.isValidPushCardToLiFoStack(g, player, card));
		player.removeCardFromPlayerPile(card);
		lifoStack.addCard(card);
		player.addRoundStage();
		Assert.assertEquals(RoundStage.FINISHED, player.getRoundStage());
		setNextPlayer(g);
	}

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param player
	 * @param g
	 * @param pullStack
	 */
	private void pullFromPullStack(Player player, Game g, PullStack pullStack) {
		Assert.assertTrue(gameValidation.isValidDrawCardFromPullStack(g, player));
		player.addCardToPlayerPile(pullStack.pullTopCard());
		player.addRoundStage();
		Assert.assertEquals(RoundStage.PUT_AND_PUSH, player.getRoundStage());
	}

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	private void setNextPlayer(Game game) {
		Player nextPlayer = game.getNextPlayer();
		game.setCurrentPlayer(nextPlayer);

		if (nextPlayer.hasSkipCard()) {
			nextPlayer.removeSkipCard();
			setNextPlayer(game);

		}
		nextPlayer.resetRoundStage();
	}

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param player
	 * @param card
	 * @param destination
	 * @param g
	 * @param lifoStack
	 */

	private void laySkipCard(Player player, Card card, Player destination, Game g, LiFoStack lifoStack) {
		Assert.assertTrue(gameValidation.isValidLaySkipCard(player, destination, g));
		player.getPlayerPile().removeCard(card);
		lifoStack.addCard(card);
		destination.givePlayerSkipCard();
		player.addRoundStage();
		setNextPlayer(g);
	}

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param t
	 * @param g
	 * @param lifoStack
	 */
	private void pullFromLiFo(Player t, Game g, LiFoStack lifoStack) {
		Assert.assertTrue(gameValidation.isValidDrawCardFromLiFoStack(g, t));
		t.addCardToPlayerPile(lifoStack.pullTopCard());
		t.addRoundStage();
		Assert.assertTrue(t.getRoundStage() == RoundStage.PUT_AND_PUSH);
	}

	@Test
	public void testIsValidPushCardToLiFoStack() throws Exception {

	}

	@Test
	public void testIsValidDrawCardFromPullStack() throws Exception {

	}

	@Test
	public void testIsValidLayStageToTable() throws Exception {

	}

	@Test
	public void testIsValidToAddCard() throws Exception {

	}

	@Test
	public void testIsValidLaySkipCard() throws Exception {

	}
}
