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
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;

/**
 * @author Marc Mettke
 */
public class GameManagmentTest {
	private ServiceHandlerImpl serviceHandler;
	
	private CountDownLatch latchPlayer;
	private CountDownLatch latchGame;
	private Message messagePlayer;
	private Message messageGame;

	@Before
	public void setUp() throws Exception {
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.playerConsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messagePlayer = message;
				latchPlayer.countDown();
			}
		});
		this.serviceHandler.gameConsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messageGame  = message;
				latchGame.countDown();
			}
		});
	}
	
	@After
	public void tearDown() throws Exception {
		this.serviceHandler.playerConsumer.setMessageListener(null);	
		this.serviceHandler.gameConsumer.setMessageListener(null);	
	}

	@Test
	public void testRequestGameMessage() throws Exception {
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.requestGameMessage();
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}

	@Test
	public void testTakeCardFromDrawPile() throws Exception {
		// throws NotYourTurnException, CardAlreadyTakenInThisTurnException;

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.takeCardFromPullstack();
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}

	@Test
	public void testTakeCardFromDiscardPile() throws Exception {
		// throws NotYourTurnException, CardAlreadyTakenInThisTurnException;

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.takeCardFromLiFoStack();
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}

	@Test
	public void testAddToOpenPile() throws Exception {
		// throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException;
		
		Card card = new Card(Color.BLUE, CardValue.ONE);
		DockPile dockPile = new ColorDockPile(Color.BLUE);		

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.addToPileOnTable(card, dockPile);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}

	@Test
	public void testGoOut() throws Exception {
		// throws NotYourTurnException, InvalidCardCompilationException;
		
		Collection<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Color.BLUE, CardValue.ONE));
		cards.add(new Card(Color.BLUE, CardValue.ONE));
		cards.add(new Card(Color.BLUE, CardValue.ONE));

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.layPhaseToTable(cards);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}

	@Test
	public void testDiscardCardToDiscardPile() throws Exception {
		// throws NotYourTurnException, TakeCardBeforeDiscardingException;
		
		Card card = new Card(Color.BLUE, CardValue.ONE);

		this.latchPlayer = new CountDownLatch(1);
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.layCardToLiFoStack(card);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchGame.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}
}
