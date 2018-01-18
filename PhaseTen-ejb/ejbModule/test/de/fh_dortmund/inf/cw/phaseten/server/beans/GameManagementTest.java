package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fh_dortmund.inf.cw.phaseten.server.entities.TestType;

/**
 * @author Marc Mettke
 */
@RunWith(ApplicationComposer.class)
public class GameManagementTest {
	@EJB
	GameManagementBean gameManagment;

	@EJB
	UserSessionBean userSession1;

	@EJB
	UserSessionBean userSession2;

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
		final StatelessBean bean = new StatelessBean(GameManagementBean.class);
		bean.setLocalBean(new Empty());
		return bean;
	}

	@Test
	public void testSendGameMessage() throws Exception
	{

	}
}
