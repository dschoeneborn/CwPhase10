package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fh_dortmund.inf.cw.phaseten.server.shared.StubLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

@Stateless
public class ServerStub implements StubRemote, StubLocal {
    @PersistenceContext(unitName = "PhaseTenDB")
    private EntityManager entityManager;
    
	@Override
	public String helloWorld() {
		Query query = entityManager.createQuery("SELECT t from TestType as t");
		return "Hello World from GlassFish Server. Entries: " + query.getResultList().size();
	}
}
