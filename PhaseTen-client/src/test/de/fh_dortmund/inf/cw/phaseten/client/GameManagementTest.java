package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class GameManagementTest {
	private ServiceHandlerImpl serviceHandler;
	
	private CountDownLatch latchPlayer;
	private CountDownLatch latchGame;
	private Message messagePlayer;
	private Message messageGame;

	@Before
	public void setUp() throws Exception {
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.getPlayerConsumer().setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messagePlayer = message;
				latchPlayer.countDown();
			}
		});
		this.serviceHandler.getGameConsumer().setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messageGame  = message;
				latchGame.countDown();
			}
		});
	}
	
	@After
	public void tearDown() throws Exception {
		this.serviceHandler.getPlayerConsumer().setMessageListener(null);	
		this.serviceHandler.getGameConsumer().setMessageListener(null);	
	}

	//@Test
	public void testRequestGameMessage() throws Exception {		
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.requestGameMessage();
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}

	//@Test
	public void testTakeCardFromDrawPile() throws Exception {
		// throws NotYourTurnException, CardAlreadyTakenInThisTurnException;

		//TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser Test funktioniert
		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.takeCardFromPullstack();
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}

	//@Test
	public void testTakeCardFromDiscardPile() throws Exception {
		// throws NotYourTurnException, CardAlreadyTakenInThisTurnException;

		//TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser Test funktioniert
		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.takeCardFromLiFoStack();
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}

	//@Test
	public void testAddToOpenPile() throws Exception {
		// throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException;

		//TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser Test funktioniert
		Card card = new Card(Color.BLUE, CardValue.ONE);
		DockPile dockPile = new ColorDockPile(Color.BLUE);		

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.addToPileOnTable(card, dockPile);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}

	//@Test
	public void testGoOut() throws Exception {
		// throws NotYourTurnException, InvalidCardCompilationException;
		//TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser Test funktioniert
		
		Collection<DockPile> piles = new ArrayList<DockPile>();
		
		DockPile cards = new SetDockPile(CardValue.ONE);
		cards.addCard(new Card(Color.BLUE, CardValue.ONE));
		cards.addCard(new Card(Color.BLUE, CardValue.ONE));
		cards.addCard(new Card(Color.BLUE, CardValue.ONE));
		
		piles.add(cards);

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.layPhaseToTable(piles);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}

	//@Test
	public void testDiscardCardToDiscardPile() throws Exception {
		// throws NotYourTurnException, TakeCardBeforeDiscardingException;
		//TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser Test funktioniert
		
		Card card = new Card(Color.BLUE, CardValue.ONE);

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.layCardToLiFoStack(card);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}
	
	//@Test
	public void testLaySkipCardInfrontOfPlayer()
	{
		//TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser Test funktioniert
		throw new NotImplementedException();
	}
}
