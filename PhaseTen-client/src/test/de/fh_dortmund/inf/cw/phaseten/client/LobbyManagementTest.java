package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class LobbyManagementTest {
	private ServiceHandlerImpl serviceHandler;
	
	private CountDownLatch latchLobby;
	private CountDownLatch latchGame;
	private Message messageLobby;
	private Message messageGame;

	@Before
	public void setUp() throws Exception {
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.getLobbyConsumer().setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messageLobby = message;
				latchLobby.countDown();
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
		this.serviceHandler.getLobbyConsumer().setMessageListener(null);	
		this.serviceHandler.getGameConsumer().setMessageListener(null);	
	}

	//@Test
	public void testRequestLobbyMessage() throws Exception {
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.requestLobbyMessage();
		this.latchLobby.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
	}

	//@Test
	public void testEnterAsPlayer() throws Exception {
		// throws NoFreeSlotException;
		
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.enterLobbyAsPlayer();
		this.latchLobby.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
	}

	//@Test
	public void testEnterAsSpectator() throws Exception {
		//TODO - BM - 03.01.2018 - Dieser Test funktioniert hier nicht
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.enterLobbyAsPlayer();
		this.latchLobby.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
	}

	//@Test
	public void testStartGame() throws Exception {
		
		this.latchGame = new CountDownLatch(1);
		this.serviceHandler.startGame();
		this.latchGame.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(messageGame instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageGame).getObject() instanceof GameGuiData);
	}
}
