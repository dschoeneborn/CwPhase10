package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Remote
public interface LobbyManagementRemote extends LobbyManagement {
	void requestLobbyMessage();
	void enterLobby(Lobby lobby, Player player) throws NoFreeSlotException;
	void enterLobby(long lobbyId, Player player) throws NoFreeSlotException;
	void enterOrCreateNewLobby(Player player) throws NoFreeSlotException;
	void enterLobby(Lobby lobby, Spectator spectator);
	void enterLobby(long lobbyId, Spectator orCreateCurrentSpectator);
}
