package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class LobbyManagmentTest implements MessageListener {
	private ServiceHandlerImpl serviceHandler;
	private CountDownLatch latch;
	private Message message;

	@Before
	public void setUp() throws Exception {
		this.latch = new CountDownLatch(1);
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.lobbyConsumer.setMessageListener(this);
	}

	@Test
	public void testRequestLobbyMessage() throws Exception {
		this.serviceHandler.requestLobbyMessage();
		this.latch.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(message instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) message).getObject() instanceof Lobby);
	}

	@Test
	public void testEnterAsPlayer() throws Exception {
		// throws NoFreeSlotException;
	}

	@Test
	public void testEnterAsSpectator() throws Exception {
		
	}

	@Test
	public void testStartGame() throws Exception {
		// throws NotEnoughPlayerException;
	}
	
	public void onMessage(Message message) {
		this.message = message;
		latch.countDown();
	}
}
