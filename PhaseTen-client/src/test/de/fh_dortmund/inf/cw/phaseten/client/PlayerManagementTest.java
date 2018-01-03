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

import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class PlayerManagementTest {
	private ServiceHandlerImpl serviceHandler;

	private CountDownLatch latchPlayer;
	private CountDownLatch latchLobby;
	private Message messagePlayer;
	private Message messageLobby;

	@Before
	public void setUp() throws Exception {
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.getPlayerConsumer().setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messagePlayer = message;
				latchPlayer.countDown();
			}
		});
		this.serviceHandler.getLobbyConsumer().setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				messageLobby = message;
				latchLobby.countDown();
			}
		});
	}
	
	@After
	public void tearDown() throws Exception {
		this.serviceHandler.getPlayerConsumer().setMessageListener(null);
		this.serviceHandler.getLobbyConsumer().setMessageListener(null);		
	}

	@Test
	public void testRequestPlayerMessage() throws Exception {
		this.latchPlayer = new CountDownLatch(1);
		this.serviceHandler.requestPlayerMessage();
		this.latchPlayer.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
	}

	@Test
	public void register() throws Exception {
		// throws UsernameAlreadyTakenException;
		
		String username = "testUser";
		String password = "testPassword";
		
		this.latchPlayer = new CountDownLatch(1);
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.register(username, password);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchLobby.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageLobby).getObject() instanceof Lobby);
	}

	@Test
	public void login() throws Exception {
		// throws UserDoesNotExistException;
		
		String username = "testUser";
		String password = "testPassword";
		
		this.latchPlayer = new CountDownLatch(1);
		this.latchLobby = new CountDownLatch(1);
		this.serviceHandler.login(username, password);
		this.latchPlayer.await(30, TimeUnit.SECONDS);
		this.latchLobby.await(30, TimeUnit.SECONDS);

		Assert.assertTrue(messagePlayer instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messagePlayer).getObject() instanceof CurrentPlayer);
		Assert.assertTrue(messageLobby instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) messageLobby).getObject() instanceof Lobby);
	}
}
