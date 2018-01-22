package de.fh_dortmund.inf.cw.phaseten.client;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Sebastian Seitz
 */
public class GameManagementTest {
	private ServiceHandlerImpl serviceHandler1;
	private ServiceHandlerImpl serviceHandler2;

	private CountDownLatch latchPlayer1;
	private CountDownLatch latchGame1;
	private Message messagePlayer1;
	private Message messageGame1;
	private CountDownLatch latchPlayer2;
	private CountDownLatch latchGame2;
	private Message messagePlayer2;
	private Message messageGame2;

	@Before
	public void setUp() throws Exception {

		Constructor<ServiceHandlerImpl> c = ServiceHandlerImpl.class.getDeclaredConstructor();
		c.setAccessible(true);
		this.serviceHandler1 = c.newInstance();
		this.serviceHandler2 = c.newInstance();

		try {
			serviceHandler1.register("Tim", "tim");
		} catch (UsernameAlreadyTakenException e) {
			serviceHandler1.login("Tim", "tim");
		}

		try {
			serviceHandler2.register("Björn", "björn");
		} catch (UsernameAlreadyTakenException e) {
			serviceHandler2.login("Björn", "björn");
		}

		serviceHandler1.enterLobbyAsPlayer();
		serviceHandler2.enterLobbyAsPlayer();

		latchGame1 = new CountDownLatch(1);
		latchGame2 = new CountDownLatch(1);

		this.serviceHandler1.getPlayerConsumer().setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				messagePlayer1 = message;

				if (latchPlayer1 != null && latchPlayer1.getCount() > 0) {
					latchPlayer1.countDown();
				}
			}
		});
		this.serviceHandler1.getGameConsumer().setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				messageGame1 = message;

				if (latchGame1 != null && latchGame1.getCount() > 0) {
					latchGame1.countDown();
				}
			}
		});

		this.serviceHandler2.getPlayerConsumer().setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				messagePlayer2 = message;

				if (latchPlayer2 != null && latchPlayer2.getCount() > 0) {
					latchPlayer2.countDown();
				}
			}
		});
		this.serviceHandler2.getGameConsumer().setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				messageGame2 = message;

				if (latchGame2 != null && latchGame2.getCount() > 0) {
					latchGame2.countDown();
				}
			}
		});
		serviceHandler1.startGame();
		latchGame1.await(30, TimeUnit.SECONDS);
		latchGame2.await(30, TimeUnit.SECONDS);

	}

	@After
	public void tearDown() throws Exception {
		this.serviceHandler1.getPlayerConsumer().setMessageListener(null);
		this.serviceHandler1.getGameConsumer().setMessageListener(null);
		this.serviceHandler2.getPlayerConsumer().setMessageListener(null);
		this.serviceHandler2.getGameConsumer().setMessageListener(null);

		serviceHandler1.unregister("tim");
		serviceHandler2.unregister("björn");
	}

	// @Test
	public void testRequestGameMessage() throws Exception {
		this.latchGame1 = new CountDownLatch(1);
		this.serviceHandler1.requestGameMessage();
		this.latchGame1.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messageGame1 instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame1).getObject() instanceof GameGuiData);
	}

	// @Test
	public void testTakeCardFromDrawPile() throws Exception {
		// throws NotYourTurnException, CardAlreadyTakenInThisTurnException;

		// TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder
		// das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser
		// Test funktioniert
		this.latchPlayer1 = new CountDownLatch(1);
		this.latchGame1 = new CountDownLatch(1);
		this.serviceHandler1.takeCardFromPullstack();
		this.latchPlayer1.await(30, TimeUnit.SECONDS);
		this.latchGame1.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer1 instanceof ObjectMessage);
		Assert.assertTrue(messageGame1 instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame1).getObject() instanceof GameGuiData);
	}

	// @Test
	public void testTakeCardFromDiscardPile() throws Exception {
		// throws NotYourTurnException, CardAlreadyTakenInThisTurnException;

		// TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder
		// das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser
		// Test funktioniert
		this.latchPlayer1 = new CountDownLatch(1);
		this.latchGame1 = new CountDownLatch(1);
		this.serviceHandler1.takeCardFromLiFoStack();
		this.latchPlayer1.await(30, TimeUnit.SECONDS);
		this.latchGame1.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer1 instanceof ObjectMessage);
		Assert.assertTrue(messageGame1 instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame1).getObject() instanceof GameGuiData);
	}

	// @Test
	public void testAddToOpenPile() throws Exception {
		// throws NotYourTurnException, CardCannotBeAddedException,
		// PhaseNotCompletedException;

		// TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder
		// das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser
		// Test funktioniert
		Card card = new Card(Color.BLUE, CardValue.ONE);
		DockPile dockPile = new ColorDockPile(Color.BLUE);

		this.latchPlayer1 = new CountDownLatch(1);
		this.latchGame1 = new CountDownLatch(1);
		this.serviceHandler1.addToPileOnTable(card.getId(), dockPile.getId(), false);
		this.latchPlayer1.await(30, TimeUnit.SECONDS);
		this.latchGame1.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer1 instanceof ObjectMessage);
		Assert.assertTrue(messageGame1 instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame1).getObject() instanceof GameGuiData);
	}

	// @Test
	public void testGoOut() throws Exception {
		// throws NotYourTurnException, InvalidCardCompilationException;
		// TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder
		// das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser
		// Test funktioniert

		Collection<DockPile> piles = new ArrayList<>();

		DockPile cards = new SetDockPile(CardValue.ONE);
		cards.addLast(new Card(Color.BLUE, CardValue.ONE));
		cards.addLast(new Card(Color.BLUE, CardValue.ONE));
		cards.addLast(new Card(Color.BLUE, CardValue.ONE));

		piles.add(cards);

		this.latchPlayer1 = new CountDownLatch(1);
		this.latchGame1 = new CountDownLatch(1);
		this.serviceHandler1.layPhaseToTable(piles);
		this.latchPlayer1.await(30, TimeUnit.SECONDS);
		this.latchGame1.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer1 instanceof ObjectMessage);
		Assert.assertTrue(messageGame1 instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame1).getObject() instanceof GameGuiData);
	}

	// @Test
	public void testDiscardCardToDiscardPile() throws Exception {
		// throws NotYourTurnException, TakeCardBeforeDiscardingException;
		// TODO - BM - 31.12.2017 - Die GameValidationBean muss weggemockt werden oder
		// das komplette Spiel muss mit allen Spielern aufgebaut werden, damit dieser
		// Test funktioniert

		Card card = new Card(Color.BLUE, CardValue.ONE);

		this.latchPlayer1 = new CountDownLatch(1);
		this.latchGame1 = new CountDownLatch(1);
		this.serviceHandler1.layCardToLiFoStack(card.getId());
		this.latchPlayer1.await(30, TimeUnit.SECONDS);
		this.latchGame1.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer1 instanceof ObjectMessage);
		Assert.assertTrue(messageGame1 instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame1).getObject() instanceof GameGuiData);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws UsernameAlreadyTakenException
	 * @throws NoFreeSlotException
	 * @throws PlayerDoesNotExistsException
	 * @throws NotLoggedInException
	 * @throws NotEnoughPlayerException
	 * @throws InterruptedException
	 * @throws JMSException
	 */
	@Test(timeout = 3000)
	public void testInitializeGame()
			throws UsernameAlreadyTakenException, NoFreeSlotException, PlayerDoesNotExistsException,
			NotLoggedInException, NotEnoughPlayerException, InterruptedException, JMSException {
		Assert.assertEquals(10, serviceHandler1.getCards().size());
		Assert.assertEquals(10, serviceHandler2.getCards().size());
		GameGuiData gameGuiData1 = ((GameGuiData) ((ObjectMessage) messageGame1).getObject());
		GameGuiData gameGuiData2 = ((GameGuiData) ((ObjectMessage) messageGame2).getObject());
		Assert.assertEquals(0, gameGuiData1.getOpenPiles().size());
		Assert.assertTrue(gameGuiDataEquals(gameGuiData1, gameGuiData2));
		Assert.assertTrue(gameGuiData1.getLiFoStackTop() != null);
		Assert.assertEquals(2, gameGuiData1.getPlayers().size());
	}

	private boolean gameGuiDataEquals(GameGuiData gameGuiData1, GameGuiData gameGuiData2) {
		// Diese Methode ist nicht wirklich gleichheit. Wenn gleichheit getestet
		// werden muss, muss diese methode noch verändert werden
		// - Cardvalue und Color-checks in String equals umgewandelt. (Sebastian Seitz)
		if (gameGuiData1.getLiFoStackTop().getCardValue().toString().equals(gameGuiData2.getLiFoStackTop().getCardValue().toString())
				&& gameGuiData1.getLiFoStackTop().getColor().toString().equals(gameGuiData2.getLiFoStackTop().getColor().toString()) 
				&& gameGuiData1.getOpenPiles().size() == gameGuiData2.getOpenPiles().size()
				&& gameGuiData1.getPlayers().size() == gameGuiData2.getPlayers().size()
				&& gameGuiData1.getSpectators().size() == gameGuiData2.getSpectators().size()) {
			return true;
		} else {
			return false;
		}
	}
}
