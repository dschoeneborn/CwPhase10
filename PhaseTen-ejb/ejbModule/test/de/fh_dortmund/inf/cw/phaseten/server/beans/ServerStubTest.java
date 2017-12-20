package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fh_dortmund.inf.cw.phaseten.server.entities.TestType;

/**
 * @author Marc Mettke
 * @author Daniela
 */
@RunWith(ApplicationComposer.class)
public class ServerStubTest {
	@EJB
	ServerStub serverStub;

	@Module
	public PersistenceUnit persistence() {
		PersistenceUnit unit = new PersistenceUnit("PhaseTenDB");
		unit.setJtaDataSource("testTypeDatabase");
		unit.setNonJtaDataSource("testTypeDatabaseUnmanaged");
		unit.getClazz().add(TestType.class.getName());
		unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
		return unit;
	}

	@Module
	public StatelessBean app() throws Exception {
		final StatelessBean bean = new StatelessBean(ServerStub.class);
		bean.setLocalBean(new Empty());
		return bean;
	}

	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals("Hello World from GlassFish Server. Entries: 1", serverStub.helloWorld());
	}

}
