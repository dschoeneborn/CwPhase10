package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ApplicationComposer.class)
public class ServerStubTest {
	@EJB
	ServerStub serverStub;

    @Module // here a single bean
    public StatelessBean app() throws Exception {
        final StatelessBean bean = new StatelessBean(ServerStub.class);
        bean.setLocalBean(new Empty());
        return bean;
    }

	@Test
    public void testHelloWorld() throws Exception {
		//StubLocal serverStub = (StubLocal) context.lookup("java:global/classes/ServerStub!de.fh_dortmund.inf.cw.phaseten.server.shared.StubLocal");
		Assert.assertEquals("Hello World from GlassFish Server", serverStub.helloWorld());
    }

}
