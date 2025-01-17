/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import static org.junit.Assert.assertEquals;

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
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.RoundStage;

/**
 * @author Dennis Schöneborn
 * @author Tim Prange
 * @author Björn Merschmeier
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
		liFo.addLast(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		game.setInitialized();
		// Everything ok
		Assert.assertEquals(true, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		liFo.addLast(new Card(Color.NONE, CardValue.SKIP));
		// SKIP on top of LiFo Stack
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		liFo.addLast(new Card(Color.BLUE, CardValue.SEVEN));
		p1.addRoundStage();
		// Player in wrong stage
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));
	}

	/**
	 * @author Björn Merschmeier
	 * @author Tim Prange
	 */
	@Test
	public void testGameValidation() {
		Player t = new Player("Tim");
		Card tCardTwo = new Card(Color.BLUE, CardValue.TWO,24);
		Card tCardFour1 = new Card(Color.BLUE, CardValue.FOUR,25);
		Card tCardFour2 = new Card(Color.BLUE, CardValue.FOUR,26);
		Card tCardFive1 = new Card(Color.BLUE, CardValue.FIVE,27);
		Card tCardFive2 = new Card(Color.BLUE, CardValue.FIVE,28);
		Card tCardSix = new Card(Color.BLUE, CardValue.SIX,29);
		Card tCardSeven = new Card(Color.BLUE, CardValue.SEVEN,30);
		Card tCardNine = new Card(Color.BLUE, CardValue.NINE,31);
		Card tCardTwelve = new Card(Color.BLUE, CardValue.TWELVE,32);
		Card tCardSkip = new Card(Color.NONE, CardValue.SKIP,33);
		t.addCardToPlayerPile(tCardTwo);
		t.addCardToPlayerPile(tCardFour1);
		t.addCardToPlayerPile(tCardFour2);
		t.addCardToPlayerPile(tCardFive1);
		t.addCardToPlayerPile(tCardFive2);
		t.addCardToPlayerPile(tCardSix);
		t.addCardToPlayerPile(tCardSeven);
		t.addCardToPlayerPile(tCardNine);
		t.addCardToPlayerPile(tCardTwelve);
		t.addCardToPlayerPile(tCardSkip);

		Player b = new Player("Björn");
		Card bCardOne = new Card(Color.BLUE, CardValue.ONE,34);
		Card bCardFour = new Card(Color.BLUE, CardValue.FOUR,35);
		Card bCardSix = new Card(Color.BLUE, CardValue.SIX,36);
		Card bCardSeven = new Card(Color.BLUE, CardValue.SEVEN,37);
		Card bCardNine1 = new Card(Color.BLUE, CardValue.NINE,38);
		Card bCardNine2 = new Card(Color.BLUE, CardValue.NINE,39);
		Card bCardEleven1 = new Card(Color.BLUE, CardValue.ELEVEN,40);
		Card bCardEleven2 = new Card(Color.BLUE, CardValue.ELEVEN,41);
		Card bCardTwelve = new Card(Color.BLUE, CardValue.TWELVE,42);
		Card bCardWild = new Card(Color.NONE, CardValue.WILD,43);
		b.addCardToPlayerPile(bCardOne);
		b.addCardToPlayerPile(bCardFour);
		b.addCardToPlayerPile(bCardSix);
		b.addCardToPlayerPile(bCardSeven);
		b.addCardToPlayerPile(bCardNine1);
		b.addCardToPlayerPile(bCardNine2);
		b.addCardToPlayerPile(bCardEleven1);
		b.addCardToPlayerPile(bCardEleven2);
		b.addCardToPlayerPile(bCardTwelve);
		b.addCardToPlayerPile(bCardWild);

		Set<Player> players = new HashSet<>();
		players.add(t);
		players.add(b);

		Game g = new Game(players, new HashSet<Spectator>());

		PullStack pullStack = new PullStack();
		Card pullStackSeven1 = new Card(Color.BLUE, CardValue.SEVEN, 0);
		pullStack.addLast(pullStackSeven1);
		Card pullStackEleven1 = new Card(Color.BLUE, CardValue.ELEVEN, 1);
		pullStack.addLast(pullStackEleven1);
		Card pullStackFour1 = new Card(Color.BLUE, CardValue.FOUR, 2);
		pullStack.addLast(pullStackFour1);
		Card pullStackNine1 = new Card(Color.BLUE, CardValue.NINE,3);
		pullStack.addLast(pullStackNine1);
		Card pullStackTwelve1 = new Card(Color.BLUE, CardValue.TWELVE, 4);
		pullStack.addLast(pullStackTwelve1);
		Card pullStackTen1 = new Card(Color.BLUE, CardValue.TEN, 5);
		pullStack.addLast(pullStackTen1);
		Card pullStackWild1 = new Card(Color.NONE, CardValue.WILD,6);
		pullStack.addLast(pullStackWild1);
		Card pullStackTwo1 = new Card(Color.BLUE, CardValue.TWO,7);
		pullStack.addLast(pullStackTwo1);
		Card pullStackFour2 = new Card(Color.BLUE, CardValue.FOUR,8);
		pullStack.addLast(pullStackFour2);
		Card pullStackThree1 = new Card(Color.BLUE, CardValue.THREE,9);
		pullStack.addLast(pullStackThree1);
		Card pullStackFive1 = new Card(Color.BLUE, CardValue.FIVE,10);
		pullStack.addLast(pullStackFive1);
		Card pullStackSix1 = new Card(Color.BLUE, CardValue.SIX,11);
		pullStack.addLast(pullStackSix1);
		Card pullStackOne1 = new Card(Color.BLUE, CardValue.ONE,12);
		pullStack.addLast(pullStackOne1);
		Card pullStackThree2 = new Card(Color.BLUE, CardValue.THREE,13);
		pullStack.addLast(pullStackThree2);
		Card pullStackWild2 = new Card(Color.NONE, CardValue.WILD,14);
		pullStack.addLast(pullStackWild2);
		Card pullStackSix2 = new Card(Color.BLUE, CardValue.SIX,15);
		pullStack.addLast(pullStackSix2);
		Card pullStackTwo2 = new Card(Color.BLUE, CardValue.TWO,16);
		pullStack.addLast(pullStackTwo2);
		Card pullStackSkip1 = new Card(Color.NONE, CardValue.SKIP,17);
		pullStack.addLast(pullStackSkip1);
		Card pullStackSeven2 = new Card(Color.BLUE, CardValue.SEVEN,18);
		pullStack.addLast(pullStackSeven2);
		Card pullStackTwo3 = new Card(Color.BLUE, CardValue.TWO,19);
		pullStack.addLast(pullStackTwo3);
		Card pullStackTen2 = new Card(Color.BLUE, CardValue.TEN,20);
		pullStack.addLast(pullStackTen2);
		Card pullStackSeven3 = new Card(Color.BLUE, CardValue.SEVEN,21);
		pullStack.addLast(pullStackSeven3);
		Card pullStackSkip2 = new Card(Color.NONE, CardValue.SKIP,22);
		pullStack.addLast(pullStackSkip2);
		g.setPullstack(pullStack);

		LiFoStack lifoStack = new LiFoStack();
		Card lifoStackTwelve = new Card(Color.BLUE, CardValue.TWELVE,23);
		lifoStack.addLast(lifoStackTwelve);
		g.setLiFoStack(lifoStack);
		g.setCurrentPlayer(t);
		g.setInitialized();

		pullFromLiFo(t, g, lifoStack);
		// T: 12 von LIFO

		laySkipCard(t, tCardSkip, b, g, lifoStack);
		// T: Skip legen und B auswählen

		Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, b));

		pullFromPullStack(t, g, pullStack);
		// T: SKIP von PULL

		Assert.assertFalse(gameValidation.isValidDrawCardFromPullStack(g, t));

		laySkipCard(t, tCardSkip, b, g, lifoStack);
		// T: Skip legen und B auswählen

		Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, b));

		Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, t));

		pullFromPullStack(t, g, pullStack);
		// T: 7 von PULL

		layCardToLiFoStack(t, tCardTwo, g, lifoStack);
		// T: 2 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 10 von PULL

		layCardToLiFoStack(b, pullStackTen2, g, lifoStack);
		// B: 10 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 2 von PULL

		layCardToLiFoStack(t, tCardNine, g, lifoStack);
		// T: 9 auf LIFO

		pullFromLiFo(b, g, lifoStack);
		// B: 9 von LIFO

		Assert.assertTrue(b.getPlayerPile().containsCard(tCardNine));

		Collection<DockPile> pilesInCollection = new ArrayList<>();
		DockPile phase1b1 = new SetDockPile(CardValue.NINE);
		Assert.assertTrue(phase1b1.addLast(bCardNine1));
		Assert.assertTrue(phase1b1.addLast(bCardNine2));
		Assert.assertTrue(phase1b1.addLast(tCardNine));
		Assert.assertFalse(phase1b1.addLast(bCardSeven));

		DockPile phase1b2 = new SetDockPile(CardValue.ELEVEN);
		Assert.assertTrue(phase1b2.addLast(bCardEleven1));
		Assert.assertTrue(phase1b2.addLast(bCardEleven2));
		Assert.assertTrue(phase1b2.addLast(bCardWild));

		pilesInCollection.add(phase1b1);
		pilesInCollection.add(phase1b2);

		Assert.assertTrue(gameValidation.isValidLayStageToTable(g, b, pilesInCollection));

		b.setPlayerLaidStage(true);

		Assert.assertFalse(gameValidation.isValidLayStageToTable(g, b, pilesInCollection));

		b.removeCardFromPlayerPile(bCardNine1);
		b.removeCardFromPlayerPile(bCardNine2);
		b.removeCardFromPlayerPile(bCardEleven2);
		b.removeCardFromPlayerPile(bCardEleven1);
		b.removeCardFromPlayerPile(bCardWild);
		b.removeCardFromPlayerPile(tCardNine);

		g.addOpenPile(phase1b1);
		g.addOpenPile(phase1b2);
		// B: 9 9 9 und 11 11 W auslegen

		layCardToLiFoStack(b, bCardTwelve, g, lifoStack);
		// B: 12 auf LIFO

		Assert.assertFalse(gameValidation.isValidToAddCardFirst(g, t, phase1b1, tCardTwo));
		Assert.assertFalse(gameValidation.isValidToAddCardLast(g, t, phase1b1, tCardTwo));

		pullFromLiFo(t, g, lifoStack);
		Assert.assertTrue(t.getPlayerPile().containsCard(bCardTwelve));
		// T: 12 von LIFO

		layCardToLiFoStack(t, tCardSix, g, lifoStack);
		// T: 6 auf LIFO

		pullFromLiFo(b, g, lifoStack);
		// B: 6 von LIFO

		layCardToLiFoStack(b, bCardSeven, g, lifoStack);
		// B: 7 auf LIFO

		pullFromLiFo(t, g, lifoStack);
		// T: 7 von LIFO

		Collection<DockPile> pilesInCollectionT = new ArrayList<>();
		DockPile phase1t1 = new SetDockPile(CardValue.SEVEN);
		Assert.assertTrue(phase1t1.addLast(bCardSeven));
		Assert.assertTrue(phase1t1.addLast(tCardSeven));
		Assert.assertTrue(phase1t1.addLast(pullStackSeven3));

		DockPile phase1t2 = new SetDockPile(CardValue.TWELVE);
		Assert.assertTrue(phase1t2.addLast(tCardTwelve));
		Assert.assertTrue(phase1t2.addLast(bCardTwelve));
		Assert.assertTrue(phase1t2.addLast(lifoStackTwelve));

		pilesInCollectionT.add(phase1t1);
		pilesInCollectionT.add(phase1t2);

		Assert.assertTrue(gameValidation.isValidLayStageToTable(g, t, pilesInCollectionT));

		t.setPlayerLaidStage(true);

		Assert.assertFalse(gameValidation.isValidLayStageToTable(g, t, pilesInCollectionT));

		t.removeCardFromPlayerPile(bCardSeven);
		t.removeCardFromPlayerPile(tCardSeven);
		t.removeCardFromPlayerPile(pullStackSeven3);
		t.removeCardFromPlayerPile(tCardTwelve);
		t.removeCardFromPlayerPile(bCardTwelve);
		t.removeCardFromPlayerPile(lifoStackTwelve);

		g.addOpenPile(phase1t1);
		g.addOpenPile(phase1t2);
		// T: PHASE 7,7,7 12,12,12

		layCardToLiFoStack(t, tCardTwo, g, lifoStack);
		// T: 2 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 7 von Pull

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, b, phase1t1, pullStackSeven2));
		phase1t1.addLast(pullStackSeven2);
		b.removeCardFromPlayerPile(pullStackSeven2);
		// B: 7 Anlegen

		layCardToLiFoStack(b, bCardOne, g, lifoStack);
		// B: 1 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: s von Pull

		laySkipCard(t, pullStackSkip1, b, g, lifoStack);
		// T: s auf LIFO B auswählen

		pullFromPullStack(t, g, pullStack);
		// T: 2 von PULL

		layCardToLiFoStack(t, pullStackTwo2, g, lifoStack);
		// T: 2 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 6 vom PULL

		layCardToLiFoStack(b, bCardFour, g, lifoStack);
		// B: 4 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: W von PULL

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, t, phase1b2, pullStackWild2));
		phase1b2.addLast(pullStackWild2);
		t.removeCardFromPlayerPile(pullStackWild2);
		// T: W an 11 anlegen

		layCardToLiFoStack(t, tCardFive1, g, lifoStack);
		// T: 5 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 3 von PULL

		layCardToLiFoStack(b, pullStackThree2, g, lifoStack);
		// B: 3 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 1 von PULL

		layCardToLiFoStack(t, pullStackOne1, g, lifoStack);
		// T: 1 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 6 von PULL

		layCardToLiFoStack(b, pullStackSix1, g, lifoStack);
		// B: 6 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 5 von PULL

		layCardToLiFoStack(t, pullStackFive1, g, lifoStack);
		// T: 5 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 3 von PULL

		layCardToLiFoStack(b, pullStackThree1, g, lifoStack);
		// B: 3 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 4 von PULL

		layCardToLiFoStack(t, tCardFive2, g, lifoStack);
		// T: 5 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 2 von PULL

		layCardToLiFoStack(b, pullStackTwo1, g, lifoStack);
		// B: 2 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: W von PULL

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, t, phase1b1, pullStackWild1));
		phase1b1.addLast(pullStackWild1);
		t.removeCardFromPlayerPile(pullStackWild1);
		// T: W an 9

		layCardToLiFoStack(t, tCardFour1, g, lifoStack);
		// T: 4 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 10 von PULL

		layCardToLiFoStack(b, pullStackTen1, g, lifoStack);
		// B: 10 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 12 von PULL

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, t, phase1t2, pullStackTwelve1));
		phase1t2.addLast(pullStackTwelve1);
		t.removeCardFromPlayerPile(pullStackTwelve1);
		// T: 12 an 12 anlegen

		layCardToLiFoStack(t, tCardFour2, g, lifoStack);
		// T: 4 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 9 von PULL

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, b, phase1b1, pullStackNine1));
		phase1b1.addLast(pullStackNine1);
		b.removeCardFromPlayerPile(pullStackNine1);
		// B: 9 anlegen an 9en

		layCardToLiFoStack(b, bCardSix, g, lifoStack);
		// B: 6 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 4 von PULL

		layCardToLiFoStack(t, pullStackFour1, g, lifoStack);
		// T: 4 auf LIFO

		pullFromPullStack(b, g, pullStack);
		// B: 11 von PULL

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, b, phase1b2, pullStackEleven1));
		phase1b2.addLast(pullStackEleven1);
		b.removeCardFromPlayerPile(pullStackEleven1);
		// B: 11 an 11en anlegen

		layCardToLiFoStack(b, pullStackSix2, g, lifoStack);
		// B: 6 auf LIFO

		pullFromPullStack(t, g, pullStack);
		// T: 7 von PULL

		Assert.assertTrue(gameValidation.isValidToAddCardFirst(g, t, phase1t1, pullStackSeven1));
		phase1t1.addLast(pullStackSeven1);
		t.removeCardFromPlayerPile(pullStackSeven1);
		// T: 7 an 7en anlegen

		layCardToLiFoStack(t, pullStackFour2, g, lifoStack);
		// T: 4 auf LIFO

	}

	@Test
	public void testIsValidPushCardToLiFoStack() throws Exception {
		HashSet<Player> players = new HashSet<>();
		Player p1 = new Player("P1");
		Player p2 = new Player("P2");
		players.add(p1);
		players.add(p2);
		Game game = new Game(players, new HashSet<Spectator>());
		game.setCurrentPlayer(p1);

		LiFoStack liFo = new LiFoStack();
		liFo.addLast(new Card(Color.GREEN, CardValue.ELEVEN));
		game.setLiFoStack(liFo);

		// Game is not initialized
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		game.setInitialized();
		// Everything ok
		Assert.assertEquals(true, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		liFo.addLast(new Card(Color.NONE, CardValue.SKIP));
		// SKIP on top of LiFo Stack
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		liFo.addLast(new Card(Color.BLUE, CardValue.SEVEN));

		pullFromLiFo(p1, game, liFo);
		// Player in wrong stage
		Assert.assertEquals(false, gameValidation.isValidDrawCardFromLiFoStack(game, p1));

		// Everything ok
		Assert.assertEquals(true, gameValidation.isValidPushCardToLiFoStack(game, p1, p1.getPlayerPile().getCard(0)));

		layCardToLiFoStack(p1, p1.getPlayerPile().getCard(0), game, liFo);
	}

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
		liFo.addLast(new Card(Color.GREEN, CardValue.ELEVEN));
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
	public void testPileIsFull() throws Exception {
		SequenceDockPile dockPile = new SequenceDockPile();

		dockPile.addLast(new Card(Color.BLUE, CardValue.ONE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWO));
		dockPile.addLast(new Card(Color.BLUE, CardValue.THREE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FOUR));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FIVE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.SIX));
		dockPile.addLast(new Card(Color.BLUE, CardValue.SEVEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.EIGHT));
		dockPile.addLast(new Card(Color.BLUE, CardValue.NINE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.ELEVEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWELVE));

		assertEquals(true, pileIsFull(dockPile));

		dockPile = new SequenceDockPile();
		dockPile.addLast(new Card(Color.BLUE, CardValue.ONE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWO));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FOUR));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FIVE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.SIX));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.EIGHT));
		dockPile.addLast(new Card(Color.BLUE, CardValue.NINE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.ELEVEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWELVE));

		assertEquals(true, pileIsFull(dockPile));

		dockPile = new SequenceDockPile();
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWO));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FOUR));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FIVE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.SIX));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.EIGHT));
		dockPile.addLast(new Card(Color.BLUE, CardValue.NINE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.ELEVEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWELVE));

		assertEquals(true, pileIsFull(dockPile));

		dockPile = new SequenceDockPile();
		dockPile.addLast(new Card(Color.BLUE, CardValue.ONE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TWO));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FOUR));
		dockPile.addLast(new Card(Color.BLUE, CardValue.FIVE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.SIX));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));
		dockPile.addLast(new Card(Color.BLUE, CardValue.EIGHT));
		dockPile.addLast(new Card(Color.BLUE, CardValue.NINE));
		dockPile.addLast(new Card(Color.BLUE, CardValue.TEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.ELEVEN));
		dockPile.addLast(new Card(Color.BLUE, CardValue.WILD));

		assertEquals(true, pileIsFull(dockPile));
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
		liFo.addLast(new Card(Color.GREEN, CardValue.ELEVEN));
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
		liFo.addLast(new Card(Color.GREEN, CardValue.ELEVEN));
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


	private boolean pileIsFull(SequenceDockPile dockPile) {
		return dockPile.getCopyOfCardsList().size() == 12;
	}

	/**
	 * Lay a card to the lifo stack (only test-method)
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
		lifoStack.addLast(card);
		player.addRoundStage();
		Assert.assertEquals(RoundStage.FINISHED, player.getRoundStage());
		setNextPlayer(g);
	}

	/**
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
	 * Lay a skip card for the player (only called from test-methods)
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
		lifoStack.addLast(card);
		destination.givePlayerSkipCard();
		player.addRoundStage();
		setNextPlayer(g);
	}

	/**
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
}
