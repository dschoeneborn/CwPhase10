package de.fh_dortmund.inf.cw.phaseten.client;

import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

/**
 * @author Marc Mettke
 */
public interface ServiceHandler extends StubRemote, 
										PlayerManagmentRemote, 
										LobbyManagmentRemote,
										GameManagmentRemote,
										MessageListener {
}
