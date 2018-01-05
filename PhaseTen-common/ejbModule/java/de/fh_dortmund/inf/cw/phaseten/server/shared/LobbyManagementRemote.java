package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Remote
public interface LobbyManagementRemote extends LobbyManagement {
	void requestLobbyMessage();	
	void enterLobby(Player player) throws NoFreeSlotException;
	void enterLobby(Spectator spectator);
	Collection<PlayerGuiData> getPlayersForGui();
	Collection<String> getSpectatorNamesForGui();
}
