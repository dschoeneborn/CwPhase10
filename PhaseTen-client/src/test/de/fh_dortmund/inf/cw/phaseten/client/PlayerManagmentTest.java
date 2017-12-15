package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;

/**
 * @author Marc Mettke
 */
public class PlayerManagmentTest implements MessageListener {
	private ServiceHandlerImpl serviceHandler;
	private CountDownLatch latch;
	private Message message;

	@Before
	public void setUp() throws Exception {
		this.latch = new CountDownLatch(1);
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.playerConsumer.setMessageListener(this);
	}

	@Test
	public void testRequestPlayerMessage() throws Exception {
		this.serviceHandler.requestPlayerMessage();
		this.latch.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(message instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) message).getObject() instanceof CurrentPlayer);
	}

	@Test
	public void register() throws Exception {
		// throws UsernameAlreadyTakenException;
	}

	@Test
	public void login() throws Exception {
		// throws UserDoesNotExistException;
	}
	
	public void onMessage(Message message) {
		this.message = message;
		latch.countDown();
	}
}
