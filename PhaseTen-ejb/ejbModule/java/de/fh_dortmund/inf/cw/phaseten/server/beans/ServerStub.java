package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.phaseten.server.shared.StubLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

@Stateless
public class ServerStub implements StubRemote, StubLocal {
	@Override
	public String helloWorld() {
		return "Hello World from GlassFish Server";
	}
}
