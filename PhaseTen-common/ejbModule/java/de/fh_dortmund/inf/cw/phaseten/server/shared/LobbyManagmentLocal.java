package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;

/**
 * @author Marc Mettke
 */
@Local
public interface LobbyManagmentLocal extends LobbyManagment {
	public void sendLobbyMessage();

	void sendLobbyMessage(Lobby lobby);

	void deleteEmptyLobbies();
}
