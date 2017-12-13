package de.fh_dortmund.inf.cw.phaseten.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandlerImpl;

/**
 * 
 * @author Marc, Daniela
 *
 */
public class ServiceHandlerTest {
	private ServiceHandlerImpl serviceHandler;

	@Before
	public void setUp() throws Exception {
		this.serviceHandler = ServiceHandlerImpl.getInstance();
	}

	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals("Hello World from GlassFish Server. Entries: 1", this.serviceHandler.helloWorld());
	}
}
