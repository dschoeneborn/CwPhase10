package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

/**
 * @author Marc Mettke
 */
@Remote
public interface LobbyManagmentRemote extends LobbyManagment {
	void requestLobbyMessage();
}