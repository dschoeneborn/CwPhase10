package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;

/**
 * @author Marc Mettke
 */
public class GameManagmentTest implements MessageListener {
	private ServiceHandlerImpl serviceHandler;
	private CountDownLatch latch;
	private Message message;

	@Before
	public void setUp() throws Exception {
		this.latch = new CountDownLatch(1);
		this.serviceHandler = ServiceHandlerImpl.getInstance();
		this.serviceHandler.gameConsumer.setMessageListener(this);
	}

	@Test
	public void testRequestGameMessage() throws Exception {
		this.serviceHandler.requestGameMessage();
		this.latch.await(30, TimeUnit.SECONDS);
		
		Assert.assertTrue(message instanceof ObjectMessage);
		Assert.assertTrue(((ObjectMessage) message).getObject() instanceof Game);
	}

	public void onMessage(Message message) {
		this.message = message;
		latch.countDown();
	}
}
