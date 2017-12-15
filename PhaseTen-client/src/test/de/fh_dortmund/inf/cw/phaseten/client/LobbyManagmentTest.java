package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class LobbyManagmentTest {
	private ServiceHandlerImpl serviceHandler;
	
	private CountDownLatch latchLobby;
	private CountDownLatch latchGame;
	private Message messageLobby;
	private Message messageGame;

	@Before
	public void setUp() throws Exception {
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.lobbyConsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messageLobby = message;
				latchLobby.countDown();
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
		this.serviceHandler.lobbyConsumer.setMessageListener(null);	
		this.serviceHandler.gameConsumer.setMessageListener(null);	
	}

	@Test
	public void testRequestLobbyMessage() throws Exception {
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.requestLobbyMessage();
		this.latchLobby.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageLobby).getObject() instanceof Lobby);
	}

	@Test
	public void testEnterAsPlayer() throws Exception {
		// throws NoFreeSlotException;
		
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.enterAsPlayer();
		this.latchLobby.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageLobby).getObject() instanceof Lobby);
	}

	@Test
	public void testEnterAsSpectator() throws Exception {
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.enterAsSpectator();
		this.latchLobby.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageLobby).getObject() instanceof Lobby);
	}

	@Test
	public void testStartGame() throws Exception {
		// throws NotEnoughPlayerException;
		
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.startGame();
		this.latchGame.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof Game);
	}
}
