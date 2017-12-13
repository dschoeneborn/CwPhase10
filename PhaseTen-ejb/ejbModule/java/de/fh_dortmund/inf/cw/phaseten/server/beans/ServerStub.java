package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fh_dortmund.inf.cw.phaseten.server.shared.StubLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.entities.TestType;

/**
 * 
 * @author Marc, Daniela
 *
 */
@Stateless
public class ServerStub implements StubRemote, StubLocal {
	@PersistenceContext(unitName = "PhaseTenDB")
	private EntityManager entityManager;

	@Override
	public String helloWorld() {
		Query query = entityManager.createQuery("SELECT t from TestType as t");
		if (query.getResultList().size() == 0) {
			entityManager.persist(new TestType());
			entityManager.flush();
		}
		return "Hello World from GlassFish Server. Entries: " + query.getResultList().size();
	}
}
